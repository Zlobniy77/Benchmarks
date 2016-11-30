package entity;

import java.time.LocalDate;

/**
 * Created by Zlobniy on 30.11.2016.
 */
public class Person {

    private long id = 0;
    private String name;
    private String surName;
    private String address;
    private LocalDate birthday;

    public Person(){

    }

    public Person( String name, String surName, LocalDate birthday ){
        this.name = name;
        this.surName = surName;
        this.birthday = birthday;
    }

    public long getId(){
        return id;
    }

    public void setId( long id ){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public String getSurName(){
        return surName;
    }

    public void setSurName( String surName ){
        this.surName = surName;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress( String address ){
        this.address = address;
    }

    public LocalDate getBirthday(){
        return birthday;
    }

    public void setBirthday( LocalDate birthday ){
        this.birthday = birthday;
    }
}
