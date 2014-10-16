package de.versatel.noc.vsafe.server.data.database.mysql.tables;

import de.versatel.noc.vSafe.services.userService.AbstractUser;
import de.versatel.noc.vSafe.services.userService.UserDTO;
import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;
import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;
import de.versatel.noc.vSafe_Server.users.User;
import de.versatel.noc.vSafe_Server.users.UserException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse beinhaltet alle Methoden zur Abfrage, Änderung, zum Löschen und
 * Einfuegen von Benutzern der Datenbanktabelle 'vSafe.users'.
 * Die Methoden geben den jeweiligen SQL-String zurueck oder 'null',
 * wenn die uebergebenen Daten fehlerhaft sind.
 *

 */
public class SQLUser extends AbstractUser {

    private final String DATABASE = "vSafe";
    private final String KOMMA = ",";
    private final String SEMIKOLON = ";";
    private final String SPACE = " ";
    private final String ASC = "ASC";
    private final String DESC = "DESC";
    private final String USERS = DATABASE + ".users";
    private final String USERS_A = DATABASE + ".users a";
    public static final String U_F01_USERID = "userid";
    public static final String U_F02_USERGROUPID = "userGroupId";
    public static final String U_F03_USERNAME = "username";
    public static final String U_F04_NAME = "name";
    public static final String U_F05_FIRSTNAME = "firstname";
    public static final String U_F06_PASSWORD = "password";
    public static final String U_F07_STATE = "state";
    public static final String U_F08_ACTWRONGLOGINS = "actWrongLogins";
    public static final String U_F09_MAXWRONGLOGINS = "maxWrongLogins";
    public static final String U_F10_MAXSESSIONS = "maxSessions";
    public static final String U_F11_SESSIONLIFETIME = "SessionLifeTime";
    public static final String U_F12_SESSIONINACTIVITYTIMEOUT = "SessionInactivityTimeout";
    private MySQL_PoolHandling mySQL_PoolHandling;

    public SQLUser(MySQL_PoolHandling mySQL_PoolHandling) {
        this.mySQL_PoolHandling = mySQL_PoolHandling;
    }

