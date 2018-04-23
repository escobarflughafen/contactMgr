package classes;

import sun.jvm.hotspot.debugger.Address;

public class member{
    private int id;
    private String name;
    private String group;
    private int grade;
    private int clas;
    private String phoneNum;
    private String email;
    private String dormitory;
    private String address;

    public member(int id,String name,String group,int clas,String phoneNum,String email,String dormitory,String address){

    }


    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGroup(String group){
        this.group = group;
    }

    public void setGrade(int grade){
        this.grade = grade;
    }

    public void setClas (int clas){
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

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int getGrade(){
        return grade;
    }

    public int getClas(){
        return clas;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    public String getEmail(){
        return email;
    }

    public String getDormitory(){
        return dormitory;
    }

    public String getAddress(){
        return address;
    }
}
