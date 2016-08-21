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

package nanoverse.compiler.pipeline.instantiate.loader.io.serialize.binary;

import nanoverse.compiler.pipeline.instantiate.factory.io.serialize.binary.VisualizationTailSerializerFactory;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.OutputLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.serialize.binary.VisualizationTailSerializer;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/10/2015.
 */
public class VisualizationTailSerializerLoader extends OutputLoader<VisualizationTailSerializer> {
    private final VisualizationTailSerializerFactory factory;
    private final VisualizationTailSerializerInterpolator interpolator;

    public VisualizationTailSerializerLoader() {
        factory = new VisualizationTailSerializerFactory();
        interpolator = new VisualizationTailSerializerInterpolator();
    }

    public VisualizationTailSerializerLoader(VisualizationTailSerializerFactory factory,
                                             VisualizationTailSerializerInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Serializer instantiate(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        factory.setP(p);
        factory.setLm(layerManager);

        String prefix = interpolator.prefix(node);
        factory.setPrefix(prefix);

        Visualization visualization = interpolator.visualization(node, layerManager, p);
        factory.setVisualization(visualization);

        return factory.build();
    }
}
