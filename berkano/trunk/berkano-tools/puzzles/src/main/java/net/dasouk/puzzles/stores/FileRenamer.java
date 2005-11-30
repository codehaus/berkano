package net.dasouk.puzzles.stores;

import java.io.Serializable;

/**
 * Creates new names for file base on their previous name
 */
public interface FileRenamer extends Serializable {
    public String generateNewName(String filename);
}
