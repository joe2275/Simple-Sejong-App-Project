package com.example.whgml.sejongapps.Model;

public class User {
    private int id;
    private String name;
    private String email;
    private int age;
    private String password;

    public int getID()
    {
        return id;
    }
    public void setID(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
}
