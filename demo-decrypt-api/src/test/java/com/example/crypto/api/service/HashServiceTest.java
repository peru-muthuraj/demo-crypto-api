package com.example.crypto.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HashServiceTest {

    private HashService hashService;

    @BeforeEach
    void setUp() {
        hashService = new HashService();
    }

    @Test
    public void testHashWithValidAlgorithm() throws NoSuchAlgorithmException {
        String inputText = "test123";
        String expectedHash = "7NcYcNGWMxapfjrDQIyYNa2M8PPBvHA1J8MCZVNPda4=";

        String actualHash = hashService.hash("SHA-256", inputText);

        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testHashWithInvalidAlgorithm() {
        String inputText = "test123";
        String invalidAlgorithm = "invalidAlgorithm";

        assertThrows(NoSuchAlgorithmException.class, () -> hashService.hash(invalidAlgorithm, inputText));
    }
}
