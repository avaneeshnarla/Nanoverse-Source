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

package nanoverse.runtime.geometry;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.control.identifiers.Coordinate3D;
import nanoverse.runtime.control.identifiers.Flags;
import nanoverse.runtime.geometry.boundaries.Boundary;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Geometry {

	/* Lookup modes */

    // Use boundary conditions to calculate "true" coordinate.
    public static final int APPLY_BOUNDARIES = 0;

    // Ignore boundary conditions; return coordinate as if infinite
    // boundary conditions existed.
    public static final int IGNORE_BOUNDARIES = 1;

    // Remove any agents that are beyond boundaries from results.
    public static final int EXCLUDE_BOUNDARIES = 2;

    // Ignore boundary conditions, but indicate that they would have
    // applied.
    public static final int FLAG_BOUNDARIES = 3;

    protected Lattice lattice;
    protected Boundary boundary;
    protected Shape shape;

    private HashMap<Coordinate, Integer> coordinateIndex = new HashMap<Coordinate, Integer>();

    public Geometry(Lattice lattice, Shape shape, Boundary boundary) {
        this.boundary = boundary;
        this.lattice = lattice;
        this.shape = shape;
        rebuildIndex();
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public Shape getShape() {
        return shape;
    }
    public void rebuildIndex() {
        coordinateIndex.clear();


        // dependencies are sometimes left uninitialized for mock testing.
        // In these cases, there is nothing to index, so return.
        if (getCanonicalSites() == null) {
            return;
        }

        Coordinate[] sites = getCanonicalSites();


        for (Integer i = 0; i < sites.length; i++) {
            Coordinate c = sites[i];

            // Coordinate index is for canonical coordinates only
            Coordinate cc = c.canonicalize();
            coordinateIndex.put(cc, i);
        }

    }

    public Coordinate[] getCanonicalSites() {
        return shape.getCanonicalSites();
    }

    public Coordinate apply(Coordinate coord, int mode) {
        if (mode == APPLY_BOUNDARIES) {
            return applyBoundaries(coord);
        } else if (mode == FLAG_BOUNDARIES) {
            return setBoundaryFlag(coord);
        } else if (mode == EXCLUDE_BOUNDARIES) {
            return excludeBoundaries(coord);
        } else if (mode == IGNORE_BOUNDARIES) {
            return coord;
        } else {
            throw new IllegalArgumentException("Unrecognized mode " + mode + ".");
        }
    }

    private Coordinate setBoundaryFlag(Coordinate c) {
        Coordinate w = boundary.apply(c);

        if (!w.equals(c)) {
            int flags = c.flags() | Flags.BOUNDARY_IGNORED;

            if (c.hasFlag(Flags.PLANAR)) {
                return new Coordinate2D(c.x(), c.y(), flags);
            } else {
                return new Coordinate3D(c.x(), c.y(), c.z(), flags);
            }
        } else {
            return c;
        }
    }

    private Coordinate applyBoundaries(Coordinate c) {
        Coordinate wrapped = boundary.apply(c);
        return wrapped;
    }

    private Coordinate excludeBoundaries(Coordinate c) {
        Coordinate w = boundary.apply(c);
        if (w == null) {
            return null;
        } else if (!w.equals(c)) {
            return null;
        } else {
            return c;
        }
    }

    public Coordinate[] getNeighbors(Coordinate coord, int mode) {

        Coordinate[] neighbors = lattice.getNeighbors(coord);
        if (mode == APPLY_BOUNDARIES) {
            return applyBoundaries(neighbors);
        } else if (mode == FLAG_BOUNDARIES) {
            return setBoundaryFlag(neighbors);
        } else if (mode == EXCLUDE_BOUNDARIES) {
            return excludeBoundaries(neighbors);
        } else if (mode == IGNORE_BOUNDARIES) {
            return neighbors;
        } else {
            throw new IllegalArgumentException("Unrecognized mode " + mode + ".");
        }
    }

    public Coordinate[] getBox(Coordinate coord, int mode) {

        Coordinate[] neighbors = lattice.getBox(coord);
        if (mode == APPLY_BOUNDARIES) {
            return applyBoundaries(neighbors);
        } else if (mode == FLAG_BOUNDARIES) {
            return setBoundaryFlag(neighbors);
        } else if (mode == EXCLUDE_BOUNDARIES) {
            return excludeBoundaries(neighbors);
        } else if (mode == IGNORE_BOUNDARIES) {
            return neighbors;
        } else {
            throw new IllegalArgumentException("Unrecognized mode " + mode + ".");
        }
    }

    private Coordinate[] setBoundaryFlag(Coordinate[] coords) {
        ArrayList<Coordinate> applied = new ArrayList<>(coords.length);

        for (Coordinate coord : coords) {
            applied.add(setBoundaryFlag(coord));
        }

        return applied.toArray(new Coordinate[0]);

    }

    private Coordinate[] applyBoundaries(Coordinate[] coords) {
        ArrayList<Coordinate> applied = new ArrayList<>(coords.length);

        for (Coordinate coord : coords) {
            Coordinate res = applyBoundaries(coord);

            if (res != null) {
                applied.add(res);
            }
        }

        return applied.toArray(new Coordinate[0]);
    }

    private Coordinate[] excludeBoundaries(Coordinate[] coords) {
        ArrayList<Coordinate> applied = new ArrayList<Coordinate>(coords.length);

        for (Coordinate coord : coords) {
            Coordinate candidate = excludeBoundaries(coord);
            if (candidate != null) {
                applied.add(candidate);
            }
        }
        return applied.toArray(new Coordinate[0]);
    }

    public Coordinate[] getAnnulus(Coordinate coord, int r, int mode) {
        Coordinate[] annulus = lattice.getAnnulus(coord, r);
        if (mode == APPLY_BOUNDARIES) {
            return applyBoundaries(annulus);
        } else if (mode == FLAG_BOUNDARIES) {
            return setBoundaryFlag(annulus);
        } else if (mode == EXCLUDE_BOUNDARIES) {
            return excludeBoundaries(annulus);
        } else if (mode == IGNORE_BOUNDARIES) {
            return annulus;
        } else {
            throw new IllegalArgumentException("Unrecognized mode " + mode + ".");
        }
    }

    public Coordinate rel2abs(Coordinate coord, Coordinate displacement, int mode) {
        Coordinate naive = lattice.rel2abs(coord, displacement);

        if (mode == APPLY_BOUNDARIES) {
            return applyBoundaries(naive);
        } else if (mode == FLAG_BOUNDARIES) {
            return setBoundaryFlag(naive);
        } else if (mode == EXCLUDE_BOUNDARIES) {
            return excludeBoundaries(naive);
        } else if (mode == IGNORE_BOUNDARIES) {
            return naive;
        } else {
            throw new IllegalArgumentException("Unrecognized mode " + mode + ".");
        }
    }

    public int getNeighborhoodDistance(Coordinate p, Coordinate q, int mode) {
        if (mode == APPLY_BOUNDARIES) {
            Coordinate pw = boundary.apply(p);
            Coordinate qw = boundary.apply(q);
            return lattice.getNeighborhoodDistance(pw, qw);
        } else if (mode == IGNORE_BOUNDARIES) {
            return lattice.getNeighborhoodDistance(p, q);
        } else {
            throw new IllegalArgumentException("Unrecognized mode " + mode + ".");
        }
    }

    public Coordinate getDisplacement(Coordinate pCoord, Coordinate qCoord, int mode) {

        if (mode == APPLY_BOUNDARIES) {
            Coordinate pw = boundary.apply(pCoord);
            Coordinate qw = boundary.apply(qCoord);
            //Coordinate pw= boundary.apply(pw1, qw1)[0];
            //Coordinate qw= boundary.apply(pw1, qw1)[1];
            Coordinate wrapped = lattice.getDisplacement(pw, qw);
            /*if (wrapped.x()>0 || wrapped.y()>0){
            System.out.println("Preparing to shove "+pw+" toward "+qw+". " +
                    "Displacement:"+ wrapped);}*/
            return wrapped;
        } else if (mode == FLAG_BOUNDARIES) {
            throw new UnsupportedOperationException();
        } else if (mode == EXCLUDE_BOUNDARIES) {
            throw new UnsupportedOperationException();
        } else if (mode == IGNORE_BOUNDARIES) {
            Coordinate naive = lattice.getDisplacement(pCoord, qCoord);
            return naive;
        } else {
            throw new IllegalArgumentException("Unrecognized mode " + mode + ".");
        }
    }

    public int getDimensionality() {
        return lattice.getDimensionality();
    }

    public int getConnectivity() {
        return lattice.getConnectivity();
    }

    public boolean isInfinite() {
        return boundary.isInfinite();
    }

    public Function<Coordinate, Integer> getIndexer() {
        return this::coordToIndex;
    }

    private Integer coordToIndex(Coordinate coord) {
        Coordinate canonical = coord.canonicalize();

        if (!coordinateIndex.containsKey(canonical)) {
//            System.out.println("I don't think I contain the coordinate " + canonical);
            return null;
        } else {
            return coordinateIndex.get(canonical);
        }
    }

    public Coordinate getCenter() {
        return shape.getCenter();
    }

    @Override
    public boolean equals(Object obj) {
        // Not an object?
        if (!(obj instanceof Geometry)) {
            return false;
        }

        Geometry other = (Geometry) obj;

        // Shape different?
        if (!other.shape.equals(this.shape)) {
            return false;
        }

        // Lattice different?
        if (!other.lattice.equals(this.lattice)) {
            return false;
        }

        // Boundaries different?
        if (!other.boundary.equals(this.boundary)) {
            return false;
        }
        // If all these things are equal, the geometries are equal
        return true;
    }

    public Geometry cloneAtScale(double rangeScale) {
        Lattice clonedLattice = lattice.clone();
        Shape scaledShape = shape.cloneAtScale(clonedLattice, rangeScale);
        Boundary scaledBoundary = boundary.clone(scaledShape, clonedLattice);
        Geometry scaledClone = new Geometry(clonedLattice, scaledShape, scaledBoundary);
        return scaledClone;
    }

    /**
     * Returns true if the other nanoverse.runtime.geometry is exactly the same as this one
     * except for the dimensions of its Shape object. Used for testing.
     *
     * @param other
     * @return
     */
    public boolean similar(Geometry other) {
        Class selfClass, otherClass;
        selfClass = getClass();
        otherClass = other.getClass();

        // Same type of shape?
        if (!selfClass.equals(otherClass)) {
            return false;
        }

        // Lattice different?
        if (!other.lattice.equals(this.lattice)) {
            return false;
        }

        // Boundaries different?
        if (!other.boundary.equals(this.boundary)) {
            return false;
        }

        // They are similar if all of these things were true.
        return true;
    }

    public boolean isInBounds(Coordinate coordinate) {
        Coordinate canonicalized = coordinate.canonicalize();
        return (coordinateIndex.containsKey(canonicalized));
    }

    public Coordinate getZeroVector() {
        return lattice.getZeroVector();
    }

    /**
     * Janky solution to problem of identifying components without compromising
     * encapsulation. Permanent solution should involve a different hierarchy
     * of constructors.
     *
     * @return
     */
    public Class[] getComponentClasses() {
        Class[] componentClasses = {
            lattice.getClass(),
            shape.getClass(),
            boundary.getClass()
        };

        return componentClasses;
    }
}
