package com.example.sellingperfume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "User_Authority")
public class UserAuthorityEntity extends AbtractEntity {
    @Column(name = "User_id", columnDefinition = "Long")
    private Long userId;
    @Column(name = "Authority_id", columnDefinition = "Long")
    private String authorityId;
    @Column(name = "view_authority", columnDefinition = "varchar(1) default 0")
    private String viewAuthority;
    @Column(name="create_authority", columnDefinition = "varchar(1) default 0")
    private String createAuthority;
    @Column(name = "update_authority", columnDefinition = "varchar(1) default 0")
    private String updateAuthority;
    @Column(name = "delete_authority", columnDefinition = "varchar(1) default 0")
    private String deleteAuthority;
    @Column(name = "export_authority", columnDefinition = "varchar(1) default 0")
    private String exportAuthority;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getViewAuthority() {
        return viewAuthority;
    }

    public void setViewAuthority(String viewAuthority) {
        this.viewAuthority = viewAuthority;
    }

    public String getCreateAuthority() {
        return createAuthority;
    }

    public void setCreateAuthority(String createAuthority) {
        this.createAuthority = createAuthority;
    }

    public String getUpdateAuthority() {
        return updateAuthority;
    }

    public void setUpdateAuthority(String updateAuthority) {
        this.updateAuthority = updateAuthority;
    }

    public String getDeleteAuthority() {
        return deleteAuthority;
    }

    public void setDeleteAuthority(String deleteAuthority) {
        this.deleteAuthority = deleteAuthority;
    }

    public String getExportAuthority() {
        return exportAuthority;
    }

    public void setExportAuthority(String exportAuthority) {
        this.exportAuthority = exportAuthority;
    }
}
