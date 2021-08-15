package com.epam.jwd.domain;

public class Space extends BaseText {
    public Space(String value) {
        super(value);
    }

    @Override
    public void print() {
        System.out.print(getValue());
    }
}
