/*
 * Lorenzo Miori - 16/01/2012
 * Creates a new frame visualizing the AboutPanel object.
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

package AboutWindow;

import javax.swing.*;
import java.awt.*;

/**
 * @author lorenzo
 */

public class AboutFrame {

    private JFrame aboutFrame;
    private JPanel aboutPanel;

    public AboutFrame() {

        aboutFrame = new JFrame("About this application");
        /* Hide the window when the user wants to close it... */
        aboutFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        /* Get the main panel to be used later */
        aboutPanel = (JPanel)aboutFrame.getContentPane();

        /* User cannot resize the about window freely: set a default fixed size */
        aboutFrame.setResizable(false);
        aboutFrame.setPreferredSize(new Dimension(400, 330));
        aboutFrame.setLocationRelativeTo(aboutFrame.getRootPane());
        
        aboutFrame.pack();
        /* Finally show our info panel... */
        aboutPanel.add(new AboutPanel());

    }

    public void setVisible(boolean x) {
        aboutFrame.setVisible(x);
    }
    
    public boolean isVisible() {
        return aboutFrame.isVisible();
    }
}