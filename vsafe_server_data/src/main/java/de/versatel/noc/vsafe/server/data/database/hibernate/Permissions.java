/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.versatel.noc.vsafe.server.data.database.hibernate;

import de.versatel.noc.vsafe.server.data.database.persistence.*;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulrich.stemmer
 */
@Entity
@Table(name = "permissions", catalog = "vSafe", schema = "")
@NamedQueries({
    @NamedQuery(name = "Permissions.findAll", query = "SELECT p FROM Permissions p"),
    @NamedQuery(name = "Permissions.findById", query = "SELECT p FROM Permissions p WHERE p.id = :id"),
    @NamedQuery(name = "Permissions.findByServiceId", query = "SELECT p FROM Permissions p WHERE p.serviceId = :serviceId"),
    @NamedQuery(name = "Permissions.findByUsergroupId", query = "SELECT p FROM Permissions p WHERE p.usergroupId = :usergroupId"),
    @NamedQuery(name = "Permissions.findByPermissions", query = "SELECT p FROM Permissions p WHERE p.permissions = :permissions")})
public class Permissions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "serviceId", nullable = false)
    private int serviceId;
    @Basic(optional = false)
    @Column(name = "usergroupId", nullable = false)
    private short usergroupId;
    @Basic(optional = false)
    @Column(name = "permissions", nullable = false, length = 32)
    private String permissions;

    public Permissions() {
    }

    public Permissions(Integer id) {
        this.id = id;
    }

    public Permissions(Integer id, int serviceId, short usergroupId, String permissions) {
        this.id = id;
        this.serviceId = serviceId;
        this.usergroupId = usergroupId;
        this.permissions = permissions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public short getUsergroupId() {
        return usergroupId;
    }

    public void setUsergroupId(short usergroupId) {
        this.usergroupId = usergroupId;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permissions)) {
            return false;
        }
        Permissions other = (Permissions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.versatel.noc.vSafe_Server.persistence.Permissions[id=" + id + "]";
    }

}
