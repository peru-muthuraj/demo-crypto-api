package com.example.crypto.api.service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DecryptServiceTest {

    private DecryptService decryptService;
    Cipher cipher;
    ReusableCipher reusableCipher;

    @Before
    public void setup() throws Exception {
        reusableCipher = new ReusableCipher();
        decryptService = new DecryptService();

    }

    @Test
    public void testDecrypt() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //Given
        String ALGORITHM = "AES/CBC/PKCS5Padding";
        String PASSWORD = "passwordpassword";
        String IV = "S6+XkSIM2T5KdKS72E0iAw==";

        cipher = reusableCipher.getCipher(ALGORITHM, PASSWORD, IV);

        String cipherText = "6+ZOKgw5sblUlV456zx0WKjW9RIgzSJpV0IV6RwljM0=";
        String expectedPlainText = "This is a test message.";

        //When
        String actualPlainText = decryptService.decrypt(cipher,cipherText);

        //Then
        Assert.assertEquals(expectedPlainText, actualPlainText);
    }

}
