/*
 * Lorenzo Miori - 16/01/2012 - 18/01/2012
 * This class manages the saving process of the operations done using the
 * calculator
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

import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * @author lorenzo
 */
public class Logger {

    private String fileName;
    private StringBuffer strBuffer;
    private PrintWriter Writer;
    private FileOutputStream Stream;
    private boolean fileOK;

    public Logger(String filename) {
        this.fileName = filename;
        strBuffer = new StringBuffer();
        fileOK = openFile();
    }

    private boolean openFile() {
        try {
            /* Open a new file. If it exists, just overwrite it! */
            Stream = new FileOutputStream(fileName, false);
            /* Character conversion bridge to actual file data */
            Writer = new PrintWriter(Stream);
            return true;
        } catch (Exception e) {
            System.out.println("Unable to open file for writing!");
            return false;
        }
    }

    public void close() {
        if (fileOK) {
            Writer.close();
            try {
                Stream.close();
            } catch (Exception e) {
                //Do nothing
            }
        }
    }

    /** Clears both the buffer and the file content that has been written */
    public void clear() {
        strBuffer.delete(0, strBuffer.length());
        close();
        openFile();
    }

    public void writeLine(Object str) {
        if (fileOK) {
            Writer.println(str);
            Writer.flush();
        }
        strBuffer.append(str);
        strBuffer.append('\n');
    }

    public void write(Object str) {
        if (fileOK) {
            Writer.print(str);
            Writer.flush();
        }
        strBuffer.append(str);
    }

    public String getBuffer() {
        return strBuffer.toString();
    }
}
