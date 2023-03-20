package com.example.crypto.api.model;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
public class EncryptResponse {

    @NotNull(message = "CipherAlgorithm cannot be null")
    private String cipherAlgorithm;
    @NotNull(message = "encrypted cannot be null")
    private String encrypted;
    @NotNull(message = "password cannot be null")
    private String password;
    @Nullable
    private String iv;
}
