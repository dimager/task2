package com.epam.jwd.domain;

public class ContentWord extends BaseText{
    public ContentWord(String value) {
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
