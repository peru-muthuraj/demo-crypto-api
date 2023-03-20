package com.example.crypto.api.service;

import com.example.crypto.api.model.EncryptedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class EncryptServiceTest {

    private EncryptService encryptService;

    @BeforeEach
    void setUp() {
        encryptService = new EncryptService();
    }

    @Test
    void testEncrypt() throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        // Arrange
        String cipherAlgorithm = "AES/CBC/PKCS5Padding";
        String plainText = "Hello, world!";
        String password = "passwordpassword";

        // Act
        EncryptedData encryptedData = encryptService.encrypt(cipherAlgorithm, plainText, password);

        // Assert
        assertNotNull(encryptedData);
        assertNotNull(encryptedData.getIv());
        assertNotNull(encryptedData.getEncryptedText());

        // Verify that the encrypted data can be decrypted
        byte[] ivBytes = Base64.getDecoder().decode(encryptedData.getIv());
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData.getEncryptedText());

        //Verify Encrypted value match with the given plain text after decryption
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
        String decryptedText = new String(cipher.doFinal(encryptedBytes));

        assertEquals(plainText, decryptedText);
    }
}
