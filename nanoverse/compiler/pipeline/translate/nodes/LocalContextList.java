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

package nanoverse.compiler.pipeline.translate.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dbborens on 2/22/15.
 */
public class LocalContextList {

    private List<ObjectNode> members;

    public LocalContextList() {
        members = new ArrayList<>();
    }

    public ObjectNode get(int i) {
        if (i >= members.size()) {
            throw new IllegalArgumentException("Retrieval of undefined indexed member");
        }

        return members.get(i);
    }

    public void loadMember(ObjectNode toAdd) {
        members.add(toAdd);
    }

    public int size() {
        return members.size();
    }

    public Stream<ObjectNode> getMembers() {
        return members.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalContextList that = (LocalContextList) o;

        return members.equals(that.members);

    }
}
