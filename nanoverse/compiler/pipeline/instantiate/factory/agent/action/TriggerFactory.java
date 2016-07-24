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
package nanoverse.compiler.pipeline.instantiate.factory.agent.action;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.agent.action.ExpandDescriptor;
import nanoverse.runtime.agent.action.TriggerDescriptor;
import nanoverse.runtime.agent.targets.TargetDescriptor;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;

public class TriggerFactory implements Factory<TriggerDescriptor> {

    private final TriggerFactoryHelper helper;

    private LayerManager arg0;
    private String arg1;
    private TargetDescriptor arg2;
    private IntegerArgument arg3;
    private IntegerArgument arg4;

    public TriggerFactory() {
        helper = new TriggerFactoryHelper();
    }

    public TriggerFactory(TriggerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArg0(LayerManager arg0) {
        this.arg0 = arg0;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public void setArg2(TargetDescriptor arg2) {
        this.arg2 = arg2;
    }

    public void setArg3(IntegerArgument arg3) {
        this.arg3 = arg3;
    }

    public void setArg4(IntegerArgument arg4) {
        this.arg4 = arg4;
    }

    @Override
    public TriggerDescriptor build() {
        return helper.build(arg0, arg1, arg2, arg3, arg4);
    }
}