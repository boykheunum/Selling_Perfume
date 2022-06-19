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

    @Column(name = "create_by", columnDefinition = "nvarchar(50)")
    private String createBy;

    @Column(name = "Update_at", columnDefinition = "DATETIME")
    private LocalDateTime updateAt;

    @Column(name = "Update_by", columnDefinition = "nvarchar(50)")
    private String updateBy;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
