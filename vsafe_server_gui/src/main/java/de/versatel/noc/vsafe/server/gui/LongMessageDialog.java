/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.versatel.noc.vsafe.server.gui;

/**
 *
 * @author ulrich.stemmer
 */
import java.awt.*;
import java.awt.event.*;
//import org.apache.log4j.spi.LoggingEvent;

// A simple aux. class that implements a dialog box with a multiline
// message and an "OK" button to dismiss it.
public class LongMessageDialog extends Dialog {

    // Constructor.  Put the message you want in the array messageList, one
    // line per array element.
    public LongMessageDialog(Frame parent, String title,
            String[] messageList, Font messageFont) {
        super(parent, title, true);
        Panel p1 = new Panel();
        int i;
        for (i = 0; i < messageList.length; i++) {
            Label nextMessage = new Label(messageList[i]);
            nextMessage.setFont(messageFont);
            p1.add(nextMessage);
        }
        add(p1, "Center");

        Panel p2 = new Panel();
        Button okButton = new Button("OK");
        p2.add(okButton);
        add(p2, "South");

        // A couple of anonymous "inner" classes.
        // Don't worry about what these are.
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        });

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        setSize(220, 150);
    }
}
