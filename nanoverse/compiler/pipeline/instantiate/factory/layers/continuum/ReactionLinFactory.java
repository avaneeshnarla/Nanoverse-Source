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
package nanoverse.compiler.pipeline.instantiate.factory.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.layers.continuum.ReactionLin;

public class ReactionLinFactory implements Factory<ReactionLin> {

    private final ReactionLinFactoryHelper helper;

    private double inj;
    private double exp;
    private String id;

    public ReactionLinFactory() {
        helper = new ReactionLinFactoryHelper();
    }

    public ReactionLinFactory(ReactionLinFactoryHelper helper) {
        this.helper = helper;
    }

    public void setInj(double inj) {
        this.inj = inj;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ReactionLin build() {
        return helper.build(inj, exp, id);
    }
}