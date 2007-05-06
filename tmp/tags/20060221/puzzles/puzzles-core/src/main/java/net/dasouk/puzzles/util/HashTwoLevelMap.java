package net.dasouk.puzzles.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * HashMap based implementation of the TwoLevelMap interface
 */
public class HashTwoLevelMap<FirstLevel,SecondLevel, Value> implements TwoLevelMap<FirstLevel, SecondLevel, Value> {
    private Map<FirstLevel, Map<SecondLevel, Value>> firstLevels;

    public HashTwoLevelMap() {
        firstLevels = new HashMap<FirstLevel, Map<SecondLevel, Value>>();
    }

    public Set<FirstLevel> firstLevelSet() {
        return new HashSet<FirstLevel>(firstLevels.keySet());
    }

    public Set<SecondLevel> secondLevelSet(FirstLevel firstLevel) {
        final Map<SecondLevel, Value> secondLevelMap = this.firstLevels.get(firstLevel);
        Set<SecondLevel> result = new HashSet<SecondLevel>();
        if (secondLevelMap == null) {
            return result;
        }
        result.addAll(secondLevelMap.keySet());
        return result;
    }

    public Set<Value> valuesSet(FirstLevel firstLevel) {
        final Map<SecondLevel, Value> secondLevelMap = this.firstLevels.get(firstLevel);
        Set<Value> result = new HashSet<Value>();
        if (secondLevelMap == null) {
            return result;
        }
        result.addAll(secondLevelMap.values());
        return result;
    }

    public Value remove(FirstLevel firstLevel, SecondLevel secondLevel) {
        final Map<SecondLevel, Value> secondLevelMap = this.firstLevels.get(firstLevel);
        if (secondLevelMap == null) {
            return null; //should never happen
        }
        final Value value = secondLevelMap.remove(secondLevel);
        //do some possible clean up
        if (secondLevelMap.size() == 0) {
            this.firstLevels.remove(firstLevel);
        }
        return value;
    }

    public void put(FirstLevel firstLevel, SecondLevel secondLevel, Value value) {
        Map<SecondLevel, Value> secondLevelMap = this.firstLevels.get(firstLevel);
        if (secondLevelMap == null) {
            secondLevelMap = new HashMap<SecondLevel, Value>();
        }
        secondLevelMap.put(secondLevel, value);
        this.firstLevels.put(firstLevel, secondLevelMap);
    }

    public Value get(FirstLevel firstLevel, SecondLevel secondLevel) {
        Map<SecondLevel, Value> secondLevelMap = this.firstLevels.get(firstLevel);
        if (secondLevelMap == null) {
            return null;
        }
        return secondLevelMap.get(secondLevel);
    }

    public boolean contains(FirstLevel firstLevel, SecondLevel secondLevel) {
        Map<SecondLevel, Value> secondLevelMap = this.firstLevels.get(firstLevel);
        if (secondLevelMap == null) {
            return false;
        } else {
            return secondLevelMap.containsKey(secondLevel);
        }

    }

    public int firstLevelSize() {
        return firstLevels.size();
    }

    public int secondLevelSize() {
        int size = 0;
        for (Map<SecondLevel, Value> secondLevelMap : firstLevels.values()) {
            size += secondLevelMap.size();
        }
        return size;
    }

    public int size(FirstLevel firstLevel) {
        Map<SecondLevel, Value> secondLevelMap = this.firstLevels.get(firstLevel);
        if (secondLevelMap == null) {
            return 0;
        }
        return secondLevelMap.size();
    }

    public void clear() {
        firstLevels.clear();
    }
}
