package de.versatel.noc.vsafe.server.data.database.mysql.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;
import de.versatel.noc.vSafe.services.userService.PermissionsDTO;
import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;
import de.versatel.noc.vSafe_Server.users.UserException;



import de.versatel.noc.vSafe_Server.users.ServicePermissions;
import de.versatel.noc.vSafe_Server.users.UserGroup;

public class SQLPermissions {

    public static final String DATABASE = "vSafe";
    public static final String KOMMA = ",";
    public static final String SEMIKOLON = ";";
    public static final String SPACE = " ";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String PERMISSIONS = DATABASE + ".permissions";
    public static final String PERMISSIONS_A = DATABASE + ".permissions a";
    public static final String P_F01_ID = "id";
    public static final String P_F02_SERVICEID = "serviceId";
    public static final String P_F03_USERGROUPID = "usergroupId";
    public static final String P_F04_PERMISSIONS = "permissions";
    private final MySQL_PoolHandling mySQL_PoolHandling;

    /**
     *
     */
    public SQLPermissions(final MySQL_PoolHandling mySQL_PoolHandling) {
        this.mySQL_PoolHandling = mySQL_PoolHandling;
    }

    public int addPermissions(int usergroupid, ServicePermissions pc) throws UserException {
        if (pc == null) {
            throw new UserException("SQLPermissions.addPermissions", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION);
        }

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO '");
        sql.append(PERMISSIONS);
        sql.append("' ");

        StringBuilder sqlFields = new StringBuilder();
        sqlFields.append("(");

        StringBuilder sqlValues = new StringBuilder();
        sqlValues.append("(");

        sqlFields.append("'");
        sqlFields.append(P_F02_SERVICEID);
        sqlFields.append("'");
        sqlValues.append("'");
        sqlValues.append(pc.getService().getServiceId());
        sqlValues.append("'");

        sqlFields.append(", '");
        sqlFields.append(P_F03_USERGROUPID);
        sqlFields.append("'");
        sqlValues.append(", '");
        sqlValues.append(usergroupid);
        sqlValues.append("'");

        sqlFields.append(", '");
        sqlFields.append(P_F04_PERMISSIONS);
        sqlFields.append("'");
        sqlValues.append(", '");
        sqlValues.append(pc.getPermissionHexString());
        sqlValues.append("'");

        sql.append(sqlFields);
        sql.append(") VALUES (");
        sql.append(sqlValues);
        sql.append(");");

        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    private List<PermissionsDTO> getPermissions(ResultSet rSet) throws SQLException, UserException {
        if (rSet == null) {
            throw new UserException("SQLPermissions.getPermissions", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION);
        }
        rSet.last();
        int rows = rSet.getRow();
        if (rows > 0) {
            List<PermissionsDTO> sps = new ArrayList<PermissionsDTO>();
            rSet.beforeFirst();
            for (int i = 0; i
                    < rows; i++) {
                rSet.next();
                PermissionsDTO sp = new PermissionsDTO();
                sp.setPermissionid(rSet.getInt(P_F01_ID));
                sp.setServiceid(rSet.getInt(P_F02_SERVICEID));
                sp.setPermissionid(rSet.getInt(P_F03_USERGROUPID));
                sp.setPermissionString(rSet.getString(P_F04_PERMISSIONS));
                sps.add(sp);
            }
            return sps;
        }
        return null;
    }

    public List<PermissionsDTO> getPermissions(int userGroupId) throws SQLException, UserException {
        if (userGroupId < 0) {
            throw new UserException("SQLPermissions.getPermissions", ExceptionCodes.USERGROUP_WRONGGROUPID);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(PERMISSIONS);
        sql.append(" WHERE ");
        sql.append(P_F03_USERGROUPID);
        sql.append(" = '");
        sql.append(userGroupId);
        sql.append("';");
        return getPermissions(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    public List<PermissionsDTO> getPermissions() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(PERMISSIONS);
        sql.append(" ORDER BY '");
        sql.append(P_F01_ID);
        sql.append("';");
        return getPermissions(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    public List<PermissionsDTO> getPermissions(UserGroup ug, int serviceId) throws SQLException, UserException {
        if (ug == null) {
            throw new UserException("SQLPermissions.getPermissions", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION);
        }
        if (serviceId < 0) {
            throw new UserException("SQLPermissions.getPermissions", ExceptionCodes.SERVICE_WRONGSERVICE);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(PERMISSIONS);
        sql.append(" WHERE ");
        sql.append(P_F03_USERGROUPID);
        sql.append(" = '");
        sql.append(ug.getUserGroupId());
        sql.append("' AND ");
        sql.append(P_F02_SERVICEID);
        sql.append(" = '");
        sql.append(serviceId);
        sql.append("';");
        return getPermissions(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    public List<PermissionsDTO> getPermissionsForService(int serviceId) throws SQLException, UserException {
        if (serviceId < 0) {
            throw new UserException("SQLPermissions.getPermissionsForService", ExceptionCodes.SERVICE_WRONGSERVICE);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(PERMISSIONS);
        sql.append(" WHERE ");
        sql.append(P_F02_SERVICEID);
        sql.append(" = '");
        sql.append(serviceId);
        sql.append("';");
        return getPermissions(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    public List<PermissionsDTO> getPermissionsForUserGroup(UserGroup ug) throws SQLException, UserException {
        if (ug == null) {
            throw new UserException("SQLPermissions.getPermissionsForUserGroup", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(PERMISSIONS);
        sql.append(" WHERE ");
        sql.append(P_F03_USERGROUPID);
        sql.append(" = '");
        sql.append(ug.getUserGroupId());
        sql.append("';");
        return getPermissions(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    public int getPermissionId(UserGroup ug, int serviceId) throws SQLException, UserException {
        if (ug == null) {
            throw new UserException("SQLPermissions.getPermissionId", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION);
        }
        if (serviceId < 0) {
            throw new UserException("SQLPermissions.getPermissionId", ExceptionCodes.SERVICE_WRONGSERVICE);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT '");
        sql.append(P_F01_ID);
        sql.append("' FROM ");
        sql.append(PERMISSIONS);
        sql.append(" WHERE ");
        sql.append(P_F03_USERGROUPID);
        sql.append(" = '");
        sql.append(ug.getUserGroupId());
        sql.append("' AND ");
        sql.append(P_F02_SERVICEID);
        sql.append(" = '");
        sql.append(serviceId);
        sql.append("';");
        return getPermissionId(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    private int getPermissionId(ResultSet rSet) throws SQLException, UserException {
        if (rSet == null) {
            throw new UserException("SQLPermissions.getPermissionId", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION);
        }
        rSet.last();
        int rows = rSet.getRow();
        if (rows > 0) {
            rSet.beforeFirst();
            rSet.next();
            return rSet.getInt(P_F01_ID);
        }
        return -1;
    }

    public int removePermissions(long serviceId) throws UserException {
        if (serviceId < 0) {
            throw new UserException("SQLPermissions.removePermissions", ExceptionCodes.SERVICE_WRONGSERVICE);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(PERMISSIONS);
        sql.append(" WHERE ");
        sql.append(P_F02_SERVICEID);
        sql.append(" ='");
        sql.append(serviceId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int removePermissions(UserGroup ug) throws UserException {
        if (ug == null) {
            throw new UserException("SQLPermissions.removePermissions", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(PERMISSIONS);
        sql.append(" WHERE ");
        sql.append(P_F03_USERGROUPID);
        sql.append(" ='");
        sql.append(ug.getUserGroupId());
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int removePermissions(int serviceId) throws UserException {
        if (serviceId < 0) {
            throw new UserException("SQLPermissions.removePermissions", ExceptionCodes.SERVICE_WRONGSERVICE);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(PERMISSIONS);
        sql.append(" WHERE ");
        sql.append(P_F02_SERVICEID);
        sql.append(" ='");
        sql.append(serviceId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setPermissions(int id, String hexString)  throws UserException {
        if (id < 0) {
            throw new UserException("SQLPermissions.setPermissions", ExceptionCodes.USERGROUP_WRONGPERMISSIONID);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(PERMISSIONS);
        sql.append(" SET ");
        sql.append(P_F04_PERMISSIONS);
        sql.append(" ='");
        sql.append(hexString);
        sql.append("' WHERE ");
        sql.append(P_F01_ID);
        sql.append(" ='");
        sql.append(id);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setPermissions(int usergroupid, int serviceid, String hexString)  throws UserException {
        if (usergroupid < 0) {
            throw new UserException("SQLPermissions.setPermissions", ExceptionCodes.USERGROUP_WRONGPERMISSIONID);
        }
        if (serviceid < 0) {
            throw new UserException("SQLPermissions.setPermissions", ExceptionCodes.SERVICE_WRONGSERVICE);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(PERMISSIONS);
        sql.append(" SET ");
        sql.append(P_F04_PERMISSIONS);
        sql.append(" ='");
        sql.append(hexString);
        sql.append("' WHERE ");
        sql.append(P_F03_USERGROUPID);
        sql.append(" = '");
        sql.append(usergroupid);
        sql.append("' AND ");
        sql.append(P_F02_SERVICEID);
        sql.append(" = '");
        sql.append(serviceid);
        sql.append("';");

        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }
}
