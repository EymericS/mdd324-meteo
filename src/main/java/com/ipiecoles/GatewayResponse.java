package com.ipiecoles;
import java.util.Map;

/**
 * POJO contenant l'objet de réponse pour API Gateway
 */
public class GatewayResponse {

    private final String body; // Corps de la réponse
    private final int statusCode; // Code statut HTTP (200, 404...)
    private final Map<String, String> headers; // Eventuels headers (clé = nom du header, valeur = valeur du header)


    public GatewayResponse(String body, Map<String, String> headers, int statusCode) {
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "GatewayResponse{" +
                "body='" + body + '\'' +
                ", headers=" + headers +
                ", statusCode=" + statusCode +
                '}';
    }
}