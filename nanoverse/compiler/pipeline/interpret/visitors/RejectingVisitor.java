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

package nanoverse.compiler.pipeline.interpret.visitors;

import nanoverse.compiler.pipeline.interpret.nanosyntax.*;
import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.BoolPrimitiveContext;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.runtime.structural.NotYetImplementedException;
import org.antlr.v4.runtime.misc.NotNull;

/**
 * Rejects any visit that is not explicitly overridden. This prevents my custom
 * visitor from quietly using default behavior.
 * <p>
 * Created by dbborens on 4/22/15.
 */
public class RejectingVisitor extends NanosyntaxBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitRoot(@NotNull NanosyntaxParser.RootContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitStatement(@NotNull NanosyntaxParser.StatementContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitAssignment(@NotNull NanosyntaxParser.AssignmentContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitBlock(@NotNull NanosyntaxParser.BlockContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitSingleton(@NotNull NanosyntaxParser.SingletonContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitId(@NotNull NanosyntaxParser.IdContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitPrimitive(@NotNull NanosyntaxParser.PrimitiveContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitStringPrimitive(@NotNull NanosyntaxParser.StringPrimitiveContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitFloatPrimitive(@NotNull NanosyntaxParser.FloatPrimitiveContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitIntPrimitive(@NotNull NanosyntaxParser.IntPrimitiveContext ctx) {
        throw new NotYetImplementedException();
    }

    @Override
    public ASTNode visitBoolPrimitive(@NotNull BoolPrimitiveContext ctx) {
        throw new NotYetImplementedException();
    }
}
