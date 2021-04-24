package com.etl.gui;

public class GenInstructionDB {
    public static String insert_instruction(String table_name, String[] column_details, String[] entry_details) {
        int n = entry_details.length;
        String s = "INSERT INTO " + table_name + " ("+column_details[0];
        for(int i=1; i<n; i++){
            s = s + "," + column_details[i];
        }
        s = s + ") VALUES ('";
        s += entry_details[0] + "'";
        for(int i = 1; i < n; i++){
            s = s + ',' + "'" + entry_details[i] + "'";
        }
        s = s + ")";
        return s;
    }

    public static String delete_instruction(String table_name, String UID_column, String key_to_data) {
        return "DELETE FROM " + table_name + " WHERE " + UID_column + " = " + "'" + key_to_data + "'";
    }

    public static String select_instruction(String table_name, String[] columns, String condition) {
        int n = columns.length;
        String s = "SELECT " + columns[0];
        for(int i = 1; i < n; i++){
            s += "," + columns[i];
        }
        s += " FROM " + table_name + "WHERE " + condition;
        return s;
    }

    public static String select_one_instruction(String table_name) {
        return "SELECT * FROM " + table_name + " LIMIT 1";
    }

    public static  String select_count_instruction(String table_name) {
        return "SELECT count(*) FROM " + table_name;
    }
}


