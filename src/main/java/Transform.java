import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;

public class Transform {

    public static void get_source_table(String filename) {
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

            SourceTable source_table = new SourceTable(tableName, columnName, urlRunModule, storeDataModule);

            System.out.println(source_table);

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't insert source table Data");
            return;
        }
    }

    public static void get_target_table(String filename) {
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

            TargetTable target_table = new TargetTable(tableName, columnName, storeDataModule);

            System.out.println(target_table);

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't insert target table Data");
            return;
        }

    }

    public static void get_transformation(String filename) {
        try {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document document=builder.parse(new File("./src/main/resources/" + filename));
            HashMap<ArrayList<String>,Transformations> transformations = new HashMap<ArrayList<String>,Transformations>();

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
                Transformations value = new Transformations(url, type, tcs);

                ArrayList<String> key = new ArrayList<String>();
                key.add(url);
                key.add(type);
                transformations.put(key, value);
            }

            //Testing this method
            System.out.println("Testing get_transformation");
            ArrayList<String> k = new ArrayList<String>(Arrays.asList("url_to_loc_x","json"));
            Transformations v = (Transformations) transformations.get(k);
            System.out.println(v);

            k = new ArrayList<String>(Arrays.asList("url_to_loc_y","csv"));
            v = (Transformations) transformations.get(k);
            System.out.println(v);
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't load the transformations from the xml.");
            return;
        }
    }

    public void run_transformation() {
        try {

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Couldn't run the thread.");
        }

    }


}
