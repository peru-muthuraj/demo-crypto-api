package com.example.crypto.api.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class HashService {
    public String hash(String algorithm, String decryptedText) throws NoSuchAlgorithmException {
        // Hash the decrypted text using the given algorithm
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashedBytes = digest.digest(decryptedText.getBytes(StandardCharsets.UTF_8));
        String hashedText = Base64.getEncoder().encodeToString(hashedBytes);

        return hashedText;
    }
}