    public int add(User user) throws UserException {
        if (user == null) {
            throw new UserException("SQLUser.add", ExceptionCodes.USER_MISSINGDATA,
                    "Benutzer ist 'null'!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO '");
        sql.append(USERS);
        sql.append("' ");
        StringBuilder sqlFields = new StringBuilder();
        sqlFields.append("(");
        StringBuilder sqlValues = new StringBuilder();
        sqlValues.append("(");
        if (user.getUserGroup().getUserGroupId() >= 0 && user.getUserGroup().getUserGroupId() <= 255) {
            sqlFields.append("'");
            sqlFields.append(SQLUser.U_F02_USERGROUPID);
            sqlFields.append("'");
            sqlValues.append("'");
            sqlValues.append(user.getUserGroup().getUserGroupId());
            sqlValues.append("'");
        } else {
            throw new UserException("SQLUser.add", ExceptionCodes.USER_WRONGGROUPID,
                    "Benutzergruppe ungueltig:'" + user.getUserGroup().getUserGroupId() + "'!");
        }

        if (user.getUsername() != null) {
            sqlFields.append(", '");
            sqlFields.append(SQLUser.U_F03_USERNAME);
            sqlFields.append("'");
            sqlValues.append(", '");
            sqlValues.append(user.getUsername());
            sqlValues.append("'");
        } else {
            throw new UserException("SQLUserGroup.add", ExceptionCodes.USER_WRONGUSERNAME,
                    "Benutzername ungueltig:'" + user.getUsername() + "'!");
        }

        if (user.getName() != null) {
            sqlFields.append(", '");
            sqlFields.append(SQLUser.U_F04_NAME);
            sqlFields.append("'");
            sqlValues.append(", '");
            sqlValues.append(user.getName());
            sqlValues.append("'");
        }

        if (user.getFirstname() != null) {
            sqlFields.append(", '");
            sqlFields.append(SQLUser.U_F05_FIRSTNAME);
            sqlFields.append("'");
            sqlValues.append(", '");
            sqlValues.append(user.getFirstname());
            sqlValues.append("'");
        }

        if (user.getPassword() != null) {
            sqlFields.append(", '");
            sqlFields.append(SQLUser.U_F06_PASSWORD);
            sqlFields.append("'");
            sqlValues.append(", '");
            sqlValues.append(user.getPassword());
            sqlValues.append("'");
        }

        sqlFields.append(", '");
        sqlFields.append(U_F07_STATE);
        sqlFields.append("'");
        sqlValues.append(", '");
        sqlValues.append(user.getState());
        sqlValues.append("'");

        sqlFields.append(", '");
        sqlFields.append(U_F08_ACTWRONGLOGINS);
        sqlFields.append("'");
        sqlValues.append(", '");
        sqlValues.append(user.getActWrongLogins());
        sqlValues.append("'");

        sqlFields.append(", '");
        sqlFields.append(U_F09_MAXWRONGLOGINS);
        sqlFields.append("'");
        sqlValues.append(", '");
        sqlValues.append(user.getMaxWrongLogins());
        sqlValues.append("'");

        sqlFields.append(", '");
        sqlFields.append(U_F10_MAXSESSIONS);
        sqlFields.append("'");
        sqlValues.append(", '");
        sqlValues.append(user.getMaxSessions());
        sqlValues.append("'");

        sqlFields.append(", '");
        sqlFields.append(SQLUser.U_F11_SESSIONLIFETIME);
        sqlFields.append("'");
        sqlValues.append(", '");
        sqlValues.append(user.getSessionLifeTime());
        sqlValues.append("'");

        sqlFields.append(", '");
        sqlFields.append(SQLUser.U_F12_SESSIONINACTIVITYTIMEOUT);
        sqlFields.append("'");
        sqlValues.append(", '");
        sqlValues.append(user.getSessionInactivityTo());
        sqlValues.append("'");

        sql.append(sqlFields);
        sql.append(") VALUES (");
        sql.append(sqlValues);
        sql.append(");");

        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int remove(int userId) {
        if (userId < 0) {
            throw new UserException("SQLUserGroup.add(User)", ExceptionCodes.USER_WRONGUSERID,
                    "BenutzerId ungueltig:'" + userId + "'!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(USERS);
        sql.append(" WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int createTables() {
        return 0;
    }

    public int dropTables() {
        return 0;
    }

    public User getUser(int userId) throws SQLException {
        if (userId < 0) {
            return null;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(USERS);
        sql.append(" WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" = '");
        sql.append(userId);
        sql.append("';");
        ResultSet rSet = mySQL_PoolHandling.executeQuery(sql.toString());
        return getUser(rSet);
    }

    private User getUser(ResultSet rSet) throws SQLException {
        User user;
        if (rSet != null) {
            rSet.beforeFirst();
            rSet.next();
            user = new User();
            user.setUserid(rSet.getInt(SQLUser.U_F01_USERID));
            user.setUsername(rSet.getString(SQLUser.U_F03_USERNAME));
            user.setName(rSet.getString(SQLUser.U_F04_NAME));
            user.setFirstname(rSet.getString(SQLUser.U_F05_FIRSTNAME));
            user.setPassword(rSet.getString(SQLUser.U_F06_PASSWORD));
            user.setState(User.Userstate.valueOf(rSet.getString(SQLUser.U_F07_STATE)));
            user.setActWrongLogins(rSet.getInt(SQLUser.U_F08_ACTWRONGLOGINS));
            user.setMaxWrongLogins(rSet.getInt(SQLUser.U_F09_MAXWRONGLOGINS));
            user.setMaxSessions(rSet.getInt(SQLUser.U_F10_MAXSESSIONS));
            user.setSessionLifeTime(rSet.getLong(SQLUser.U_F11_SESSIONLIFETIME));
            user.setSessionInactivityTo(rSet.getLong(SQLUser.U_F12_SESSIONINACTIVITYTIMEOUT));
            return user;
        } else {
            return null;
        }
    }

    public int getUserId(String userName) throws SQLException, UserException {
        if (userName == null || userName.isEmpty()) {
            throw new UserException("SQLUserGroup.add(User)", ExceptionCodes.USER_MISSINGDATA,
                    "Benutzername ungueltig:'" + userName + "'!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT '");
        sql.append(U_F01_USERID);
        sql.append("' FROM ");
        sql.append(USERS);
        sql.append(" WHERE ");
        sql.append(U_F03_USERNAME);
        sql.append(" = '");
        sql.append(userName);
        sql.append("';");

        ResultSet r = mySQL_PoolHandling.executeQuery(sql.toString());
        return getUserid(userName, r);
    }

    private int getUserid(String userName, ResultSet rSet) throws SQLException, UserException {
        if (rSet != null) {
            int rows;
            rSet.last();
            rows = rSet.getRow();
            if (rows > 0) {
                rSet.beforeFirst();
                for (int i = 0; i
                        < rows; i++) {
                    rSet.next();
                    return rSet.getInt(SQLUser.U_F01_USERID);
                }
            }
            throw new UserException("SQLUser.getUserid", ExceptionCodes.USER_NOUSER,
                    "Benutzername ungueltig:'" + userName + "'!");

        }
        throw new UserException("SQLUser.getUserid", ExceptionCodes.USER_MISSINGDATA,
                "Benutzername ungueltig:'" + userName + "'!");
    }

    public ResultSet getUsernames() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ");
        sql.append(U_F03_USERNAME);
        sql.append(" FROM  ");
        sql.append(USERS);
        sql.append(" ORDER BY '");
        sql.append(U_F03_USERNAME);
        sql.append("';");
        return mySQL_PoolHandling.executeQuery(sql.toString());
    }

    public List<UserDTO> getUsers() throws SQLException, UserException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(USERS);
        sql.append(" ORDER BY '");
        sql.append(U_F03_USERNAME);
        sql.append("';");
        return getUsers(mySQL_PoolHandling.executeQuery(sql.toString()));
    }

    public List<UserDTO> getUsers(int userGroupId)  throws SQLException, UserException {
        if (userGroupId < 0) {
            return null;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(USERS);
        sql.append(" WHERE ");
        sql.append(U_F02_USERGROUPID);
        sql.append(" = '");
        sql.append(userGroupId);
        sql.append("';");
        return getUsers(mySQL_PoolHandling.executeQuery(sql.toString()));
    }
    private List<UserDTO> getUsers(ResultSet rSet) throws SQLException, UserException {
        UserDTO userDTO;
        List<UserDTO> usersDTOs = new ArrayList<UserDTO>();

        if (rSet != null) {
            int rows;
            if (rSet.last()) {
                rows = rSet.getRow();
                rSet.beforeFirst();
                for (int i = 0; i < rows; i++) {
                    rSet.next();
                    userDTO = new UserDTO();
                    userDTO.setUserid(rSet.getInt(SQLUser.U_F01_USERID));
                    userDTO.setUsername(rSet.getString(SQLUser.U_F03_USERNAME));
                    userDTO.setName(rSet.getString(SQLUser.U_F04_NAME));
                    userDTO.setFirstname(rSet.getString(SQLUser.U_F05_FIRSTNAME));
                    userDTO.setPassword(rSet.getString(SQLUser.U_F06_PASSWORD));
                    userDTO.setState(rSet.getString(SQLUser.U_F07_STATE));
                    userDTO.setActWrongLogins(rSet.getInt(SQLUser.U_F08_ACTWRONGLOGINS));
                    userDTO.setMaxWrongLogins(rSet.getInt(SQLUser.U_F09_MAXWRONGLOGINS));
                    userDTO.setMaxSessions(rSet.getInt(SQLUser.U_F10_MAXSESSIONS));
                    userDTO.setSessionLifeTime(rSet.getLong(SQLUser.U_F11_SESSIONLIFETIME));
                    userDTO.setSessionInactivityTo(rSet.getLong(SQLUser.U_F12_SESSIONINACTIVITYTIMEOUT));
                    usersDTOs.add(userDTO);
                }
            }
        }
        return usersDTOs;
    }

    public int setActWrongLogins(int userId, int wrongLogins) throws UserException {
        if (userId < 0) {
            throw new UserException("SQLUserGroup.setActWrongLogins", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F08_ACTWRONGLOGINS);
        sql.append(" ='");
        sql.append(wrongLogins);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setFirstname(int userId, String firstName) throws UserException {
        if (userId < 0) {
            throw new UserException("SQLUser.setFirstname", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (firstName == null) {
            firstName = "";
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F05_FIRSTNAME);
        sql.append(" ='");
        sql.append(firstName);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setMaxSessions(int userId, int maxSessions) throws UserException {
        if (userId < 0) {
            throw new UserException("SQLUser.setMaxSessions", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (maxSessions < 0) {
            throw new UserException("SQLUser.setMaxSessions", ExceptionCodes.USER_WRONGVALUE_MAXSESSIONS,
                    "Ungueltiger Wert:'" + maxSessions + "'!");
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F10_MAXSESSIONS);
        sql.append(" ='");
        sql.append(maxSessions);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setMaxWrongLogins(int userId, int maxWrongLogins) throws UserException {
        if (userId < 0) {
            throw new UserException("SQLUser.setMaxWrongLogins", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (maxWrongLogins < 0) {
            throw new UserException("SQLUser.setMaxWrongLogins", ExceptionCodes.USER_WRONGVALUE_MAXWRONGLOGINS,
                    "Ungueltiger Wert:'" + maxWrongLogins + "'!");
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F09_MAXWRONGLOGINS);
        sql.append(" ='");
        sql.append(maxWrongLogins);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setName(int userId, String name) throws UserException {
        if (userId < 0) {
            throw new UserException("SQLUser.setName", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (name == null) {
            name = "";
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F04_NAME);
        sql.append(" ='");
        sql.append(name);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setPassword(int userId, String password) throws UserException {
        if (userId < 0) {
            throw new UserException("SQLUser.setPassword", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new UserException("SQLUser.setPassword", ExceptionCodes.USER_WRONGPASSWORD,
                    "Passwort ist leer!");
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F06_PASSWORD);
        sql.append(" ='");
        sql.append(password);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setSessionInactivityTimeout(int userId, long sessionInactivityTimeout) throws UserException {
        if (userId < 0) {
            throw new UserException("SQLUser.setSessionInactivityTimeout", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (sessionInactivityTimeout < 0L) {
            throw new UserException("SQLUser.setSessionInactivityTimeout", ExceptionCodes.USER_WRONGVALUE_SESSIONINACTIVITYTIMEOUT,
                    "Wert darf nicht kleiner '0' sein!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F12_SESSIONINACTIVITYTIMEOUT);
        sql.append(" ='");
        sql.append(sessionInactivityTimeout);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setSessionLifetime(int userId, long sessionLifetime) {
        if (userId < 0) {
            throw new UserException("SQLUser.setSessionLifetime", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (sessionLifetime < 0L) {
            throw new UserException("SQLUser.setSessionLifetime", ExceptionCodes.USER_WRONGVALUE_SESSIONLIFETIME,
                    "Wert darf nicht kleiner '0' sein: '" + sessionLifetime + " '!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F11_SESSIONLIFETIME);
        sql.append(" ='");
        sql.append(sessionLifetime);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public void setState(int userId, User.Userstate state) {
        if (userId < 0) {
            throw new UserException("SQLUser.setState", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F07_STATE);
        sql.append(" ='");
        sql.append(state.toString());
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setUserGroupId(int userId, int userGroupId) {
        if (userId < 0) {
            throw new UserException("SQLUser.setUserGroupId", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (userGroupId < 0) {
            throw new UserException("SQLUser.setUserGroupId", ExceptionCodes.USER_WRONGGROUPID,
                    "Wert darf nicht kleiner '0' sein: '" + userGroupId + " '!");
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F02_USERGROUPID);
        sql.append(" ='");
        sql.append(userGroupId);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }

    public int setUserName(int userId, String userName) {
        if (userId < 0) {
            throw new UserException("SQLUser.setUserGroupId", ExceptionCodes.USER_WRONGUSERID,
                    "Benutzer-Id ungueltig:'" + userId + "'!");
        }

        if (userName == null || userName.isEmpty()) {
            throw new UserException("SQLUser.setUserGroupId", ExceptionCodes.USER_WRONGUSERNAME,
                    "Benutzername darf nicht kleiner leer sein!");
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(USERS);
        sql.append(" SET ");
        sql.append(U_F03_USERNAME);
        sql.append(" ='");
        sql.append(userName);
        sql.append("' WHERE ");
        sql.append(U_F01_USERID);
        sql.append(" ='");
        sql.append(userId);
        sql.append("';");
        return mySQL_PoolHandling.executeUpdate(sql.toString());
    }
}
