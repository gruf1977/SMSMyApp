package com.example.smsmyapp;

public class SmsClass {
    private String name;
    private String text;

    SmsClass(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    String getText() {
        return text;
    }
}
