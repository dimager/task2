package com.epam.jwd.domain;

public class Word extends BaseText {
    public Word(String value) {
        super(value);
    }

    @Override
    public void print() {
        System.out.println(getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
