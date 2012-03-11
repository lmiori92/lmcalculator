/*
 * Lorenzo Miori - 16/01/2012 - 17/01/2012 - 18/01/2012
 * This panel contains the buttons for the simple operations.
 * Every button is binded also to the real keyboard (numeric pad).
 * Each action is redirected to the display object, which does calculations.
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
public class BasicOperationsPanel extends JPanel
        implements ActionListener {

    private JButton addButton, subButton, divButton, multButton, equalsButton;
    private int[] KEY_BINDS = {
        KeyEvent.VK_DIVIDE, KeyEvent.VK_MULTIPLY,
        KeyEvent.VK_SUBTRACT, KeyEvent.VK_ADD, KeyEvent.VK_ENTER
    };
    private DisplayPanel display;

    public BasicOperationsPanel(DisplayPanel display) {

        this.display = display;
        setLayout(new GridLayout(0, 1, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Basic"));
        setBindings();
    }

    private void setBindings() {

        /* Initialize every button */
        addButton = new JButton("+");
        addButton.setToolTipText("Tip: use the real keyboard...");
        addButton.setActionCommand("3");
        addButton.addActionListener(this);
        subButton = new JButton("-");
        subButton.setToolTipText("Tip: use the real keyboard...");
        subButton.setActionCommand("2");
        subButton.addActionListener(this);
        divButton = new JButton("/");
        divButton.setToolTipText("Tip: use the real keyboard...");
        divButton.setActionCommand("0");
        divButton.addActionListener(this);
        multButton = new JButton("*");
        multButton.setToolTipText("Tip: use the real keyboard...");
        multButton.setActionCommand("1");
        multButton.addActionListener(this);
        equalsButton = new JButton("=");
        equalsButton.setToolTipText("Tip: use the real keyboard...");
        equalsButton.setActionCommand("4");
        equalsButton.addActionListener(this);

        add(addButton);
        add(subButton);
        add(divButton);
        add(multButton);
        add(equalsButton);

        /* Bind the key press to the action listener. The command for each key is the keycode of the pressed button */
        for (int key : KEY_BINDS) {
            KeyStroke stroke = KeyStroke.getKeyStroke(key, 0, true);
            registerKeyboardAction(this, Integer.toString(key), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int key = Integer.parseInt(ae.getActionCommand());
//        System.out.println("Basic Operations key pressed!!! " + key);

        switch (key) {
            /* Events coming from a button pressed with mouse */
            case 0:
                display.performCalc(DisplayPanel.OPERATION.DIV);
                break;
            case 1:
                display.performCalc(DisplayPanel.OPERATION.MULT);
                break;
            case 2:
                display.performCalc(DisplayPanel.OPERATION.SUB);
                break;
            case 3:
                display.performCalc(DisplayPanel.OPERATION.ADD);
                break;
            case 4:
                display.performCalc(DisplayPanel.OPERATION.EQUAL);
                break;
            /* Events coming from the real keyboard
             * doClick() fires a button action, so this listener will be invoked
             * again to do the specific task according to the button.
             */
            case KeyEvent.VK_DIVIDE:
                divButton.doClick();
                break;
            case KeyEvent.VK_MULTIPLY:
                multButton.doClick();
                break;
            case KeyEvent.VK_SUBTRACT:
                subButton.doClick();
                break;
            case KeyEvent.VK_ADD:
                addButton.doClick();
                break;
            case KeyEvent.VK_ENTER:
                equalsButton.doClick();
                break;
            default:
                break;
        }
    }
}