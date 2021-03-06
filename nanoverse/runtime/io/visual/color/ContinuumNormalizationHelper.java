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

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Extrema;
import nanoverse.runtime.layers.SystemState;

import java.util.HashSet;

/**
 * Created by dbborens on 5/31/2015.
 */
public class ContinuumNormalizationHelper {
    private final String continuumId;
    private HashSet<Double> observedValues = new HashSet<>();

    public ContinuumNormalizationHelper(String continuumId) {
        this.continuumId = continuumId;
    }

    /**
     * Normalize a continuum value at a particular coordinate to a range
     * between 0 and 1.
     *
     * @param c
     * @param systemState
     * @return
     */
    // TODO double check this with a couple examples
    public double normalize(Coordinate c, SystemState systemState) {
        Extrema extrema = systemState.getContinuumExtrema(continuumId);
        double rawValue = systemState.getContinuumValue(continuumId, c);
        if (c.y() == 128)
            //System.out.println(rawValue);
        //System.out.println(c.toString()+" "+rawValue);
        //System.out.println(continuumId);
        if (!observedValues.contains(rawValue)) {
        //    System.err.println(rawValue);
            observedValues.add(rawValue);
        }
        double centeredValue = rawValue - extrema.min();
        double range = extrema.max() - extrema.min();
        //System.out.println(extrema.min());
        double normalized = 0;
        if (range!=0) {
             normalized = centeredValue / range;
            //System.out.println(normalized+ " = "+centeredValue+" / "+range);
        }
        //else {
        //    System.out.println("Continuum Value is the same " +
        //            "everywhere:"+extrema.max());
        //}
        //System.out.println(normalized);
        return normalized;
    }

    @Override
    public int hashCode() {
        return continuumId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContinuumNormalizationHelper that = (ContinuumNormalizationHelper) o;

        return continuumId.equals(that.continuumId);

    }
}
