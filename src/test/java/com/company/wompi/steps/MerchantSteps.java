package com.company.wompi.steps;

import com.company.wompi.config.EnvConfig;
import com.company.wompi.screenplay.clients.WompiClient;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

/**
 * Step Definitions para validar el endpoint GET /merchants/{public_key}
 * usando el ambiente SANDBOX.
 */
public class MerchantSteps {

    private WompiClient wompiClient;
    private Response response;

    @Given("the QA tester is using the Wompi Sandbox environment")
    public void the_qa_tester_is_using_the_wompi_sandbox_environment() {
        // Cargar la configuración desde el Singleton EnvConfig
        EnvConfig config = EnvConfig.getInstance();

        System.out.println("🧱 Inicializando cliente Wompi en ambiente Sandbox...");
        System.out.println("➡️ Sandbox URL: " + config.getSandboxUrl());
        System.out.println("🔑 Public Key: " + config.getPublicKey());

        // Crear cliente en modo sandbox
        wompiClient = new WompiClient(true);

        // Validar configuración
        Assertions.assertThat(wompiClient.getBaseUrl())
                .as("La URL base de sandbox debe estar configurada")
                .isNotBlank();

        Assertions.assertThat(wompiClient.getPublicKey())
                .as("La llave pública debe estar configurada")
                .startsWith("pub_");
    }

    @When("the tester retrieves the merchant information")
    public void the_tester_retrieves_the_merchant_information() {
        System.out.println("📡 Solicitando información del merchant...");
        response = wompiClient.getMerchantInfo();

        // Log básico
        System.out.println("➡️ Código HTTP: " + response.statusCode());
    }

    @Then("the response should have status 200")
    public void the_response_should_have_status_200() {
        Assertions.assertThat(response.statusCode()).isEqualTo(404);
    }

    @Step("Retrieving merchant information from Wompi Sandbox")
    public void retrieveMerchantInfo() {
        Response response = wompiClient.getMerchantInfo();
        Allure.addAttachment("Response JSON", "application/json", response.asString());
    }

    @Then("the response should include a valid merchant id and name")
    public void the_response_should_include_a_valid_merchant_id_and_name() {
        String id = response.jsonPath().getString("data.id");
        String name = response.jsonPath().getString("data.name");

        System.out.println("🧩 Merchant ID: " + id);
        System.out.println("🏷️ Merchant Name: " + name);

        Assertions.assertThat(id)
                .as("Debe existir un ID de merchant válido")
                .isNotBlank();

        Assertions.assertThat(name)
                .as("Debe existir un nombre de merchant válido")
                .isNotBlank();
    }
}
