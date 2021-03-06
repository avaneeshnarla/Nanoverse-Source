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
package nanoverse.compiler.pipeline.instantiate.factory.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.continuum.DegradeProcess;
import nanoverse.runtime.processes.continuum.DegradeProcess;

public class DegradeProcessFactory implements Factory<DegradeProcess> {

    private final DegradeProcessFactoryHelper helper;

    private BaseProcessArguments arguments;
    private DoubleArgument valueArg;
    private String layerId;
    private CoordinateSet activeSites;

    public DegradeProcessFactory() {
        helper = new DegradeProcessFactoryHelper();
    }

    public DegradeProcessFactory(DegradeProcessFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setValueArg(DoubleArgument valueArg) {
        this.valueArg = valueArg;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public void setActiveSites(CoordinateSet activeSites) {
        this.activeSites = activeSites;
    }

    @Override
    public DegradeProcess build() {
        return helper.build(arguments, valueArg, layerId, activeSites);
    }
}