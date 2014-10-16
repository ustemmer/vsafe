/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.versatel.noc.vsafe.common.gui.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Uli
 */
public class BlinkLabel extends JLabel {

    protected final Timer timer;
    private static final long serialVersionUID = 1L;
    private boolean blinkingStarted = false;
    private boolean blink = true;
    private int onDuration = 1000; // in ms
    private int offDuration = 1000; // in ms
    private ImageIcon onEnabledIcon = null;
    private ImageIcon offEnabledIcon = null;
    private ImageIcon onDisabledIcon = null;
    private ImageIcon offDisabledIcon = null;
    private Color onForegroundColor = null;
    private Color offForegroundColor = null;
    private Color onBackgroundColor = null;
    private Color offBackgroundColor = null;

    public BlinkLabel() {
        super("");

        timer = new Timer(onDuration, new TimerListener(this));
        timer.setInitialDelay(0);
        timer.start();
    }

    public BlinkLabel(String text) {
        super(text);

        timer = new Timer(1000, new TimerListener(this));
        timer.setInitialDelay(0);
        timer.start();
    }

    public BlinkLabel(String text, int interval) {
        super(text);
        if (interval < 0) {
            interval = 0;
        } else if (interval > 15000) {
            onDuration = 15000;
            offDuration = 15000;
        }
        onDuration = interval;
        offDuration = interval;
        timer = new Timer(1000, new TimerListener(this));
        timer.setInitialDelay(0);
        timer.start();
    }

    public void setBlinkingStarted(boolean flag) {
        this.blinkingStarted = flag;
    }

    public boolean isBlinkingStarted() {
        return blinkingStarted;
    }

    public Color getOffBackgroundColor() {
        return offBackgroundColor;
    }

    public void setOffBackgroundColor(Color offBackgroundColor) {
        this.offBackgroundColor = offBackgroundColor;
    }

    public ImageIcon getOffDisabledIcon() {
        return offDisabledIcon;
    }

    public void setOffDisabledIcon(ImageIcon offDisabledIcon) {
        this.offDisabledIcon = offDisabledIcon;
    }

    public int getOffDuration() {
        return offDuration;
    }

    public void setOffDuration(int offDuration) {
        this.offDuration = offDuration;
    }

    public ImageIcon getOffEnabledIcon() {
        return offEnabledIcon;
    }

    public void setOffEnabledIcon(ImageIcon offEnabledIcon) {
        this.offEnabledIcon = offEnabledIcon;
    }

    public Color getOffForegroundColor() {
        return offForegroundColor;
    }

    public void setOffForegroundColor(Color offForegroundColor) {
        this.offForegroundColor = offForegroundColor;
    }

    public Color getOnBackgroundColor() {
        return onBackgroundColor;
    }

    public void setOnBackgroundColor(Color onBackgroundColor) {
        this.onBackgroundColor = onBackgroundColor;
    }

    public ImageIcon getOnDisabledIcon() {
        return onDisabledIcon;
    }

    public void setOnDisabledIcon(ImageIcon onDisabledIcon) {
        this.onDisabledIcon = onDisabledIcon;
    }

    public int getOnDuration() {
        return onDuration;
    }

    public void setOnDuration(int onDuration) {
        this.onDuration = onDuration;
    }

    public ImageIcon getOnEnabledIcon() {
        return onEnabledIcon;
    }

    public void setOnEnabledIcon(ImageIcon onEnabledIcon) {
        this.onEnabledIcon = onEnabledIcon;
    }

    public Color getOnForegroundColor() {
        return onForegroundColor;
    }

    public void setOnForegroundColor(Color onForegroundColor) {
        this.onForegroundColor = onForegroundColor;
    }

    private class TimerListener implements ActionListener {

        private BlinkLabel bl;

        public TimerListener(BlinkLabel bl) {
            this.bl = bl;
        }

        public void actionPerformed(ActionEvent e) {
            if (bl.isBlinkingStarted()) {
                if (blink) {
                    timer.setDelay(offDuration);
                    if (offForegroundColor != null) {
                        bl.setForeground(offForegroundColor);
                    }
                    if (offBackgroundColor != null) {
                        bl.setBackground(offBackgroundColor);
                    }
                    if (offEnabledIcon != null) {
                        bl.setIcon(offEnabledIcon);
                    }
                    if (offDisabledIcon != null) {
                        bl.setDisabledIcon(offDisabledIcon);
                    }
                    blink = false;
                } else {
                    timer.setDelay(onDuration);
                    if (onForegroundColor != null) {
                        bl.setForeground(onForegroundColor);
                    }
                    if (onBackgroundColor != null) {
                        bl.setBackground(onBackgroundColor);
                    }
                    if (onEnabledIcon != null) {
                        bl.setIcon(onEnabledIcon);
                    }
                    if (onDisabledIcon != null) {
                        bl.setDisabledIcon(onDisabledIcon);
                    }
                    blink = true;
                }
            } else {
                // here we want to make sure that the label is visible
                // if the blinking is off.
                if (!blink) {
                    timer.setDelay(onDuration);
                    if (onForegroundColor != null) {
                        bl.setForeground(onForegroundColor);
                    }
                    if (onBackgroundColor != null) {
                        bl.setBackground(onBackgroundColor);
                    }
                    if (onEnabledIcon != null) {
                        bl.setIcon(onEnabledIcon);
                    }
                    if (onDisabledIcon != null) {
                        bl.setDisabledIcon(onDisabledIcon);
                    }
                    blink = true;
                }
            }
        }
    }
}
