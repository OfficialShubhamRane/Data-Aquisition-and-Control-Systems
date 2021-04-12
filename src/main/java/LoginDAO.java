/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/

import java.sql.*;

public class LoginDAO {

    public static boolean authenticateUser(String username, String password) throws SQLException {

        Connection con = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
        Statement stmt = con.createStatement();
        String getSql = "SELECT password from neon.user WHERE user_id=? ";
        PreparedStatement prpdStmt = con.prepareStatement(getSql);
        prpdStmt.setString(1,username);
        ResultSet rs = prpdStmt.executeQuery();
        rs.next();

        return password.equals(rs.getString("password"));
    }
}
