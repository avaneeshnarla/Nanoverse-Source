/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic;

import java.util.stream.Stream;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.agent.action.stochastic.NormalizedDynamicActionRangeMapDescriptor;
import nanoverse.runtime.layers.LayerManager;

public class NormalizedDynamicActionRangeMapFactory implements Factory<NormalizedDynamicActionRangeMapDescriptor> {

    private final NormalizedDynamicActionRangeMapFactoryHelper helper;

    private Stream options;
    private LayerManager layerManager;

    public NormalizedDynamicActionRangeMapFactory() {
        helper = new NormalizedDynamicActionRangeMapFactoryHelper();
    }

    public NormalizedDynamicActionRangeMapFactory(NormalizedDynamicActionRangeMapFactoryHelper helper) {
        this.helper = helper;
    }

    public void setOptions(Stream options) {
        this.options = options;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    @Override
    public NormalizedDynamicActionRangeMapDescriptor build() {
        return helper.build(options, layerManager);
    }
}