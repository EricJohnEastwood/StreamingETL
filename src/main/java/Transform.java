import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
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

            System.out.println(source_table.getTableName());
            System.out.println(source_table.getColumnName());
            System.out.println(source_table.getUrlRunModule());
            System.out.println(source_table.getStoreDataModule());

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

            System.out.println(target_table.getTableName());
            System.out.println(target_table.getColumnName());
            System.out.println(target_table.getStoreDataModule());

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't insert target table Data");
            return;
        }

    }

    public static void get_transformation(String filename) {


    }

    public void run_transformation() {

    }


}
