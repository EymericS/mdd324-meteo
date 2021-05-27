package com.ipiecoles;

import java.util.Map;

public class GatewayRequest {
    private String body;
    private Map<String,String> headers;

    public GatewayRequest() {
    }

    public GatewayRequest(String body, Map<String, String> headers) {
        this.body = body;
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
