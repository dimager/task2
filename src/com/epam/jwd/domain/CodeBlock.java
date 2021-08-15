package com.epam.jwd.domain;

public class CodeBlock extends BaseText{
    public CodeBlock(String value) {
        super(value);
    }

    @Override
    public void print() {
        System.out.println(getValue());
    }

}
