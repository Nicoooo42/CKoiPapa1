package com.demotxt.droidsrce.homedashboard.Model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private Integer id;
    private String name;
    private String email;
    private String roles;
    private Integer nbApiCall;
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getNbApiCall() {
        return nbApiCall;
    }

    public void setNbApiCall(Integer nbApiCall) {
        this.nbApiCall = nbApiCall;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
