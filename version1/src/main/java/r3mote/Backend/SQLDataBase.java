package r3mote.Backend;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SQLDataBase {
    
    public boolean databaseExists(String dbFilePath){
        File dbFile = new File(dbFilePath);
        return dbFile.exists();
    }

    public static Connection getConnection() throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try {

            String db = "jdbc:sqlite:C:/sqlite3/db/students.db";
            connection = DriverManager.getConnection(db);
            
            System.out.println("Connected to database...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return connection;
    }

    public void createUser(String username, String password) throws Exception
    {

        Connection connection;
        PreparedStatement pstmt;

        try{

            connection = getConnection();
            pstmt = connection.prepareStatement("INSERT INTO staff(username, password) VALUES (?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();
        
        }catch(SQLException e){
            e.getMessage();
        }
    }

}
