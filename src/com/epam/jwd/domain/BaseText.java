package com.epam.jwd.domain;
import java.util.List;

public abstract class BaseText implements Text {
    private String value;

    public BaseText(String value) {
        this.value = value;
    }

    public BaseText() {
    }

    @Override
    public abstract void print();

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseText baseText = (BaseText) o;
        return value.equals(baseText.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
