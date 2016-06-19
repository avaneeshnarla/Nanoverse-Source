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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic.ContinuumHillProbabilitySupplierFactory;
import nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic.ContinuumProbabilitySupplierFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.action.stochastic.ContinuumHillProbabilitySupplierDescriptor;
import nanoverse.runtime.agent.action.stochastic.ContinuumProbabilitySupplierDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/25/2015.
 */
public class ContinuumHillProbabilitySupplierLoader
        extends ProbabilitySupplierLoader
        <ContinuumHillProbabilitySupplierDescriptor> {

    private final ContinuumHillProbabilitySupplierFactory factory;
    private final ContinuumHillProbabilitySupplierInterpolator interpolator;

    public ContinuumHillProbabilitySupplierLoader() {
        factory = new ContinuumHillProbabilitySupplierFactory();
        interpolator = new ContinuumHillProbabilitySupplierInterpolator();
    }

    public ContinuumHillProbabilitySupplierLoader(
            ContinuumHillProbabilitySupplierFactory factory,
            ContinuumHillProbabilitySupplierInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public ContinuumHillProbabilitySupplierDescriptor instantiate(MapObjectNode
                                                                           node,
                                                              LayerManager lm,
                                                              GeneralParameters p) {

        factory.setLayerManager(lm);

        String layerId = interpolator.layer(node);
        factory.setLayer(layerId);

        double offset = interpolator.offset(node, p.getRandom());
        factory.setOffset(offset);

        double coefficient = interpolator.coefficient(node, p.getRandom());
        factory.setCoefficient(coefficient);

        double halfpoint = interpolator.halfpoint(node, p.getRandom());
        factory.setHalfpoint(halfpoint);

        double maximum = interpolator.maximum(node, p.getRandom());
        factory.setMaximum(maximum);

        return factory.build();
    }
}
