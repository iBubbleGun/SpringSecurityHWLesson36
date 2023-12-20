package edu.hillel.springsecurityhwlesson36.enums;

public enum Endpoints {

    PING("/ping"),
    PRODUCTS("/products"),
    USER("/user"),
    LOGIN("/login");

    private final String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
