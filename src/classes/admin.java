package classes;

public class admin {
    //private boolean flag = false;
    private String username;
    private String password;

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

/*
    public void isLogin(){
        flag = true;
    }

    public void isLogout(){
        flag = false;
    }
*/

    public void setUsername(String username){
        this.username = username;
    }

    public admin(){

    }

}
