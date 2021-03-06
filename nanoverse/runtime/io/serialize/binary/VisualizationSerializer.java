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

package nanoverse.runtime.io.serialize.binary;

import com.keypoint.PngEncoder;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.SystemStateReader;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.*;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;
import java.io.*;

/**
 * Created by dbborens on 4/2/14.
 */
public class VisualizationSerializer extends Serializer {

    // The visualization to render
    private final Visualization visualization;

    // Leading part of the file name (after the instance path)
    private final VisualizationFrameRenderer renderer;

    @FactoryTarget
    public VisualizationSerializer(GeneralParameters p,
                                   Visualization visualization,
                                   String prefix, LayerManager lm) {
        super(p, lm);
        Geometry geometry = lm.getAgentLayer().getGeometry();
        this.visualization = visualization;
        //System.out.println("Constructor 1 reached");
        renderer = new VisualizationFrameRenderer(visualization, geometry, p, prefix);
    }

    public VisualizationSerializer(GeneralParameters p,
                                   LayerManager lm,
                                   Visualization visualization,
                                   VisualizationFrameRenderer renderer) {

        super(p, lm);
        this.renderer = renderer;
        this.visualization = visualization;
        //System.out.println("Constructor 2 reached");
    }

    @Override
    public void init() {

    }

    public void commit(StepState state) {

    }


    @Override
    public void dispatchHalt(HaltCondition ex) {
        // Doesn't do anything
        int[] highlightChannels = visualization.getHighlightChannels();

        // Create a SystemStateReader.
        try {
            renderer.renderAll(highlightChannels);
        }
        catch (Exception e)
        {
            System.out.println("Oops! System hasn't started recording yet!");
        }

    }

    @Override
    public void close() {
        // Doesn't do anything
    }

    @Override
    public void flush(StepState stepState) {
        // Doesn't do anything
        int[] highlightChannels = visualization.getHighlightChannels();

        // Create a SystemStateReader.
        try {
            renderer.renderAll(highlightChannels, stepState);
        }
        catch (Exception e)
        {
            System.out.println("Oops! System hasn't started recording yet!");
        }
    }
}
