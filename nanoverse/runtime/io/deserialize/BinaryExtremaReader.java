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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.control.identifiers.*;

import java.io.*;

/**
 * Created by dbborens on 6/1/2015.
 */
public class BinaryExtremaReader {

    public Extrema read(DataInputStream input) throws IOException {
        double min = input.readDouble();
        TemporalCoordinate argMin = readArgument(input);
        double max = input.readDouble();
        TemporalCoordinate argMax = readArgument(input);
        return new Extrema(min, argMin, max, argMax);
    }

//    private String readName(DataInputStream input) throws IOException{
//        int length = input.readInt();
//        String name = readString(input, length);
//    }

    private TemporalCoordinate readArgument(DataInputStream input) throws IOException {
        int flags = input.readInt();
        int x = input.readInt();
        int y = input.readInt();

        Coordinate c;

        if ((flags & Flags.PLANAR) == 0) {
            int z = input.readInt();
            c = new Coordinate3D(x, y, z, flags);
        } else {
            c = new Coordinate2D(x, y, flags);
        }
        double time = input.readDouble();

        return new TemporalCoordinate(c, time);
    }

//    private String readString(DataInputStream input, int length) {
//        StringBuffer nameBuffer = new StringBuffer(length);
//        IntStream.range(0, length)
//                .forEach(i -> {
//                    try {
//                        char c = input.readChar();
//                        nameBuffer.append(c);
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                });
//        return nameBuffer.toString();
//    }
}
