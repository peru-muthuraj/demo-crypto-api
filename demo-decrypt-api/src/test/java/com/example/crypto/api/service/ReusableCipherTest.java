package com.example.crypto.api.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertNotNull;

public class ReusableCipherTest {

    private ReusableCipher reusableCipher;

    @Before
    public void setUp() {
        reusableCipher = new ReusableCipher();
    }

    @After
    public void tearDown() {
        reusableCipher.close();
    }

    @Test
    public void testGetCipher() throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "AES/CBC/PKCS5Padding";
        String password = "passwordpassword";
        String iv = "eU6jxHk7Rk6ebIKpYX/FfA==";
        Cipher cipher = reusableCipher.getCipher(algorithm, password, iv);
        assertNotNull(cipher);
    }

    @Test
    public void testGetCipherWithoutIV() throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "AES/ECB/PKCS5Padding";
        String password = "passwordpassword";
        Cipher cipher = reusableCipher.getCipher(algorithm, password);
        assertNotNull(cipher);
    }
}
