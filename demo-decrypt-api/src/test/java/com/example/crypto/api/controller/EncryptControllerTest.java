package com.example.crypto.api.controller;

import com.example.crypto.api.model.EncryptRequest;
import com.example.crypto.api.model.EncryptedData;
import com.example.crypto.api.service.EncryptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EncryptControllerTest {

    @Mock
    private EncryptService encryptService;

    @InjectMocks
    private EncryptController encryptController;

    private MockMvc mockMvc;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(encryptController).build();

    }

    @Test
    public void testEncryptControllerEncrypt() throws Exception {
        String cipherText = "6+ZOKgw5sblUlV456zx0WKjW9RIgzSJpV0IV6RwljM0=";
        String cipherAlgorithm = "AES/CBC/PKCS5Padding";
        String password = "passwordpassword";
        String plainText = "This is a test message.";
        String iv = "S6+XkSIM2T5KdKS72E0iAw==";


        when(encryptService.encrypt(any(String.class), any(String.class), any(String.class))).thenReturn(new EncryptedData(cipherText, iv));

        EncryptRequest request = new EncryptRequest(cipherAlgorithm, plainText, password);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/crypto/api/encrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));


        //When
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"cipherAlgorithm\":\"" + cipherAlgorithm + "\",\"encrypted\":\"" + cipherText + "\",\"password\":\"" + password + "\",\"iv\":\"" + iv + "\"}";

        //Then
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


}




