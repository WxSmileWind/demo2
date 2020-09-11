package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴啸
 * @since 2020-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("grid_org_role")
@ApiModel(value="GridOrgRole对象", description="")
public class GridOrgRole implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("CODE")
    private String code;

    @TableField("CREATETIME")
    private Long createtime;


}
