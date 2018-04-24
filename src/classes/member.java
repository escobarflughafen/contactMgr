package classes;

import sun.jvm.hotspot.debugger.Address;

public class member{
    private String id;
    private String name;
    private int group;
    private int grade;
    private int clas;
    private String phoneNum;
    private String email;
    private String dormitory;
    private String address;

    public member(String id,String name,int group,int grade,int clas,String phoneNum,String email,String dormitory,String address){
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

    public void setGroup(int group){
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

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getGroup() {
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
