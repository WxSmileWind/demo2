package com.example.demo.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Data
@ApiModel(value = "Others",description = "其他类")
//过滤字段null值
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Others implements Serializable {
    @ApiModelProperty(value = "姓名",dataType = "String",example = "小妈")
    private String name;
    @ApiModelProperty("学生ID")
    private Integer student_id;
    private String desc;

    private String oname;
    private Integer ostudent_id;
    private String odesc;



}
