/*
 * Lorenzo Miori - 16/01/2012 - 18/01/2012
 * This is the main class of the "Calculator" Winter Exam Project
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

import CalcGui.MainView;
import javax.swing.*;
import CalcTools.*;

/**
 * @author lorenzo
 */
public class LMCalculator {

    public static void main(String[] args) {

        /* Initialize settings */
        Settings mySettings = new Settings();
        /* Initialize logger */
        Logger calcLog = new Logger("LMCalculator_Log.txt");
        calcLog.writeLine("LMCalculator " + Utils.getVerString() + " started!");

        System.out.println("LMCalculator (C) 2012 " + Utils.AUTHOR);
        System.out.println("Version " + Utils.getVerString());

        /* Initialize the main window */
        JFrame myFrame = new JFrame("LMCalculator");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel myPane = (JPanel)myFrame.getContentPane();

        /* Initialize MainView panel */
        MainView MVPanel = new MainView(mySettings, myFrame, calcLog);
        
        myFrame.add(MVPanel);
        
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setVisible(true);
        
    }
}