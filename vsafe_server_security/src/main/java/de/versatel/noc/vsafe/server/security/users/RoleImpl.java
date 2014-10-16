package de.versatel.noc.vsafe.server.security.users;

import de.versatel.noc.vSafe.services.Service;
import de.versatel.noc.vSafe.services.userService.UserGroupInterface;
import de.versatel.noc.vSafe.services.userService.UserGroupDTO;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ulrich.stemmer
 */
public class RoleImpl implements UserGroupInterface, java.io.Serializable, Comparable {

    protected UserHandling userHandling;
    protected List<User> users = new ArrayList<User>();
    //protected List<Service> services = new ArrayList<Service>();
    protected List<ServicePermissions> servicePermissions = new ArrayList<ServicePermissions>();
    protected int userGroupId;
    protected String name;
    protected String remark;

    /*public RoleImpl() {
    }*/

    public RoleImpl(UserHandling uh, UserGroupDTO dto) {
        this.userHandling = uh;
        this.userGroupId = dto.getUserGroupId();
        this.name = dto.getName();
        this.remark = dto.getRemark();
    }

    public RoleImpl(UserHandling uh) {
        this.userHandling = uh;
    }

    public UserHandling getUserHandling() {
        return userHandling;
    }

    public boolean addServicePermissions(Service service) {
        ServicePermissions sp = new ServicePermissions(this, service);
        servicePermissions.add(sp);
        return true;
    }

    public boolean addUser(User user) {
        users.add(user);
        Collections.sort(users);
        return true;
    }

    public int compareTo(Object o) {
        if (o instanceof RoleImpl) {
            return ((RoleImpl) o).name.compareToIgnoreCase(name);
        } else {
            throw new UnsupportedOperationException("Object not a UserGroup.");
        }
    }

    public UserGroupDTO getDTO(){
        UserGroupDTO dto = new UserGroupDTO();
        dto.setName(name);
        dto.setRemark(remark);
        dto.setUserGroupId(userGroupId);
        return dto;
    
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

    public List<User> getUsers() {
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
    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public boolean removeServicePermissions(long serviceId) {
        for (ServicePermissions sp : servicePermissions) {
            if (sp.getService().getServiceId() == serviceId) {
                servicePermissions.remove(sp);
                sp = null;
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
        for (ServicePermissions sp : servicePermissions) {
            if (sp.getService().getServiceId() == serviceId) {
                sp.setPermissionHexString(HexString);
            }
        }
    }

    /*public void setServices(List<Service> services) {
        this.services = services;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void setUserHandling(UserHandling uh) {
        this.userHandling = uh;
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
