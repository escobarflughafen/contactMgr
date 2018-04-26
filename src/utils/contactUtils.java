package utils;

import classes.member;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Stack;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class contactUtils {
    // use stack to save deleted rows
    private String database;
    private String username;

    public contactUtils(){

    }

    public void setDatabase(String database){
        this.database = database;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setUserInfo(DefaultTableModel model){
        Vector <String> items = new Vector<>();
        Vector <String> values = new Vector<>();
        Vector <String> complex = new Vector<>();
        items.add("用户名");
        values.add(this.username);
        items.add("密码");
        values.add("123456");
        //model.insertRow(0,items.get(0),values.get(0));
        for(int i = 0; i < items.size(); i++){
            complex.add(items.get(i));
            complex.add(values.get(i));
            model.insertRow(i,complex);
            complex.remove(0);
            complex.remove(0);
        }
    }

}
