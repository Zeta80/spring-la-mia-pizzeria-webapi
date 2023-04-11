package org.lessons.pizzery.springlamiapizzeriacrud.exceptions;

public class PizzaNotFoundException  extends RuntimeException{
    public PizzaNotFoundException(String message) {
        super(message);
    }
}
