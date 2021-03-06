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

package nanoverse.runtime.processes.discrete.check;

import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.halt.ThresholdOccupancyReachedEvent;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.processes.discrete.AgentProcess;
import nanoverse.runtime.processes.discrete.AgentProcessArguments;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Throws a halt event when the system's total occupancy exceeds a specified
 * threshold.
 */
public class CheckForThresholdOccupancy extends AgentProcess {
    private int thresholdCount;
    private DoubleArgument thresholdOccupancy;

    @FactoryTarget
    public CheckForThresholdOccupancy(BaseProcessArguments arguments, AgentProcessArguments cpArguments, DoubleArgument thresholdOccupancy) {
        super(arguments, cpArguments);
        this.thresholdOccupancy = thresholdOccupancy;
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
        // There's only one event that can happen in this process.
        if (gs != null) {
            gs.add(this.getID(), 1, 0.0D);
        }

    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        int numOccupied = (int) getLayer().getViewer().getOccupiedSites().count();
        /*if (state.getFrame()%20==0)
            System.out.println(numOccupied);*/
        if (numOccupied >= thresholdCount) {
            throw new ThresholdOccupancyReachedEvent();
        }
    }

    @Override
    public void init() {
        double toVal;

        try {
            toVal = thresholdOccupancy.next();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }

        if (toVal > 1.0 || toVal < 0) {
            throw new IllegalArgumentException("Illegal occupancy fraction " + toVal);
        }
        thresholdCount = (int) Math.floor(getLayer().getGeometry().getCanonicalSites().length * toVal);
    }
}
