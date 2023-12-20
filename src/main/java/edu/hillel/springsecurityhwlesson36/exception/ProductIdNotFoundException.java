package edu.hillel.springsecurityhwlesson36.exception;

public class ProductIdNotFoundException extends Exception {

    public ProductIdNotFoundException(String message) {
        super(message);
    }
}
