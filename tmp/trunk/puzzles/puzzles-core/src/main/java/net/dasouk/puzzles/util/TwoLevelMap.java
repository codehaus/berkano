package net.dasouk.puzzles.util;

import java.util.Set;

/**
 * todo documentation
 */
public interface TwoLevelMap<FirstLevel,SecondLevel, Value> {
    /**
     * @return an empty Set if no namespace has been defined
     */
    public Set<FirstLevel> firstLevelSet();

    /**
     * gets all the artifacts' ids in the given firstLevel
     *
     * @param firstLevel
     * @return an empty Set if the firstLevel does not exist or if there are no artifacts in this firstLevel
     */
    public Set<SecondLevel> secondLevelSet(FirstLevel firstLevel);

    public Set<Value> valuesSet(FirstLevel firstLevel);

    public Value remove(FirstLevel firstLevel, SecondLevel secondLevel);

    public void put(FirstLevel firstLevel, SecondLevel secondLevel, Value value);

    public Value get(FirstLevel firstLevel, SecondLevel secondLevel);

    public boolean contains(FirstLevel firstLevel, SecondLevel secondLevel);

    public int firstLevelSize();

    public int secondLevelSize();

    public int size(FirstLevel firstLevel);

    public void clear();
}
