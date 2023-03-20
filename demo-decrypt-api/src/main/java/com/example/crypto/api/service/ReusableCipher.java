package com.example.crypto.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ReusableCipher {

    private final Map<String, CipherData> cipherMap;
    private final ScheduledExecutorService scheduler;

    long TEN_MINUTES = 10 * 60 * 1000;

    public ReusableCipher() {
        this.cipherMap = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public Cipher getCipher(String algorithm, String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return getCipher(algorithm, password, null);
    }

    public Cipher getCipher(String algorithm, String password, String iv) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        // Check if the cipher object with given algorithm, password and iv combination is already present in the map
        String key = getKey(algorithm, password, iv);
        CipherData cipherData = cipherMap.get(key);

        // Generate a new cipher object and add it to the map
        if (cipherData == null || cipherData.isExpired()) {
            Cipher cipher = getCipherObject(algorithm, password, iv);
            CipherData newCipherData = new CipherData(cipher);
            cipherMap.put(key, newCipherData);

            //schedule the expiry of a cipher object to be removed after 10 minutes
            scheduleExpiry(key);
            return newCipherData.getCipher();
        } else {
            log.info("Returning existing cipher");
            //If cipher object is present in the map and is not expired, return the same cipher object and reset the time
            cipherData.resetExpiry();
            return cipherData.getCipher();
        }
    }

    //If the iv is available then return decryption cipher with iv
    private Cipher getCipherObject(String algorithm, String password, String iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKeySpec secretKeySpec = getSecretKeySpec(password, algorithm);
        if (iv != null) {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            log.info("Set Decrypt Cipher with IV");
        } else {
            log.info("Set Decrypt Cipher with no IV");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        }
        return cipher;
    }

    private SecretKeySpec getSecretKeySpec(String password, String algorithm) {
        return new SecretKeySpec(password.getBytes(), algorithm.contains("/") ? algorithm.split("/")[0] : algorithm);
    }

    private String getKey(String algorithm, String password, String iv) {
        return iv == null ? algorithm + ":" + password : algorithm + ":" + password + ":" + iv;
    }

    private void scheduleExpiry(String key) {
        scheduler.schedule(() -> cipherMap.remove(key), TEN_MINUTES, TimeUnit.MILLISECONDS);
    }

    public void close() {
        scheduler.shutdown();
    }

    private class CipherData {
        private final Cipher cipher;
        private volatile long expiryTime;

        public CipherData(Cipher cipher) {
            this.cipher = cipher;
            this.resetExpiry();
        }

        public Cipher getCipher() {
            return cipher;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() >= expiryTime;
        }

        public void resetExpiry() {
            expiryTime = System.currentTimeMillis() + (10 * 60 * 1000);
        }
    }
}
