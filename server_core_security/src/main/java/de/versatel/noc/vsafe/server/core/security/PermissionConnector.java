package de.versatel.noc.vsafe.server.core.security;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public abstract class PermissionConnector implements PermissionConnectorInterface {

    protected final List<Connector> connectors;

    public PermissionConnector() {
        this.connectors = new ArrayList<Connector>();
    }

    public int getPermissionId(int methodId) {
        for (Connector c : connectors) {
            if (c.methodId == methodId) {
                return c.permissionId;
            }
        }
        return -1;
    }

    public Connector getConnector(int methodId) {
        for (Connector c : connectors) {
            if (c.getMethodId() == methodId) {
                return c;
            }
        }
        return null;
    }

    public List getConnectors() {
        return connectors;
    }

    public List getConnectors(int permissionId) {
        if (connectors.isEmpty()) {
            return null;
        }
        List l = new ArrayList<Connector>();
        for (Connector c : connectors) {
            if (c.getPermissionId() == permissionId) {
                l.add(c);
            }
        }
        return l;
    }

    protected class Connector {

        private final int methodId;
        private final int permissionId;

        public Connector(int methodId, int permissionId) {
            this.methodId = methodId;
            this.permissionId = permissionId;
        }

        public int getMethodId() {
            return this.methodId;
        }

        public int getPermissionId() {
            return this.permissionId;
        }
    }
}
