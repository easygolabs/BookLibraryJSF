package com.lab1.database.model;

public enum Format{
    PDF(".pdf"),
    TXT(".txt"),
    DOC(".doc");

    private String format;

    Format(String format){
        this.format=format;
    }

    public String getFormat() {
        return format;
    }
}
