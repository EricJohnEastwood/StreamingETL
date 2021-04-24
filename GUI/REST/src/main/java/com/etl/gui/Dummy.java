package com.etl.gui;

public class Dummy {
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String URL;
    private String type;
    private String name;

    public String getQueryFrequency() {
        return queryFrequency;
    }

    public void setQueryFrequency(String queryFrequency) {
        this.queryFrequency = queryFrequency;
    }

    private String queryFrequency;

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    private String mapping;

    public Dummy()
    {
    }



}
