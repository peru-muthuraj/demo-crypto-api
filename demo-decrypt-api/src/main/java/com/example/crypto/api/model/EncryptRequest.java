package com.example.crypto.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncryptRequest {
    @NotNull(message = "CipherAlgorithm cannot be null")
    private String cipherAlgorithm;
    @NotNull(message = "Plain text cannot be null")
    private String plainText;
    @NotNull(message = "Password text cannot be null")
    private String password;
}
