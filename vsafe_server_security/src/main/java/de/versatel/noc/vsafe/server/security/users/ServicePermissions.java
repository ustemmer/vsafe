package de.versatel.noc.vsafe.server.security.users;

import de.versatel.noc.vSafe.services.Service;
import de.versatel.noc.vSafe.util.Converters;
import de.versatel.noc.vSafe.system.SystemSettings;
import java.util.BitSet;

/**
 *
 * @author ulrich.stemmer
 */
public class ServicePermissions {

    protected final RoleImpl userGroup;
    protected final Service service;
    private BitSet permissionBitSet;
    private String permissionHexString;

    public ServicePermissions(RoleImpl userGroup, Service service) {
        this.userGroup = userGroup;
        this.service = service;
        permissionBitSet = new BitSet(SystemSettings.PERMISSION_ARRAYSIZE);
        permissionHexString = Converters.bitSetToHexString(permissionBitSet);
    }

    public Service getService() {
        return service;
    }

    public BitSet getPermissionBitSet() {
        return permissionBitSet;
    }

    public boolean getPermissionBit(int index) {
        return permissionBitSet.get(index);
    }

    public String getPermissionHexString() {
        return permissionHexString;
    }

    public RoleImpl getUserGroup() {
        return userGroup;
    }

    public void setPermissionBit(int index) {
        permissionBitSet.set(index);
        permissionHexString = Converters.bitSetToHexString(permissionBitSet);
    }

    public void setPermissionBit(int index, boolean newValue) {
        permissionBitSet.set(index, newValue);
        permissionHexString = Converters.bitSetToHexString(permissionBitSet);
    }

    public void setPermissionHexString(String hexString) {
        this.permissionHexString = hexString;
        this.permissionBitSet = Converters.hexStringToBitSet(hexString);
    }



}
