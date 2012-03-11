/*
 * Lorenzo Miori - 16/01/2012 - 19/01/2012
 * This class defines program version, author and some other info!
 * Moreover it has two useful static methods to copy/paste text from the
 * system clipboard.
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

import java.awt.*;
import java.awt.datatransfer.*;

/**
 * @author lorenzo
 */
public class Utils {

    public static final int[] VER = {1, 0};
    public static final String AUTHOR = "Lorenzo Miori";
    public static final String AUTHOR_INFO = "ID: 9666";
    public static final String DATE = "Jan 2012";
    public static final String BUILD_OS = "Linux (Kubuntu 11.10)";

    public static String getVerString() {
        return VER[0] + "." + VER[1];
    }

    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    //These two methods are slightly modified by me from an example hosted at:
    //http://www.javapractices.com/topic/TopicAction.do?Id=82

    /** Set a text into the system clipboard */
    public static void setClipboardContents(String aString) {
        StringSelection stringSelection = new StringSelection(aString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /** Get the text set into the system clipboard */
    public static String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);

        if ((contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
            }
        }
        return result;
    }
}