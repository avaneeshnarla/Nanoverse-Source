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

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.io.visual.highlight.Glyph;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/28/2015.
 */
public class GlyphClassSymbolTable extends ClassSymbolTable<Glyph> {

    @Override
    public String getDescription() {
        return "Glyphs represent visual overlays that are drawn over " +
            "particular locations on a spatial visualization to " +
            "highlight locations of interest. Locations are selected " +
            "using highlight channels.";
    }

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        dot(ret);
        bullseye(ret);
        crosshairs(ret);
        return ret;
    }

    public void crosshairs(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = CrosshairsGlyphInstSymbolTable::new;
        ret.put("Crosshairs", supplier);
    }

    public void bullseye(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = BullseyeGlyphInstSymbolTable::new;
        ret.put("Bullseye", supplier);
    }

    public void dot(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = DotGlyphInstSymbolTable::new;
        ret.put("Dot", supplier);
    }
}
