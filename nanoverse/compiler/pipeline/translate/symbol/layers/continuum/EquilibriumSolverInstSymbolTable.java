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

package nanoverse.compiler.pipeline.translate.symbol.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.layers.continuum.solvers.EquilibriumSolverLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.layers.continuum.solvers.EquilibriumSolver;

/**
 * Created by dbborens on 7/28/2015.
 */
public class EquilibriumSolverInstSymbolTable extends MapSymbolTable<EquilibriumSolver> {
    @Override
    public String getDescription() {
        return "An equilibrium solver uses a matrix solver to find the state " +
            "of the system at equilibrium (i.e., after an infinite " +
            "amount of time).";
    }

    @Override
    public Loader getLoader() {
        return new EquilibriumSolverLoader();
    }
}
