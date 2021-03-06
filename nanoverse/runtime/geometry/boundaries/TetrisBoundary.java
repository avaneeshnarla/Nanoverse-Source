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

package nanoverse.runtime.geometry.boundaries;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.control.identifiers.Flags;
import nanoverse.runtime.geometry.boundaries.helpers.WrapHelper2D;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Rectangle;
import nanoverse.runtime.geometry.shape.Shape;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Periodic in x, hard on southern y boundary, absorbing, on northern y
 * boundary, halt on absorption.
 * <p>
 * Created by dbborens on 2/9/15.
 */
public class TetrisBoundary extends Boundary implements HaltBoundary {

    private WrapHelper2D wrapper;

    @FactoryTarget
    public TetrisBoundary(Shape shape, Lattice lattice) {
        super(shape, lattice);
        wrapper = new WrapHelper2D(shape, lattice);
    }

    @Override
    protected void verify(Shape shape, Lattice lattice) {
        if (!(shape instanceof Rectangle)) {
            throw new IllegalArgumentException("Tetris boundary condition requires a Rectangle shape");
        }

        if (lattice.getDimensionality() != 2) {
            throw new IllegalArgumentException("Tetris boundary condition only well defined in 2D");
        }
    }

    @Override
    public Coordinate apply(Coordinate c) {
        Coordinate ob = shape.getOverbounds(c);
        Coordinate wrapped = wrapper.xWrap(c);

        // I have to get rid of these stupid flags. Where absolutely
        // necessary, they can be interfaces.
        if (!wrapped.equals(c)) {
            wrapped = wrapped.addFlags(Flags.BOUNDARY_APPLIED);
        }

        if (ob.y() < 0) {
            return null;
        } else if (ob.y() > 0) {
            return wrapped.addFlags(Flags.BOUNDARY_APPLIED | Flags.END_OF_WORLD);
        }

        return wrapped;
    }

    public Coordinate[] apply(Coordinate c, Coordinate d) {
        if (Math.abs(c.x() - d.x()) > wrapper.getWidth() / 2)
            if (c.x() > d.x()) {
                c = new Coordinate2D(c.x() - wrapper.getWidth(), c.y(), c.flags());
            }
        if (d.x() > c.x()) {
            d = new Coordinate2D(d.x() - wrapper.getWidth(), d.y(), d.flags());
        }
        Coordinate[] cd = {c, d};
        return cd;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return new TetrisBoundary(scaledShape, clonedLattice);
    }
}
