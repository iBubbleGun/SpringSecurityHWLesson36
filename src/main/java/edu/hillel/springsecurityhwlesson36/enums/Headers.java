package edu.hillel.springsecurityhwlesson36.enums;

public enum Headers {

    AUTHORIZATION("Authorization");

    private final String header;

    Headers(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
