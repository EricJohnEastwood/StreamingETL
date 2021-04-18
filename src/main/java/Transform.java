import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.sql.*;
import java.lang.reflect.Method;

class PeriodicTransformThread extends Thread {
    private boolean run_thread;
    private final EngineData engine;
    private final ConnectionDB connectionDB;

    public PeriodicTransformThread(EngineData engine, ConnectionDB connectionDB){
        this.run_thread = false;
        this.engine = engine;
        this.connectionDB = connectionDB;
        System.out.println("Construction Completed");
    }
    @Override
    public void run() {
        this.run_thread = true;
        System.out.println("ThreadExecution");
        Transform.run_transform(this.engine, this.connectionDB);
        this.run_thread = false;
    }

    public void stop_thread() {
        this.run_thread = false;
    }

    public boolean isRun_thread() {
        return this.run_thread;
    }
}

class PeriodicTransform extends TimerTask{
    private boolean run_thread;
    private final EngineData engine;
    private final ConnectionDB connectionDB;

    public PeriodicTransform(EngineData engine, ConnectionDB connectionDB){
        this.run_thread = true;
        this.engine = engine;
        this.connectionDB = connectionDB;
        System.out.println("Construction Completed");
    }
    @Override
    public void run() {
        if(this.run_thread) {
            System.out.println("ThreadExecution");
            Transform.construct_transform_thread(this.engine, this.connectionDB);
        }
    }

    public void stop() {
        this.run_thread = false;
    }

}


public class Transform {
    private static PeriodicTransformThread pt_thread;
    private static PeriodicTransform pt_schedule;
    private static Timer timer;

    public static void init_source_table(String filename, EngineData engine) {
        try{
            String tableName;
            ArrayList<String> columnName = new ArrayList<String>();

            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();

            Document document=builder.parse(new File("./src/main/resources/" + filename));
//           remove hardcoded target
            NodeList source_Target = document.getElementsByTagName("sourceTarget");
            Element eElement = (Element) source_Target.item(0);

            tableName = eElement.getElementsByTagName("table").item(0).getTextContent();
            NodeList column_list = eElement.getElementsByTagName("column");
            for(int i = 0; i < column_list.getLength(); i++) {
                columnName.add(column_list.item(i).getTextContent());
            }

            engine.constructSourceTable(tableName, columnName);

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't insert source table Data");
        }
    }

    public static void init_target_table(String filename, EngineData engine) {
        try{
            String tableName;
            ArrayList<String> columnName = new ArrayList<String>();
            String storeDataModule;

            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();

            Document document=builder.parse(new File("./src/main/resources/" + filename));
//          remove hardcoded target
            NodeList source_Target = document.getElementsByTagName("transformationTarget");
            Element eElement = (Element) source_Target.item(0);

            tableName = eElement.getElementsByTagName("table").item(0).getTextContent();
            NodeList column_list = eElement.getElementsByTagName("column");
            for(int i = 0; i < column_list.getLength(); i++) {
                columnName.add(column_list.item(i).getTextContent());
            }

            engine.constructTargetTable(tableName, columnName);

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't insert target table Data");
        }

    }

    public static void init_transformation(String filename, EngineData engine) {
        try {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document document=builder.parse(new File("./src/main/resources/" + filename));

            NodeList transformationSteps = document.getElementsByTagName("transformationSteps");

            for(int i=0; i<transformationSteps.getLength(); i++){
                Element eElement = (Element) transformationSteps.item(i);
                String type = eElement.getElementsByTagName("type").item(0).getTextContent();
                String url = eElement.getElementsByTagName("URL").item(0).getTextContent();
                String transformerEngine = eElement.getElementsByTagName("transformation-rule").item(0).getTextContent();
                String datatype = eElement.getElementsByTagName("datatype").item(0).getTextContent();


                ArrayList<String> tcs = new ArrayList<String>();

                NodeList tcs_elems = eElement.getElementsByTagName("transformation");
                for(int j=0; j<tcs_elems.getLength(); j++){
                    Element tmp = (Element) tcs_elems.item(j);
                    tcs.add(tmp.getTextContent());
                }

                engine.constructTransformations(url, type, transformerEngine, datatype, tcs);
            }
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't load the transformations from the xml.");
        }
    }

    public static void init_transformers(EngineData engine) throws Exception{
        engine.constructTransformer("ConcreteTransformer");
    }

