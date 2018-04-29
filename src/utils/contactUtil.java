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
import java.util.regex.*;



public class contactUtil {
    // use stack to save deleted rows
    private String database;
    private String username;



    public contactUtil(){


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
        ret.setName(obj2str(table.getValueAt(rowIndex,1)));
        ret.setGroup(obj2str(table.getValueAt(rowIndex,2)));
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

    public void contactSearch(String searchAttempt, Vector<member> contacts, Vector<String> searchResult, Vector<Integer> searchResultIndex, JList list){
        String rPattern = "";
        String searchArr[] = searchAttempt.split(" ");

        for(int j = 0; j < searchArr.length; j++){
            rPattern += searchArr[j];
            if(j != searchArr.length - 1){
                rPattern += ".*";
            }
        }

        rPattern = ".*" + rPattern + ".*";

        System.out.println("contactSearch REGEX PATTERN: " + rPattern); // SHOW REGEX PATTERN IN COMMAND LINE

        boolean isMatch = false;

        for(int i = 0; i < contacts.size(); i++){
           /* isMatch = Pattern.matches(rPattern,contactsArray[i]);
            if(isMatch){
                list.setListData();
            } */
           isMatch = Pattern.matches(rPattern,contacts.get(i).toString());
           if(isMatch){
               searchResult.add(contacts.get(i).getName() + " (" + contacts.get(i).toString() + ")");
               searchResultIndex.add(i);
           }

        }

        list.setListData(searchResult);




    }

}
