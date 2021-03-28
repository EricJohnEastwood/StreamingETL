import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
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


    public static void get_source_db(String filename) {
        try{
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document document=builder.parse(new File("./src/main/resources/" + filename));
            // document.getDocumentElement().normalize();
            NodeList nodes_list = document.getElementsByTagName("sourceTarget");
            // source is the name of the element
            System.out.println("Source Target: "+nodes_list.getLength());

            Element eElement = (Element) nodes_list.item(0);
            System.out.println("Table Name: "+ eElement.getElementsByTagName("table").item(0).getTextContent());
            System.out.println("Column1 : "+ eElement.getElementsByTagName("column1").item(0).getTextContent());
            System.out.println("Column2 : "+ eElement.getElementsByTagName("column2").item(0).getTextContent());
            System.out.println("Column3 : "+ eElement.getElementsByTagName("column3").item(0).getTextContent());
            System.out.println("Column4 : "+ eElement.getElementsByTagName("column4").item(0).getTextContent());
            System.out.println("get_data_from_URL : "+ eElement.getElementsByTagName("column1").item(0).getTextContent());
            System.out.println("store_in_table : "+ eElement.getElementsByTagName("column1").item(0).getTextContent());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void get_target_db(String filename) {

    }

    public static void get_transformation(String filename) {

    }

    public void run_transformation() {

    }


}
