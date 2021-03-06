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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.HighlightLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.io.visual.highlight.Highlight;

import java.util.HashMap;

/**
 * Created by dbborens on 7/28/2015.
 */
public class HighlightInstSymbolTable extends MapSymbolTable<Highlight> {
    @Override
    public String getDescription() {
        return "A highlight describes how selected locations (specified " +
            "using a highlight channel) should be accentuated in a " +
            "visualization.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        glyph(ret);
        channel(ret);
        return ret;
    }

    private void channel(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The channel to be highlighted.");
        ret.put("channel", ms);
    }

    private void glyph(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new GlyphClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The glyph with which to highlight the channel.");
        ret.put("glyph", ms);
    }

    @Override
    public Loader getLoader() {
        return new HighlightLoader();
    }
}
