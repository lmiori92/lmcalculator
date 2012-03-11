/*
 * Lorenzo Miori
 * 16/01/2012 - 17/01/2012 - 18/01/2012 - 20/01/2012 - 21/01/2012 - 22/01/2012
 * 23/01/2012 - 28/01/2012
 * This class represents the display of the calculator, hence displaying the
 * result of the last operation and other indications (e.g. Memory, Errors).
 * Offers the possibility to add, remove, clear digits and/or whole number.
 * An important feature of this class is keeping track of various values:
 *  - value being inserted by the user
 *  - value stored once the user presses enter OR an operation
 * Moreover, it add the MEMORY functionality (clear, recall, add, subract)
 * Last but not least, it performs calculations, helped by the Calculator class.
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
import java.text.*;
import java.util.Arrays;
import CalcTools.*;
import LogWindow.LogFrame;

/**
 * @author lorenzo
 */
public class DisplayPanel extends JPanel {

    /* Let's define some common errors to be displayed */
    public static enum ERRORS {

        NONE, ZERO_DIV_ERR, OVERFLOW_ERR, UNDERFLOW_ERR,
        DECBIN_ERR, BINDEC_ERR, OTHER_ERR
    };
    /* Available operations */

    public static enum OPERATION {

        NONE, EQUAL, ADD, SUB, DIV, MULT, EXP, LOG10, LOG2, INV, POW2, POW3,
        FACT, BINDEC, DECBIN, SIGN, LN
    };
    // MEMPLUS, MEMMINUS, MEMCLEAR, MEMRECALL,
    private static String[] OPER_NAMES = {
        "", "=", "+", "-", "/", "*", "Exponential", "Logarithm base 10",
        "Logarithm base 2", "Inverse", "Square power", "Cube power",
        "Factorial", "Base conversion: BINARY to DECIMAL",
        "Base conversion: DECIMAL to BINARY", "Change sign", "Natural logarithm"
    };
    //"Memory +", "Memory -", "Memory cleared", "Memory recall",
    private final int MAX_DIGITS = 14;
    /* Maximum values before changing normal <-> scientific notation view */
    private final double MAX_VALUE = 1.0E14;
    private final double MIN_VALUE = 1.0E-05;
    private final String INPUT_EMPTY = "";
    private final String OUPUT_ZERO = "0.";
    private final char SIGN_CHAR = '-';
    /* State variables */
    private OPERATION lastOperation;
    private ERRORS ErrorStatus;
    private boolean isFirstCalc; // true if first input after having cleared the display

    /* Buffer for both numeric and string value */
    private String inputBuffer; // what the user is being typing
    private double numBuffer; // the numeric value of what the user inserted, after confirmation (operation invoke)
    // This is also the most accurate value used in calculations
    private JLabel output;
    private DecimalFormat fmt, fmtExp;
    private DecimalFormatSymbols fmtOptions;
    private Settings settings;
    private Logger calcLog;
    private LogFrame calcLogWindow;

    /* Memory feature stuff */
    private JLabel memStatusLabel;
    private double memValue;
    private boolean memInUse;

    public DisplayPanel(Settings settings, Logger calcLog, LogFrame calcLogWindow) {

        /* Initialize all the variables to avoid problems */
        this.settings = settings;
        this.calcLog = calcLog;
        this.calcLogWindow = calcLogWindow;
        memInUse = false;
        memValue = 0;
        resetStatus();

        fmt = new DecimalFormat();
        fmtExp = new DecimalFormat();
        fmtOptions = new DecimalFormatSymbols();

        updateViewSettings();

        output = new JLabel();
        output.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        output.setHorizontalAlignment(JLabel.RIGHT);
        memStatusLabel = new JLabel();
        memStatusLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

        setBackground(Color.WHITE);
        /* To keep the text aligned to the right side */
        setLayout(new BorderLayout());
        add(output, BorderLayout.EAST);
        add(memStatusLabel, BorderLayout.SOUTH);
        setDisplay(0); //zero value at init
        setMemLabel();
    }

