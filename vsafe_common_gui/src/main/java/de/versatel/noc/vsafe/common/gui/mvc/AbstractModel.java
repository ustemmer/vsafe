package de.versatel.noc.vsafe.common.gui.mvc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides base level functionality for all models, including a 
 * support for a property change mechanism (using the PropertyChangeSupport class),
 * as well as a convenience method that other objects can use to reset model state.
 * @author Robert Eckstein
 */
public abstract class AbstractModel {

    /**
     * Convenience class that allow others to observe changes to the model properties
     */
    protected PropertyChangeSupport propertyChangeSupport;
    protected List<MVCProperty> mVCProperties;

    /**
     * Default constructor. Instantiates the PropertyChangeSupport class.
     */
    public AbstractModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        mVCProperties = new ArrayList<MVCProperty>();
    }

    /**
     * Adds a property change listener to the observer list.
     * @param l The property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    /**
     * Removes a property change listener from the observer list.
     * @param l The property change listener
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void addProperty(MVCProperty p) {
        this.mVCProperties.add(p);
    }

    public void setPropertyValue(String propertyName, Object newValue) {
        for (MVCProperty p : mVCProperties) {
            if (p.name.equals(propertyName)) {
                p.setValue(newValue);
            }
        }
    }

    public void setPropertyValue(String propertyName, int index, Object newValue) {
        for (MVCProperty p : mVCProperties) {
            if (p.name.equals(propertyName)) {
//                System.out.println("5 - AbstractModel.setPropertyValue(String propertyName, int " + index + ", Object " + newValue + ")");
                p.setValue(index, newValue);
            }
        }
    }


    public Object getPropertyValue(String propertyName) {
        for (MVCProperty p : mVCProperties) {
            if (p.name.equals(propertyName)) {
                return p.getValue();
            }
        }
        return null;
    }

    /**
     * Fires an event to all registered listeners informing them that a property in
     * this model has changed.
     * @param propertyName The name of the property
     * @param oldValue The previous value of the property before the change
     * @param newValue The new property value after the change
     */
    /*protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        //System.out.println("Abstractmodel : firePropertyChange");
    }

    protected void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
        propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
        //System.out.println("Abstractmodel : firePropertyChange");
    }*/

    public void updateViews() {
        for (MVCProperty p : mVCProperties) {
            propertyChangeSupport.firePropertyChange(p.getName(), null, p.getValue());
        }
    }

    public void updateViews(int index) {
        int i = propertyChangeSupport.getPropertyChangeListeners().length;
        //System.out.println("AbstractModel.updateViews(" + index + ")");
        //System.out.println("AbstractModel.updateViews(" + index + "), listeners:" + i);
        for (MVCProperty p : mVCProperties) {
            //System.out.println("AbstractModel.updateViews(" + index + "), " + p.getName() + ", " + p.getValue());
            propertyChangeSupport.fireIndexedPropertyChange( p.getName(), index, null, p.getValue());
        }

    }

    public class MVCProperty {

        private Object value;
        private String name;

        @Override
        public String toString(){
            return this.name;
        }
        public MVCProperty(String name, Object initialValue) {
            this.name = name;
            this.value = initialValue;
        }

        public void setValue(Object newValue) {
            propertyChangeSupport.firePropertyChange(name, value, newValue);
            this.value = newValue;
        }

        public void setValue(int index, Object newValue) {
//            System.out.println("6 - AbstractModel.MVCProperty.setValue(int " + index + "), Object " + newValue+ ")");
            propertyChangeSupport.fireIndexedPropertyChange(name, index , value, newValue);
//            System.out.println("6b - AbstractModel.MVCProperty fireIndexedPropertyChange(" + name + ", " + index + ", " + value + ", " + newValue + ")");
            this.value = newValue;
        }

        public Object getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }
}
