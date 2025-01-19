package org.capps.testutil;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FaultyState {
    String sql;
    Set<Integer> faultyIndexes = new HashSet<>();
    Integer currentIndex = 0;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FaultyState other = (FaultyState) obj;
        if (this.sql.equals(((FaultyState) obj).sql))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return this.sql.hashCode();
    }
}
