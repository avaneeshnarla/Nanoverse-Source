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

package nanoverse.compiler.pipeline.instantiate.loader.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.layers.continuum.ReactionLinFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.layers.continuum.ReactionLin;

import java.util.Random;

/**
 * Created by dbborens on 8/13/15.
 */
public class ReactionLinLoader extends Loader<ReactionLin> {

    private final ReactionLinFactory factory;
    private final ReactionLinInterpolator interpolator;

    public ReactionLinLoader() {
        factory = new ReactionLinFactory();
        interpolator = new ReactionLinInterpolator();
    }

    public ReactionLinLoader(ReactionLinFactory factory,
                             ReactionLinInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public ReactionLin instantiate(MapObjectNode node, Random random) {
        double exp = interpolator.exp(node, random);
        factory.setExp(exp);

        double inj = interpolator.inj(node, random);
        factory.setInj(inj);

        String layer = interpolator.layer(node);
        factory.setId(layer);

        return factory.build();
    }
}
