package de.versatel.noc.vsafe.common.gui.mvc;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public abstract class AbstractController implements PropertyChangeListener {

    protected ArrayList<AbstractViewPanel> registeredViews;
    protected ArrayList<AbstractModel> registeredModels;

    public AbstractController() {
        registeredViews = new ArrayList<AbstractViewPanel>();
        registeredModels = new ArrayList<AbstractModel>();
    }

    public void addModel(AbstractModel model) {
        registeredModels.add(model);
        model.addPropertyChangeListener(this);
    }

    public void addView(AbstractViewPanel view) {
        registeredViews.add(view);
    }

    public final int getViewSize() {
        return registeredViews.size();
    }

    public final int getModelSize() {
        return registeredModels.size();
    }

    public final boolean isViewListEmpty() {
        return registeredViews.isEmpty();
    }

    public final boolean isModelListEmpty() {
        return registeredModels.isEmpty();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt instanceof IndexedPropertyChangeEvent) {
            IndexedPropertyChangeEvent ipce = (IndexedPropertyChangeEvent) evt;
        }
        for (AbstractViewPanel view : registeredViews) {
            view.modelPropertyChange(evt);
        }
    }



    public void removeModel(AbstractModel model) {
        registeredModels.remove(model);
    }

    public void removeModels() {
        if (!registeredModels.isEmpty()) {
            for (AbstractModel m : registeredModels) {
                m.removePropertyChangeListener(this);
                m=null;
            }
        }
    }

    public void removeView(AbstractViewPanel view) {
        registeredViews.remove(view);
    }

    protected void setModelProperty(String propertyName, Object newValue) {
        if (propertyName != null && !propertyName.isEmpty()) {
            for (AbstractModel model : registeredModels) {
                model.setPropertyValue(propertyName, newValue);
            }
        }
    }

    protected void setModelProperty(String propertyName, int index, Object newValue) {
        if (propertyName != null && !propertyName.isEmpty()) {
            try {
                AbstractModel m = registeredModels.get(index);
                if (m != null) {
                    m.setPropertyValue(propertyName, index, newValue);
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }
    public void updateViews() {
        for (AbstractModel model : registeredModels) {
            model.updateViews();
        }
    }

    public void updateViews(int modelIndex) {
        try {
            AbstractModel m = registeredModels.get(modelIndex);
            m.updateViews(modelIndex);
        } catch (IndexOutOfBoundsException e) {
        }

    }

}
