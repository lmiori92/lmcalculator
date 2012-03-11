/*
 * Lorenzo Miori - 19/01/2012
 * Creates a new frame visualizing the LogPanel object.
 * Having this window opened, user can still use the calculator!
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

import javax.swing.*;
import java.awt.*;
import CalcTools.Logger;

/**
 * @author lorenzo
 */

public class LogFrame {

    private JFrame logFrame;
    private JPanel logPanel;
    private LogPanel innerLogPanel;

    public LogFrame(Logger calcLog) {

        logFrame = new JFrame("LMCalculator History");
        /* Hide the window when the user wants to close it... */
        logFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        /* Get the main panel to be used later */
        logPanel = (JPanel)logFrame.getContentPane();

        /* User cannot resize the about window freely: set a default fixed size */
        logFrame.setResizable(false);
        logFrame.setPreferredSize(new Dimension(400, 300));
        logFrame.setLocationRelativeTo(logFrame.getRootPane());
        
        logFrame.pack();
        /* Finally show our info panel... */
        innerLogPanel = new LogPanel(calcLog);
        logPanel.add(innerLogPanel);

    }

    public void setVisible(boolean x) {
        logFrame.setVisible(x);
        innerLogPanel.updateText();
    }
    
    public void updateText() {
        if (logFrame.isVisible()) {
            innerLogPanel.updateText();
        }
    }
    
    public boolean isVisible() {
        return logFrame.isVisible();
    }
}