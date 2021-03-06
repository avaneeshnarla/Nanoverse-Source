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

package nanoverse.runtime.io.visual.kymograph;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.highlight.HighlightManager;
import nanoverse.runtime.io.visual.map.CoordinateRenderer;
import nanoverse.runtime.io.visual.map.PixelTranslator;
import nanoverse.runtime.layers.SystemState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dbborens on 4/1/14.
 */
public class Kymograph extends Visualization {
    // All state members associated with this visualization.
    protected PixelTranslator translator;
    protected BufferedImage img;
    protected Graphics2D g;

    @FactoryTarget
    public Kymograph(VisualizationProperties properties) {
        this.properties = properties;
    }

    @Override
    public void init(Geometry geometry, double[] times, int[] frames) {
        Coordinate[] coordinates = geometry.getCanonicalSites();
        properties.setCoordinates(coordinates);
        properties.setFrames(frames);
        properties.setTimes(times);
        translator = new KymoPixelTranslator();
        translator.init(properties);

        Coordinate pDims = translator.getImageDims();
        HighlightManager highlightManager = properties.getHighlightManager();
        highlightManager.init(translator);
        img = new BufferedImage(pDims.x(), pDims.y(), BufferedImage.TYPE_INT_RGB);

        g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        properties.getHighlightManager().setGraphics(g);
    }

    @Override
    public BufferedImage render(SystemState systemState) {
        // Build the image one frame (=column) at a time, but don't return it
        // until all columns are loaded.

        System.out.println("Rendering frame " + systemState.getFrame());
        CoordinateRenderer renderer = new CoordinateRenderer(g, translator, properties);
        for (Coordinate c : properties.getCoordinates()) {
            renderer.render(c, systemState);
        }

        if (systemState.getFrame() == properties.getFrames()[properties.getFrames().length - 1]) {
            return img;
        } else {
            return null;
        }
    }

    @Override
    public void conclude() {
    }

    @Override
    public String[] getSoluteIds() {
        // At the moment, no solute fields are supported.
        return new String[]{};
    }

    @Override
    public int[] getHighlightChannels() {
        return properties.getHighlightManager().getHighlightChannels();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Kymograph)) {
            return false;
        }

        Kymograph other = (Kymograph) obj;

        if (!properties.equals(other.properties)) {
            return false;
        }

        return translator.equals(other.translator);

    }

}
