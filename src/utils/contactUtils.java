package utils;

import classes.member;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Stack;
import java.util.Vector;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.revokeStack;



public class contactUtils {
    // use stack to save deleted rows
    private String database;
    private String username;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/contacts";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection  conn;
    private Statement   stmt;

    public contactUtils(){


        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public Connection getConn(){
        try{
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public String getOne(){
        System.out.println();
        return "";
    }

    public void setDatabase(String database){
        this.database = database;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setUserInfo(DefaultTableModel model){

    }

    public void contactDataReload(){

    }

    public void deleteRow(JTable table, int rowIndex , DefaultTableModel model,revokeStack revokStack){
        if (rowIndex >= 0){
            revokStack.enIndex(rowIndex);
            String[] row = new String [table.getColumnCount()];
            for(int i = 0; i< table.getColumnCount() ; i++){
                row[i] = ((table.getValueAt(rowIndex, i) == null) ? "" : table.getValueAt(rowIndex, i).toString());
                System.out.print(row[i] + ((i == table.getColumnCount()) ? "" :","));
            }

            revokStack.enDelet(row);
            model.removeRow(rowIndex);

        }
    }

    public void revokeDeletRow(JTable table, DefaultTableModel model, revokeStack revokStack){
        model.insertRow(revokStack.popIndex(),revokStack.popRevoke());
    }


    public void saveRow(JTable table, int rowIndex, DefaultTableModel model,member memInfo){

            int tempRowIndex = rowIndex;
            model.removeRow(tempRowIndex);
            model.insertRow(tempRowIndex, memInfo.toArray());


    }

    public void createNewRow(JTable table, int rowIndex, DefaultTableModel model){
        String[] emptyRow = {""};
        model.insertRow((rowIndex == 0 && table.getRowCount() == 0) ? rowIndex : rowIndex + 1, emptyRow); // 插入空表：插入非空表
    }

    public String obj2str(Object obj){
        return (obj != null) ? obj.toString() : "";
    }

    public member readRowToMember(JTable table, int rowIndex){
        member ret = new member();
        ret.setId(obj2str(table.getValueAt(rowIndex,0)));
        ret.setName(obj2str(table.getValueAt(rowIndex,1) != null));
        ret.setGroup(obj2str(table.getValueAt(rowIndex,2) != null));
        ret.setGrade(obj2str(table.getValueAt(rowIndex,3)));
        ret.setClas(obj2str(table.getValueAt(rowIndex,4)));
        ret.setPhoneNum(obj2str(table.getValueAt(rowIndex,5)));
        ret.setEmail(obj2str(table.getValueAt(rowIndex,6)));
        ret.setDormitory(obj2str(table.getValueAt(rowIndex,7)));
        ret.setAddress(obj2str(table.getValueAt(rowIndex,8)));
        return ret;
    }

    public Vector <member> createAllContactObjects(JTable table){
        Vector <member> ret = new Vector<>();
        for(int i=0; i < table.getRowCount(); i++){
            ret.add(readRowToMember(table,i));
        }
        return ret;
    }

    public void contactSearch(){

    }

}
