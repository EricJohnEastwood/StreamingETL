import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConcreteTransformer {
    ArrayList<String> string_key;
    ArrayList<String> string_value;
    ArrayList<Integer> int_key;
    ArrayList<Integer> int_value;

    ArrayList<String> datatype;

    ConcreteTransformer() {
        this.string_key = new ArrayList<String>();
        this.string_value = new ArrayList<String>();
        this.int_key = new ArrayList<Integer>();
        this.int_value = new ArrayList<Integer>();
        this.datatype = new ArrayList<String>();
    }

    public ArrayList<Integer> getInt_key() {
        return int_key;
    }

    public ArrayList<Integer> getInt_value() {
        return int_value;
    }

    public void setInt_key(ArrayList<Integer> int_key) {
        this.int_key = int_key;
    }

    public void setInt_value(ArrayList<Integer> int_value) {
        this.int_value = int_value;
    }

    public ArrayList<String> getString_key() {
        return string_key;
    }

    public ArrayList<String> getString_value() {
        return string_value;
    }

    public void setString_key(ArrayList<String> string_key) {
        this.string_key = string_key;
    }

    public void setString_value(ArrayList<String> string_value) {
        this.string_value = string_value;
    }

    public ArrayList<String> getDatatype() {
        return datatype;
    }

    public void setDatatype(ArrayList<String> datatype) {
        this.datatype = datatype;
    }



    public void transformer_for_json(String source_row, TargetTable target_table, Transformations transformations) throws IOException {
        ArrayList<String> data_format = this.getDatatype();
        if(data_format.get(0).equals("simple") && data_format.get(1).equals("multiple") && data_format.get(2).equals("same") ) {
            transformer_for_simple_multiple_same(source_row, target_table, transformations);
        }
    }

    public void transformer_for_simple_multiple_same(String source_row, TargetTable target_table, Transformations transformations) throws IOException {
        HashMap<String, String> rows = this.transform_to_json(source_row);

        for (HashMap.Entry<String, String> entry : rows.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            for(int tno = 0; tno < transformations.getSize(); tno++) {
                System.out.println(transformations.getTransformationTypesModule(tno));
            }
        }

    }

    public HashMap<String, String> transform_to_json(String source_row) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> row = mapper.readValue(source_row, HashMap.class);
        return row;
    }

    public void add_default_date_time(Integer source_table_loc, Integer target_table_loc, SourceTable source_table, TargetTable target_table) {
        String date_time = source_table.getOneColumn(source_table_loc);
        target_table.setOneColumn(target_table_loc,date_time);
    }

    public void add_date_from_source_data(String value, Map<String,String> source_data, Integer column, TargetTable target_table) {
        target_table.setOneColumn(column, source_data.get(value));
    }

    public void add_value_to_target(String value, Integer column, TargetTable target_table) {
        target_table.setOneColumn(column,value);
    }

    public String replace_from_dict(String key, HashMap<String, String> dict) {
        return dict.get(key);
    }

    public Integer replace_from_dict(Integer key, HashMap<String, Integer> dict) {
        return dict.get(Integer.toString(key));
    }

    public String get_from_dict(String key, HashMap<String, String> dict) {
        return dict.get(key);
    }

    public String[] split_string(String value, String delimiter) {
        if(delimiter.equals("none")) {
            return new String[]{value};
        }
        return value.split(delimiter);
    }

}

