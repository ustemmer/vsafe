/*
 * DefaultController.java
 *
 * Created on January 22, 2007, 8:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package de.versatel.noc.vsafe.server.mvc.util;

import de.versatel.noc.vSafe.mvc.AbstractController;
import java.awt.Font;

/**
 * This controller implements the required methods and provides the properties
 * necessary to work with the DisplayViewPanel and PropertyViewPanel views. Each of
 * methods in this class can be called upon by the views to update to state of the
 * registered models.
 *
 * @author Robert Eckstein
 */
public class DefaultController extends AbstractController
{
    //  Properties this controller expects to be stored in one or more registered models
    
    /**
     * Change the document name in the model
     * @param newName The new document name
     */
    public void changeDocumentName(String newName) {
        
        setModelProperty(DocumentModel.NAME, newName);
    }
    
    /**
     * Change the document width in the model
     * @param newWidth The new doucment width
     */
    public void changeDocumentWidth(int newWidth) {
        setModelProperty(DocumentModel.WIDTH, newWidth);
    }
    
    /**
     * Change the document height in the model
     * @param newHeight The new document height
     */
    public void changeDocumentHeight(int newHeight) {
        setModelProperty(DocumentModel.HEIGHT, newHeight);
    }
    
    
    /**
     * Change the text element string in the model
     * @param newText The new text element string
     */
    public void changeElementText(String newText) {
        setModelProperty(TextElementModel.TEXT, newText);
    }
    
    /**
     * Change the text element font in the model
     * @param newFont The new text element font
     */
    public void changeElementFont(Font newFont) {
        setModelProperty(TextElementModel.FONT, newFont);
    }
    
    /**
     * Change the text element X position value in the model
     * @param newX The new text element X position
     */
    public void changeElementXPosition(int newX) {
        setModelProperty(TextElementModel.X, newX);
    }
    
    /**
     * Change the text element Y position value in the model
     * @param newY The new text element Y position
     */
    public void changeElementYPosition(int newY) {
        setModelProperty(TextElementModel.Y, newY);
    }
    
    /**
     * Change the text element opacity value in the model
     * @param newOpacity The new text element opacity value
     */
    public void changeElementOpacity(int newOpacity) {
        setModelProperty(TextElementModel.OPACITY, newOpacity);
    }
    
    /**
     * Change the text element rotation value in the model
     * @param newRotation The new text element rotation value
     */
    public void changeElementRotation(int newRotation) {
        setModelProperty(TextElementModel.ROTATION, newRotation);
    }
    
}
