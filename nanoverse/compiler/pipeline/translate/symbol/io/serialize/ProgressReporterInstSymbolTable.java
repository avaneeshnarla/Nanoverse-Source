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
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.interactive.ProgressReporterLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.io.serialize.interactive.ProgressReporter;

/**
 * Created by dbborens on 7/26/2015.
 */
public class ProgressReporterInstSymbolTable extends MapSymbolTable<ProgressReporter> {
    @Override
    public String getDescription() {
        return "LEGACY: ProgressReporter provides verbose output concerning " +
            "Nanoverse's state and the progress of the simulation. This " +
            "information should be gradually replaced by slf4j logging " +
            "with several levels of verboseness.";
    }

    @Override
    public Loader getLoader() {
        return new ProgressReporterLoader();
    }
}
