package com.epam.jwd.domain;

public class PunctuationMark extends BaseText {
    public PunctuationMark(String value) {
        super(value);
    }

    @Override
    public void print() {
        System.out.print(getValue());
    }
}
