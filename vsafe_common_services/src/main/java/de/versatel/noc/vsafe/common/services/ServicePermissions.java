package de.versatel.noc.vsafe.common.services;

import de.versatel.noc.vsafe.vsafe_core_shared.SystemSettings;
import de.versatel.noc.vsafe.common.services.util.Converters;
import java.util.BitSet;

/**
 *
 * @author ulrich.stemmer
 */
public class ServicePermissions {

    private Service service;
    private BitSet permissionBitSet;
    private String permissionHexString;

    public ServicePermissions(Service service) {
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

    public void setPermissionBit(int index) {
        permissionBitSet.set(index);
        permissionHexString = Converters.bitSetToHexString(permissionBitSet);
    }

    public void setPermissionBit(int index, boolean bool) {
        permissionBitSet.set(index, bool);
        permissionHexString = Converters.bitSetToHexString(permissionBitSet);
    }

    public void setPermissionHexString(String hexString) {
        this.permissionHexString = hexString;
        this.permissionBitSet = Converters.hexStringToBitSet(hexString);
    }

    public String getPermissionHexString() {
        return permissionHexString;
    }

}
