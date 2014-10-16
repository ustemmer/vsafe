package de.versatel.noc.vsafe.server.rmi.services.usermanagement;

import de.versatel.noc.vSafe.services.PermissionConnector;

/**
 *
 * @author ulrich.stemmer
 */
public class UserPermissionConnector extends PermissionConnector {

    public UserPermissionConnector() {
//        connectors.add(new Connector(UserServiceExecutor.login,
//                UserPermissionDescriptions.AddSession));
//        connectors.add(new Connector(UserServiceExecutor.logout,
//                UserPermissionDescriptions));
//      connectors.add(new Connector(UserServiceExecutor.getService,
//                UserPermissionDescriptions));
        connectors.add(new Connector(UserServiceExecutor.addUserGroup,
                UserPermissionDescriptions.AddUserGroup));
        connectors.add(new Connector(UserServiceExecutor.removeUserGroup,
                UserPermissionDescriptions.RemoveUserGroup));
        connectors.add(new Connector(UserServiceExecutor.hasUserGroups,
                UserPermissionDescriptions.GetUserGroups));
        connectors.add(new Connector(UserServiceExecutor.getUsers,
                UserPermissionDescriptions.GetUsers));
        connectors.add(new Connector(UserServiceExecutor.getUserGroup,
                UserPermissionDescriptions.GetUserGroup));
        connectors.add(new Connector(UserServiceExecutor.getUserGroup_UserGroupId,
                UserPermissionDescriptions.GetUserGroup));
        connectors.add(new Connector(UserServiceExecutor.getUserGroup_Name,
                UserPermissionDescriptions.GetUserGroup));
        connectors.add(new Connector(UserServiceExecutor.getUserGroup_PermissionBits,
                UserPermissionDescriptions.GetUserGroup));
        connectors.add(new Connector(UserServiceExecutor.getUserGroup_Remark,
                UserPermissionDescriptions.GetUserGroup));
        connectors.add(new Connector(UserServiceExecutor.setUserGroup_Name,
                UserPermissionDescriptions.ChangeUserGroup));
        connectors.add(new Connector(UserServiceExecutor.setUserGroup_PermissionBit,
                UserPermissionDescriptions.ChangeUserGroup));
        connectors.add(new Connector(UserServiceExecutor.setUserGroup_Remark,
                UserPermissionDescriptions.ChangeUserGroup));
        connectors.add(new Connector(UserServiceExecutor.getUserGroup_Users,
                UserPermissionDescriptions.GetUserGroup));
        connectors.add(new Connector(UserServiceExecutor.hasUserGroup_Users,
                UserPermissionDescriptions.GetUserGroup));
        connectors.add(new Connector(UserServiceExecutor.addUser,
                UserPermissionDescriptions.ChangeUserGroup));
        connectors.add(new Connector(UserServiceExecutor.removeUser,
                UserPermissionDescriptions.ChangeUserGroup));
        connectors.add(new Connector(UserServiceExecutor.getUserViaId,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUserViaName,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_UserGroup,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_UserId,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_ActWrongLogins,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_Firstname,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_MaxSessions,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_MaxWrongLogins,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_Name,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_Password,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_SessionInactivityTimeout,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_SessionLifetime,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_State,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_Username,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.isUserBarred,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.isUserPasswordValid,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_Values,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_ActWrongLogins,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_Firstname,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_MaxSessions,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_MaxWrongLogins,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_Name,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_Password,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_SessionInactivityTimeout,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_SessionLifetime,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_State,
                UserPermissionDescriptions.ChangeUser));
//        connectors.add(new Connector(UserServiceExecutor.changeUserState,
//                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_UserGroup,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.setUser_Username,
                UserPermissionDescriptions.ChangeUser));
        connectors.add(new Connector(UserServiceExecutor.hasUser_Sessions,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_SessionCount,
                UserPermissionDescriptions.GetUser));
        connectors.add(new Connector(UserServiceExecutor.getUser_Sessions,
                UserPermissionDescriptions.GetUser));
        //connectors.add(new Connector(UserServiceExecutor.addSession,
        //        UserPermissionDescriptions.addSession));
        connectors.add(new Connector(UserServiceExecutor.getSession,
                UserPermissionDescriptions.GetSession));
        connectors.add(new Connector(UserServiceExecutor.getSession_CreatedAt,
                UserPermissionDescriptions.GetSession));
        connectors.add(new Connector(UserServiceExecutor.getSession_Ipv4,
                UserPermissionDescriptions.GetSession));
        connectors.add(new Connector(UserServiceExecutor.getSession_LastTouch,
                UserPermissionDescriptions.GetSession));
        connectors.add(new Connector(UserServiceExecutor.getSession_LocalHostName,
                UserPermissionDescriptions.GetSession));
        connectors.add(new Connector(UserServiceExecutor.getSession_SessionId,
                UserPermissionDescriptions.GetSession));
        connectors.add(new Connector(UserServiceExecutor.hasSessionExpired,
                UserPermissionDescriptions.GetSession));
        connectors.add(new Connector(UserServiceExecutor.setSession_CreatedAt,
                UserPermissionDescriptions.ChangeSession));
        connectors.add(new Connector(UserServiceExecutor.setSession_LastTouch,
                UserPermissionDescriptions.ChangeSession));
        connectors.add(new Connector(UserServiceExecutor.setSession_SessionId,
                UserPermissionDescriptions.ChangeSession));
    }
}
