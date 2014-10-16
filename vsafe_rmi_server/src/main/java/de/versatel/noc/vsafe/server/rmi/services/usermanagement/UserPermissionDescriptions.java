package de.versatel.noc.vsafe.server.rmi.services.usermanagement;

import de.versatel.noc.vSafe.services.PermissionDescription;
import de.versatel.noc.vSafe.services.PermissionDescriptions;
//import de.versatel.noc.vSafe.server.service.Service;

/**
 *
 * @author ulrich.stemmer
 */
public class UserPermissionDescriptions extends PermissionDescriptions {

    public final static int GetUserGroups = 1;
    public final static int GetUserGroup = 2;
    public final static int ChangeUserGroup = 3;
    public final static int AddUserGroup = 4;
    public final static int RemoveUserGroup = 5;
    public final static int GetUsers = 6;
    public final static int GetUser = 7;
    public final static int ChangeUser = 8;
    public final static int AddUser = 9;
    public final static int RemoveUser = 10;
    public final static int GetSessions = 11;
    public final static int GetSession = 12;
    public final static int ChangeSession = 13;
    public final static int AddSession = 14;
    public final static int RemoveSession = 15;
    public final static int GetServices = 16;
    public final static int GetService = 17;
    public final static int ChangeService = 18;
    public final static int AddService = 19;
    public final static int RemoveService = 20;
    public final static int GetServicePermissions = 21;
    public final static int GetServicePermission = 22;
    public final static int ChangeServicePermission = 23;
    public final static int AddServicePermission = 24;
    public final static int RemoveServicePermission = 25;

    public UserPermissionDescriptions(UserService service) {
        super(service);
        permissions.add(new PermissionDescription(service.getServiceId(), GetUserGroups, "GetUserGroups", "Benutzergruppen: Alle holen"));
        permissions.add(new PermissionDescription(service.getServiceId(), GetUserGroup, "GetUserGroup", "Benutzergruppe: holen"));
        permissions.add(new PermissionDescription(service.getServiceId(), ChangeUserGroup, "ChangeUserGroup", "Benutzergruppe: ändern"));
        permissions.add(new PermissionDescription(service.getServiceId(), AddUserGroup, "AddUserGroup", "Benutzergruppe: hinzufügen"));
        permissions.add(new PermissionDescription(service.getServiceId(), RemoveUserGroup, "RemoveUserGroup", "Benutzergruppe: löschen"));

        permissions.add(new PermissionDescription(service.getServiceId(), GetUsers, "GetUsers", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GetUser, "GetUser", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), ChangeUser, "ChangeUser", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), AddUser, "AddUser", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), RemoveUser, "RemoveUser", ""));

        permissions.add(new PermissionDescription(service.getServiceId(), GetSessions, "GetSessions", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GetSession, "GetSession", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), ChangeSession, "ChangeSession", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), AddSession, "AddSession", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), RemoveSession, "RemoveSession", ""));

        permissions.add(new PermissionDescription(service.getServiceId(), GetServices, "GetServices", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GetService, "GetService", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), ChangeService, "ChangeService", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), AddService, "AddService", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), RemoveService, "RemoveService", ""));

        permissions.add(new PermissionDescription(service.getServiceId(), GetServicePermissions, "GetServicePermissions", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GetServicePermission, "GetServicePermission", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), ChangeServicePermission, "ChangeServicePermission", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), AddServicePermission, "AddServicePermission", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), RemoveServicePermission, "RemoveServicePermission", ""));
    }
}
