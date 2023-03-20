package com.example.crypto.api.controller;

import com.example.crypto.api.model.DecryptRequest;
import com.example.crypto.api.model.DecryptRequestWithIV;
import com.example.crypto.api.model.DecryptResponse;
import com.example.crypto.api.service.DecryptService;
import com.example.crypto.api.service.HashService;
import com.example.crypto.api.service.ReusableCipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.validation.Valid;

@RestController
@RequestMapping("/crypto/api")
@Validated
@RequiredArgsConstructor
@Slf4j
public class DecryptController {

    private final DecryptService cryptoService;
    private final HashService hashService;
    private final ReusableCipher reusableCipher;


    @PostMapping(value = "/decrypt", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DecryptResponse> decrypt(
            @RequestBody @Valid DecryptRequest request) throws Exception {
        // Get the reusable cipher for the given algorithm and password combination
        Cipher cipher = reusableCipher.getCipher(request.getCipherAlgorithm(), request.getPassword());
        // Decrypt the cipher text using the cipher
        String decryptedText = cryptoService.decrypt(cipher, request.getCipherText());
        log.info("decryptedText::: {}", decryptedText);
        // Hash the decrypted text using the given hash algorithm
        String hashedText = hashService.hash(request.getHashingAlgorithm(), decryptedText);
        DecryptResponse decryptResponse = new DecryptResponse();
        decryptResponse.setHashedText(hashedText);
        // Return the hashed text
        return new ResponseEntity<>(decryptResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/decrypt-with-iv", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DecryptResponse> decryptWithIv(
            @RequestBody @Valid DecryptRequestWithIV request) throws Exception {
        // Get the reusable cipher for the given algorithm, password and the iv(initialization vector) combination
        Cipher cipher = reusableCipher.getCipher(request.getCipherAlgorithm(), request.getPassword(), request.getIv());
        // Decrypt the cipher text using the cipher
        String decryptedText = cryptoService.decrypt(cipher, request.getCipherText());
        log.info("decryptedText::: {}", decryptedText);
        // Hash the decrypted text using the given hash algorithm
        String hashedText = hashService.hash(request.getHashingAlgorithm(), decryptedText);
        DecryptResponse decryptResponse = new DecryptResponse();
        decryptResponse.setHashedText(hashedText);
        // Return the hashed text
        return new ResponseEntity<>(decryptResponse, HttpStatus.OK);
    }
}
