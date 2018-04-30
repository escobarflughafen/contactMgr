package classes;

import javafx.beans.binding.ObjectExpression;
import sun.jvm.hotspot.debugger.Address;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class member{
    private String id;
    private String name;
    private String group;
    private String grade;
    private String clas;
    private String phoneNum;
    private String email;
    private String dormitory;
    private String address;

    public member(String id,String name,String group,String grade,String clas,String phoneNum,String email,String dormitory,String address){

        setId(id);
        setName(name);
        setAddress(address);
        setClas(clas);
        setDormitory(dormitory);
        setEmail(email);
        setGroup(group);
        setPhoneNum(phoneNum);
        setGrade(grade);

    }


    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGroup(String group){
        this.group = group;
    }

    public void setGrade(String grade){
        this.grade = grade;
    }

    public void setClas (String clas){
        this.clas = clas;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setDormitory(String dormitory){
        this.dormitory = dormitory;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getId(){
        return ((id != null) ? id : "");
    }

    public String getName() {
        return ((name != null) ? name : "");
    }

    public String getGroup() {
        return ((group != null) ? group : "");
    }

    public String getGrade(){
        return ((grade != null) ? grade : "");
    }

    public String getClas(){
        return ((clas != null) ? clas : "");
    }

    public String getPhoneNum(){
        return ((phoneNum != null) ? phoneNum : "");
    }

    public String getEmail(){
        return ((email != null) ? email : "");
    }

    public String getDormitory(){
        return ((dormitory != null) ? dormitory : "");
    }

    public String getAddress(){
        return ((address != null) ? address : "");
    }

    public String[] getRecord(){
        String[] record = {id,name,group,grade,clas,phoneNum,email,dormitory,address};
        return record;
    }

    public void makeTableItem(DefaultTableModel model){
        Object[] rowData = {getId(),getName(),getGroup(),getGrade(),
                                getClas(),getPhoneNum(),getEmail(),
                                getDormitory(),getAddress()};
        model.addRow(rowData);
    }

    public String toString(){
           return (  id +
                    "," + name +
                    "," + group +
                    "," + grade +
                    "," + clas +
                    "," + phoneNum +
                    "," + email +
                    "," + dormitory +
                    "," + address);
    }



    public String[] toArray(){

        String[] array = {getId(),getName(),getGroup(),getGrade(),getClas(),getPhoneNum(),getEmail(),getDormitory(),getAddress()};

        return array;
    }
}
