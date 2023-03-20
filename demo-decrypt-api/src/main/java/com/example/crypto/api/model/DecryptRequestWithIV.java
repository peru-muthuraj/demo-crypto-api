package com.example.crypto.api.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DecryptRequestWithIV {
    @NotNull(message = "CipherAlgorithm cannot be null")
    private String cipherAlgorithm;
    @NotNull(message = "Cipher text cannot be null")
    private String cipherText;
    @NotNull(message = "Password text cannot be null")
    private String password;
    @NotNull(message = "Hashing Algorithm cannot be null")
    private String hashingAlgorithm;
    @NotNull(message = "Initialization Vector cannot be null")
    private String iv;
}
