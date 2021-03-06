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

package nanoverse.compiler.pipeline.instantiate.helpers;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.arguments.*;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by dbborens on 8/13/15.
 */
public class LoadHelper {

    private LoaderRetriever loaderRetriever;
    private BooleanLoadHelper booleanLoadHelper;
    private IntegerLoadHelper integerLoadHelper;
    private StringLoadHelper stringLoadHelper;
    private DoubleLoadHelper doubleLoadHelper;

    public LoadHelper() {
        this.loaderRetriever = new LoaderRetriever();
        this.booleanLoadHelper = new BooleanLoadHelper();
        this.integerLoadHelper = new IntegerLoadHelper();
        this.stringLoadHelper = new StringLoadHelper();
        this.doubleLoadHelper = new DoubleLoadHelper();
    }

    public LoadHelper(LoaderRetriever loaderRetriever,
                      BooleanLoadHelper booleanLoadHelper,
                      IntegerLoadHelper integerLoadHelper,
                      StringLoadHelper stringLoadHelper,
                      DoubleLoadHelper doubleLoadHelper) {

        this.loaderRetriever = loaderRetriever;
        this.booleanLoadHelper = booleanLoadHelper;
        this.integerLoadHelper = integerLoadHelper;
        this.stringLoadHelper = stringLoadHelper;
        this.doubleLoadHelper = doubleLoadHelper;
    }

    public Loader getLoader(MapObjectNode parent, String field, boolean require) {
        return loaderRetriever.getLoader(parent, field, require);
    }

    public Integer anInteger(MapObjectNode node, String id, Random random,
                             Supplier<Integer> defaultSupplier) {

        return integerLoadHelper.load(node, id, random, defaultSupplier);
    }

    public Integer anInteger(MapObjectNode node, String id, Random random) {
        return integerLoadHelper.load(node, id, random, null);
    }

    public IntegerArgument anIntegerArgument(MapObjectNode node, String id, Random random,
                                             Supplier<IntegerArgument> defaultSupplier) {

        return integerLoadHelper.loadArgument(node, id, random, defaultSupplier);
    }

    public IntegerArgument anIntegerArgument(MapObjectNode node, String id, Random random) {
        return integerLoadHelper.loadArgument(node, id, random, null);
    }

    public Double aDouble(MapObjectNode node, String id, Random random,
                          Supplier<Double> defaultSupplier) {

        return doubleLoadHelper.load(node, id, random, defaultSupplier);
    }

    public Double aDouble(MapObjectNode node, String id, Random random) {
        return doubleLoadHelper.load(node, id, random, null);
    }

    public DoubleArgument aDoubleArgument(MapObjectNode node, String id, Random random) {
        return doubleLoadHelper.loadArgument(node, id, random, null);
    }

    public DoubleArgument aDoubleArgument(MapObjectNode node, String id, Random random,
                                          Supplier<DoubleArgument> defaultSupplier) {

        return doubleLoadHelper.loadArgument(node, id, random, defaultSupplier);
    }

    public Boolean aBoolean(MapObjectNode node, String id, Random random,
                            Supplier<Boolean> defaultSupplier) {

        return booleanLoadHelper.load(node, id, random, defaultSupplier);
    }

    public Boolean aBoolean(MapObjectNode node, String id, Random random) {
        return booleanLoadHelper.load(node, id, random, null);
    }

    public String aString(MapObjectNode node, String id,
                          Supplier<String> defaultSupplier) {

        return stringLoadHelper.load(node, id, defaultSupplier);
    }

    public String aString(MapObjectNode node, String id) {
        return stringLoadHelper.load(node, id, null);
    }
}
