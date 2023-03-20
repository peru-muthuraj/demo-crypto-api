package com.example.crypto.api.service;

import com.example.crypto.api.exception.FailedToDecryptException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class DecryptService {

    public String decrypt(Cipher cipher, String cipherText) throws IllegalBlockSizeException, BadPaddingException {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new FailedToDecryptException(ex);
        }

    }

}