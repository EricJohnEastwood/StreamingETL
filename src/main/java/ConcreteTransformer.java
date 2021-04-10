import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConcreteTransformer implements Transformer{

    @Override
    public void transform_csv(String source_row, TargetTable target_row) {
    }

    @Override
    public void transform_xml(String source_row, TargetTable target_row) {

    }

    @Override
    public void transform_json(String source_row, TargetTable target_row) {

    }

    public HashMap<String, String> transform_json(String source_row) throws IOException {
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
        return value.split(delimiter);
    }
}

