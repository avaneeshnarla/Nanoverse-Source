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

package nanoverse.compiler.pipeline.instantiate.loader.control;

import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.NanoverseProcess;

import java.util.stream.Stream;

/**
 * Created by dbborens on 9/19/2015.
 */
public class ProcessManagerInterpolator {

    private final ProcessManagerDefaults defaults;

    public ProcessManagerInterpolator() {
        defaults = new ProcessManagerDefaults();
    }

    public ProcessManagerInterpolator(ProcessManagerDefaults defaults) {
        this.defaults = defaults;
    }

    public Stream<NanoverseProcess> processes(ListObjectNode node, LayerManager lm, GeneralParameters p) {
        if (node == null) {
            return defaults.processes();
        }

        return node.getMemberStream()
            .map(cNode -> (MapObjectNode) cNode)
            .map(cNode -> toProcess(cNode, lm, p));
    }

    private NanoverseProcess toProcess(MapObjectNode cNode, LayerManager lm, GeneralParameters p) {
        InstantiableSymbolTable ist = cNode.getSymbolTable();
        ProcessLoader loader = (ProcessLoader) ist.getLoader();
        return loader.instantiate(cNode, lm, p);
    }
}
