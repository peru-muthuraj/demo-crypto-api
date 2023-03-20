package com.example.crypto.api.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DecryptServiceTest {

    Cipher cipher;
    ReusableCipher reusableCipher;
    private DecryptService decryptService;

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
        String actualPlainText = decryptService.decrypt(cipher, cipherText);

        //Then
        Assert.assertEquals(expectedPlainText, actualPlainText);
    }


    @Test
    public void testDecryptRC4() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //Given
        String ALGORITHM = "RC4";
        String PASSWORD = "passwordpasswordpassword";

        cipher = reusableCipher.getCipher(ALGORITHM, PASSWORD);

        String cipherText = "q51RfWxXx44Acd8ZQT+PC7hy1ago7gg=";
        String expectedPlainText = "This is a test message.";

        //When
        String actualPlainText = decryptService.decrypt(cipher, cipherText);

        //Then
        Assert.assertEquals(expectedPlainText, actualPlainText);
    }

    @Test
    public void testDecryptRC2() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //Given
        String ALGORITHM = "RC2";
        String PASSWORD = "passwordpasswordpassword";

        cipher = reusableCipher.getCipher(ALGORITHM, PASSWORD);

        String cipherText = "ZgPsh4pSzkPv27KSwXCp3hnjNoQ0dj/H";
        String expectedPlainText = "This is a test message.";

        //When
        String actualPlainText = decryptService.decrypt(cipher, cipherText);

        //Then
        Assert.assertEquals(expectedPlainText, actualPlainText);
    }
}
