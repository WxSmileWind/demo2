package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("grid_event_event")
@ApiModel("事件Event对象")
public class Event {

    @ApiModelProperty("主键")
    @Column(name="ID")
    private String ID;

    @ApiModelProperty("事件编码")
    @Column(name="c_NUMBER")
    private String c_NUMBER;

    @ApiModelProperty("事件类型")
    private String c_TYPE;

    @ApiModelProperty("事件描述")
    private String content;

    @ApiModelProperty("地址")
    private String ADDRESS;

    @ApiModelProperty("X坐标")
    private String COORDINATEX;

    @ApiModelProperty("Y坐标")
    private String COORDINATEY;

    @ApiModelProperty("创建部门uid")
    private String ORGUID;

    @ApiModelProperty("创建人姓名")
    private String userName;

    @ApiModelProperty("创建人userid")
    private String userId;

    @ApiModelProperty("事件创建时间")
    private Long CREATETIME;

    @ApiModelProperty("更新时间")
    private Long UPDATETIME;

    @ApiModelProperty("事件状态")
    private String c_STATUS;

    @ApiModelProperty("事件模块来源")
    private String SOURCE;

    @ApiModelProperty("截止事件")
    private Long endTime;

    @ApiModelProperty("是否紧急")
    private String emergency;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("首页推送标志")
    private String showStatus;

    @ApiModelProperty("是否重点场所")
    private String isKeyPlace;

    @ApiModelProperty("事件发生时间")
    private Long eventFoundTime;

    @ApiModelProperty("涉及人数")
    private String involvePeoNum;

    @ApiModelProperty("事件规模")
    private String eventScaleType;

    @ApiModelProperty("是否重点关注")
    private String isImportant;

    @ApiModelProperty("重点人员")
    private String keyPeople;

    @ApiModelProperty("关闭时间")
    private Long finishtime;

    @ApiModelProperty("设备来源")
    private String deviceSource;

    @ApiModelProperty("综治细类")
    private String subType;

    @ApiModelProperty("事件级别")
    private String eventLevel;

    @ApiModelProperty("是否是省综治对接的事件")
    private String isszz;


    private Integer isSyncToSzz;

    @ApiModelProperty("省事件编号")
    private String snumber;

    @ApiModelProperty("是否回退")
    private String isHt;

    @ApiModelProperty("是否删除")
    private String isDelete;

    @ApiModelProperty("流转节点")
    private String activeOrgUid;

    @ApiModelProperty("判断是否是网格自关闭")
    private String isDoSelf;

    private String syncStateJfmt;

    private String outSystem;

    private Integer outSystemDealStatus;

    private String xtStatus;

    private Integer isXt;

    private String xtWhereto;

    private String reporterName;

    private String reporterPhone;

    private String bzType;

    @ApiModelProperty("标志描述")
    private String bzContent;

    @ApiModelProperty("标注事件预计完成时间（到达这个时间会重启事件）")
    private String bzFinishDate;

    @ApiModelProperty("复杂问题关联的事件id")
    private String bzLinkEvent;

    @ApiModelProperty("转派镇街")
    private String dispatchZjName;

    @ApiModelProperty("是否复杂问题关联事件")
    private Integer isLinkFzwt;

    @ApiModelProperty("考核有效：0 未处理,1 有效,2 无效")
    private String khKhyx;

    @ApiModelProperty("字数有效：0 未处理,1 有效,2 无效")
    private String khZsyx;

    @ApiModelProperty("图片有效：0 未处理,1 有效,2 无效")
    private String khTpyx;

    @ApiModelProperty("三级事件即时关闭标志：0 未处理 1 即时关闭 2 超时关闭")
    private String finishInTime;

    private String accessIdentity;

    @ApiModelProperty("星级")
    private Double star;

    @ApiModelProperty("维稳事件类型（0 普通事件 1 维稳事件）")
    private Integer stableType;

    @ApiModelProperty("涉及国家安全关注要点（0表示没有）")
    private Integer securityCarePoint;

    @ApiModelProperty("旧版事件类型")
    private String oldType;

    @ApiModelProperty("旧版事件签注子类型")
    private String oldSubType;


}