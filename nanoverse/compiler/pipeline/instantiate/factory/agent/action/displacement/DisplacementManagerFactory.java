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
package nanoverse.compiler.pipeline.instantiate.factory.agent.action.displacement;

import java.util.Random;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.agent.action.displacement.DisplacementManager;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class DisplacementManagerFactory implements Factory<DisplacementManager> {

    private final DisplacementManagerFactoryHelper helper;

    private AgentLayer arg0;
    private Random arg1;

    public DisplacementManagerFactory() {
        helper = new DisplacementManagerFactoryHelper();
    }

    public DisplacementManagerFactory(DisplacementManagerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArg0(AgentLayer arg0) {
        this.arg0 = arg0;
    }

    public void setArg1(Random arg1) {
        this.arg1 = arg1;
    }

    @Override
    public DisplacementManager build() {
        return helper.build(arg0, arg1);
    }
}