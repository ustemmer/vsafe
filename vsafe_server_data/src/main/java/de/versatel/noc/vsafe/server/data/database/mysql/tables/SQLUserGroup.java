package de.versatel.noc.vsafe.server.data.database.mysql.tables;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.versatel.noc.vSafe.services.userService.UserGroupDTO;
import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;

import de.versatel.noc.vSafe_Server.database.DatabaseException;
import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;
import de.versatel.noc.vSafe_Server.users.UserException;

public class SQLUserGroup {

    private final String KOMMA = ",";
    private final String SEMIKOLON = ";";
    private final String SPACE = " ";
    private final String ASC = "ASC";
    private final String DESC = "DESC";
    private final String DATABASE = "vSafe";
    private final String USERGROUPS = DATABASE + ".usergroups";
    public static final String UG_F01_USERGOUPID = "userGroupId";
    public static final String UG_F02_NAME = "name";
    public static final String UG_F03_REMARK = "remark";
    private final MySQL_PoolHandling mySQL_PoolHandling;

    public SQLUserGroup(final MySQL_PoolHandling mySQL_PoolHandling) {
        this.mySQL_PoolHandling = mySQL_PoolHandling;
    }

    public int add(UserGroupDTO userGroupDTO) throws DatabaseException {
        if (userGroupDTO != null) {

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO '");
            sql.append(USERGROUPS);
            sql.append("' ");

            StringBuilder sqlFields = new StringBuilder();
            sqlFields.append("(");

            StringBuilder sqlValues = new StringBuilder();
            sqlValues.append("(");

            if (userGroupDTO.getName() != null && userGroupDTO.getName().length() > 0) {
                sqlFields.append("'");
                sqlFields.append(UG_F02_NAME);
                sqlFields.append("'");
                sqlValues.append("'");
                sqlValues.append(userGroupDTO.getName());
                sqlValues.append("'");
            } else {
                throw new DatabaseException(
                        "SQLUserGroup.add",
                        ExceptionCodes.USERGROUP_WRONGGROUPNAME);
            }
            sqlFields.append(", '");
            sqlFields.append(UG_F03_REMARK);
            sqlFields.append("'");
            sqlValues.append(", '");
            sqlValues.append(userGroupDTO.getRemark());
            sqlValues.append("'");
            sql.append(sqlFields);
            sql.append(") VALUES (");
            sql.append(sqlValues);
            sql.append(");");
            return mySQL_PoolHandling.executeUpdate(sql.toString());
        } else {
            throw new DatabaseException(
                    "SQLUserGroup.add",
                    ExceptionCodes.USERGROUP_NOUSERGROUP);
        }
    }

