package nanoverse.runtime.geometry.basis;

import nanoverse.runtime.control.identifiers.Coordinate;

/**
 * Created by dbborens on 11/16/2015.
 */
public interface BasisHelper2D {

    /**
     * Convert 2D cartesian coordinates to coordinates described
     * in the native basis of the lattice.
     */
    Coordinate adjust(Coordinate c);

    /**
     * Convert 2D cartesian coordinates to coordinates described
     * in the native basis of the lattice.
     */
    Coordinate invAdjust(Coordinate c);
}
