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
package nanoverse.compiler.pipeline.instantiate.factory.control.arguments;

import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.geometry.shape.Shape;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class GeometryDescriptorFactory implements Factory<GeometryDescriptor> {

    private final GeometryDescriptorFactoryHelper helper;

    private Lattice arg0;
    private Shape arg1;

    public GeometryDescriptorFactory() {
        helper = new GeometryDescriptorFactoryHelper();
    }

    public GeometryDescriptorFactory(GeometryDescriptorFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArg0(Lattice arg0) {
        this.arg0 = arg0;
    }

    public void setArg1(Shape arg1) {
        this.arg1 = arg1;
    }

    @Override
    public GeometryDescriptor build() {
        return helper.build(arg0, arg1);
    }
}