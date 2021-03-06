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

package nanoverse.compiler.pipeline.translate.symbol.geometry;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.GeometryDescriptorLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.geometry.lattice.LatticeClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.geometry.shape.ShapeClassSymbolTable;
import nanoverse.runtime.control.arguments.GeometryDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class GeometryDescriptorInstanceSymbolTable extends MapSymbolTable<GeometryDescriptor> {
    @Override
    public String getDescription() {
        return "The top-level geometric properties of the simulation. " +
            "Properties specified here will carry over to all nanoverse.runtime.layers of " +
            "the simulation.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        shape(ret);
        lattice(ret);
        return ret;
    }

    private void lattice(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new LatticeClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The lattice topology " +
            "(square, triangular, etc) to be used for all nanoverse.runtime.layers in this" +
            " simulation.");
        ret.put("lattice", ms);
    }

    private void shape(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ShapeClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The boundary shape and size " +
            "associated with all nanoverse.runtime.layers of this simulation. This does " +
            "not necessarily need to match the lattice topology, " +
            "although they must be compatible. For example, one could " +
            "use a TriangularLattice with a Rectangle shape, but not " +
            "with a Cuboid.");
        ret.put("shape", ms);
    }

    @Override
    public Loader getLoader() {
        return new GeometryDescriptorLoader();
    }
}
