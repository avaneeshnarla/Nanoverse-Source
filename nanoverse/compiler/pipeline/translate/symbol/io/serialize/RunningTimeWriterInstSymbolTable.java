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

package nanoverse.compiler.pipeline.translate.symbol.io.serialize;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.text.RunningTimeWriterLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.io.serialize.text.RunningTimeWriter;

/**
 * Created by dbborens on 7/26/2015.
 */
public class RunningTimeWriterInstSymbolTable extends MapSymbolTable<RunningTimeWriter> {
    @Override
    public String getDescription() {
        return "Produces a tab-separated text file reporting the simulation " +
            "and wall-clock running time since the last time the file " +
            "was updated. Used to track performance.";
    }

    @Override
    public Loader getLoader() {
        return new RunningTimeWriterLoader();
    }
}
