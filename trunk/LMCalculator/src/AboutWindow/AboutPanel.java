/*
 * Lorenzo Miori - 16/01/2012 - 19/01/2012
 * This panel shows the user some information about the programmer and the
 * program itself...
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
import java.net.URL;
import CalcTools.Utils;

/**
 * @author lorenzo
 */

public class AboutPanel extends JPanel {

    private JLabel imageLabel;
    private JTextArea infoArea;
    private ImageIcon bg_image;

    public AboutPanel() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        loadIMG();
        loadUI();

    }

    private void loadUI() {
        imageLabel = new JLabel();
        infoArea = new JTextArea();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);


        infoArea.append("\n");
        infoArea.append("** LMCalculator: a simple calculator! **\n");
        infoArea.append("Version: " + Utils.getVerString());
        infoArea.append("\n");
        infoArea.append("Date: " + Utils.DATE);
        infoArea.append("\n");
        infoArea.append("Author: " + Utils.AUTHOR + " [" + Utils.AUTHOR_INFO + "]\n");
        infoArea.append("Coded on: "+ Utils.BUILD_OS);
        infoArea.append("\n");
        infoArea.append("Tested on: "+ Utils.BUILD_OS + " and Windows");
        infoArea.append("\n");
        infoArea.append("Number of classes: 19");
        infoArea.append("\n");
        infoArea.append("Number of lines: about 1900");
        infoArea.append("\n");
        infoArea.setEditable(false);

        if (bg_image != null) {
            imageLabel.setIcon(bg_image);
        }

        add(imageLabel, BorderLayout.NORTH);
        add(infoArea, BorderLayout.CENTER);
    }

    private void loadIMG() {
        try {
            URL imgURL = getClass().getResource("/res/about_img.jpeg");
            bg_image = new ImageIcon(imgURL);
        } catch (Exception e) {
            bg_image = null;
            System.out.println("Failed to load about image!");
        }
    }
}