package com.company.wompi.screenplay.clients;

import com.company.wompi.config.EnvConfig;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;

/**
 * Cliente REST para interactuar con la API de Wompi.
 * Usa la configuración centralizada en el singleton EnvConfig.
 */
public class WompiClient {

    private final EnvConfig config;
    private final String baseUrl;
    private final String publicKey;
    private final String privateKey;

    public WompiClient(boolean useSandbox) {
        this.config = EnvConfig.getInstance();
        this.baseUrl = useSandbox ? config.getSandboxUrl() : config.getProdUrl();
        this.publicKey = config.getPublicKey();
        this.privateKey = config.getPrivateKey();

        // Configuración global de RestAssured
        RestAssured.baseURI = baseUrl;
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 10000)
                .setParam("http.socket.timeout", 10000));
    }

    /**
     * Crea una transacción PSE o de otro tipo.
     *
     * @param payloadJson cuerpo de la solicitud en formato JSON
     * @return Response con la información de la transacción creada
     */
    public Response createTransaction(String payloadJson) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + publicKey)
                .body(payloadJson)
                .when()
                .post("/transactions")
                .then()
                .extract()
                .response();
    }

    /**
     * Obtiene el detalle de una transacción por ID.
     *
     * @param transactionId ID de la transacción
     * @return Response con el detalle
     */
    public Response getTransaction(String transactionId) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + publicKey)
                .when()
                .get("/transactions/" + transactionId)
                .then()
                .extract()
                .response();
    }

    /**
     * Devuelve información del merchant asociado a la public key.
     *
     * @return Response con la información del merchant
     */
    public Response getMerchantInfo() {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .when()
                .get("/merchants/" + publicKey)
                .then()
                .extract()
                .response();
    }

    // Getters útiles
    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
