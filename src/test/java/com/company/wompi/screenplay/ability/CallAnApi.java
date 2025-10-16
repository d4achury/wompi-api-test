package com.company.wompi.screenplay.ability;

import com.company.wompi.screenplay.clients.WompiClient;

public class CallAnApi {
    private final WompiClient client;

    public CallAnApi(WompiClient client) {
        this.client = client;
    }

    public WompiClient client() {
        return client;
    }

    public static CallAnApi with(WompiClient client) {
        return new CallAnApi(client);
    }
}
