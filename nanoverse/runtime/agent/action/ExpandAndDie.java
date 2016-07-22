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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.displacement.DisplacementManager;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentUpdateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by avaneesh on 7/22/2016.
 */
public class ExpandAndDie extends Action {

    private final IntegerArgument channel;

    private final DisplacementManager displacementManager;
    private final SelfTargetHighlighter stHighlighter;
    private final Logger logger;

    private final Random random;

    /**
     * Main constructor
     */
    public ExpandAndDie(Agent callback, LayerManager layerManager,
                        IntegerArgument selfChannel, IntegerArgument targetChannel,
                        Random random) {
        super(callback, layerManager);
        stHighlighter = new SelfTargetHighlighter(highlighter, selfChannel, targetChannel);
        this.random = random;

        displacementManager = new DisplacementManager(layerManager.getAgentLayer(), random);
        logger = LoggerFactory.getLogger(Expand.class);
        this.channel = selfChannel;
    }

    /**
     * Testing constructor
     */
    public ExpandAndDie(ActionIdentityManager identityManager,
               CoordAgentMapper mapper,
               ActionHighlighter highlighter,
               SelfTargetHighlighter stHighlighter, Random random,
               DisplacementManager displacementManager,
               IntegerArgument channel) {

        super(identityManager, mapper, highlighter);
        this.channel = channel;
        this.displacementManager = displacementManager;
        this.stHighlighter = stHighlighter;
        this.random = random;
        logger = LoggerFactory.getLogger(Expand.class);
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Coordinate parentLocation = identity.getOwnLocation();

        AgentUpdateManager u = mapper.getLayerManager().getAgentLayer().getUpdateManager();

        // Step 1: identify nearest vacant site.
        Coordinate target = displacementManager.chooseVacancy(parentLocation);

        logger.debug("Origin {}. Nearest vacancy is {}.", parentLocation, target);

        // Step 2: shove parent toward nearest vacant site.
        displacementManager.shove(parentLocation, target);

        // Step 3: Clone parent.
        Agent child = identity.getSelf().copy();

        logger.debug("Attempting to place child in parent location {}.", parentLocation);
        // Step 4: Place child in parent location.
        u.place(child, parentLocation);

        // Step 5: Clean up out-of-bounds agents.
        displacementManager.removeImaginary();

        // Step 6: Highlight the parent and target locations.
        stHighlighter.highlight(target, parentLocation);
        Coordinate location = identity.getOwnLocation();
        highlighter.doHighlight(channel, location);

        Agent self = identity.getSelf();
        self.die();
    }

    @Override
    public Action copy(Agent child) {
        IntegerArgument selfChannel = stHighlighter.getSelfChannel();
        IntegerArgument targetChannel = stHighlighter.getTargetChannel();
        LayerManager lm = mapper.getLayerManager();
        return new ExpandAndDie(child, lm, selfChannel, targetChannel,
                random);
    }
}
