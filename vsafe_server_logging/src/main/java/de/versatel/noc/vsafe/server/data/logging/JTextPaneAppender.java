/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.versatel.noc.vsafe.server.data.logging;

/**
 *
 * @author ulrich.stemmer
 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.*;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.helpers.QuietWriter;

/**
 * <b>Experimental</b> TextPaneAppender. <br>
 *
 *
 * Created: Sat Feb 26 18:50:27 2000 <br>
 *
 * @author Sven Reimers
 */
public class JTextPaneAppender extends AppenderSkeleton {

    JTextPane textpane;
    StyledDocument doc;
    //TracerPrintWriter tp;
    StringWriter sw;
    QuietWriter qw;
    HashMap<Level, MutableAttributeSet> attributes;
    HashMap<Level, ImageIcon> icons;
    private String label;
    private boolean showIcons;
    final String LABEL_OPTION = "Label";
    final String COLOR_OPTION_FATAL = "Color.Emerg";
    final String COLOR_OPTION_ERROR = "Color.Error";
    final String COLOR_OPTION_WARN = "Color.Warn";
    final String COLOR_OPTION_INFO = "Color.Info";
    final String COLOR_OPTION_DEBUG = "Color.Debug";
    final String COLOR_OPTION_BACKGROUND = "Color.Background";
    final String FANCY_OPTION = "Fancy";
    final String FONT_NAME_OPTION = "Font.Name";
    final String FONT_SIZE_OPTION = "Font.Size";

    public static Image loadIcon(String path) {
        Image img = null;
        try {
            URL url = ClassLoader.getSystemResource(path);
            img = (Image) (Toolkit.getDefaultToolkit()).getImage(url);
        } catch (Exception e) {
            System.out.println("Exception occured: " + e.getMessage()
                    + " - " + e);
        }
        return (img);
    }

    public JTextPaneAppender(Layout layout, String name) {
        this();
        this.layout = layout;
        this.name = name;
        //setTextPane();
        //createAttributes();
        //createIcons();
    }

