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
package nanoverse.compiler.pipeline.instantiate.factory.processes.discrete;

import nanoverse.runtime.processes.discrete.AgentProcessArguments;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class AgentProcessArgumentsFactory implements Factory<AgentProcessArguments> {

    private final AgentProcessArgumentsFactoryHelper helper;

    private CoordinateSet arg0;
    private IntegerArgument arg1;

    public AgentProcessArgumentsFactory() {
        helper = new AgentProcessArgumentsFactoryHelper();
    }

    public AgentProcessArgumentsFactory(AgentProcessArgumentsFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArg0(CoordinateSet arg0) {
        this.arg0 = arg0;
    }

    public void setArg1(IntegerArgument arg1) {
        this.arg1 = arg1;
    }

    @Override
    public AgentProcessArguments build() {
        return helper.build(arg0, arg1);
    }
}