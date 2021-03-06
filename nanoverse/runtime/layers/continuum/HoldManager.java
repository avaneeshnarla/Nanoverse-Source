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

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.continuum.solvers.ContinuumSolver;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;

/**
 * Created by dbborens on 1/9/15.
 */
public class HoldManager {
    private ContinuumAgentManager manager;
    private ContinuumSolver solver;
    private boolean held;

    @FactoryTarget
    public HoldManager(ContinuumAgentManager manager, ContinuumSolver solver) {
        this.manager = manager;
        this.solver = solver;
        this.held = false;
    }

    public void hold() {
        if (held) {
            throw new IllegalStateException("Attempted to initiate hold for already held continuum layer.");
        }

        held = true;
    }

    public void release() {
        if (!held) {
            throw new IllegalStateException("Attempted to release hold on a continuum layer that was not already held.");
        }

        held = false;

        solve();
    }

    public void solve() {
        if (held) {
            throw new IllegalStateException("Attempting to solve while hold is in place.");
        }

        solver.solve();
    }

    public void resolve(Runnable runnable) {
        runnable.run();
        solveIfNotHeld();
    }

    private void solveIfNotHeld() {
        if (held)
            return;

        solve();
    }

    public void reset() {
        manager.reset();
        held = false;
    }

    public ContinuumAgentLinker getLinker(Function<Coordinate, Double> stateLookup) {
        return manager.getLinker(stateLookup);
    }

    public String getId() {
        return manager.getId();
    }

    public boolean isHeld() {
        return held;
    }

    public void scheduleApplyRelationships() {
        manager.apply();
    }
}
