import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

// TODO: Make this class an implementation of ConcreteTransformer
public class ConcreteTransformer implements Transformer{
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


    @Override
    public void transform_for_json(String source_row, ArrayList<TargetTable> target_table, Transformations transformations) throws IOException {
//        ArrayList<String> data_format = this.getDatatype();
//        if(data_format.get(0).equals("simple") && data_format.get(1).equals("multiple") && data_format.get(2).equals("same") ) {
        String data_content = transformations.getData_content();
        String[] data_format = data_content.split(":");
        if(data_format[0].equals("simple") && data_format[1].equals("multiple") && data_format[2].equals("same") ) {
            transformer_for_simple_multiple_same(source_row, target_table, transformations);
        }
    }

    @Override
    public void transform_csv(String source_row, TargetTable target_row) {
    }

    @Override
    public void transform_xml(String source_row, TargetTable target_row) {

    }

    @Override
    public void transform_for_json(String source_row, TargetTable target_row) {

    }

    public void transformer_for_simple_multiple_same(String source_row, ArrayList<TargetTable> target_table, Transformations transformations) throws IOException {
        HashMap<String, String> rows = this.transform_to_json(source_row);
        TargetTable target_row = new TargetTable();

//        for (HashMap.Entry<String, String> entry : rows.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            for(int tno = 0; tno < transformations.getSize(); tno++) {
//                System.out.println(transformations.getTransformationTypesModule(tno));
//                String[] trans_details = transformations.getTransformationTypesModule(tno).split(" ");
//                //TODO: If else statements for methods; give the methods the one and only element of the target_table array
//                Class cls = this.getClass();
//                Method method = cls.getDeclaredMethod(trans_details[1], String.class);
//                method.invoke(obj, table_for_transform.getData());
////                if(trans_details[2] == "")
//            }
//        }

        for(int tno = 0; tno < transformations.getSize(); tno++) {
            System.out.println(transformations.getTransformationTypesModule(tno));
            String[] trans_details = transformations.getTransformationTypesModule(tno).split(" ");
            //TODO: If else statements for methods; give the methods the one and only element of the target_table array
            if(trans_details[1].equals("split_string")){
                split_string(((trans_details[0].equals("key"))?0:1), rows, trans_details[2]);
            }
            else if(trans_details[1].equals("replace_from_dict")){
                HashMap<String, String> tmp = null;
                replace_from_dict(((trans_details[0].equals("key"))?0:1), Integer.parseInt(trans_details[2]), tmp);
            }
            else if(trans_details[1].equals("add_value_to_target")){
                add_value_to_target(((trans_details[0].equals("key"))?0:1), Integer.parseInt(trans_details[2]), Integer.parseInt(trans_details[3]), target_row);
            }
        }
        target_table.add(target_row);
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

    public void add_value_to_target(Integer korv, Integer index, Integer column, TargetTable target_table) {
        ArrayList<String> arr;
        if(korv == 0){
            arr = string_key;
        }
        else{
            arr = string_value;
        }
        if(target_table.getSize() < column+1){
            ArrayList<String> tmp = target_table.getColumnName();
            int n = column-target_table.getSize()+1;
            for(int i=0; i<n; i++){
                tmp.add("");
            }
        }
        target_table.setOneColumn(column,arr.get(index));
    }

    public void replace_from_dict(Integer korv, Integer index, HashMap<String, String> dict) {
        ArrayList<String> arr;
        if(korv == 0){
            arr = string_key;
        }
        else{
            arr = string_value;
        }

        arr.set(index, arr.get(index).toLowerCase());
    }

    public Integer replace_from_dict(Integer key, HashMap<String, Integer> dict) {
        return dict.get(Integer.toString(key));
    }

    public String get_from_dict(String key, HashMap<String, String> dict) {
        return dict.get(key);
    }

    public void split_string(Integer korv, HashMap<String, String> rows, String delimiter) {
        Collection<String> elements;
        ArrayList<String> arr;
        if(korv == 0){
            elements = (Collection<String>) rows.keySet();
            arr = string_key;
        }
        else {
            elements = (Collection<String>) rows.values();
            arr = string_value;
        }
        if(delimiter.equals("none")) {
            for(String s: elements){
                arr.add(s);
            }
        }
        else{
            for(String s: elements){
                for(String j: s.split(delimiter)){
                    arr.add(j);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "ConcreteTransformer{}";
    }

}

