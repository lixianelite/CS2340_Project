package cs2340.gatech.edu.m4.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tianyunan on 9/29/17.
 */

public class User {
    private int id;
    public static List<String> UserType = Arrays.asList("User", "Admin");
    private String username, password, email, usertype;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setUsertype(String usertype){
        this.usertype = usertype;
    }
    public String getUsertype(){
        return usertype;
    }
}
