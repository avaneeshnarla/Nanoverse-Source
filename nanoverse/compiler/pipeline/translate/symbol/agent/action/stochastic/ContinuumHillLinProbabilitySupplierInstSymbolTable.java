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

package nanoverse.compiler.pipeline.translate.symbol.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.ContinuumHillLinProbabilitySupplierLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.agent.action.stochastic.ContinuumHillLinProbabilitySupplierDescriptor;

import java.util.HashMap;

/**
 * Created by avaneesh on 18/06/2016.
 */
public class ContinuumHillLinProbabilitySupplierInstSymbolTable
        extends MapSymbolTable<ContinuumHillLinProbabilitySupplierDescriptor> {

    @Override
    public String getDescription() {
        return "The weight of the action being chosen depends on the " +
                "local value of two continuum layers. Given a local " +
                "concentration x of layer and y of the substrate," +
                "the probability will be reported as weight = [offset + " +
                "x^coefficient/(halfpoint^coefficient+x^coefficient)*" +
                "(maximum-offset).]*y";
    }

    @Override
    public Loader getLoader() {
        return new ContinuumHillLinProbabilitySupplierLoader();
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        offset(ret);
        coefficient(ret);
        layer(ret);
        maximum(ret);
        halfpoint(ret);
        substrate(ret);
        return ret;
    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The layer whose value should" +
                " be used for the Hill function.");
        ret.put("layer", ms);
    }

    private void substrate(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The layer whose value should" +
                " be used for the linear function.");
        ret.put("substrate", ms);
    }

    private void coefficient(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The factor by which to " +
                "multiply the local field value. See class description.");
        ret.put("coefficient", ms);
    }

    private void offset(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The weight given that the " +
                "local value x=0. See class description.");
        ret.put("offset", ms);
    }

    private void halfpoint(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The weight given that the " +
                "local value x=(offset+maximum)/2. See class description.");
        ret.put("halfpoint", ms);
    }

    private void maximum(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The weight given that the " +
                "local value x=infinity. See class description.");
        ret.put("maximum", ms);
    }
}
