package de.versatel.noc.vsafe.userservice.common;

/**
 *
 * @author ulrich.stemmer
 */
public interface UserGroupInterface {

    public String getName();
    public String getRemark();
    public int getUserGroupId();
    public void setName(String name);
    public void setRemark(String remark);
    public void setUserGroupId(int userGroupId);
}
