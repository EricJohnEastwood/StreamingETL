import java.sql.*;
import java.util.ArrayList;

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

    public SourceTable selectFromTable(String selectCommand, EngineData engine) {
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            ResultSet rs = null;
            rs = stmt.executeQuery(selectCommand);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            String tableName = rsMetaData.getTableName(4);
            ArrayList<String> columnValues = new ArrayList<String>();

            if(rs.next()) {
                for (int i = 2; i <= engine.getSourceTable().getColumnName().size()+1; i++) { // indexing starts from 1
                    columnValues.add(rs.getString(i));
                    System.out.println(rs.getString(i));
                }
            }
            else {
                System.out.println("Not Found / Table Empty");
            }
            conn.commit();
            stmt.close();
            System.out.println(selectCommand);
            return new SourceTable(tableName,columnValues);
        } catch(Exception se){
            se.printStackTrace();
        }
        finally {
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
        }
        return null;
    }

}
