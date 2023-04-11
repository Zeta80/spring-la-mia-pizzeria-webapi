package org.lessons.pizzery.springlamiapizzeriacrud.exceptions;

public class OfferNotFoundException extends RuntimeException{
    public OfferNotFoundException(String message){
        super(message);
    }
}