    /** Resets status variables */
    private void resetStatus() {
        numBuffer = 0;
        inputBuffer = INPUT_EMPTY;
        isFirstCalc = true;
        ErrorStatus = ERRORS.NONE;
        lastOperation = OPERATION.NONE;
    }

    private void setMemLabel() {
        /* The space string is used to keep the same display size even if not
        displaying the "M" character */
        memStatusLabel.setText(memInUse ? "M" : " ");
    }

    /** Sets up the display settings according user preferences */
    public final void updateViewSettings() {

        /* setup the formatter. This will:
         * - display decimal digits only if necessary
         * - display no decimal digits for integer values
         */

        fmt.setGroupingUsed(settings.getGrouping());
        fmtOptions.setDecimalSeparator(settings.getSeparator());
        fmtOptions.setGroupingSeparator('\'');
        fmtOptions.setExponentSeparator(" E");
        fmt.setDecimalSeparatorAlwaysShown(true);
        // We need to limit especially the number of decimal numbers to avoid bad
        // number visualization (-> 14,819999 instead of 14,82)
        fmt.setMaximumFractionDigits(MAX_DIGITS);
        fmt.setMaximumIntegerDigits(MAX_DIGITS);
        fmt.setDecimalFormatSymbols(fmtOptions);

        fmtExp.applyPattern("#.##########E0");
        fmtExp.setDecimalFormatSymbols(fmtOptions);
    }

    /** Shows the magic ... */
    public final void setDisplay(double num) {

        /* Enable scientific notation in case of too large numbers
         * NOTE: comparing a double using != isn't safe, but works well in this situation
         */
        if (numBuffer != 0.0D && (Math.abs(num) >= MAX_VALUE || Math.abs(num) <= MIN_VALUE)) {
            output.setText(fmtExp.format(num));
        } else {
            output.setText(fmt.format(num));
        }

    }

    /** This is used to print also trailing zeros, only while user inputs data */
    public final void setDisplay(double num, int numZeros) {
        char[] fill = new char[numZeros];
        Arrays.fill(fill, '0');
        output.setText(fmt.format(num) + new String(fill));
    }

    /** Just show a string (e.g. Error code) */
    public void setDisplay(String str) {
        output.setText(str);
    }

    /** Perform a display refresh */
    public void refreshDisplay() {
        if (inputBuffer.isEmpty()) {
            setDisplay(numBuffer);
        } else {
            viewInputBuffer();
        }
    }

    /** Get the label's content */
    public String getDisplay() {
        return output.getText();
    }

    /** Returns current stored value in double format */
    public double getValue() {

        double value;
        if (!inputBuffer.isEmpty()) {
            value = Double.parseDouble(inputBuffer);
        } else {
            value = numBuffer;
        }
        return value;
    }

    /** View and set as input a new numeric value */
    public void setValue(double val) {
        if (ErrorStatus != ERRORS.NONE) {
            return;
        }
        /* If the user has pressed "=" button, the result is shown on the display,
         * but it's not editable or such. So reset the calculator's status!
         */
        if (lastOperation == OPERATION.EQUAL) {
            resetStatus();
        }
        inputBuffer = Double.toString(val);
        /* Double toString puts an extra .0 also for rounded values */
        /* We need to adapt the format with our "input" format for inputBuffer */
        if (inputBuffer.endsWith(".0")) {
            inputBuffer = inputBuffer.substring(0, inputBuffer.length() - 2);
        }
        if (inputBuffer.equals("0")) {
            inputBuffer = INPUT_EMPTY;
        }

        viewInputBuffer();
    }

    /** Display the current user input */
    private void viewInputBuffer() {

        if (inputBuffer.isEmpty()) {
            setDisplay(0);
        } else {
            double temp = Double.parseDouble(inputBuffer);
            int numZeros = 0;
            /* Count the trailing zeros; only if is a decimal number */
            if (inputBuffer.length() > 1 && inputBuffer.endsWith("0") && inputBuffer.indexOf(".") != -1) {
                for (int i = (inputBuffer.length() - 1);
                        (i >= 0 && inputBuffer.charAt(i) == '0');
                        i--) {
                    numZeros++;
                }
            }
            setDisplay(temp, numZeros);
        }
    }

