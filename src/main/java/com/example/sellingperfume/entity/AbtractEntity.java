package com.example.sellingperfume.entity;



import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
public abstract class AbtractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Create_at", columnDefinition = "DATETIME")
    private LocalDateTime createAt;

    @Column(name = "create_by", columnDefinition = "int")
    private Long createBy;

    @Column(name = "Update_at", columnDefinition = "DATETIME")
    private LocalDateTime updateAt;

    @Column(name = "Update_by", columnDefinition = "Long")
    private Long updateBy;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