    public static void set_transform_schedule(EngineData engine, ConnectionDB connectionDB) {
        int i_inter = 10000;
        Transform.timer = new Timer();
        Transform.pt_schedule = new PeriodicTransform(engine, connectionDB);
        Transform.timer.schedule(Transform.pt_schedule, 0, i_inter);

    }

    public static void stop_transform_schedule() {
        Transform.pt_schedule.cancel();
        Transform.timer.cancel();
        Transform.timer.purge();
    }

    public static void construct_transform_thread(EngineData engine, ConnectionDB connectionDB) {
        if(pt_thread.isRun_thread()) {
            System.out.println("Thread already executing");
        }
        else {
            pt_thread = new PeriodicTransformThread(engine, connectionDB);
            pt_thread.start();
        }
    }

    public static void run_transform(EngineData engine, ConnectionDB connectionDB) {
        Integer steps = 0;

        String check_non_empty_command = GenInstructionDB.select_count_instruction(engine.getSourceTable().getTableName());
        steps = connectionDB.checkIfNonEmptyTable(check_non_empty_command);

        if(steps > 0) {
            for( ; steps > 0; steps--) {
                run_transformation(engine,connectionDB);
            }
        }
    }

    public static void run_transformation(EngineData engine, ConnectionDB connectionDB) {
        try {
            System.out.println("running a transformation");

            String table_name = engine.getSourceTable().getTableName();

            // Get one row from source data dump
//            String selectCommand = GenInstructionDB.select_one_instruction(table_name);
//            SourceTable table_for_transform = connectionDB.selectFromTable(selectCommand,engine);


//            Transformations transformation_to_run = engine.getOneTransformation(table_for_transform.getKey());


            // Brute Data Input
            ArrayList<String> key = new ArrayList<String>();
            key.add("json");
            key.add("https://free.currconv.com/api/v7/convert?q=USD_INR,INR_USD&compact=ultra&apiKey=c0dbece0e1a955a43e02");
            Transformations transformation_to_run = engine.getOneTransformation(key);
            ArrayList<String> column_value = new ArrayList<String>();
            column_value.add("json");
            column_value.add("https://free.currconv.com/api/v7/convert?q=USD_INR,INR_USD&compact=ultra&apiKey=c0dbece0e1a955a43e02");
            column_value.add("2022-01-31 00:38:00");
            column_value.add("{\"USD_INR\": \"74.7297\"}");
            SourceTable table_for_transform = new SourceTable("source_data_dump",column_value);
            // End Brute Data Input

            System.out.println(table_for_transform);
            System.out.println(transformation_to_run);

            // Running the transform
            run_one_transformation(engine, connectionDB, table_for_transform, transformation_to_run);

            // TODO: Delete transformed row from source data dump

//            String deleteCommand = GenInstructionDB.delete_instruction(table_name, engine.getSourceTable().getColumnName().get(0), table_for_transform.getColumnName().get(0));
//            connectionDB.deleteFromTable(deleteCommand);

            System.out.println("Exiting thread");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Couldn't run the thread.");
        }
    }

    public static void run_one_transformation(EngineData engine, ConnectionDB connectionDB, SourceTable table_for_transform, Transformations transformation_to_run) throws Exception{
//        String[] class_method_str;
        Transformer transformer = engine.getTransformer(transformation_to_run.getTransformationEngine());
        ArrayList<TargetTable> target_rows;
        if(transformation_to_run.getData_type().equals("json")) {
            transformer.transform_for_json(table_for_transform.getData(), target_rows, transformation_to_run);
        }
        System.out.println(target_rows);
        //TODO: Store the target rows in the target data warehouse
        String target_name = engine.getTargetTable().getTableName();
        for(TargetTable target_row: target_rows){
            String[] tmp = target_row.getColumnName().toArray(new String[0]);
            String insert_command = GenInstructionDB.insert_instruction(target_name, tmp);
            connectionDB.insertIntoTable(insert_command);
        }
//        try {
//                for (int i = 0; i < transformation_to_run.getSize(); i++) {
//                    class_method_str = transformation_to_run.getTransformationTypesModule(i).split("\\.");
//                    Class cls = Class.forName(class_method_str[0]);
//                    Object obj = cls.newInstance();
//                    Method method = cls.getDeclaredMethod(class_method_str[1], String.class);
//                    method.invoke(obj, table_for_transform.getData());
//
//                }
//            } catch (ArrayIndexOutOfBoundsException | ClassNotFoundException | NoSuchMethodException e) {
//                System.out.println("Testing failed");
//                return;
//
//            } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

    }
}
