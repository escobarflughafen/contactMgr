package utils;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import classes.member;

public class dbUtil {

    private static final String USERNAME    = "root@localhost";
    private static final String PASSWORD    = "114514yjsnpi";
    private static final String DRIVER      = "com.mysql.cj.jdbc.Driver";
    private static final String URL         = "jdbc:mysql://localhost:3306/contactMgr?autoReconnect=true&useSSL=false&user=root&password=114514yjsnpi";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public dbUtil(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }   catch (Exception e){
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws Exception{
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(URL);
        return con;
    }

    public void closeConnection(Connection con) throws Exception{
        if (con != null){
            con.close();
        }
    }


}
