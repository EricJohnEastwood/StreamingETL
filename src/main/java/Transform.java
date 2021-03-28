import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class Transform {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ETL";

    static final String USER = "root";
    static final String PASS = "";

    public Connection conn;
    public Statement stmt;

    //constructor
    public Transform() {
        conn = null;
        stmt = null;
    }

    public void connectToDB() {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected...");

        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void disconnectFromDB() {
        try {
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Disconnected");
    }


    public static void get_source_table(String filename) {
        try{
            String tableName;
            ArrayList<String> columnName = new ArrayList<String>();

            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();

            Document document=builder.parse(new File("./src/main/resources/" + filename));
            NodeList source_Target = document.getElementsByTagName("sourceTarget");

            System.out.println("Source Target: "+ source_Target.getLength());
            Element eElement = (Element) source_Target.item(0);

            tableName = eElement.getElementsByTagName("table").item(0).getTextContent();
            NodeList column_list = eElement.getElementsByTagName("column");

            for(int i = 0; i < column_list.getLength(); i++) {
                columnName.add(column_list.item(i).getTextContent());
            }

            SourceTable src_table = new SourceTable(tableName, columnName);

            System.out.println(src_table.getTableName());
            System.out.println(src_table.getColumnName());
            System.out.println("get_data_from_URL : "+ eElement.getElementsByTagName("data_request").item(0).getTextContent());
            System.out.println("store_in_table : "+ eElement.getElementsByTagName("store_class").item(0).getTextContent());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void get_target_table(String filename) {

    }

    public static void get_transformation(String filename) {

    }

    public void run_transformation() {

    }


}
