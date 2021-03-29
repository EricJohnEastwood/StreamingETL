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
        this.conn = null;
        this.stmt = null;
    }

    public void connectToDB() {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected...");

        } catch(Exception se){
            se.printStackTrace();
        }

    }

    public void disconnectFromDB() {
        try {
            this.conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }finally{
            try{
                if(this.stmt!=null)
                    this.stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    this.conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Disconnected");
    }

    public void insertIntoTable(String insertCommand) {
        try {
            conn.setAutoCommit(false);
            this.stmt = this.conn.createStatement();
            this.stmt.executeUpdate(insertCommand);

            this.conn.commit();
            this.stmt.close();

            System.out.println(insertCommand);
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            try{

                if(this.stmt!=null)
                    this.stmt.close();
            }catch(SQLException se2){
            }
        }
    }

    public void deleteFromTable(String deleteCommand) {
        try {
            this.conn.setAutoCommit(false);
            this.stmt = this.conn.createStatement();
            this.stmt.executeUpdate(deleteCommand);

            this.conn.commit();
            this.stmt.close();

            System.out.println(deleteCommand);
        } catch(Exception se){

            se.printStackTrace();
        }
        finally {
            try{

                if(this.stmt!=null)
                    this.stmt.close();
            }catch(SQLException se2){
            }
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
