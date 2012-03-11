/*
 * Lorenzo Miori - 16/01/2012 - 17/01/2012 - 19/01/2012
 * This panel will initialize and display all the calculator components.
 * Moreover it sets up the program's menu
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import CalcTools.*;
import AboutWindow.AboutFrame;
import LogWindow.LogFrame;

/**
 * @author lorenzo
 */
public class MainView extends JPanel
        implements ActionListener, WindowListener {

    private Settings settings;
    private Logger calcLog;
    private JFrame MainFrame;
    private JMenuBar menuBar;
    private JMenu toolsMenu, AppMenu;
    private JCheckBoxMenuItem option1Btn;
    private AboutFrame aboutFrame;
    private LogFrame logFrame;

    /* Calculator's panels */
    private KeypadPanel numPad;
    private BasicOperationsPanel basicPanel;
    private DisplayPanel display;
    private AdvancedOperations advOperations;
    private MiscPanel memPanel;
    private BaseConvPanel baseConvP;

    /* Some constants */
    private final int X_SIZE = 540;
    private final int Y_SIZE = 300;

    /* This implements the main Calculator's panel, with buttons etc... */
    private class CalcPanel extends JPanel {

        public CalcPanel() {

            setLayout(new BorderLayout());
            JPanel temp = new JPanel();
            temp.setLayout(new BorderLayout());
            temp.add(basicPanel, BorderLayout.WEST);
            temp.add(advOperations, BorderLayout.EAST);

            add(numPad, BorderLayout.WEST);
            /* Operations tools */
            add(temp, BorderLayout.CENTER);
            add(memPanel, BorderLayout.EAST);
            add(baseConvP, BorderLayout.SOUTH);
        }
    }

    public MainView(Settings settings, JFrame MainFrame, Logger calcLog) {

        this.settings = settings;
        this.MainFrame = MainFrame;
        this.calcLog = calcLog;

        MainFrame.addWindowListener(this);

        /* Main menu entry */
        AppMenu = new JMenu("LMCalculator", true);
        AppMenu.setMnemonic('u');
        JMenuItem aboutItem = new JMenuItem("About");
        AppMenu.add(aboutItem);
        aboutItem.addActionListener(this);
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
        JMenuItem exitItem = new JMenuItem("Exit");
        AppMenu.add(exitItem);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exitItem.addActionListener(this);

        /* Tools menu */
        toolsMenu = new JMenu("Tools");
        toolsMenu.setMnemonic('t');
        JMenuItem logMenu = new JMenuItem("History");
        logMenu.addActionListener(this);
        logMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));

        JMenuItem copyMenu = new JMenuItem("Copy");
        copyMenu.addActionListener(this);
        copyMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        JMenuItem pasteMenu = new JMenuItem("Paste");
        pasteMenu.addActionListener(this);
        pasteMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));

        option1Btn = new JCheckBoxMenuItem("Digits grouping");
        option1Btn.addActionListener(this);
        option1Btn.setActionCommand("option1");
        option1Btn.setState(settings.getGrouping());
        option1Btn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));

        /* Decimal separator submenu */
        JMenu option2Btn = new JMenu("Decimal separator");
        ButtonGroup decSepGroup = new ButtonGroup();
        JRadioButtonMenuItem pointBtn = new JRadioButtonMenuItem("Point");
        pointBtn.addActionListener(this);
        JRadioButtonMenuItem commaBtn = new JRadioButtonMenuItem("Comma");
        commaBtn.addActionListener(this);
        decSepGroup.add(pointBtn);
        decSepGroup.add(commaBtn);
        pointBtn.setSelected(true);
        option2Btn.add(pointBtn);
        option2Btn.add(commaBtn);

        toolsMenu.add(logMenu);
        toolsMenu.addSeparator();
        toolsMenu.add(copyMenu);
        toolsMenu.add(pasteMenu);
        toolsMenu.addSeparator();
        toolsMenu.add(option1Btn);
        toolsMenu.addSeparator();
        toolsMenu.add(option2Btn);

        /* Create the menu bar, fill it, and set it to main window */
        menuBar = new JMenuBar();
        menuBar.setBorderPainted(true);
        menuBar.add(AppMenu);
        menuBar.add(toolsMenu);
        MainFrame.setJMenuBar(menuBar);

        /* Set window size and center it in the screen */
        this.setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        centerFrame();

        setLayout(new BorderLayout());

        /* Initialize calculator's components */
        aboutFrame = new AboutFrame();
        logFrame = new LogFrame(calcLog);
        display = new DisplayPanel(settings, calcLog, logFrame);
        display.setDisplay("Welcome to LMCalculator!"); // :D
        numPad = new KeypadPanel(settings, display);
        basicPanel = new BasicOperationsPanel(display);
        advOperations = new AdvancedOperations(display);
        memPanel = new MiscPanel(display);
        baseConvP = new BaseConvPanel(display);

        add(display, BorderLayout.NORTH);
        add(new CalcPanel(), BorderLayout.CENTER);

    }

    private void centerFrame() {
        /* Fetch screen's size */
        Dimension dim = Utils.getScreenSize();
        /* Calculate the new position, according frame size itself
        and set it to window location */
        MainFrame.setLocation((dim.width - X_SIZE) / 2, (dim.height - Y_SIZE) / 2);

    }

    private void doShutdown() {
        System.out.println("Exiting...");
        calcLog.write("Bye Bye!");
        calcLog.close();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String cmd = ae.getActionCommand();

        if (cmd.equals("About")) {
            if (!aboutFrame.isVisible()) {
                aboutFrame.setVisible(true);
            }
        }
        if (cmd.equals("History")) {
            if (!logFrame.isVisible()) {
                logFrame.setVisible(true);
            }
        }
        if (cmd.equals("Exit")) {
            doShutdown();
            System.exit(0);
        }
        if (cmd.equals("option1")) {
            settings.setGrouping(option1Btn.getState());
            display.updateViewSettings();
            display.refreshDisplay();
        }
        if (cmd.equals("Comma")) {
            settings.setSeparator(Settings.COMMA_SEPARATOR);
            numPad.setSeparatorLabel();
            display.updateViewSettings();
            display.refreshDisplay();
        }
        if (cmd.equals("Point")) {
            settings.setSeparator(Settings.POINT_SEPARATOR);
            numPad.setSeparatorLabel();
            display.updateViewSettings();
            display.refreshDisplay();
        }
        if (cmd.equals("Copy")) {
            Utils.setClipboardContents(Double.toString(display.getValue()));
        }
        if (cmd.equals("Paste")) {
            try {
                /* Replace comma by dot and eventually + by 0 for exponential forms */
                display.setValue(Double.parseDouble(Utils.getClipboardContents().replace(',', '.').replace('+', '0')));
            } catch (Exception e) {
                //Do nothing
            }
        }
    }

    @Override
    /* Invoked when the user closes the window */
    public void windowClosing(WindowEvent e) {
        doShutdown();
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }
}