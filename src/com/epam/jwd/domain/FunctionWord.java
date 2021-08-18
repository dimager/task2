package com.epam.jwd.domain;

public class FunctionWord extends BaseText {
    public FunctionWord(String value) {
        super(value);
    }

    @Override
    public void print() {
        System.out.print(getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
