package com.example.crypto.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncryptedData {
    private final String encryptedText;
    private final String iv;

}