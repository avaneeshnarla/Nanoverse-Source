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
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by avaneesh on 19/06/2016.
 */
public class ContinuumHillLinProbabilitySupplierDescriptor extends
        ProbabilitySupplierDescriptor<ContinuumHillLinProbabilitySupplier> {

    @FactoryTarget(displayName = "ContinuumHillLinProbabilitySupplier")
    public ContinuumHillLinProbabilitySupplierDescriptor(String layer,
                                                      String substrate,
                                                      double coefficient,
                                                      double offset,
                                                      double halfpoint,
                                                      double maximum,
                                                      LayerManager layerManager) {
        //System.out.println(layer+substrate);
        Function<Agent, Double> valueLookup = c -> getFieldValueAt(c, layerManager, layer);
        Function<Agent, Double> substrateLookup = c -> getFieldValueAt(c,
                layerManager, substrate);
        Function<Agent, ContinuumHillLinProbabilitySupplier> constructor = cell ->
                new ContinuumHillLinProbabilitySupplier(valueLookup,
                        substrateLookup,
                        cell, coefficient, offset, halfpoint, maximum);
        super.setConstructor(constructor);
    }

    private double getFieldValueAt(Agent cell, LayerManager layerManager, String fieldName) {
        Supplier<Coordinate> supplier = () -> layerManager
                .getAgentLayer()
                .getLookupManager()
                .getAgentLocation(cell);
        /*System.out.println("\n"+fieldName+" has value "+
                layerManager.getContinuumLayer(fieldName).getLinker().get
                (supplier));*/
        return layerManager.getContinuumLayer(fieldName).getLinker().get(supplier);
    }
}