    public UserGroupDTO getUserGroupDTO(int userGroupId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(USERGROUPS);
        sql.append(" WHERE ");
        sql.append(UG_F01_USERGOUPID);
        sql.append(" = '");
        sql.append(userGroupId);
        sql.append("';");
        return getUserGroupDTO(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    private UserGroupDTO getUserGroupDTO(ResultSet rSet) {
        if (rSet == null) {
            return null;
        }
        try {
            rSet.last();
            int rows = rSet.getRow();
            rSet.first();
            if (rows == 0 || rows > 1) {
                return null;
            }
            UserGroupDTO userGroup = new UserGroupDTO();
            userGroup.setUserGroupId(rSet.getInt(SQLUserGroup.UG_F01_USERGOUPID));
            userGroup.setName(rSet.getString(SQLUserGroup.UG_F02_NAME));
            userGroup.setRemark(rSet.getString(SQLUserGroup.UG_F03_REMARK));
            return userGroup;
        } catch (SQLException e) {
            return null;
        }
    }

    public ResultSet getUserGroupNames() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ");
        sql.append(UG_F02_NAME);
        sql.append(" FROM  ");
        sql.append(USERGROUPS);
        sql.append(" ORDER BY '");
        sql.append(UG_F02_NAME);
        sql.append("';");
        return mySQL_PoolHandling.executeQuery(sql.toString());
    }

    public List<UserGroupDTO> getUserGroups() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(USERGROUPS);
        sql.append(" ORDER BY '");
        sql.append(UG_F02_NAME);
        sql.append("';");
        return getUserGroups(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    private List<UserGroupDTO> getUserGroups(ResultSet rSet) throws SQLException {
        if (rSet == null) {
            throw new SQLException("ResultSet='null'");
        }
        int rows;
        rSet.last();
        rows = rSet.getRow();
        if (rows > 0) {
            List<UserGroupDTO> ugs = new ArrayList<UserGroupDTO>();
            rSet.beforeFirst();
            for (int i = 0; i
                    < rows; i++) {
                rSet.next();
                UserGroupDTO userGroup = new UserGroupDTO();
                userGroup.setUserGroupId(rSet.getInt(SQLUserGroup.UG_F01_USERGOUPID));
                userGroup.setName(rSet.getString(SQLUserGroup.UG_F02_NAME));
                userGroup.setRemark(rSet.getString(SQLUserGroup.UG_F03_REMARK));
                ugs.add(userGroup);
            }
            //Collections.sort(ugs);
            return ugs;
        }
        return null;
    }

    public int remove(int userGroupId) throws UserException {
        if (userGroupId < 0) {
            throw new UserException("SQLUserGroup.remove", ExceptionCodes.USERGROUP_WRONGGROUPID,
                    "Benutzergruppe darf nicht kleiner als 'null' sein!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(USERGROUPS);
        sql.append(" WHERE ");
        sql.append(UG_F01_USERGOUPID);
        sql.append(" ='");
        sql.append(userGroupId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int remove(String name) throws UserException {
        if (name == null || name.isEmpty()) {
            throw new UserException("SQLUserGroup.remove", ExceptionCodes.USERGROUP_WRONGGROUPNAME,
                    "Gruppenname darf nicht leer sein!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(USERGROUPS);
        sql.append(" WHERE ");
        sql.append(UG_F02_NAME);
        sql.append(" ='");
        sql.append(name);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int getUserGroupId(String name) throws UserException {
        if (name == null || name.isEmpty()) {
            throw new UserException("SQLUserGroup.getUserGroupId", ExceptionCodes.USERGROUP_WRONGGROUPNAME,
                    "Gruppenname '" + name + "' falsch!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(UG_F01_USERGOUPID);
        sql.append(" FROM ");
        sql.append(USERGROUPS);
        sql.append(" WHERE ");
        sql.append(UG_F02_NAME);
        sql.append(" = '");
        sql.append(name);
        sql.append("';");

        return getUserGroupId(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    private int getUserGroupId(ResultSet rSet) throws UserException {
        if (rSet != null) {
            try {
                rSet.last();
                int rows = rSet.getRow();
                rSet.first();
                if (rows == 0 || rows > 1) {
                    return -1;
                }
                return rSet.getInt(SQLUserGroup.UG_F01_USERGOUPID);
            } catch (SQLException e) {
                throw new UserException("SQLUserGroup.getUserGroupId", ExceptionCodes.DB_DBMISMATCH, "Fehler bei der Datenbankabfrage!", e);
            }
        }
        return -1;
    }

    public int setName(int userGroupId, String name) {
        if (userGroupId >= 0 && name != null && name.length() > 0) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ");
            sql.append(USERGROUPS);
            sql.append(" SET ");
            sql.append(UG_F02_NAME);
            sql.append(" ='");
            sql.append(name);
            sql.append("' WHERE ");
            sql.append(UG_F01_USERGOUPID);
            sql.append(" ='");
            sql.append(userGroupId);
            sql.append("';");
            return mySQL_PoolHandling.executeUpdate(sql.toString());
        } else {
            throw new UserException("SQLUserGroup.setName", ExceptionCodes.USERGROUP_WRONGGROUPNAME,
                    "Id oder Name '" + userGroupId + "'/'" + name + "' falsch!");
        }
    }

    public int setRemark(int userGroupId, String remark) {
        if (userGroupId < 0) {
            throw new UserException("SQLUserGroup.setRemark", ExceptionCodes.USERGROUP_WRONGGROUPID,
                    "GruppenId '" + userGroupId + "' falsch!");
        }
        if (remark == null) {
            remark = "";
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERGROUPS);
        sql.append(" SET ");
        sql.append(UG_F03_REMARK);
        sql.append(" ='");
        sql.append(remark);
        sql.append("' WHERE ");
        sql.append(UG_F01_USERGOUPID);
        sql.append(" ='");
        sql.append(userGroupId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

}
