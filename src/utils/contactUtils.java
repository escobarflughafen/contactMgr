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

    }

    public void contactDataReload(){

    }

}
