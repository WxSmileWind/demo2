package com.example.demo.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
//过滤字段null值
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID=1L;
    private Integer id;
    private  Integer student_id;
    private String name;
    private String password;
    private Integer age;
    private String sex;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //JsonFormat   时间格式化
    private Date birthday;
    private List<Others> others;

    //以下是需要通过查询其它表得到的字段信息
    /**
     * Transident  注解表示非实体表的字段；
     */
    @Transient
    private String countothers;//外表字段

    public void setCountothers(String countothers) {
        this.countothers = countothers;
    }

    public String getCountothers() {
        return countothers;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getStudent_id() {
        return student_id;
    }
    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setOthers(List<Others> others) {
        this.others = others;
    }

    public List<Others> getOthers() {
        return others;
    }

    /* getter and setter */
}
