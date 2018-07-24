package com.yeay.shorturl.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;


/**
 *
 * 实体基类，所有entity继承此父类
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, updatable = false, name = "create_date")
    private Date createDate;

    @Column(nullable = false, name = "modify_date")
    private Date modifyDate;

    public BaseEntity() {
    }

    public BaseEntity(Date createDate, Date modifyDate) {
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id='" + id + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
