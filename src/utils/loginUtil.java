package utils;

import classes.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginUtil {

    public admin login(Connection conn, admin user) throws Exception{
        admin loginAdmin = null;
        String sql = "select * from t_user where username = ? and password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());


        ResultSet rs = pstmt.executeQuery();

        if(rs.next()){
            loginAdmin = new admin();
            loginAdmin.setUsername(rs.getString("username"));
            loginAdmin.setPassword(rs.getString("password"));
        }

        return loginAdmin;

    }

    public int createAdmin(Connection con, admin newAdmin) throws Exception{
        String sql = "insert into t_user values ()";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,newAdmin.getUsername());
        pstmt.setString(2,newAdmin.getPassword());

        return pstmt.executeUpdate();
    }

    public int deleteAdmin(Connection con, admin user) throws Exception{
        String sql = "delete from t_user where username = ? and password = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());

        return pstmt.executeUpdate();
    }

    public int adminEditPassword(Connection con, admin user) throws Exception{
        String sql = "update t_user set username = ? where password = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());

        return pstmt.executeUpdate();
    }



}
