package de.versatel.noc.vsafe.server.data.logging;

/**
 *
 * @author ulrich.stemmer
 */
import java.awt.Color;
import java.io.*;
import java.util.HashMap;

//import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.apache.log4j.*;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.helpers.QuietWriter;

public class ExceptionJOptionPaneAppender extends AppenderSkeleton {

    StringWriter sw;
    QuietWriter qw;
    HashMap<Level, MutableAttributeSet> attributes;
    
    private Component parent;

    public ExceptionJOptionPaneAppender(Layout layout, String name) {
        this();
        this.layout = layout;
        this.name = name;
    }

    public ExceptionJOptionPaneAppender() {
        super();
        createAttributes();
        this.sw = new StringWriter();
        this.qw = new QuietWriter(sw, errorHandler);
    }

    public void close() {
    }

    private void createAttributes() {
        attributes = new HashMap<Level, MutableAttributeSet>();
        MutableAttributeSet att = new SimpleAttributeSet();
        attributes.put(Level.ERROR, att);
        att = new SimpleAttributeSet();
        attributes.put(Level.WARN, att);
        att = new SimpleAttributeSet();
        attributes.put(Level.INFO, att);
        att = new SimpleAttributeSet();
        attributes.put(Level.DEBUG, att);

        StyleConstants.setForeground((MutableAttributeSet) attributes.get(Level.ERROR), Color.red);
        StyleConstants.setForeground((MutableAttributeSet) attributes.get(Level.WARN), Color.orange);
        StyleConstants.setForeground((MutableAttributeSet) attributes.get(Level.INFO), Color.gray);
        StyleConstants.setForeground((MutableAttributeSet) attributes.get(Level.DEBUG), Color.black);
    }

      public void append(LoggingEvent event) {
        showOptionPane(this.layout.format(event), event.getLevel());
    }

      private void showOptionPane(String message, Level level){
          
          if (message == null || message.isEmpty()){
              return;
          }
          
          if (parent == null){
              parent = new JFrame();
          }
          
          int messageType = JOptionPane.INFORMATION_MESSAGE;
          String title = "Hinweis !";
          
          if (level.equals(Level.FATAL) || level.equals(Level.ERROR)){
            messageType = JOptionPane.ERROR_MESSAGE;
            title = "Fehler !";
          }else if (level.equals(Level.WARN)){
            messageType = JOptionPane.WARNING_MESSAGE;
            title = "Warnung !";
          }else if (level.equals(Level.INFO)){
            messageType = JOptionPane.INFORMATION_MESSAGE;
            title = "Hinweis !";
          }else {
            messageType = JOptionPane.INFORMATION_MESSAGE;
            title = "DEBUG !";
          }
          JOptionPane.showMessageDialog(parent, message, title,  messageType);
      }



    @Override
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    public Component getParent() {
        return parent;
    }

    public boolean requiresLayout() {
        return true;
    }
} // TextPaneAppender

