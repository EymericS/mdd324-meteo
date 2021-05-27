package com.ipiecoles;

public class WeatherException extends Exception {
    enum TYPE {
        NO_CITY,
        UNKNOWN_EXTERNAL_ERROR,
        WRONG_EXTERNAL_API_KEY,
        CITY_NOT_FOUND,
        UNHANDLE_HTTP_CODE,
        DEBUG
    }

    private int http_status_code;
    private TYPE type;

    public WeatherException(TYPE type, String msg) {
        super(exceptionMessage(type) + " ## " + msg);
        this.type = type;
        this.http_status_code = 500;
    }

    public WeatherException(TYPE type, int status_code) {
        super(exceptionMessage(type));
        this.type = type;
        this.http_status_code = status_code;
    }

    public WeatherException(TYPE type) {
        this(type, 500);
    }

    public int get_statusCode() {
        return http_status_code;
    }

    public TYPE get_type() {
        return type;
    }

    static public String exceptionMessage(TYPE type) {
        switch (type) {
            case NO_CITY:
                return "No given city";
            case UNKNOWN_EXTERNAL_ERROR:
                return "Unhandled error from external API";
            case WRONG_EXTERNAL_API_KEY:
                return "Wrong external API key";
            case CITY_NOT_FOUND:
                return "City not found by external API";
            case UNHANDLE_HTTP_CODE:
                return "HTTP response code unhandled";
            case DEBUG:
                return "DEBUG Stop point";
            default:
                return "Unhandled error ...";
        }
    }
}
