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
@Table(name = "treemodels", catalog = "vSafe", schema = "")
@NamedQueries({
    @NamedQuery(name = "Treemodels.findAll", query = "SELECT t FROM Treemodels t"),
    @NamedQuery(name = "Treemodels.findById", query = "SELECT t FROM Treemodels t WHERE t.id = :id"),
    @NamedQuery(name = "Treemodels.findByModel", query = "SELECT t FROM Treemodels t WHERE t.model = :model"),
    @NamedQuery(name = "Treemodels.findByObject", query = "SELECT t FROM Treemodels t WHERE t.object = :object"),
    @NamedQuery(name = "Treemodels.findByElementid", query = "SELECT t FROM Treemodels t WHERE t.elementid = :elementid"),
    @NamedQuery(name = "Treemodels.findByParentid", query = "SELECT t FROM Treemodels t WHERE t.parentid = :parentid"),
    @NamedQuery(name = "Treemodels.findByName", query = "SELECT t FROM Treemodels t WHERE t.name = :name"),
    @NamedQuery(name = "Treemodels.findByLayer", query = "SELECT t FROM Treemodels t WHERE t.layer = :layer"),
    @NamedQuery(name = "Treemodels.findByPosition", query = "SELECT t FROM Treemodels t WHERE t.position = :position"),
    @NamedQuery(name = "Treemodels.findByAttribute", query = "SELECT t FROM Treemodels t WHERE t.attribute = :attribute"),
    @NamedQuery(name = "Treemodels.findByIntval", query = "SELECT t FROM Treemodels t WHERE t.intval = :intval"),
    @NamedQuery(name = "Treemodels.findByStringval", query = "SELECT t FROM Treemodels t WHERE t.stringval = :stringval"),
    @NamedQuery(name = "Treemodels.findByBoolval", query = "SELECT t FROM Treemodels t WHERE t.boolval = :boolval"),
    @NamedQuery(name = "Treemodels.findByLongval", query = "SELECT t FROM Treemodels t WHERE t.longval = :longval")})
public class Treemodel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "model", nullable = false)
    private short model;
    @Basic(optional = false)
    @Column(name = "object", nullable = false, length = 20)
    private String object;
    @Basic(optional = false)
    @Column(name = "elementid", nullable = false)
    private short elementid;
    @Basic(optional = false)
    @Column(name = "parentid", nullable = false)
    private short parentid;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Basic(optional = false)
    @Column(name = "layer", nullable = false)
    private short layer;
    @Column(name = "position")
    private Short position;
    @Column(name = "attribute", length = 20)
    private String attribute;
    @Column(name = "intval")
    private Integer intval;
    @Column(name = "stringval", length = 255)
    private String stringval;
    @Column(name = "boolval")
    private Boolean boolval;
    @Column(name = "longval")
    private Integer longval;

    public Treemodel() {
    }

    public Treemodel(Integer id) {
        this.id = id;
    }

    public Treemodel(Integer id, short model, String object, short elementid, short parentid, String name, short layer) {
        this.id = id;
        this.model = model;
        this.object = object;
        this.elementid = elementid;
        this.parentid = parentid;
        this.name = name;
        this.layer = layer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getModel() {
        return model;
    }

    public void setModel(short model) {
        this.model = model;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public short getElementid() {
        return elementid;
    }

    public void setElementid(short elementid) {
        this.elementid = elementid;
    }

    public short getParentid() {
        return parentid;
    }

    public void setParentid(short parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLayer() {
        return layer;
    }

    public void setLayer(short layer) {
        this.layer = layer;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Integer getIntval() {
        return intval;
    }

    public void setIntval(Integer intval) {
        this.intval = intval;
    }

    public String getStringval() {
        return stringval;
    }

    public void setStringval(String stringval) {
        this.stringval = stringval;
    }

    public Boolean getBoolval() {
        return boolval;
    }

    public void setBoolval(Boolean boolval) {
        this.boolval = boolval;
    }

    public Integer getLongval() {
        return longval;
    }

    public void setLongval(Integer longval) {
        this.longval = longval;
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
        if (!(object instanceof Treemodel)) {
            return false;
        }
        Treemodel other = (Treemodel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.versatel.noc.vSafe_Server.persistence.Treemodels[id=" + id + "]";
    }

}
