package com.example.crypto.api.controller;

import com.example.crypto.api.model.EncryptRequest;
import com.example.crypto.api.model.EncryptResponse;
import com.example.crypto.api.model.EncryptedData;
import com.example.crypto.api.service.EncryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/crypto/api")
@Validated
@RequiredArgsConstructor
public class EncryptController {

    private final EncryptService encryptService;

    @PostMapping(value = "/encrypt", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EncryptResponse> encrypt(
            @RequestBody @Valid EncryptRequest request) throws Exception {
        // implementation to encrypt

        EncryptedData encrypted = encryptService.encrypt(request.getCipherAlgorithm(), request.getPlainText(), request.getPassword());
        EncryptResponse response = new EncryptResponse();
        response.setEncrypted(encrypted.getEncryptedText());
        response.setPassword(request.getPassword());
        response.setCipherAlgorithm(request.getCipherAlgorithm());
        response.setIv(encrypted.getIv());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}