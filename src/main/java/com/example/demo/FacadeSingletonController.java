package com.example.demo;

public class FacadeSingletonController {

    private static FacadeSingletonController instance;

    public static synchronized FacadeSingletonController getInstance() {
        if (instance == null) {
            instance = new FacadeSingletonController();
        }
        return instance;
    }
}
