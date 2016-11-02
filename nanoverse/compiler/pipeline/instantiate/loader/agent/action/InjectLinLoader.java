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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.compiler.pipeline.instantiate.factory.agent.action.InjectLinFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.action.InjectLinDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/3/2015.
 */
public class InjectLinLoader extends ActionLoader<InjectLinDescriptor> {

    private final InjectLinFactory factory;
    private final InjectLinInterpolator interpolator;

    public InjectLinLoader() {
        factory = new InjectLinFactory();
        interpolator = new InjectLinInterpolator();
    }

    public InjectLinLoader(InjectLinFactory factory,
                           InjectLinInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public InjectLinDescriptor instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayerManager(lm);

        String layerId = interpolator.layer(node);
        factory.setLayerId(layerId);

        String substrateId = interpolator.substrate(node);
        factory.setSubstrateId(substrateId);

        DoubleArgument delta = interpolator.delta(node, p.getRandom());
        factory.setDeltaArg(delta);

        return factory.build();
    }
}
