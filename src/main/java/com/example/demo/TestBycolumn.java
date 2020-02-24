package com.example.demo;
import lombok.Data;
@Data
public class TestBycolumn {
    public  String name;
    public  String job;
    public  String sex;
    public  String type;
    public void TestBycolumn(String name,String job,String sex,String type){
        this.name=name;
        this.job=job;
        this.sex=sex;
        this.type=type;
    }
}
