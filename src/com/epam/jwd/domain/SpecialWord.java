package com.epam.jwd.domain;

public class SpecialWord  extends  BaseText{
    public SpecialWord(String value) {
        super(value);
    }

    @Override
    public void print() {
        System.out.print(getValue());
    }
}
