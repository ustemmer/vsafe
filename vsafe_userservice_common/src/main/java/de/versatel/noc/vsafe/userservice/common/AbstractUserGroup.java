package de.versatel.noc.vsafe.userservice.common;

import de.versatel.noc.vsafe.vsafe_services_shared.Service;
import de.versatel.noc.vsafe.vsafe_services_shared.ServicePermissions;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public abstract class AbstractUserGroup implements UserGroupInterface, java.io.Serializable, Comparable {

    protected List<AbstractUser> users;
    protected List<Service> services;
    protected List<ServicePermissions> servicePermissions;
    protected int userGroupId;
    protected String name;
    protected String remark;

    public AbstractUserGroup(){
        this.users = new ArrayList<AbstractUser>();
        this.servicePermissions = new ArrayList<ServicePermissions>();
        this.userGroupId = 0;
        this.name = "neue Benutzergruppe";
        this.remark = "";
    }

    public boolean addServicePermissions(Service service) {
        ServicePermissions sp = new ServicePermissions(service);
        servicePermissions.add(sp);
        return true;
    }

    public boolean addUser(AbstractUser user) {
        users.add(user);
        Collections.sort(users);
        return true;
    }

    public int compareTo(Object o) {
        if (o instanceof AbstractUserGroup) {
            return ((AbstractUserGroup) o).name.compareToIgnoreCase(name);
        } else {
            throw new UnsupportedOperationException("Object not a UserGroup.");
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

    public BitSet getPermissionBits(long serviceId) {
        for (ServicePermissions pc : servicePermissions) {
            if (pc.getService().getServiceId() == serviceId) {
                return pc.getPermissionBitSet();
            }
        }
        return null;
    }

    public List<AbstractUser> getUsers() {
        return users;
    }

    public boolean hasUsers() {
        if (users.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return the permissionBit
     */
    public boolean isPermitted(long serviceId, int index) {
        for (ServicePermissions pc : servicePermissions) {
            if (pc.getService().getServiceId() == serviceId) {
                return pc.getPermissionBit(index);
            }
        }
        return false;
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean removeUser(AbstractUser user) {
            return users.remove(user);
    }

    public boolean removeServicePermissions(long serviceId) {
        for (ServicePermissions sp : servicePermissions) {
            if (sp.getService().getServiceId() == serviceId) {
                servicePermissions.remove(sp);
                return true;
            }
        }
        return false;
    }

    public boolean removeServicePermissions() {
        for (ServicePermissions sp : servicePermissions) {
            servicePermissions.remove(sp);
            sp = null;
        }
        return true;
    }

    public void setPermissionBit(long serviceId, int index, boolean value) {
        for (ServicePermissions pc : servicePermissions) {
            if (pc.getService().getServiceId() == serviceId) {
                pc.setPermissionBit(index, value);
            }
        }
    }

    /**
     * @param permissionBytes the permissionBytes to set
     */
    public void setPermissionHexString(long serviceId, String HexString) {
        for (ServicePermissions pc : servicePermissions) {
            if (pc.getService().getServiceId() == serviceId) {
                pc.setPermissionHexString(HexString);
            }
        }
    }

    public void setServices(List<Service> services) {
        this.services = services;
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

    /**
     *
     * @param username
     * @return
     */
    @Override
    public String toString() {
        return name;
    }


}
