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

package nanoverse.runtime.geometry.boundaries.helpers;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.control.identifiers.Flags;
import nanoverse.runtime.geometry.basis.BasisHelper2D;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.lattice.TriangularLattice;
import nanoverse.runtime.geometry.shape.Rectangle;
import nanoverse.runtime.geometry.shape.Shape;

/**
 * Created by dbborens on 5/7/14.
 */
public class WrapHelper2D extends WrapHelper {

    private final int width, height;
    private final BasisHelper2D basisHelper;

    public WrapHelper2D(Shape shape, Lattice lattice) {
        super(shape, lattice);

        if (shape.getClass() != Rectangle.class) {
            throw new IllegalArgumentException("WrapHelper2D only supports Rectangle arenas.");
        }

        Rectangle rect = (Rectangle) shape;

        width = rect.getDimensions()[0];
        height = rect.getDimensions()[1];

        basisHelper = rect.getBasisHelper();
        if (lattice instanceof TriangularLattice && width % 2 != 0) {
            throw new IllegalArgumentException("Periodic behavior on triangular lattice requires even width");
        }
    }

    public int getWidth() {
        return width;
    }
    public Coordinate wrapAll(Coordinate toWrap) {
        checkValid(toWrap);
        Coordinate xWrapped = xWrap(toWrap);
        Coordinate wrapped = yWrap(xWrapped);
        return wrapped;
    }

    public Coordinate xWrap(Coordinate toWrap) {
        checkValid(toWrap);
        // Remove any x-adjustment from y.
        Coordinate ret = basisHelper.invAdjust(toWrap);

        // Wrap x.
        int over = shape.getOverbounds(ret).x();

        while (over != 0) {
            if (over > 0) {
                // over == 1 --> wrap to 0
                ret = new Coordinate2D(over - 1, ret.y(), ret.flags());

            } else {
                // over == -1 --> wrap to xMax (which is width - 1)
                ret = new Coordinate2D(width + over, ret.y(), ret.flags());
            }
            over = shape.getOverbounds(ret).x();
        }

        // Apply x-adjustment to y.
        ret = basisHelper.adjust(ret);

        // Return new coordinate.
        return ret;
    }

    public Coordinate yWrap(Coordinate toWrap) {
        checkValid(toWrap);
        Coordinate ret = toWrap;

        // Wrap y.
        int over = shape.getOverbounds(ret).y();

        while (over != 0) {
            if (over > 0) {
                // over == 1 --> wrap to 0
                ret = new Coordinate2D(ret.x(), over - 1, ret.flags());

            } else {
                // over == -1 --> wrap to xMax (which is width - 1)
                ret = new Coordinate2D(ret.x(), height + over, ret.flags());
            }

            over = shape.getOverbounds(ret).y();
        }

        // Return.
        return ret;
    }


    public Coordinate zWrap(Coordinate toWrap) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void checkValid(Coordinate toWrap) {
        if (!toWrap.hasFlag(Flags.PLANAR)) {
            throw new IllegalStateException("WrapHelper2D requires a 2D coordinate.");
        }
    }

    public BasisHelper2D getBasisHelper() {
        return basisHelper;
    }
}
