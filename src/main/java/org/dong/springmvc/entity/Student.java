package org.dong.springmvc.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Past;
import java.util.Date;

public class Student {
    @NumberFormat(pattern = "###,#")//数字格式化
    private int id ;
    @NotEmpty(message = "用户名不能为空")
    private String name ;
    private int age ;
    private Address address ;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Email//要求该属性必须满足邮箱的格式
    private String email ;

    @Past(message = "出生日期必须是过去的时间！！！")//当前时间以前
    @DateTimeFormat( pattern = "yyyy-MM-dd")//日期格式化：前台传递来的数据，将前台传递来的数据 固定为yyyy-MM-dd
    private Date birthday ;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
