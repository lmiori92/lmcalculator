/*
 * Lorenzo Miori - 16/01/2012 - 17/01/2012
 * This panel contains the buttons for the numeric pad, binded also to keyboard.
 * Input itself is managed by this class while input buffer (i.e. what the user
 * is entering) is managed by the DisplayPanel class.
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
import java.awt.event.*;
import javax.swing.*;
import CalcTools.Settings;

/**
 * @author lorenzo
 */
public class KeypadPanel extends JPanel
        implements ActionListener // Used to catch key strokes
{

    private JButton[] numButtons;
    private Settings settings;
    private DisplayPanel display;
    private int[] KEY_BINDS = {KeyEvent.VK_NUMPAD0, KeyEvent.VK_0,
        KeyEvent.VK_NUMPAD1, KeyEvent.VK_1, KeyEvent.VK_NUMPAD2,
        KeyEvent.VK_2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_3, KeyEvent.VK_NUMPAD4,
        KeyEvent.VK_4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_5, KeyEvent.VK_NUMPAD6,
        KeyEvent.VK_6, KeyEvent.VK_NUMPAD7, KeyEvent.VK_7, KeyEvent.VK_NUMPAD8,
        KeyEvent.VK_8, KeyEvent.VK_NUMPAD9, KeyEvent.VK_9, KeyEvent.VK_DECIMAL,
        KeyEvent.VK_COMMA};

    public KeypadPanel(Settings settings, DisplayPanel display) {

        this.settings = settings;
        this.display = display;

        //4x3 grid for the buttons, 15 pixel gap between buttons
        setLayout(new GridLayout(0, 3, 10, 15));
        setBorder(BorderFactory.createTitledBorder("Numpad"));

        numButtons = new JButton[]{
            new JButton("1"), new JButton("2"),
            new JButton("3"), new JButton("4"),
            new JButton("5"), new JButton("6"),
            new JButton("7"), new JButton("8"),
            new JButton("9"), new JButton("0"),
            new JButton(), new JButton("+/-")
        };

        setSeparatorLabel();
        setBindings();

    }

    private void setBindings() {

        /* Now setup the panel itself adding various buttons */
        int i = 20; //Starting from 20 to be able to use the same ActionListener without collisions
        for (JButton btn : numButtons) {
            btn.setToolTipText("Tip: use the real keyboard...");
            btn.setActionCommand(Integer.toString(i));
            btn.addActionListener(this);
            add(btn);
            i++;
        }

        /* Bind the key press to the action listener */
        for (int key : KEY_BINDS) {
            KeyStroke stroke = KeyStroke.getKeyStroke(key, 0, true);
            registerKeyboardAction(this, Integer.toString(key), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
    }

    public final void setSeparatorLabel() {
        numButtons[10].setText(String.valueOf(settings.getSeparator()));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int key = Integer.parseInt(ae.getActionCommand());
//        System.out.println("Numeric pad key pressed!!! " + key);

        /* Cases from 20-30 are related to the virtual keypad (button ActionListener)
         * Other cases are real keyboard events (specific key code)
         */
        switch (key) {
            /* Event triggered by the various buttons */
            case 20:
                display.addDigit(1);
                break;
            case 21:
                display.addDigit(2);
                break;
            case 22:
                display.addDigit(3);
                break;
            case 23:
                display.addDigit(4);
                break;
            case 24:
                display.addDigit(5);
                break;
            case 25:
                display.addDigit(6);
                break;
            case 26:
                display.addDigit(7);
                break;
            case 27:
                display.addDigit(8);
                break;
            case 28:
                display.addDigit(9);
                break;
            case 29:
                display.addDigit(0);
                break;
            case 30:
                display.addSeparator();
                break;
            case 31:
                //display.performCalc(DisplayPanel.OPERATION.SIGN);
                //display.performCalc(DisplayPanel.OPERATION.EQUAL);
                display.switchSign();
                break;
            /* Events triggered by the real keyboard -> Key Code
             * Pressing the real key, will invoke doClick on the right button.
             * Then, action listener for the button will fire and the right action will be
             * chosen.
             */
            case KeyEvent.VK_0:
            case KeyEvent.VK_NUMPAD0:
                numButtons[9].doClick();
                break;
            case KeyEvent.VK_1:
            case KeyEvent.VK_NUMPAD1:
                numButtons[0].doClick();
                break;
            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2:
                numButtons[1].doClick();
                break;
            case KeyEvent.VK_3:
            case KeyEvent.VK_NUMPAD3:
                numButtons[2].doClick();
                break;
            case KeyEvent.VK_4:
            case KeyEvent.VK_NUMPAD4:
                numButtons[3].doClick();
                break;
            case KeyEvent.VK_5:
            case KeyEvent.VK_NUMPAD5:
                numButtons[4].doClick();
                break;
            case KeyEvent.VK_6:
            case KeyEvent.VK_NUMPAD6:
                numButtons[5].doClick();
                break;
            case KeyEvent.VK_7:
            case KeyEvent.VK_NUMPAD7:
                numButtons[6].doClick();
                break;
            case KeyEvent.VK_8:
            case KeyEvent.VK_NUMPAD8:
                numButtons[7].doClick();
                break;
            case KeyEvent.VK_9:
            case KeyEvent.VK_NUMPAD9:
                numButtons[8].doClick();
                break;
            case KeyEvent.VK_COMMA:
            case KeyEvent.VK_DECIMAL:
                numButtons[10].doClick();
                break;
            default:
                break;
        }
    }
}