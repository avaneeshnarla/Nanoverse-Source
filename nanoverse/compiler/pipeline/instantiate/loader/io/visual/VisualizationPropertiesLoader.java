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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual;

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.VisualizationPropertiesFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.color.ColorManager;
import nanoverse.runtime.io.visual.highlight.HighlightManager;
import nanoverse.runtime.layers.LayerManager;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/21/2015.
 */
public class VisualizationPropertiesLoader
    extends Loader<VisualizationProperties> {

    private final VisualizationPropertiesFactory factory;
    private final VisualizationPropertiesInterpolator interpolator;

    public VisualizationPropertiesLoader() {
        interpolator = new VisualizationPropertiesInterpolator();
        factory = new VisualizationPropertiesFactory();
    }

    public VisualizationPropertiesLoader(VisualizationPropertiesFactory factory,
                                         VisualizationPropertiesInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public VisualizationProperties instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        configureFactory(node, lm, p);
        return factory.build();
    }

    private void configureFactory(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        Integer edge = interpolator.edge(node, p.getRandom());
        factory.setEdge(edge);

        Integer outline = interpolator.outline(node, p.getRandom());
        factory.setOutline(outline);

        ColorManager colorModel = interpolator.colorModel(node, lm, p);
        factory.setColorManager(colorModel);

        HighlightManager highlight = interpolator.highlights(node, p);
        factory.setHighlightManager(highlight);
    }

    public VisualizationProperties instantiate(GeneralParameters p,
                                               LayerManager lm,
                                               Stream<Consumer<VisualizationPropertiesFactory>> overrides) {
        configureFactory(null, lm, p);
        overrides.forEach(override -> override.accept(factory));
        return factory.build();
    }
}
