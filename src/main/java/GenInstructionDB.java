public class GenInstructionDB {
    public static String insert_instruction(String table_name, String[] entry_details) {
        String s = "INSERT INTO " + table_name + " VALUES ('";
        int n = entry_details.length;
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
}


