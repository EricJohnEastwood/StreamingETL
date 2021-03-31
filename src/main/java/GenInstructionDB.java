public class GenInstructionDB {
    public static String insert_instruction(String table_name, String[] entry_details) {
        int n = entry_details.length;
        String s = "INSERT INTO " + table_name + " VALUES ('";
        s += entry_details[0] + "'";
        for(int i = 1; i < n; i++){
            s = s + ',' + "'" + entry_details[i] + "'";
        }
        s = s + ")";
        return s;
    }

    public static String delete_instruction(String table_name, String UID_column, String key_to_data) {
        String s = "DELETE FROM " + table_name + " WHERE " + UID_column + " = " + "'" + key_to_data + "'";
        return s;
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

    public static String select_one_row_instruction(String table_name, String date_time) {
        String s = "SELECT * FROM " + table_name + " WHERE " + date_time + " = (SELECT MIN(" + date_time + ") FROM " + table_name + ")";
        return s;
    }
}


