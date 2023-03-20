# Crypto API
## Symmetric Encryption and Decryption Service

This is a sample project to demonstrate the usage of encryption, decryption, and hashing algorithms.

Prerequisites
```
Java 8 or higher
Maven 3.5 or higher
Spring Boot
JUnit 4
```
Build and Run
To build and run the project, execute the following commands from the project directory:

bash
Copy code
# Build the project
```
mvn clean install
```

# Run the application
```
mvn spring-boot:run

or can be started  the jar using

java -jar target/my-application.jar
The application will start running on http://localhost:8080.
```

Endpoints
The following endpoints are available:

## Encrypt Endpoint

The POST /encrypt endpoint can be used to encrypt data using various encryption algorithms and modes. If the algorithm mode is not specified, Then encryption will be used in ECB mode.

### Endpoint: /encrypt
### Method: POST

### Request Body:
#### Encrypt with Algorithm default with ECB mode (Initialization Vector Not needed)
```
{
"cipherAlgorithm": "AES",
"plainText": "This is a sample plain text.",
"password": "passwordpassword"
}
```

### Response Body:
```
{
"encrypted": "encrypted text",
"password": "password",
"cipherAlgorithm": "AES",
"iv": null
}
```

#### Encrypt with Algorithm with mode other than ECB  (Initialization Vector needed)
```
{
"cipherAlgorithm": "AES/CBC/PKCS5Padding",
"plainText": "This is a sample plain text.",
"password": "passwordpassword"
}
```

### Response Body:
```
{
"encrypted": "encrypted text",
"password": "password",
"cipherAlgorithm": "AES/CBC/PKCS5Padding",
"iv": "iv"
}
```



## Decryption Endpoints
The POST /decrypt endpoint can be used to decrypt data using the same encryption algorithm and mode that was used to encrypt the data. 
Assuming the support algorithm ECB mode. Which decrypt without  Initialization vector. 

Decrypted cipher data will be hashed with given hashing algorithm. 

### Endpoint: /decrypt
### Method: POST

#### Request Body:

```
{
"cipherAlgorithm": "AES/ECB/PKCS5Padding",
"cipherText": "encrypted text",
"password": "password",
"hashingAlgorithm": "SHA-256"
}
```
#### Response Body:
```
{
"hashedText": "hashed text"
}
```
## Decrypt with IV
The POST /decrypt endpoint can be used to decrypt data using the same encryption algorithm and mode that was used to encrypt the data. Non ECB mode
This endpoint require encoded iv in the request parameter for decryption.


### Endpoint: /decrypt-with-iv
### Method: POST
#### Request Body:
```
{
"cipherAlgorithm": "AES/CBC/PKCS5Padding",
"cipherText": "encrypted text",
"password": "password",
"hashingAlgorithm": "SHA-256",
"iv": "iv"
}
```
#### Response Body:
```
{
"hashedText": "hashed text"
}
```

## Note
Reusable cipher is used to improve the performance of cryptographic operations as creating a new cipher instance for every operation can be time-consuming and resource-intensive.
A reusable cipher instance is valid for a fixed amount of time, which can be configured based on the security requirements and performance considerations of the application.

The code implementation
ReusableCipher is a class that provides a way to reuse Cipher objects, instead of creating a new one every time decryption is needed.
The class maintains a map of Cipher objects that have been created previously, using a key that is based on the algorithm, password, and initialization vector (iv) used to create the Cipher object.

