/*
 * Lorenzo Miori - 16/01/2012 - 17/01/2012 - 18/01/2012
 * This class implements the various buttons useful to manage the calculator's
 * memory content!
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
public class MiscPanel extends JPanel
        implements ActionListener {

    private JButton plusButton, minusButton, CMButton, RMButton, cAllButton, cDigitButton;
    private final int[] KEY_BINDS = {
        KeyEvent.VK_M, KeyEvent.VK_C, KeyEvent.VK_R,
        KeyEvent.VK_N, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_DELETE
    };
    private DisplayPanel display;

    public MiscPanel(DisplayPanel display) {

        this.display = display;

        setLayout(new GridLayout(3, 2, 15, 5));
        setBorder(BorderFactory.createTitledBorder("Tools"));

        initButtons();

    }

    private void initButtons() {
        /* Initialize every button */
        cAllButton = new JButton("CA");
        cAllButton.setToolTipText("Clear all, i.e. old result and errors (Keyboard: Canc)");
        cAllButton.setActionCommand("5");
        cAllButton.addActionListener(this);
        cDigitButton = new JButton("<-");
        cDigitButton.setToolTipText("Remove last digit (Keyboard: Backspace)");
        cDigitButton.setActionCommand("4");
        cDigitButton.addActionListener(this);
        plusButton = new JButton("M+");
        plusButton.setToolTipText("Add current display to memory (Keyboard: M)");
        plusButton.setActionCommand("0");
        plusButton.addActionListener(this);
        minusButton = new JButton("M-");
        minusButton.setToolTipText("Subract current display from memory (Keyboard: N)");
        minusButton.setActionCommand("1");
        minusButton.addActionListener(this);
        RMButton = new JButton("MR");
        RMButton.setToolTipText("Memory recall  (Keyboard: R)");
        RMButton.setActionCommand("2");
        RMButton.addActionListener(this);
        CMButton = new JButton("MC");
        CMButton.setToolTipText("Memory clear (Keyboard: C)");
        CMButton.setActionCommand("3");
        CMButton.addActionListener(this);

        add(cAllButton);
        add(cDigitButton);
        add(plusButton);
        add(minusButton);
        add(RMButton);
        add(CMButton);

        /* Bind the key press to the action listener. The command for each key is the keycode of the pressed button */
        for (int key : KEY_BINDS) {
            KeyStroke stroke = KeyStroke.getKeyStroke(key, 0, true);
            registerKeyboardAction(this, Integer.toString(key), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int key = Integer.parseInt(ae.getActionCommand());
//        System.out.println("Memory Operations key pressed!!! " + key);

        switch (key) {
            /* Events coming from a button */
            case 0:
                display.addMem();
                break;
            case 1:
                display.subMem();
                break;
            case 2:
                display.recallMem();
                break;
            case 3:
                display.clearMem();
                break;
            case 4:
                display.remDigit();
                break;
            case 5:
                display.clear();
                break;
            /* Events coming from the real keyboard
             * doClick() fires a button action, so this listener will be invoked
             * again to do the specific task
             */
            case KeyEvent.VK_M:
                plusButton.doClick();
                break;
            case KeyEvent.VK_N:
                minusButton.doClick();
                break;
            case KeyEvent.VK_C:
                CMButton.doClick();
                break;
            case KeyEvent.VK_R:
                RMButton.doClick();
                break;
            case KeyEvent.VK_BACK_SPACE:
                cDigitButton.doClick();
                break;
            case KeyEvent.VK_DELETE:
                cAllButton.doClick();
                break;
            default:
                break;
        }
    }
}