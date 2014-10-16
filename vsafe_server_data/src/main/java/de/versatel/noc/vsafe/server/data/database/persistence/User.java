package de.versatel.noc.vsafe.server.data.database.persistence;

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
@Table(name = "users", catalog = "vSafe", schema = "")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserid", query = "SELECT u FROM Users u WHERE u.userid = :userid"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name"),
    @NamedQuery(name = "Users.findByFirstname", query = "SELECT u FROM Users u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByUserGroupId", query = "SELECT u FROM Users u WHERE u.userGroupId = :userGroupId"),
    @NamedQuery(name = "Users.findByState", query = "SELECT u FROM Users u WHERE u.state = :state"),
    @NamedQuery(name = "Users.findByActWrongLogins", query = "SELECT u FROM Users u WHERE u.actWrongLogins = :actWrongLogins"),
    @NamedQuery(name = "Users.findByMaxWrongLogins", query = "SELECT u FROM Users u WHERE u.maxWrongLogins = :maxWrongLogins"),
    @NamedQuery(name = "Users.findByMaxSessions", query = "SELECT u FROM Users u WHERE u.maxSessions = :maxSessions"),
    @NamedQuery(name = "Users.findBySessionTimeout", query = "SELECT u FROM Users u WHERE u.sessionTimeout = :sessionTimeout")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "userid", nullable = false)
    private Integer userid;
    @Column(name = "username", length = 50)
    private String username;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "firstname", length = 50)
    private String firstname;
    @Column(name = "password", length = 50)
    private String password;
    @Basic(optional = false)
    @Column(name = "userGroupId", nullable = false)
    private short userGroupId;
    @Basic(optional = false)
    @Column(name = "userState", nullable = false)
    private int userState;
    @Basic(optional = false)
    @Column(name = "actWrongLogins", nullable = false)
    private int actWrongLogins;
    @Basic(optional = false)
    @Column(name = "maxWrongLogins", nullable = false)
    private int maxWrongLogins;
    @Basic(optional = false)
    @Column(name = "maxSessions", nullable = false)
    private int maxSessions;
    @Basic(optional = false)
    @Column(name = "sessionTimeout", nullable = false)
    private long sessionTimeout;

    public User() {
    }

    public User(Integer userid) {
        this.userid = userid;
    }

    public User(Integer userid, short userGroupId, int userState, int actWrongLogins, int maxWrongLogins, int maxSessions, long sessionTimeout) {
        this.userid = userid;
        this.userGroupId = userGroupId;
        this.userState = userState;
        this.actWrongLogins = actWrongLogins;
        this.maxWrongLogins = maxWrongLogins;
        this.maxSessions = maxSessions;
        this.sessionTimeout = sessionTimeout;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(short userGroupId) {
        this.userGroupId = userGroupId;
    }

    public int getState() {
        return userState;
    }

    public void setState(int userState) {
        this.userState = userState;
    }

    public int getActWrongLogins() {
        return actWrongLogins;
    }

    public void setActWrongLogins(int actWrongLogins) {
        this.actWrongLogins = actWrongLogins;
    }

    public int getMaxWrongLogins() {
        return maxWrongLogins;
    }

    public void setMaxWrongLogins(int maxWrongLogins) {
        this.maxWrongLogins = maxWrongLogins;
    }

    public int getMaxSessions() {
        return maxSessions;
    }

    public void setMaxSessions(int maxSessions) {
        this.maxSessions = maxSessions;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.versatel.noc.vSafe_Server.persistence.Users[userid=" + userid + "]";
    }

}
