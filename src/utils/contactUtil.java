package utils;

import classes.member;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Stack;
import java.util.Vector;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import utils.revokeStack;
import java.util.regex.*;
import utils.memberUtil;
import utils.dbUtil;



public class contactUtil {
    // use stack to save deleted rows
    private String database;
    private String username;
    private dbUtil dbLink = new dbUtil();



    public contactUtil(){
        try {
            Connection con = dbLink.getConnection();

        } catch (Exception e){
            e.printStackTrace();
        }

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




    public void saveRow(JTable table, int rowIndex, DefaultTableModel model,member memInfo){

            int tempRowIndex = rowIndex;
            model.removeRow(tempRowIndex);
            model.insertRow(tempRowIndex, memInfo.toArray());


    }


    public String obj2str(Object obj){
        return (obj != null) ? obj.toString() : "";
    }

    public member readRowToMember(JTable table, int rowIndex){
        member ret = new member(obj2str(table.getValueAt(rowIndex,0)),
                                obj2str(table.getValueAt(rowIndex,1)),
                                obj2str(table.getValueAt(rowIndex,2)),
                                obj2str(table.getValueAt(rowIndex,3)),
                obj2str(table.getValueAt(rowIndex,4)),
                obj2str(table.getValueAt(rowIndex,5)),
                obj2str(table.getValueAt(rowIndex,6)),
                obj2str(table.getValueAt(rowIndex,7)),
                obj2str(table.getValueAt(rowIndex,8)));
        /*
        ret.setId(obj2str(table.getValueAt(rowIndex,0)));
        ret.setName(obj2str(table.getValueAt(rowIndex,1)));
        ret.setGroup(obj2str(table.getValueAt(rowIndex,2)));
        ret.setGrade(obj2str(table.getValueAt(rowIndex,3)));
        ret.setClas(obj2str(table.getValueAt(rowIndex,4)));
        ret.setPhoneNum(obj2str(table.getValueAt(rowIndex,5)));
        ret.setEmail(obj2str(table.getValueAt(rowIndex,6)));
        ret.setDormitory(obj2str(table.getValueAt(rowIndex,7)));
        ret.setAddress(obj2str(table.getValueAt(rowIndex,8)));
        */
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

    public void treeBuild(Vector <member> members, String[] groups, DefaultMutableTreeNode root){
        for(int i = 0; i < members.size(); i++){
            if(members.get(i).getGroup() == groups[0]){
                ((DefaultMutableTreeNode)root.getChildAt(0)).add(new DefaultMutableTreeNode(members.get(i).getName()));
            }
            if(members.get(i).getGroup() == groups[1]){
                ((DefaultMutableTreeNode)root.getChildAt(1)).add(new DefaultMutableTreeNode(members.get(i).getName()));
            }
            if(members.get(i).getGroup() == groups[2]){
                ((DefaultMutableTreeNode)root.getChildAt(2)).add(new DefaultMutableTreeNode(members.get(i).getName()));
            }
            if(members.get(i).getGroup() == groups[3]){
                ((DefaultMutableTreeNode)root.getChildAt(3)).add(new DefaultMutableTreeNode(members.get(i).getName()));
            }
            if(members.get(i).getGroup() == groups[4]){
                ((DefaultMutableTreeNode)root.getChildAt(4)).add(new DefaultMutableTreeNode(members.get(i).getName()));
            }
            if(members.get(i).getGroup() == groups[5]){
                ((DefaultMutableTreeNode)root.getChildAt(5)).add(new DefaultMutableTreeNode(members.get(i).getName()));
            }
        }
    }


    public void tableRefresh(DefaultTableModel model, Vector<member> contacts){
        if(model.getRowCount() > 0) {
            for (int j = model.getRowCount() - 1; j >= 0; j--) {
                model.removeRow(j);
            }
        }

        /*Vector <member> tempVec = contacts;
        for(int k = tempVec.size() -1; k >= 0; k--){
            if(tempVec.get(k) == null) tempVec.remove(k);
        }*/

        for (int i = 0; i < contacts.size(); i++){
            model.insertRow(i, contacts.get(i).toArray());
        }

    }

    public boolean swapMember(Vector <member> vec, int pos1, int pos2) {
        if (pos1 < 0 || pos2 < 0 || pos1 >= vec.size() || pos2 >= vec.size())
            return false;

        member temp1 = vec.get(pos1);
        member temp2 = vec.get(pos2);

        vec.remove(pos1);
        vec.insertElementAt(temp2, pos1);
        vec.remove(pos2);
        vec.insertElementAt(temp1, pos2);

        return true;
    }





}
