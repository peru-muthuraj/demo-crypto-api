package com.example.crypto.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
