package com.company.wompi.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static EnvConfig instance;
    private final String sandboxUrl;
    private final String prodUrl;
    private final String publicKey;
    private final String privateKey;

    // Private constructor to prevent instantiation
    private EnvConfig() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        printLoadedConfig();
        this.sandboxUrl = dotenv.get("WOMPI_SANBOX_URL");
        this.prodUrl = dotenv.get("WOMPI_PROD_URL");
        this.publicKey = dotenv.get("WOMPI_PUBLIC_KEY");
        this.privateKey = dotenv.get("WOMPI_PRIVATE_KEY");
    }

    public void printLoadedConfig() {
        System.out.println("🧠 [EnvConfig] Verificación de variables cargadas:");
        System.out.println("   WOMPI_SANBOX_URL: " + (sandboxUrl != null ? "✅ Cargada" : "❌ No cargada"));
        System.out.println("   WOMPI_PROD_URL: " + (prodUrl != null ? "✅ Cargada" : "❌ No cargada"));
        System.out.println("   WOMPI_PUBLIC_KEY: " + (publicKey != null ? "✅ Cargada" : "❌ No cargada"));
        System.out.println("   WOMPI_PRIVATE_KEY: " + (privateKey != null ? "✅ Cargada" : "❌ No cargada"));
    }

    // Public method to get the singleton instance
    public static synchronized EnvConfig getInstance() {
        if (instance == null) {
            instance = new EnvConfig();
        }
        return instance;
    }

    // Getters for the parameters
    public String getSandboxUrl() {
        return sandboxUrl;
    }

    public String getProdUrl() {
        return prodUrl;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}