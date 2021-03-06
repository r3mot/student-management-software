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

    public static Connection GetConnection() throws ClassNotFoundException {

        Connection connection = null;
        try {

            Class.forName("org.sqlite.JDBC");
            String db = "jdbc:sqlite:C:/sqlite3/db/students.db";
            connection = DriverManager.getConnection(db);

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return connection;
    }

    public boolean addUser(String username, String rawPassword) throws Exception
    {
        Connection connection;
        PreparedStatement pstmt;

        try{
            connection = GetConnection();

            pstmt = connection.prepareStatement("INSERT INTO Staff (username, password) VALUES (?,?)");

            String encryptedPassword = Security.encrypt(rawPassword.getBytes(), rawPassword);
            pstmt.setString(1, username);
            pstmt.setString(2, encryptedPassword);
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();

            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean validateLogin(String user, String pass) throws Exception{

        Connection connection;
        PreparedStatement pstmt;
        ResultSet dbQuery;
        boolean isValidLogin = false;
        String usernameQuery, passwordQuery;
        String decryptedPassword;

        try{

            connection = GetConnection();

            String query = "SELECT username, password FROM staff WHERE username=?";
            pstmt = connection.prepareStatement(query);

            pstmt.setString(1, user);
            dbQuery = pstmt.executeQuery();

            while(dbQuery.next()){

                usernameQuery = dbQuery.getString("username");
                passwordQuery = dbQuery.getString("password");
                decryptedPassword = Security.decrypt(passwordQuery, pass);

                if(decryptedPassword.equals(pass) && usernameQuery.equals(user)){
                    isValidLogin = true;
                }
            }
            
            dbQuery.close();
            connection.close();
            pstmt.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return isValidLogin;
    }
}
