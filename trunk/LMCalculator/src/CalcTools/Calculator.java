/*
 * Lorenzo Miori - 16/01/2012 - 17/01/2012
 * This class implements some static methods for converting BIN<->DEC and
 * vice-versa, plus some other methods like base 2 logarithm and factorial...
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

package CalcTools;

/**
 * @author lorenzo
 */
public class Calculator {

    /** Converts a base 2 number to base 10.
     *  Returns -1 if it fails
     */
    public static int binToDec(long binNum) {

        int out = 0;
        boolean valid = true;
        String temp = Long.toString(binNum);
        /* Value of the power base 2 -> 2^val */
        int val = temp.length() - 1;

        for (int i = 0; (i < temp.length() && valid); i++) {
            char curr = temp.charAt(i);
            /* non-binary digits found -> fail */
            if (curr != '1' && curr != '0') {
                valid = false;
            } else {
                if (curr == '1') {
                    out += Math.pow(2, val);
                }
            }
            val--;
        }

        if (valid) {
            return out;
        } else {
            return -1;
        }
    }

    /** Converts a base 10 number to a base 2 one.
     * @param decNum
     * @return -1 if fails, non zero if OK
     */
    public static long decToBin(long decNum) {
        try {
            return Long.parseLong(Long.toBinaryString(decNum));
        } catch (Exception e) {
            /* in case the binary digits are too large for long type... */
            return -1;
        }
    }

    /** Calculate the factorial of the given number.
     * @param num
     * @return factorial result, long
     */
    public static double factorial(double num) {

        double out = 1;

        for (double i = 1; i <= num; i = i + 1) {
            out = out * i;
        }
        return out;
    }

    /** Calculates the base 2 logarithm
     * @param num
     * @return result double
     */
    public static double log2(double num) {
        return Math.log10(num) / Math.log10(2);
    }

    public static boolean isDecimal(double v) {
        return (Math.floor(v) != v);
    }

    public static boolean isNegative(double v) {
        return (v < 0);
    }

    public static double sum(double a, double b) {
        return a + b;
    }

    public static double sub(double a, double b) {
        return a - b;
    }

    public static double div(double a, double b) {
        return a / b;
    }

    public static double mult(double a, double b) {
        return a * b;
    }

    public static double inv(double x) {
        return 1.0D / x;
    }
}