/*
 * Lorenzo Miori - 16/01/2012
 * A class to store some simple settings for the calculator
 * e.g. decimal separator preference
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
public class Settings {

    /* The decimal separator used in the calculator's display */
    public static final char COMMA_SEPARATOR = ',';
    public static final char POINT_SEPARATOR = '.';
    private char decSeparator = POINT_SEPARATOR;
    /* If putting the separator every 3 digits */
    private boolean groupDigits = true;

    public char getSeparator() {
        return decSeparator;
    }

    public void setSeparator(char x) {
        decSeparator = x;
    }

    public boolean getGrouping() {
        return groupDigits;
    }

    public void setGrouping(boolean val) {
        groupDigits = val;
    }
}