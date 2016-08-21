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

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.SystemStateReader;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.SystemState;
import nanoverse.runtime.processes.StepState;

import java.awt.*;
import java.io.IOException;
import java.util.function.Function;

/**
 * Created by dbborens on 10/28/2015.
 */
public class VisualizationFrameRenderer {
    private final Visualization visualization;
    private final Geometry geometry;
    private final VisualizationFileGenerator generator;
    private final Function<int[], SystemStateReader> readerMaker;

    public VisualizationFrameRenderer(Visualization visualization,
                                      Geometry geometry,
                                      GeneralParameters p,
                                      String prefix) {
        generator = new VisualizationFileGenerator(prefix, p);
        this.readerMaker = channels -> new SystemStateReader(channels, p, geometry);
        this.geometry = geometry;
        this.visualization = visualization;
    }

    public VisualizationFrameRenderer(Visualization visualization,
                                      Geometry geometry,
                                      Function<int[], SystemStateReader> readerMaker,
                                      VisualizationFileGenerator generator) {

        this.visualization = visualization;
        this.geometry = geometry;
        this.generator = generator;
        this.readerMaker = readerMaker;
    }

//    public void renderAll(int[] highlightChannels) {
//        //System.out.println(Arrays.toString(highlightChannels));
//        SystemStateReader reader = readerMaker.apply(highlightChannels);
//        //System.out.println(Arrays.toString(reader.getFrames()));
//        // Initialize the visualization to this simulation.
//        visualization.init(geometry, reader.getTimes(), reader.getFrames());
//
//        // Scan through the frames...
//        int count=0;
//        System.out.println("Start count");
//        try {
//
//            for (SystemState systemState : reader) {
//                count++;
//                System.out.println("Counting...");
//                try {
//                    render(systemState);
//                } catch (Exception e) {
//                    System.out.println(systemState.getFrame());
//                }
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("Something wrong with looping");
//        }
//        System.out.println("We have "+count);
//
//        visualization.conclude();
//    }

    public void renderAll(int[] highlightChannels) throws IOException {
        //System.out.println(Arrays.toString(highlightChannels));
        SystemStateReader reader = readerMaker.apply(highlightChannels);
        //System.out.println(Arrays.toString(reader.getFrames()));
        // Initialize the visualization to this simulation.
        visualization.init(geometry, reader.getTimes(), reader.getFrames());

        // Scan through the frames...
        int count=0;
        //System.out.println("Start count");
        SystemState lastState = null;
        try {

            for (SystemState systemState : reader) {
                count++;
                //System.out.println("Counting...");
                try {
                    lastState=systemState;
                } catch (Exception e) {
                    System.out.println(systemState.getFrame());
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Something wrong with looping");
        }
        //System.out.println("We have "+count);
        render(lastState);
        visualization.conclude();
    }

    public void renderAll(int[] highlightChannels, StepState stepState) {
        //System.out.println(Arrays.toString(highlightChannels));
        SystemStateReader reader = readerMaker.apply(highlightChannels);
        //System.out.println(Arrays.toString(reader.getFrames()));
        // Initialize the visualization to this simulation.
        visualization.init(geometry, reader.getTimes(), reader.getFrames());

        // Scan through the frames...
        //for (SystemState systemState : reader) {
            //System.out.println("Reached");
//        final Iterator<LightweightSystemState> itr = reader.iterator();
//        SystemState lastState = itr.next();
//        SystemState penultState = itr.next();
//        while(itr.hasNext()) {
//            penultState=lastState;
//            lastState=itr.next();
//        }
//            render(penultState);
        //}
        SystemState lastState = null;
        //SystemState penultState = null;
        //SystemState antePenultState = null;
        int count=0;
        try {

            for (SystemState systemState : reader) {
                //System.out.println("Counting...");
                lastState=systemState;
                count++;
            }
        }
        catch(Exception e)
        {
            //System.out.println("Something wrong with looping");
        }
        try {
            render(lastState);
        } catch (RuntimeException ioe) {
        }
        //System.out.println(count);
        visualization.conclude();
    }

    public void renderAll(int[] highlightChannels, int dummy) {
        SystemStateReader reader = readerMaker.apply(highlightChannels);

        // Initialize the visualization to this simulation.
        visualization.init(geometry, reader.getTimes(), reader.getFrames());

        // Scan through the frames...
        for (SystemState systemState : reader) {
            render(systemState);
        }

        visualization.conclude();
    }

    private void render(SystemState systemState) throws RuntimeException {
        // Render the frame.
        Image image = visualization.render(systemState);

        // Image can be null if the visualization only outputs at
        // certain frames (eg kymograph, only returns image at end)
        if (image != null) {
            // Export the frame to the disk.
            double time = systemState.getTime();
            generator.generateFile(time, image);
        }
    }


}
