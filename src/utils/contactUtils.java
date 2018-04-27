package utils;

import classes.member;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Stack;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.revokeStack;



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

    public void deleteRow(JTable table, int rowIndex , DefaultTableModel model,revokeStack revokStack){
        if (rowIndex >= 0){
            revokStack.enIndex(rowIndex);
            String[] row = new String [table.getColumnCount()];
            for(int i = 0; i< table.getColumnCount() ; i++){
                row[i] = (table.getValueAt(rowIndex, i) == null) ? "" : table.getValueAt(rowIndex, i).toString();
                System.out.println(row[i]);
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

}
