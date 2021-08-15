package com.epam.jwd.service;

import com.epam.jwd.domain.Text;

import java.util.LinkedList;
import java.util.List;

public abstract class Parser {
    private Parser next;
    private List<Text> list = new LinkedList<>();

    public Parser linkWith(Parser next){
        this.next = next;
        return next;
    }
    public abstract List<Text> split (String text);

    protected List<Text> splitNext (String text){
        if (next==null){
            return list;
        }
        return next.split(text);
    }
}
