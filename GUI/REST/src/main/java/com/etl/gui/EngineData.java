package com.etl.gui;

import java.util.ArrayList;
import java.util.HashMap;


public class EngineData {
    private SourceTable source_table;
    private TargetTable target_table;
    private HashMap<String, Transformer> transformers;
    private HashMap<ArrayList<String>,Transformations> transformations;


    public EngineData() {
        this.source_table = new SourceTable();
        this.target_table = new TargetTable();
        this.transformations = new HashMap<ArrayList<String>,Transformations>();
        this.transformers = new HashMap<String, Transformer>();
    }

    public HashMap<String, Transformer> getTransformers() {
        return transformers;
    }

    public Transformer getTransformer(String key) {
        return transformers.get(key);
    }

    public void setTransformers(HashMap<String, Transformer> transformers) {
        this.transformers = transformers;
    }

    public SourceTable getSourceTable() {
        return this.source_table;
    }

    public TargetTable getTargetTable() {
        return this.target_table;
    }

    public Transformations getOneTransformation(ArrayList<String> key) {
        System.out.println(transformations.keySet());
        System.out.println(transformations.get(key));
        return transformations.get(key);
    }

    public void constructSourceTable(String tableName, ArrayList<String> columnName) {
        this.source_table.setTableName(tableName);
        this.source_table.setColumnName(columnName);
    }

    public void constructTargetTable(String tableName, ArrayList<String> columnName) {
        this.target_table.setTableName(tableName);
        this.target_table.setColumnName(columnName);
    }

    public void constructTransformations(String url, String data_type, String transformationEngine, String data_content, ArrayList<String> transformationTypesModule) {
        ArrayList<String> key = new ArrayList<String>();
        key.add(data_type);
        key.add(url);
        System.out.println("Inside constuct");
        System.out.println(data_type);
        System.out.println(url);
        Transformations value = new Transformations(url, data_type, transformationEngine, data_content, transformationTypesModule);

        this.transformations.put(key, value);
    }

    public void constructTransformer(String key) throws Exception{
        //key = "com.etl.gui."+key;
        //System.out.println(key);
        Class cls = Class.forName(key);
        Transformer transformer = (Transformer) cls.getDeclaredConstructor().newInstance();
        this.transformers.put(key, transformer);
    }

    @Override
    public String toString() {
        return "EngineData{ " + this.source_table.toString() + " \n" + this.target_table.toString() +
                " \n" + this.transformations.toString() + " }";
    }
}