    public JTextPaneAppender() {
        super();
        textpane = new JTextPane();
        textpane.setEditable(false);
        textpane.setBackground(Color.lightGray);
        this.doc = textpane.getStyledDocument();
        createAttributes();
        //createIcons();
        this.label = "";
        this.sw = new StringWriter();
        this.qw = new QuietWriter(sw, errorHandler);
        //this.tp = new TracerPrintWriter(qw);
        this.showIcons = false;
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

    private void createIcons() {
        icons = new HashMap<Level, ImageIcon>(5);
        icons.put(Level.FATAL, new ImageIcon(loadIcon("icons/RedFlag.gif")));
        icons.put(Level.ERROR, new ImageIcon(loadIcon("icons/RedFlag.gif")));
        icons.put(Level.WARN, new ImageIcon(loadIcon("icons/BlueFlag.gif")));
        icons.put(Level.INFO, new ImageIcon(loadIcon("icons/GreenFlag.gif")));
        icons.put(Level.DEBUG, new ImageIcon(loadIcon("icons/GreenFlag.gif")));
    }

    public void append(LoggingEvent event) {
        String text = this.layout.format(event);
        String trace = "";
        // Print Stacktrace
        // Quick Hack maybe there is a better/faster way?
        if (event.getThrowableInformation() != null && event.getThrowableInformation().getThrowable() != null) {
            PrintWriter p = new PrintWriter(qw);
            event.getThrowableInformation().getThrowable().printStackTrace(p);
            for (int i = 0; i < sw.getBuffer().length(); i++) {
                if (sw.getBuffer().charAt(i) == '\t') {
                    sw.getBuffer().replace(i, i + 1, "        ");
                }
            }
            trace = sw.toString();
            sw.getBuffer().delete(0, sw.getBuffer().length());
        }
        try {
            if (showIcons) {
                textpane.setEditable(true);
                //textpane.insertIcon((ImageIcon) icons.get(event.getLevel()));
                textpane.setEditable(false);
            }
            doc.insertString(doc.getLength(), text + trace,
                    (MutableAttributeSet) attributes.get(event.getLevel()));
        } catch (BadLocationException badex) {
            System.err.println(badex);
        }
        textpane.setCaretPosition(doc.getLength());
    }

    public JTextPane getTextPane() {
        return textpane;
    }

    private static Color parseColor(String v) {
        StringTokenizer st = new StringTokenizer(v, ",");
        int val[] = {255, 255, 255, 255};
        int i = 0;
        while (st.hasMoreTokens()) {
            val[i] = Integer.parseInt(st.nextToken());
            i++;
        }
        return new Color(val[0], val[1], val[2], val[3]);
    }

    private static String colorToString(Color c) {
        // alpha component emitted only if not default (255)
        String res = "" + c.getRed() + "," + c.getGreen() + "," + c.getBlue();
        return c.getAlpha() >= 255 ? res : res + "," + c.getAlpha();
    }

    @Override
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setTextPane(JTextPane textpane) {
        this.textpane = textpane;
        textpane.setEditable(false);
        textpane.setBackground(Color.lightGray);
        this.doc = textpane.getStyledDocument();
    }

    private void setColor(Level p, String v) {
        StyleConstants.setForeground(
                (MutableAttributeSet) attributes.get(p), parseColor(v));
    }

    private String getColor(Level p) {
        Color c = StyleConstants.getForeground(
                (MutableAttributeSet) attributes.get(p));
        return c == null ? null : colorToString(c);
    }

    /////////////////////////////////////////////////////////////////////
    // option setters and getters
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setColorEmerg(String color) {
        setColor(Level.FATAL, color);
    }

    public String getColorEmerg() {
        return getColor(Level.FATAL);
    }

    public void setColorError(String color) {
        setColor(Level.ERROR, color);
    }

    public String getColorError() {
        return getColor(Level.ERROR);
    }

    public void setColorWarn(String color) {
        setColor(Level.WARN, color);
    }

    public String getColorWarn() {
        return getColor(Level.WARN);
    }

    public void setColorInfo(String color) {
        setColor(Level.INFO, color);
    }

    public String getColorInfo() {
        return getColor(Level.INFO);
    }

    public void setColorDebug(String color) {
        setColor(Level.DEBUG, color);
    }

    public String getColorDebug() {
        return getColor(Level.DEBUG);
    }

    public void setColorBackground(String color) {
        textpane.setBackground(parseColor(color));
    }

    public String getColorBackground() {
        return colorToString(textpane.getBackground());
    }

    public void showIcons(boolean showIcons) {
        this.showIcons = showIcons;
    }

    public boolean getFancy() {
        return showIcons;
    }

    public void setFontSize(int size) {
        Collection<MutableAttributeSet> c = attributes.values();
        if (!c.isEmpty()) {
            for (MutableAttributeSet m : attributes.values()) {
                StyleConstants.setFontSize(m, size);
            }
        }
        return;
    }

    public int getFontSize() {
        AttributeSet attrSet = (AttributeSet) attributes.get(Level.INFO);
        return StyleConstants.getFontSize(attrSet);
    }

    public void setFontName(String name) {
        Collection<MutableAttributeSet> c = attributes.values();
        if (!c.isEmpty()) {
            for (MutableAttributeSet m : attributes.values()) {
                //Enumeration e = attributes.elements();
                //while (e.hasMoreElements()) {
                StyleConstants.setFontFamily(m, name);
            }
        }
        return;
    }

    public String getFontName() {
        AttributeSet attrSet = (AttributeSet) attributes.get(Level.INFO);
        return StyleConstants.getFontFamily(attrSet);
    }

    public boolean requiresLayout() {
        return true;
    }
} // TextPaneAppender

