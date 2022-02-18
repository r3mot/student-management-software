package r3mote.Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Staff {
    
    private String userName;
    private String password;

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Staff(String userName, String password){
        super();
        this.userName = userName;
        this.password = password;
    }

    public boolean registerUser(Staff newStaff) throws Exception{

        Connection connection;
        PreparedStatement pstmt;

        try{
            connection = SQLDataBase.GetConnection();

            pstmt = connection.prepareStatement("INSERT INTO Staff (username, password) VALUES (?,?)");

            String encryptedPassword = Security.encrypt(newStaff.getPassword().getBytes(), newStaff.getPassword());
            pstmt.setString(1, newStaff.getUserName());
            pstmt.setString(2, encryptedPassword);
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();

            return true;
        }catch(SQLException e){
            if(e.toString().equals("Tag mismatch!"))
                System.out.println("Duplicate");
            //e.printStackTrace();
        }
        return false;
    
    }

    public boolean validateUser(Staff staff) throws Exception{

        Connection connection;
        PreparedStatement pstmt;
        ResultSet dbQuery;
        boolean isValidLogin = false;
        String usernameQuery, passwordQuery;
        String decryptedPassword;

        try{

            connection = SQLDataBase.GetConnection();

            String query = "SELECT username, password FROM staff WHERE username=?";
            pstmt = connection.prepareStatement(query);

            pstmt.setString(1, staff.getUserName());
            dbQuery = pstmt.executeQuery();

            while(dbQuery.next()){

                usernameQuery = dbQuery.getString("username");
                passwordQuery = dbQuery.getString("password");
                decryptedPassword = Security.decrypt(passwordQuery, staff.getPassword());

                if(decryptedPassword.equals(staff.getPassword()) && usernameQuery.equals(staff.getUserName())){
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
