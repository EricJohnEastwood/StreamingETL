import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ETL";

    static final String USER = "root";
    static final String PASS = "";

    public Connection conn;
    public Statement stmt;

    //constructor
    public ConnectionDB() {
        conn = null;
        stmt = null;
    }

    public void connectToDB() {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected...");

        } catch(Exception se){
            se.printStackTrace();
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

    public void insertIntoTable(String insertCommand) {
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(insertCommand);

            conn.commit();
            stmt.close();

            System.out.println(insertCommand);
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            try{

                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
        }
    }

    public void deleteFromTable(String deleteCommand) {
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(deleteCommand);

            conn.commit();
            stmt.close();

            System.out.println(deleteCommand);
        } catch(Exception se){

            se.printStackTrace();
        }
        finally {
            try{

                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
        }
    }

    @Override
    public String toString() {
        return "ConnectionDB{}";
    }

//
//    public void selectFromTable(String selectCommand) {
//        try {
//    }
}
