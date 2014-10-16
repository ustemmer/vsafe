package de.versatel.noc.vsafe.common.gui.util;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author ulrich.stemmer
 */
public class HyperlinkJLabel {

    public static JLabel getInstance(final String destination) {
        JLabel label = new JLabel("<html><body><a href=\"" + destination +"\">" + destination + "</a></body></html>");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    // Browseraufruf (für Windows):
                    try {
                        Runtime.getRuntime().exec("cmd.exe /c start " + destination);
                    } catch (Exception ioe) {
                    }
                }
            }
        });
        return label;
    }
}
