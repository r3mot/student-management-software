package r3mote.Backend;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void createUser(String username, String rawPassword) throws Exception
    {

        Connection connection;
        PreparedStatement pstmt;

        try{

            Security aes = new Security();
            aes.init();
            String encryptedPassword = aes.decrypt(rawPassword);
            System.out.println(encryptedPassword);

            connection = getConnection();
            pstmt = connection.prepareStatement("INSERT INTO Staff (username, password) VALUES (?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, encryptedPassword);
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();

            System.out.println("User created");
   
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean login(String u, String p) throws Exception{

        Connection connection;
        PreparedStatement pstmt;
        ResultSet rs;

        try{

            Security aes = new Security();
            aes.init();

            connection = getConnection();
            
            pstmt = connection.prepareStatement("SELECT username, password FROM staff WHERE username=?");
            pstmt.setString(1, u);
            rs = pstmt.executeQuery();

            String user = "", pass = "";
            while(rs.next()){
                user = rs.getString("username");
                pass = rs.getString("password");

                String decryptedPassword = aes.decrypt(pass);
                if(decryptedPassword.equals(p)){
                    System.out.println("Login success");
                    return true;
                }else{
                    System.out.println("Login failed");
                    return false;
                }
            }

                rs.close();
                connection.close();
                pstmt.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