    /** clear display -> reset status variables */
    public void clear() {
        resetStatus();
        setDisplay(0);
    }

    /** Add a numeric digit to the input buffer */
    public boolean addDigit(int dig) {
        if (ErrorStatus != ERRORS.NONE) {
            return false;
        }

        if (lastOperation == OPERATION.EQUAL) {
            resetStatus();
        }

        if (inputBuffer.length() < MAX_DIGITS && !inputBuffer.equals("0")) {
            if (inputBuffer.isEmpty()) {
                inputBuffer = Integer.toString(dig);
            } else {
                inputBuffer += Integer.toString(dig);
            }
            viewInputBuffer();
            return true;
        }
        return false;
    }

    /* Remove last numeric digit from the input buffer */
    public boolean remDigit() {
        if (ErrorStatus != ERRORS.NONE) {
            return false;
        }

        if (lastOperation == OPERATION.EQUAL) {
            resetStatus();
        }
        if (inputBuffer.length() > 0) {
            inputBuffer = inputBuffer.substring(0, inputBuffer.length() - 1);
            if (inputBuffer.isEmpty()) {
                inputBuffer = INPUT_EMPTY;
            }
            viewInputBuffer();
            return true;
        } else {
            return false;
        }
    }

    /* Add the decimal point separator */
    public boolean addSeparator() {
        if (ErrorStatus != ERRORS.NONE) {
            return false;
        }

        if (lastOperation == OPERATION.EQUAL) {
            resetStatus();
        }
        /* Add the separator only if it's not present already. Check also MAX_DIGITS */
        if (inputBuffer.indexOf('.') == -1 && inputBuffer.length() < MAX_DIGITS - 1) {
            if (inputBuffer.isEmpty()) {
                inputBuffer = "0.";
            } else {
                inputBuffer += ".";
            }
            viewInputBuffer();
            return true;
        }

        return false;
    }

    /** Switch the sign of the current input */
    public boolean switchSign() {
        if (ErrorStatus != ERRORS.NONE) {
            return false;
        }

        if (lastOperation == OPERATION.EQUAL) {
            resetStatus();
        }

        if (!inputBuffer.isEmpty()) {
            if (inputBuffer.charAt(0) == SIGN_CHAR) {
                inputBuffer = inputBuffer.substring(1);
                viewInputBuffer();
                return true;
            } else {
                inputBuffer = "-" + inputBuffer;
                viewInputBuffer();
                return false;
            }
        }

        return false;

    }

    /** Clear memory content */
    public void clearMem() {
        memInUse = false;
        memValue = 0;
        setMemLabel();
    }

    /** Just view the memory's content if set */
    public void recallMem() {
        if (ErrorStatus != ERRORS.NONE) {
            return;
        }
        if (memInUse) {
            setValue(memValue);
        }
    }

    /** Set memory content internally */
    private void setMem(double val) {
        memInUse = true;
        memValue = val;
        setMemLabel();
    }

    /** Add current value to the memory content */
    public void addMem() {
        if (ErrorStatus != ERRORS.NONE) {
            return;
        }

        if (memInUse) {
            setMem(memValue + getValue());
        } else {
            setMem(getValue());
        }

        setMemLabel();
    }

    /** Subtract current value from memory content */
    public void subMem() {
        if (ErrorStatus != ERRORS.NONE) {
            return;
        }

        if (memInUse) {
            setMem(memValue - getValue());
        } else {
            setMem(getValue());
        }

        setMemLabel();
    }

    /** Set and display an error code */
    private void setError(ERRORS err) {

        if (ErrorStatus != ERRORS.NONE) {
            return;
        }

        ErrorStatus = err;

        switch (err) {
            case ZERO_DIV_ERR:
                setDisplay("Zero division error");
                break;
            case OVERFLOW_ERR:
                setDisplay("Overflow error");
                break;
            case UNDERFLOW_ERR:
                setDisplay("Underflow error");
                break;
            case DECBIN_ERR:
                setDisplay("Negative or non-integer error");
                break;
            case BINDEC_ERR:
                setDisplay("Binary value error");
                break;
            default:
                setDisplay("Other error");
        }
    }

