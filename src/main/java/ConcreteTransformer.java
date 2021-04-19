import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
    public void transform_for_json(SourceTable source_row, ArrayList<TargetTable> target_table, Transformations transformations) throws IOException {
        String data_content = transformations.getData_content();
        String[] data_format = data_content.split(":");
        if(data_format[0].equals("simple") && data_format[2].equals("same") ) {
            transformer_for_simple_multiple_same(source_row, target_table, transformations);
        }
//        if(data_format[0].equals("simple") && data_format[1].equals("multiple") && data_format[2].equals("same") ) {
//            transformer_for_simple_multiple_same(source_row, target_table, transformations);
//        }
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

    public void transformer_for_simple_multiple_same(SourceTable source_table, ArrayList<TargetTable> target_table, Transformations transformations) throws IOException {
        String source_row = source_table.getData();
        HashMap<String, String> rows = this.transform_to_json(source_row);


        for (HashMap.Entry<String, String> entry : rows.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

//            Clean values
            this.int_key = new ArrayList<>();
            this.int_value = new ArrayList<>();
            this.string_key = new ArrayList<>();
            this.string_value = new ArrayList<>();

            TargetTable target_row = new TargetTable();

            for (int tno = 0; tno < transformations.getSize(); tno++) {
                System.out.println(transformations.getTransformationTypesModule(tno));
                String[] trans_details = transformations.getTransformationTypesModule(tno).split(" ");
                try {
                    Class cls = Class.forName("ConcreteTransformer");
                    switch (trans_details[1]) {
                        case "split_string":
//                            try {
//                                Object obj = cls.newInstance();
//                                Method method = cls.getDeclaredMethod(trans_details[1], Integer.class, String.class, String.class);
//
//                                method.invoke(obj, ((trans_details[0].equals("key")) ? 0 : 1), ((trans_details[0].equals("key")) ? key : value), trans_details[2]);
//
//                            } catch (SecurityException | NoSuchMethodException e) {
//                                System.out.println(e);
//                            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
//                                e.printStackTrace();
//                            }

                            split_string(((trans_details[0].equals("key")) ? 0 : 1), ((trans_details[0].equals("key")) ? key : value), trans_details[2]);
                            break;
                        case "replace_from_dict":
//                            try {
//                                Object obj = cls.newInstance();
//                                Method method = cls.getDeclaredMethod(trans_details[1], Integer.class, Integer.class, HashMap.class);
//                                method.invoke(obj, ((trans_details[0].equals("key")) ? 0 : 1), Integer.parseInt(trans_details[2]), null);
//
//                            } catch (SecurityException | NoSuchMethodException e) {
//                                System.out.println(e);
//                            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
//                                e.printStackTrace();
//                            }

                            replace_from_dict(((trans_details[0].equals("key")) ? 0 : 1), Integer.parseInt(trans_details[2]), null);
                            break;
                        case "add_value_to_target":
//                            try {
//                                Object obj = cls.newInstance();
//                                Method method = cls.getDeclaredMethod(trans_details[1], Integer.class, Integer.class, Integer.class, TargetTable.class);
//                                method.invoke(obj, ((trans_details[0].equals("key")) ? 0 : 1), Integer.parseInt(trans_details[2]), Integer.parseInt(trans_details[3]), target_row);
//
//                            } catch (SecurityException | NoSuchMethodException e) {
//                                System.out.println(e);
//                            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
//                                e.printStackTrace();
//                            }

                            add_value_to_target(((trans_details[0].equals("key")) ? 0 : 1), Integer.parseInt(trans_details[2]), Integer.parseInt(trans_details[3]), target_row);
                            break;
                    case "add_date_time":
//                        try {
//                            Object obj = cls.newInstance();
//                            Method method = cls.getDeclaredMethod(trans_details[1], Integer.class, Integer.class, Integer.class, SourceTable.class, TargetTable.class);
//                            int tmp = 0;
//                            if(trans_details[0].equals("key")){
//                                tmp = 0;
//                            }
//                            else if(trans_details[0].equals("value")){
//                                tmp = 1;
//                            }
//                            else if(trans_details[0].equals("none")){
//                                tmp = 2;
//                            }
//                            method.invoke(obj, tmp, Integer.parseInt(trans_details[2]), Integer.parseInt(trans_details[3]), source_table, target_row);
//
//                        } catch (SecurityException | NoSuchMethodException e) {
//                            System.out.println(e);
//                        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
//                            e.printStackTrace();
//                        }

                        int tmp = 0;
                        if(trans_details[0].equals("key")){
                            tmp = 0;
                        }
                        else if(trans_details[0].equals("value")){
                            tmp = 1;
                        }
                        else if(trans_details[0].equals("none")){
                            tmp = 2;
                        }
                        add_date_time(tmp, Integer.parseInt(trans_details[2]), Integer.parseInt(trans_details[3]), source_table, target_row);
                        break;
                        default:
                            System.out.println("Not a valid function");
                            break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("Phuckkk");
                System.out.println(rows);
                System.out.println(string_key);
                System.out.println(string_value);
                System.out.println(target_row);
            }
            target_table.add(target_row);
        }
    }

    public HashMap<String, String> transform_to_json(String source_row) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(source_row, new TypeReference<HashMap<String, String>>() {});

    }

    public void add_date_time(Integer korv, Integer source_loc, Integer target_loc, SourceTable source_table, TargetTable target_table) {
        if(korv == 2){
            System.out.println(source_table);
            String date_time = source_table.getOneColumn(source_loc);
            if(target_table.getSize() < target_loc+1){
                ArrayList<String> tmp = target_table.getColumnName();
                int n = target_loc-target_table.getSize()+1;
                for(int i=0; i<n; i++){
                    tmp.add("");
                }
            }
            target_table.setOneColumn(target_loc,date_time);
        }
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

    public void split_string(Integer korv, String value, String delimiter) {
        ArrayList<String> arr;
        if(korv == 0){
            arr = string_key;
        }
        else {
            arr = string_value;
        }
        if(delimiter.equals("none")) {
            arr.add(value);
        }
        else{
            arr.addAll(Arrays.asList(value.split(delimiter)));
        }
    }

    @Override
    public String toString() {
        return "ConcreteTransformer{}";
    }

}

