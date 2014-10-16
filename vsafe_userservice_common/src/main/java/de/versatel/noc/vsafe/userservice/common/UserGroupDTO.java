package de.versatel.noc.vsafe.userservice.common;

import java.io.Serializable;

/**
 *
 * @author ulrich.stemmer
 */
public class UserGroupDTO implements UserGroupInterface, Serializable{

    private int userGroupId;
    private String name;
    private String remark;

    public UserGroupDTO() {
    }

    public int compareTo(Object o) {
        if (o instanceof UserGroupDTO) {
            return ((UserGroupDTO) o).name.compareToIgnoreCase(name);
        } else {
            throw new UnsupportedOperationException("Object not a UserGroupDTO.");
        }
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }
}
