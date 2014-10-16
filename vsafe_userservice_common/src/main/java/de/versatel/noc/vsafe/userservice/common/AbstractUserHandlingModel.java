package de.versatel.noc.vsafe.userservice.common;

import java.util.List;
import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractUserHandlingModel {

    protected int index;
    protected PropertyChangeSupport propertyChangeSupport;

    public AbstractUserHandlingModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public AbstractUserHandlingModel(int index) {
        this.index = index;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void setPropertyValue(String propertyName, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, null, newValue);
    }

    public void setPropertyValue(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void setPropertyValue(String propertyName, int index, Object newValue) {
        propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, null, newValue);
    }

    public void setPropertyValue(String propertyName, int index, Object oldValue, Object newValue) {
        propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
    }

    public Object getPropertyValue(String propertyName) {
        return null;
    }

    public void updateViews() {
    }

}
