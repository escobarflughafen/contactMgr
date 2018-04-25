package classes;

public class admin {
    private int id;
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

    public void setUsername(String username){
        this.username = username;
    }

    public admin(String username,String password){
        setUsername(username);
        setPassword(password);
    }

    @Override
    public String toString(){
        return ("Admin{" +
                "id = "+ id +
                ", username = " + username + '\\' +
                ", password = " + password + '\\' +
                '}');

    }
}
