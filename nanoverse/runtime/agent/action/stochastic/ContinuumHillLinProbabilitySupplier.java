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

package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
/**
 * This is an attempt to have a stochastic probability that is not just
 * linear. We start with looking at the hill function.
 * <p>
 * Created by avaneesh on 18/06/16.
 */
public class ContinuumHillLinProbabilitySupplier extends ProbabilitySupplier {

    private final double coefficient;
    private final double offset;
    private final double halfpoint;
    private final double maximum;
    private final Agent cell;
    private final Function<Agent, Double> valueLookup;
    private final Function<Agent, Double> substrateLookup;
    private final Logger logger = LoggerFactory.getLogger
            (ContinuumHillLinProbabilitySupplier.class);

    public ContinuumHillLinProbabilitySupplier(Function<Agent, Double> valueLookup,
                                            Function<Agent, Double>
                                                    substrateLookup,
                                            Agent cell, double coefficient,
                                            double offset, double halfpoint,
                                            double maximum) {
        //System.out.print("Reached2\n");
        this.coefficient = coefficient;
        this.offset = offset;
        this.cell = cell;
        this.valueLookup = valueLookup;
        this.substrateLookup = substrateLookup;
        this.halfpoint = halfpoint;
        this.maximum = maximum;
    }

    @Override
    public ContinuumHillLinProbabilitySupplier clone(Agent child) {
        return new ContinuumHillLinProbabilitySupplier(
                valueLookup, substrateLookup, child,
                coefficient, offset, halfpoint, maximum);
    }

    @Override
    public Double get() {
        double value = valueLookup.apply(cell);
        double substrate = substrateLookup.apply(cell);
        double probability;
        probability = (offset + Math.pow(value, coefficient)/
                (Math.pow(value, coefficient)+Math.pow(halfpoint,
                        coefficient))*(maximum-offset))*substrate;
        /*System.out.println("Offset = "+offset+"\nCoefficient = "+coefficient+
                            "\nHalfpoint = " +halfpoint+
                            "\nMaximum = "+maximum+
                "\nLayerValue = " + value +
                        "\nProbability = " + probability +
                        "\nSubstrateValue = " + substrate);*/
        //System.out.println(value+"\t"+substrate);
        logger.debug("p(x) = {}", probability);
        return probability;
    }
}
