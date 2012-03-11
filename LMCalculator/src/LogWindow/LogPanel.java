/*
 * Lorenzo Miori - 19/01/2012
 * This is the panel displaying the operations' log text
 */

/*
    Copyright (C) 2012  Lorenzo Miori

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package LogWindow;

import CalcTools.Logger;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author lorenzo
 */

public class LogPanel extends JPanel
                      implements ActionListener
{

    private JTextArea textArea;
    private JScrollPane scrText;
    private JButton emptyButton;
    private Logger calcLog;
    
    public LogPanel(Logger calcLog) {
        
        this.calcLog = calcLog;
        
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        emptyButton = new JButton("Clear history");
        emptyButton.addActionListener(this);
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrText = new JScrollPane(textArea);
        scrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(new JLabel("Calculator's History (redirected to file):"), BorderLayout.NORTH);
        add(scrText, BorderLayout.CENTER);
        add(emptyButton, BorderLayout.SOUTH);
    }
    
    public void updateText() {
        textArea.setText(calcLog.getBuffer());
        emptyButton.setEnabled(true);
    }
    
    public void clearText() {
        calcLog.clear(); // clear both buffer and file content
        updateText();
        emptyButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Just a single button...
        clearText();
    }
}
