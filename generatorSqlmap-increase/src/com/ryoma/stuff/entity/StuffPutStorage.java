package com.ryoma.stuff.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StuffPutStorage implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 入库单编号
     */
    private String putCode;

    /**
     * 项目id
     */
    private Long projId;

    /**
     * 入库单名称
     */
    private String putName;

    /**
     * 入库类型
     */
    private Integer putType;

    /**
     * 施工部位
     */
    private String conPosition;

    /**
     * 关联t_stuff_month_plan表的id
     */
    private Long planId;

    /**
     * 入库管理员
     */
    private String managerUser;

    /**
     * 入库日期
     */
    private Date putDate;

    /**
     * 入库金额
     */
    private BigDecimal totalPrice;

    /**
     * 创建用户id
     */
    private Long createUserId;

    /**
     * 更新用户id
     */
    private Long updateUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0无效，1有效
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPutCode() {
        return putCode;
    }

    public void setPutCode(String putCode) {
        this.putCode = putCode == null ? null : putCode.trim();
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getPutName() {
        return putName;
    }

    public void setPutName(String putName) {
        this.putName = putName == null ? null : putName.trim();
    }

    public Integer getPutType() {
        return putType;
    }

    public void setPutType(Integer putType) {
        this.putType = putType;
    }

    public String getConPosition() {
        return conPosition;
    }

    public void setConPosition(String conPosition) {
        this.conPosition = conPosition == null ? null : conPosition.trim();
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getManagerUser() {
        return managerUser;
    }

    public void setManagerUser(String managerUser) {
        this.managerUser = managerUser == null ? null : managerUser.trim();
    }

    public Date getPutDate() {
        return putDate;
    }

    public void setPutDate(Date putDate) {
        this.putDate = putDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", putCode=").append(putCode);
        sb.append(", projId=").append(projId);
        sb.append(", putName=").append(putName);
        sb.append(", putType=").append(putType);
        sb.append(", conPosition=").append(conPosition);
        sb.append(", planId=").append(planId);
        sb.append(", managerUser=").append(managerUser);
        sb.append(", putDate=").append(putDate);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}