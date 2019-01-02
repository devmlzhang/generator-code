package com.ryoma.stuff.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StuffPutStorageRel implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 库存ID
     */
    private Long cookId;

    /**
     * 项目id
     */
    private Long projId;

    /**
     * 关联t_stuff_put_storage表的put_code
     */
    private String storagePutCode;

    /**
     * 材料id
     */
    private Long stuffId;

    /**
     * 材料型号
     */
    private String stuffStandard;

    /**
     * 材料单位
     */
    private String stuffUnit;

    /**
     * 材料来源
     */
    private Integer stuffResource;

    /**
     * 材料名称
     */
    private String stuffName;

    /**
     * 隶属等级(1.一级 2.二级 3.三级)
     */
    private Integer level;

    /**
     * 入库数量
     */
    private Long putNum;

    /**
     * 入库单价
     */
    private BigDecimal putPrice;

    /**
     * 合计(计算规则:put_num*price)
     */
    private BigDecimal total;

    /**
     * 入库类型
     */
    private Integer putType;

    /**
     * 更新用户id
     */
    private Long updateUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户id
     */
    private Long createUserId;

    /**
     * 备注
     */
    private String remark;

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

    public Long getCookId() {
        return cookId;
    }

    public void setCookId(Long cookId) {
        this.cookId = cookId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getStoragePutCode() {
        return storagePutCode;
    }

    public void setStoragePutCode(String storagePutCode) {
        this.storagePutCode = storagePutCode == null ? null : storagePutCode.trim();
    }

    public Long getStuffId() {
        return stuffId;
    }

    public void setStuffId(Long stuffId) {
        this.stuffId = stuffId;
    }

    public String getStuffStandard() {
        return stuffStandard;
    }

    public void setStuffStandard(String stuffStandard) {
        this.stuffStandard = stuffStandard == null ? null : stuffStandard.trim();
    }

    public String getStuffUnit() {
        return stuffUnit;
    }

    public void setStuffUnit(String stuffUnit) {
        this.stuffUnit = stuffUnit == null ? null : stuffUnit.trim();
    }

    public Integer getStuffResource() {
        return stuffResource;
    }

    public void setStuffResource(Integer stuffResource) {
        this.stuffResource = stuffResource;
    }

    public String getStuffName() {
        return stuffName;
    }

    public void setStuffName(String stuffName) {
        this.stuffName = stuffName == null ? null : stuffName.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getPutNum() {
        return putNum;
    }

    public void setPutNum(Long putNum) {
        this.putNum = putNum;
    }

    public BigDecimal getPutPrice() {
        return putPrice;
    }

    public void setPutPrice(BigDecimal putPrice) {
        this.putPrice = putPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getPutType() {
        return putType;
    }

    public void setPutType(Integer putType) {
        this.putType = putType;
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        sb.append(", cookId=").append(cookId);
        sb.append(", projId=").append(projId);
        sb.append(", storagePutCode=").append(storagePutCode);
        sb.append(", stuffId=").append(stuffId);
        sb.append(", stuffStandard=").append(stuffStandard);
        sb.append(", stuffUnit=").append(stuffUnit);
        sb.append(", stuffResource=").append(stuffResource);
        sb.append(", stuffName=").append(stuffName);
        sb.append(", level=").append(level);
        sb.append(", putNum=").append(putNum);
        sb.append(", putPrice=").append(putPrice);
        sb.append(", total=").append(total);
        sb.append(", putType=").append(putType);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}