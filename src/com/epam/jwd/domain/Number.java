package com.epam.jwd.domain;

public class Number extends BaseText {
    public Number(String value) {
        super(value);
    }

    @Override
    public void print() {
        System.out.print(getValue());
    }

}
