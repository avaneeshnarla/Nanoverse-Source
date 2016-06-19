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

package nanoverse.runtime.control.identifiers;

import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 8/11/15.
 */
public class Coordinate3D extends Coordinate {

    @FactoryTarget
    public Coordinate3D(int x, int y, int z, int flags) {
        super(x, y, z, flags);
    }

    public Coordinate3D(Coordinate c) {
        super(c);
    }

    public Coordinate3D(int[] vec, int flags) {
        super(vec, flags);
    }
}