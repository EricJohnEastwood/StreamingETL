package com.etl.gui;//import org.omg.CORBA.portable.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class CommandLineTransformations {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testDB";

    static final String USER = "root";
    static final String PASS = "3.14152.7182";

    public Connection conn;
    public Statement stmt;

    public CommandLineTransformations() {
        conn = null;
        stmt = null;
    }

    public void createConnection()
    {
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

    public void executeStatement(String Statement){
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(Statement);

            conn.commit();
            stmt.close();

            System.out.println(Statement);
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

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        CommandLineTransformations obj = new CommandLineTransformations();
        obj.createConnection();
        while(true){
            System.out.println("Enter your choice\n");
            int ch;
            ch = scanner.nextInt();
            if(ch == 0)
            {
                break;
            }
            else if(ch == 1) // Create Table
            {
                System.out.println("Enter the name of the table");
                String table = scanner.next();
                String createTableStatement = "CREATE TABLE "+table+" (";
                System.out.println("Enter the no of columns");
                int no_of_columns = scanner.nextInt();
                String column, type;
                for(int i=0; i<no_of_columns; i++)
                {
                    System.out.println("Enter the column name");
                    column = scanner.next();
                    System.out.println("Enter the type");
                    type = scanner.next();
                    createTableStatement+=" "+column+" "+type;
                    if(i!=(no_of_columns-1))
                        createTableStatement+=",";
                }
                createTableStatement+=" );";
                System.out.println(createTableStatement);
                obj.executeStatement(createTableStatement);
            }
            else if(ch==2) // DROP TABLE
            {
                System.out.println("Enter the name of the table to drop");
                String table = scanner.next();
                String dropTable = "DROP TABLE 2"+table+";";
                System.out.println(dropTable);
                obj.executeStatement(dropTable);
            }
            else if(ch==3) //ADD COLUMN
            {
                System.out.println("Enter the table name");
                String table = scanner.next();
                System.out.println("Enter a new column");
                String col = scanner.next();
                System.out.println("Enter the type");
                String type = scanner.next();
                String insertColumn;
                insertColumn = "ALTER TABLE "+table+" ADD COLUMN "+col+" "+type;
                System.out.println(insertColumn);
                obj.executeStatement(insertColumn);
            }
            else if(ch==4) // DROP COLUMN
            {
                System.out.println("Enter the table name");
                String table = scanner.next();
                System.out.println("Enter a new column");
                String col = scanner.next();
                String dropColumn;
                dropColumn = "ALTER TABLE "+table+" DROP COLUMN "+col+";";
                System.out.println(dropColumn);
                obj.executeStatement(dropColumn);
            }
            else if(ch==5) // ADD PRIMARY KEY
            {
                System.out.println("Enter the table name");
                String table = scanner.next();
                System.out.println("Enter a new column");
                String col = scanner.next();
                String addPrimaryKey;
                addPrimaryKey = "ALTER TABLE "+table+" ADD PRIMARY KEY("+col+");";
                System.out.println(addPrimaryKey);
                obj.executeStatement(addPrimaryKey);
            }
            else if(ch==6) //DELETE PRIMARY KEY
            {
                System.out.println("Enter the table name");
                String table = scanner.next();
                String deleteKey;
                deleteKey = "ALTER TABLE "+table+" DROP PRIMARY KEY;";
                System.out.println(deleteKey);
                obj.executeStatement(deleteKey);
            }
            else if(ch==7) //ADD FOREIGN KEY
            {
                System.out.println("Enter the Referencee table");
                String referenceeTable = scanner.next();
                System.out.println("Enter the Referencing table name");
                String referencingTable = scanner.next();
                System.out.println("Enter the referencing foreign key name");
                String referencingForeignKeyName = scanner.next();
                System.out.println("Enter the referencee foreign key name");
                String referenceeForeignKeyName = scanner.next();
                System.out.println("Enter the name of the constraint");
                String constraint = scanner.next();
                String addForeignKey = "ALTER TABLE "+referenceeTable+" ADD CONSTRAINT "+constraint+" FOREIGN KEY ("+referenceeForeignKeyName+") REFERENCES " +
                        referencingTable+"("+referencingForeignKeyName+");";
                System.out.println(addForeignKey);
                obj.executeStatement(addForeignKey);
            }
            else if(ch==8)// DROP FOREIGN KEY
            {
                System.out.println("Enter the foreign key table name");
                String table = scanner.next();
                System.out.println("Enter the name of the foreign key constraint to drop");
                String constraint = scanner.next();
                String dropForeignKey = "ALTER TABLE "+table+" DROP CONSTRAINT "+constraint+";";
                System.out.println(dropForeignKey);
                obj.executeStatement(dropForeignKey);
            }
            else if(ch==9)// Querying using WHERE
            {
                System.out.println("Enter the name of the table");
                String table = scanner.next();
                System.out.println("Enter the number of columns");
                int no_of_columns = scanner.nextInt();
                System.out.println("Enter the list of columns");
                String columns = "";
                for(int i=0; i<no_of_columns; i++)
                {
                    String column = scanner.next();
                    columns+=(column);
                    if(i<(no_of_columns-1))
                    {
                        columns+=", ";
                    }
                }
                System.out.println("Enter your condition");
                String condition = scanner.next();
                String selectCondition = "SELECT "+columns+" FROM "+table+" WHERE "+condition+";";
                System.out.println(selectCondition);
                obj.executeStatement(selectCondition);
            }
            else if(ch==10)// Querying using PATTERN
            {
                System.out.println("Enter the name of the table");
                String table = scanner.next();
                System.out.println("Enter the number of columns");
                int no_of_columns = scanner.nextInt();
                System.out.println("Enter the list of columns");
                String columns = "";
                for(int i=0; i<no_of_columns; i++)
                {
                    String column = scanner.next();
                    columns+=(column);
                    if(i<(no_of_columns-1))
                    {
                        columns+=", ";
                    }
                }
                System.out.println("Enter your condition");
                String pattern = scanner.next();
                String patternSelect = "SELECT "+columns+" FROM "+table+" WHERE column LIKE "+pattern+";";
                System.out.println(patternSelect);
                obj.executeStatement(patternSelect);
            }
            else if(ch==11)// Querying using regular expression
            {
                System.out.println("Enter the name of the table");
                String table = scanner.next();
                System.out.println("Enter the number of columns");
                int no_of_columns = scanner.nextInt();
                System.out.println("Enter the list of columns");
                String columns = "";
                for(int i=0; i<no_of_columns; i++)
                {
                    String column = scanner.next();
                    columns+=(column);
                    if(i<(no_of_columns-1))
                    {
                        columns+=", ";
                    }
                }
                System.out.println("Enter your condition");
                String regularExpression = scanner.next();
                String regularExpressionSelect = "SELECT "+columns+" FROM "+table+" WHERE column RLIKE "+regularExpression+";";
                System.out.println(regularExpressionSelect);
                obj.executeStatement(regularExpressionSelect);
            }

        }
        obj.disconnectFromDB();

    }
}
