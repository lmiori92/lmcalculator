/*
 * Lorenzo Miori - 16/01/2012; 18/01/2012
 * This panel contains the buttons for the advanced operations
 * e.g. logarithm, exponential, factorial
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

package CalcGui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author lorenzo
 */
public class AdvancedOperations extends JPanel
        implements ActionListener {

    private DisplayPanel display;
    private JButton[] advButtons;
    /* This array has the same index as the correspondent button meaning */
    private final DisplayPanel.OPERATION[] BTN_BINDS = {
        DisplayPanel.OPERATION.FACT, DisplayPanel.OPERATION.INV,
        DisplayPanel.OPERATION.POW2, DisplayPanel.OPERATION.POW3,
        DisplayPanel.OPERATION.LOG2, DisplayPanel.OPERATION.LOG10,
        DisplayPanel.OPERATION.EXP, DisplayPanel.OPERATION.LN
    };

    public AdvancedOperations(DisplayPanel display) {

        this.display = display;
        setLayout(new GridLayout(4, 2, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Scientific"));

        /* Initialize every button */
        advButtons = new JButton[]{
            new JButton("!"), new JButton("1/x"),
            new JButton("x\u00B2"), new JButton("x\u00B3"), // square and cube power characters
            new JButton("log2"), new JButton("log10"),
            new JButton("exp"), new JButton("ln")
        };

        setBindings();

    }

    private void setBindings() {
        int i = 0;
        for (JButton btn : advButtons) {
            btn.addActionListener(this);
            btn.setActionCommand(Integer.toString(i));
            add(btn);
            i++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int key = Integer.parseInt(ae.getActionCommand());

        /* Do the appropriate calculus */
        display.performCalc(BTN_BINDS[key]);
        /* direct calculus, no need to wait for equal event from user */
        display.performCalc(DisplayPanel.OPERATION.EQUAL);
    }
}