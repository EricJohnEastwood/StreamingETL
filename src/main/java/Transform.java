import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;
import java.lang.reflect.Method;



public class Transform {

    public static void get_source_table(String filename, EngineData engine) {
        try{
            String tableName;
            ArrayList<String> columnName = new ArrayList<String>();
            String urlRunModule;
            String storeDataModule;

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
            urlRunModule = eElement.getElementsByTagName("data_request").item(0).getTextContent();
            storeDataModule =  eElement.getElementsByTagName("store_class").item(0).getTextContent();

            engine.constructSourceTable(tableName, columnName, urlRunModule, storeDataModule);

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't insert source table Data");
        }
    }

    public static void get_target_table(String filename, EngineData engine) {
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
            storeDataModule = eElement.getElementsByTagName("store_class").item(0).getTextContent();

            engine.constructTargetTable(tableName, columnName, storeDataModule);

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't insert target table Data");
        }

    }

    public static void get_transformation(String filename, EngineData engine) {
        try {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document document=builder.parse(new File("./src/main/resources/" + filename));

            NodeList transformationSteps = document.getElementsByTagName("transformationStep");

            for(int i=0; i<transformationSteps.getLength(); i++){
                Element eElement = (Element) transformationSteps.item(i);
                String type = eElement.getElementsByTagName("type").item(0).getTextContent();
                String url = eElement.getElementsByTagName("URL").item(0).getTextContent();

                ArrayList<String> tcs = new ArrayList<String>();

                NodeList tcs_elems = eElement.getElementsByTagName("transformation");
                for(int j=0; j<tcs_elems.getLength(); j++){
                    Element tmp = (Element) tcs_elems.item(j);
                    tcs.add(tmp.getTextContent());
                }

                engine.constructTransformations(url, type, tcs);
            }
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't load the transformations from the xml.");
        }
    }

    public static void run_transformation(EngineData engine, ConnectionDB connectionDB) {
        try {
            System.out.println("running a transformation");

            String table_name = engine.getSourceTable().getTableName();
            String uid = "Date_Time";
            String[] class_method_str;

            // Get one row from source data dump
            String selectCommand = GenInstructionDB.select_one_instruction(table_name, uid);
            SourceTable table_for_transform = connectionDB.selectFromTable(selectCommand,engine);


            Transformations transformation_to_run = engine.getOneTransformation(table_for_transform.getKey());

            System.out.println(table_for_transform);
            System.out.println(transformation_to_run);

            try {
                for (int i = 0; i < transformation_to_run.getSize(); i++) {
                    String temp = transformation_to_run.getTransformationTypesModule(i);
                    class_method_str = transformation_to_run.getTransformationTypesModule(i).split("\\.");
                    Class cls = Class.forName(class_method_str[0]);
                    Object obj = cls.newInstance();
                    Method method = cls.getDeclaredMethod(class_method_str[1], String.class);
                    method.invoke(obj, table_for_transform.getData());

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Testing failed");
                return;

            }
            System.out.println("Testing successful");

            // Delete transformed row from source data dump
            String deleteCommand = GenInstructionDB.delete_instruction(table_name, uid, table_for_transform.getColumnName().get(2));
            System.out.println(deleteCommand);
            connectionDB.deleteFromTable(deleteCommand);

            System.out.println("Exiting thread");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Couldn't run the thread.");
        }
    }
}
