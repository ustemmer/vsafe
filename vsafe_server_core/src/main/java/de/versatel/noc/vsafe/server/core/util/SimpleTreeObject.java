/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.versatel.noc.vsafe.server.core.util;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ulrich.stemmer
 */
public abstract class SimpleTreeObject implements java.io.Serializable, Comparable{

    public final List<SimpleTreeObject> children;
    public final Property<SimpleTreeObject> parent;

    public SimpleTreeObject() {
        this.children = new ArrayList<SimpleTreeObject>();
        this.parent = null;
    }

    public SimpleTreeObject(SimpleTreeObject parent) {
        this.children = new ArrayList<SimpleTreeObject>();
        this.parent = new Property<SimpleTreeObject>(parent);
    }


    public void addChild(final SimpleTreeObject child) {
        if (!this.children.contains(child)) {
            this.children.add(child);
        }
    }

    public List<SimpleTreeObject> getChildren() {
        return children;
    }

    public void removeChild(final SimpleTreeObject child) {
        this.children.remove(child);
    }

    public boolean isRoot() {
        if (this.parent == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasChildren() {
        if (this.children.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public class Property<T> {

        private T value;

        public Property(final T initialValue) {
            this.value = initialValue;
        }

        public void setValue(final T value) {
            this.value = value;
        }

        public T getValue() {
            return this.value;
        }
    }
   
}
