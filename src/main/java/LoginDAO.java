/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/


import java.sql.*;

/** Responsible to interact with database to authenticate user credentials */
public class LoginDAO {

    /** Authenticates user */
    public boolean authenticateUser(String username, String password) throws SQLException {

        Connection con = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
        String getSql = "SELECT password from java_projects_schema.uvc_login WHERE user_id=? ";
        PreparedStatement prpdStmt = con.prepareStatement(getSql);
        prpdStmt.setString(1,username);
        ResultSet rs = prpdStmt.executeQuery();

        /** If user credentials are correct */
        if(rs.next()){
            return password.equals(rs.getString("password"));
        }
        /** If user credentials are incorrect */
        return false;

    }

}
