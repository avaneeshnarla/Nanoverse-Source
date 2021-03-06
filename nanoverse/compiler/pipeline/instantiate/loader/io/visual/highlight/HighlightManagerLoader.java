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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.highlight.*;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.ListObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.highlight.*;

import java.util.*;

/**
 * Created by dbborens on 8/13/15.
 */
public class HighlightManagerLoader extends Loader<HighlightManager> {

    private final HighlightManagerFactory factory;
    private final HighlightManagerChildLoader cLoader;

    public HighlightManagerLoader() {
        factory = new HighlightManagerFactory();
        cLoader = new HighlightManagerChildLoader();
    }

    public HighlightManagerLoader(HighlightManagerFactory factory,
                                  HighlightManagerChildLoader cLoader) {
        this.factory = factory;
        this.cLoader = cLoader;
    }

    public HighlightManager instantiate(GeneralParameters p) {
        return instantiate(null, p);
    }

    public HighlightManager instantiate(ListObjectNode cNode,
                                        GeneralParameters p) {

        Map<Integer, Glyph> glyphMap = resolveGlyphMap(cNode, p);
        factory.setGlyphMap(glyphMap);

        return factory.build();
    }

    private Map<Integer, Glyph> resolveGlyphMap(ListObjectNode cNode, GeneralParameters p) {
        Map<Integer, Glyph> glyphMap;

        if (cNode == null) {
            glyphMap = new HashMap<>(0);
        } else {
            cNode.getMemberStream()
                .forEach(node -> cLoader.load(node, p));
            glyphMap = cLoader.getGlyphMap();
        }
        return glyphMap;
    }
}
