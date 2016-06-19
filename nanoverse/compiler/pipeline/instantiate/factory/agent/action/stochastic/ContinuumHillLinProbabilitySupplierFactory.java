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
package nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.agent.action.stochastic.ContinuumHillLinProbabilitySupplierDescriptor;

import nanoverse.runtime.layers.LayerManager;

public class ContinuumHillLinProbabilitySupplierFactory implements
        Factory<ContinuumHillLinProbabilitySupplierDescriptor> {

    private final ContinuumHillLinProbabilitySupplierFactoryHelper helper;

    private String layer;
    private String substrate;
    private double coefficient;
    private double offset;
    private double maximum;
    private double halfpoint;
    private LayerManager layerManager;

    public ContinuumHillLinProbabilitySupplierFactory() {
        helper = new ContinuumHillLinProbabilitySupplierFactoryHelper();
    }

    public ContinuumHillLinProbabilitySupplierFactory
            (ContinuumHillLinProbabilitySupplierFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public void setSubstrate(String substrate) {
        this.substrate = substrate;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public void setHalfpoint(double halfpoint) {
        this.halfpoint = halfpoint;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    @Override
    public ContinuumHillLinProbabilitySupplierDescriptor build() {
        return helper.build(layer, substrate, coefficient, offset, halfpoint,
                maximum,
                layerManager);
    }
}