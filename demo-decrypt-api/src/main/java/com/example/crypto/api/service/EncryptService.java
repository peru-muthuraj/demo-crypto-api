package com.example.crypto.api.service;

import com.example.crypto.api.exception.FailedToEncryptException;
import com.example.crypto.api.model.EncryptedData;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptService {

    public EncryptedData encrypt(String cipherAlgorithm, String plainText, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            //Extracting algorithm mode to get the Initialization vector for no ECB mode.
            String[] algorithmAndMode = extractAlgorithmAndMode(cipherAlgorithm);
            String algorithm = algorithmAndMode[0];

            SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), algorithm);
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            String iv = null;
            if (algorithmAndMode[1] != null && !algorithmAndMode[1].equals("ECB")) {
                byte[] ivByte = cipher.getIV();
                iv = Base64.getEncoder().encodeToString(ivByte);
            }
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            return new EncryptedData(encryptedText, iv);
        } catch (Exception ex) {
            throw new FailedToEncryptException(ex);
        }
    }

    private String[] extractAlgorithmAndMode(String cipherAlgorithm) {
        String[] parts = new String[2];
        if (cipherAlgorithm.contains("/")) {
            parts = cipherAlgorithm.split("/");
        } else {
            parts[0] = cipherAlgorithm;
        }
        return parts;
    }

}