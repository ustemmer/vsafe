package de.versatel.noc.vsafe.server.data.database.hibernate;

import de.versatel.noc.vsafe.server.data.database.persistence.*;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulrich.stemmer
 */
@Entity
@Table(name = "usergroups")
@NamedQueries({
    @NamedQuery(name = "Usergroups.findAll", query = "SELECT u FROM Usergroups u"),
    @NamedQuery(name = "Usergroups.findByUserGroupId", query = "SELECT u FROM Usergroups u WHERE u.userGroupId = :userGroupId"),
    @NamedQuery(name = "Usergroups.findByName", query = "SELECT u FROM Usergroups u WHERE u.name = :name"),
    @NamedQuery(name = "Usergroups.findByRemark", query = "SELECT u FROM Usergroups u WHERE u.remark = :remark"),
    @NamedQuery(name = "Usergroups.findByMaxWrongLogins", query = "SELECT u FROM Usergroups u WHERE u.maxWrongLogins = :maxWrongLogins"),
    @NamedQuery(name = "Usergroups.findByMaxSimultLogins", query = "SELECT u FROM Usergroups u WHERE u.maxSimultLogins = :maxSimultLogins")})
public class Usergroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "userGroupId")
    private Integer userGroupId;
    @Column(name = "name")
    private String name;
    @Column(name = "remark")
    private String remark;
    @Column(name = "maxWrongLogins")
    private Short maxWrongLogins;
    @Column(name = "maxSimultLogins")
    private Short maxSimultLogins;
    @Lob
    @Column(name = "permissions")
    private byte[] permissions;

    public Usergroup() {
    }

    public Usergroup(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getMaxWrongLogins() {
        return maxWrongLogins;
    }

    public void setMaxWrongLogins(Short maxWrongLogins) {
        this.maxWrongLogins = maxWrongLogins;
    }

    public Short getMaxSimultLogins() {
        return maxSimultLogins;
    }

    public void setMaxSimultLogins(Short maxSimultLogins) {
        this.maxSimultLogins = maxSimultLogins;
    }

    public byte[] getPermissions() {
        return permissions;
    }

    public void setPermissions(byte[] permissions) {
        this.permissions = permissions;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userGroupId != null ? userGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usergroup)) {
            return false;
        }
        Usergroup other = (Usergroup) object;
        if ((this.userGroupId == null && other.userGroupId != null) || (this.userGroupId != null && !this.userGroupId.equals(other.userGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.versatel.noc.vSafe_Server.persistence.Usergroups[userGroupId=" + userGroupId + "]";
    }

}
