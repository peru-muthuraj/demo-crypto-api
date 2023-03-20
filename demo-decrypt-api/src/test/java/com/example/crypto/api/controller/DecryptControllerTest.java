package com.example.crypto.api.controller;

import com.example.crypto.api.model.DecryptRequest;
import com.example.crypto.api.model.DecryptRequestWithIV;
import com.example.crypto.api.model.DecryptResponse;
import com.example.crypto.api.service.DecryptService;
import com.example.crypto.api.service.HashService;
import com.example.crypto.api.service.ReusableCipher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.crypto.Cipher;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DecryptControllerTest {

    @Mock
    private DecryptService decryptService;

    @Mock
    private ReusableCipher reusableCipher;

    @Mock
    private HashService hashService;

    @InjectMocks
    private DecryptController decryptController;


    @Autowired
    private DecryptResponse decryptResponse;

    private MockMvc mockMvc;

    public DecryptControllerTest() {
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(decryptController).build();
    }

    @Test
    public void testDecryptControllerDecrypt_AESAlgorithm() throws Exception {
        String cipherText = "8mx5YmL7Cy6Gh7bSAdAxZhXF63OgBaI7sT11J8T4bhU=";
        String password = "passwordpassword";
        String hashingAlgorithm = "SHA-512";
        String decryptedText = "This is a test message.";
        String hashedText = "wU94dhHK3Foaw6nlhlHJElTxRHA29fnVuYEDD0ctfwGyhy8bN3VmATDVdduFJZOVPsbVa36n2uIWNV/k4nlEcg==";

        DecryptRequest request = new DecryptRequest();
        request.setCipherAlgorithm("AES");
        request.setCipherText(cipherText);
        request.setPassword(password);
        request.setHashingAlgorithm(hashingAlgorithm);

        Cipher cipher = Cipher.getInstance(request.getCipherAlgorithm());
        doReturn(cipher).when(reusableCipher).getCipher(request.getCipherAlgorithm(), request.getPassword());

        doReturn(decryptedText).when(decryptService).decrypt(cipher, request.getCipherText());
        doReturn(hashedText).when(hashService).hash(hashingAlgorithm, decryptedText);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/crypto/api/decrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"hashedText\":\"" + hashedText + "\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testDecryptControllerDecrypt_DESAlgorithm() throws Exception {
        //Given
        String cipherText = "qlW1WyuCiMCZOyVFhI36NVTPEnJrKNHE";
        String password = "password";
        String hashingAlgorithm = "SHA-224";
        String decryptedText = "This is a test message.";
        String hashedText = "QB19lReAs5VStaujXfXaE+wR0LbOrtqK3pEEqA==";

        DecryptRequest request = new DecryptRequest();
        request.setCipherAlgorithm("DES");
        request.setCipherText(cipherText);
        request.setPassword(password);
        request.setHashingAlgorithm(hashingAlgorithm);

        Cipher cipher = Cipher.getInstance(request.getCipherAlgorithm());
        doReturn(cipher).when(reusableCipher).getCipher(request.getCipherAlgorithm(), request.getPassword());

        doReturn(decryptedText).when(decryptService).decrypt(cipher, request.getCipherText());
        doReturn(hashedText).when(hashService).hash(hashingAlgorithm, decryptedText);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/crypto/api/decrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));


        //When
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"hashedText\":\"" + hashedText + "\"}";

        //Then
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testDecryptControllerDecrypt_BlowFishAlgorithm() throws Exception {
        //Given
        String cipherText = "2zA6vZJZWDuldNqMkH1ZMV6KYCtq6n8h";
        String password = "passwordpassword";
        String hashingAlgorithm = "SHA-224";
        String decryptedText = "This is a test message.";
        String hashedText = "QB19lReAs5VStaujXfXaE+wR0LbOrtqK3pEEqA==";

        DecryptRequest request = new DecryptRequest();
        request.setCipherAlgorithm("DES");
        request.setCipherText(cipherText);
        request.setPassword(password);
        request.setHashingAlgorithm(hashingAlgorithm);

        Cipher cipher = Cipher.getInstance(request.getCipherAlgorithm());
        doReturn(cipher).when(reusableCipher).getCipher(request.getCipherAlgorithm(), request.getPassword());

        doReturn(decryptedText).when(decryptService).decrypt(cipher, request.getCipherText());
        doReturn(hashedText).when(hashService).hash(hashingAlgorithm, decryptedText);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/crypto/api/decrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));


        //When
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"hashedText\":\"" + hashedText + "\"}";

        //Then
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testDecryptControllerDecryptWithIv() throws Exception {
        //Given
        String cipherText = "6+ZOKgw5sblUlV456zx0WKjW9RIgzSJpV0IV6RwljM0=";
        String password = "passwordpassword";
        String hashingAlgorithm = "SHA-256";
        String decryptedText = "This is a test message.";
        String hashedText = "Bmi1Fb/EG5C2qQpq6GACVuHHamfRfHiiYSfd65syRDU=";
        String iv = "S6+XkSIM2T5KdKS72E0iAw==";

        DecryptRequestWithIV request = new DecryptRequestWithIV();
        request.setCipherAlgorithm("AES/CBC/PKCS5Padding");
        request.setCipherText(cipherText);
        request.setPassword(password);
        request.setHashingAlgorithm(hashingAlgorithm);
        request.setIv(iv);

        Cipher cipher = Cipher.getInstance(request.getCipherAlgorithm());
        doReturn(cipher).when(reusableCipher).getCipher(request.getCipherAlgorithm(), request.getPassword(), request.getIv());

        doReturn(decryptedText).when(decryptService).decrypt(cipher, request.getCipherText());
        doReturn(hashedText).when(hashService).hash(hashingAlgorithm, decryptedText);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/crypto/api/decrypt-with-iv")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));


        //When
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"hashedText\":\"" + hashedText + "\"}";

        //Then
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

    @Test
    public void testDecryptWithInvalidAlgorithm() throws Exception {
        // Create the request object with an invalid algorithm
        DecryptRequest request = new DecryptRequest();
        request.setCipherAlgorithm("InvalidAlgorithm");
        request.setCipherText("Some cipher text");

        // Make the request and verify the response
        mockMvc.perform(post("/crypto/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDecryptWithInvalidPassword() throws Exception {
        // Set up test data
        String cipherText = "Invalid cipher text";
        String password = "pass";
        Cipher cipher = Cipher.getInstance("AES");

        // Create the request object
        DecryptRequest request = new DecryptRequest();
        request.setCipherAlgorithm("AES");
        request.setCipherText(cipherText);
        request.setPassword(password);

        // Make the request and verify the response
        mockMvc.perform(post("/crypto/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
