package de.versatel.noc.vsafe.userservice.common;

/**
 *
 * @author ulrich.stemmer
 */
public class PermissionsDTO{

    protected int permissionid;
    protected int serviceid;
    protected int usergroupid;
    protected String permissionString;

    public PermissionsDTO() {
    }

    public String getPermissionString() {
        return permissionString;
    }

    public void setPermissionString(String permissionString) {
        this.permissionString = permissionString;
    }

    public int getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(int permissionid) {
        this.permissionid = permissionid;
    }

    public int getServiceid() {
        return serviceid;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public int getUsergroupid() {
        return usergroupid;
    }

    public void setUsergroupid(int usergroupid) {
        this.usergroupid = usergroupid;
    }

}
