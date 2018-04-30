package utils;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import classes.member;

public class dbUtil {

    private static final String USERNAME    = "root";
    private static final String PASSWORD    = "root";
    private static final String DRIVER      = "com.mysql.jdbc.Driver";
    private static final String URL         = "jdbc:mysql://localhost:3306/contacts";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public dbUtil(){
        try{

        }   catch   (Exception e){
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws Exception{
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return con;
    }

    public void closeConnection(Connection con) throws Exception{
        if (con != null){
            con.close();
        }
    }


}
