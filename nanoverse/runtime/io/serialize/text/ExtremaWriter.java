/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.identifiers.Extrema;

import java.io.*;

/**
 * Pushes extreme values to a writer in the appropriate format
 * for the ExtremaReader to handle.
 * <p>
 * Created by dbborens on 12/11/13.
 */
@Deprecated
public class ExtremaWriter {

    private final Writer writer;

    public ExtremaWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Format the metadata and push to the writer.
     *
     * @param fieldName
     * @param extrema
     */
    public void push(String fieldName, Extrema extrema) {
        // Format:
        // a>0.0@(0, 0, 0 | 0 | 2.0):7.0@(1, 0, 0 | 0 | 1.0)1,0,1.0

        StringBuilder sb = new StringBuilder();
        sb.append(fieldName);
        sb.append(">");
        sb.append(extrema.toString());

        try {
            writer.append(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close the writer.
     */
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
