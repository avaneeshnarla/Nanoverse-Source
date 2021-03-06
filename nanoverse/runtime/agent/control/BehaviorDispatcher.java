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

package nanoverse.runtime.agent.control;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.Action;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * BehaviorDispatcher is a map between behavior names and the
 * behaviors themselves. It is associated with a particular cell,
 * and can be used to trigger behaviors in that cell.
 * <p>
 * Created by David B Borenstein on 1/21/14.
 */
public class BehaviorDispatcher {
    private final HashMap<String, Action> behaviors;
    private final Logger logger = LoggerFactory.getLogger(BehaviorDispatcher.class);
    public BehaviorDispatcher() {
        behaviors = new HashMap<>();
    }

    public BehaviorDispatcher(HashMap<String, Action> behaviors) {
        this.behaviors = behaviors;
    }

    /**
     * Trigger a behavior associated with the cell.
     *
     * @param behaviorName
     * @param caller       The coordinate from which the call originated. If
     *                     the call originated with a top-down process, the
     *                     caller will be null.
     */
    public void trigger(String behaviorName, Coordinate caller, String name) throws HaltCondition {
        if (!behaviors.containsKey(behaviorName)) {
            throw new IllegalStateException("Action \"" + behaviorName + "\" not found on agent named \"" + name + ".\"");
        }

        //System.out.println(behaviorName+" triggered");// at "+caller
        //.stringForm());
        Action behavior = behaviors.get(behaviorName);
        /*try{System.out.println(caller.toString());}
        catch (NullPointerException n) {System.out.println();}*/
        behavior.run(caller);
        //System.out.println();
    }

    public BehaviorDispatcher copy(Agent child) {
        BehaviorDispatcher clone = new BehaviorDispatcher();

        // Clone the behavior catalog item for item.
        for (String behaviorName : behaviors.keySet()) {
            Action b = behaviors.get(behaviorName);
            Action bc = b.copy(child);
            clone.map(behaviorName, bc);
        }

        return clone;
    }

    public void map(String name, Action behavior) {
        behaviors.put(name, behavior);
    }

    public Action getMappedBehavior(String behaviorName) {
        return behaviors.get(behaviorName);
    }

    public Stream<String> getBehaviorNames() {
        return behaviors.keySet().stream();
    }

}