    /** This method does various calculations */
    private void CalcHelper(OPERATION type) {

        boolean singleOperand = true;
        double old = getValue();

        switch (type) {
            case ADD:
                singleOperand = false;
                numBuffer = Calculator.sum(numBuffer, old);
                break;
            case MULT:
                singleOperand = false;
                numBuffer = Calculator.mult(numBuffer, old);
                break;
            case SUB:
                singleOperand = false;
                numBuffer = Calculator.sub(numBuffer, old);
                break;
            case DIV:
                singleOperand = false;
                if (old == 0) {
                    setError(ERRORS.ZERO_DIV_ERR);
                } else {
                    numBuffer = Calculator.div(numBuffer, old);
                }
                break;
            case DECBIN:
                if (Calculator.isNegative(old) || Calculator.isDecimal(old)) {
                    setError(ERRORS.DECBIN_ERR);
                } else {
                    double temp = Calculator.decToBin((long) old);
                    if (temp != -1) {
                        numBuffer = temp;
                    } else {
                        setError(ERRORS.OTHER_ERR);
                    }
                }
                break;
            case BINDEC:
                double temp = Calculator.binToDec((long) old);
                if (temp == -1) {
                    setError(ERRORS.BINDEC_ERR);
                } else {
                    numBuffer = temp;
                }
                break;
            case EQUAL:
                return;
//                break;
            case EXP:
                numBuffer = Math.exp(old);
                break;
            case LOG10:
                if (old <= 0) {
                    setError(ERRORS.OTHER_ERR);
                } else {
                    numBuffer = Math.log10(old);
                }
                break;
            case LOG2:
                if (old <= 0) {
                    setError(ERRORS.OTHER_ERR);
                } else {
                    numBuffer = Calculator.log2(old);
                }
                break;
            case LN:
                if (old <= 0) {
                    setError(ERRORS.OTHER_ERR);
                } else {
                    numBuffer = Math.log(old);
                }
                break;
            case INV:
                if (old == 0) {
                    setError(ERRORS.ZERO_DIV_ERR);
                } else {
                    numBuffer = Calculator.inv(old);
                }
                break;
            case POW2:
                numBuffer = Math.pow(old, 2);
                break;
            case POW3:
                numBuffer = Math.pow(old, 3);
                break;
            case FACT:
                if (Calculator.isNegative(old) || Calculator.isDecimal(old)) {
                    setError(ERRORS.DECBIN_ERR);
                } else {
                    numBuffer = Calculator.factorial(old);
                }
                break;
            case SIGN:
                numBuffer *= -1;
                break;
            default:
                break;
        }
        /* Check overflow error */
        if (Math.abs(numBuffer) > Double.MAX_VALUE) {
            setError(ERRORS.OVERFLOW_ERR);
        }

        /* Check underflow */
//        if (Math.abs(numBuffer) < Double.MIN_VALUE) {
//            setError(ERRORS.UNDERFLOW_ERR);
//        }
        calcLog.writeLine(OPER_NAMES[type.ordinal()]);
        if (!singleOperand) {
            calcLog.writeLine("" + old);
        }
        calcLog.writeLine("=> " + numBuffer);
    }

    /** This is the calculator's interface */
    public void performCalc(OPERATION type) {

        /* In case the calculator has done an error, stop all the operations
         * until the user clears the display
         */
        if (ErrorStatus != ERRORS.NONE) {
            return;
        }

        if (isFirstCalc) {
            numBuffer = getValue();
            lastOperation = type;
            isFirstCalc = false;
            calcLog.writeLine("" + numBuffer);
        } else {
            CalcHelper(lastOperation);
            lastOperation = type;
        }
        inputBuffer = INPUT_EMPTY;

        if (ErrorStatus == ERRORS.NONE) {
            setDisplay(numBuffer);
        }
        /* To update also the windows's content if visible */
        calcLogWindow.updateText();
    }
}