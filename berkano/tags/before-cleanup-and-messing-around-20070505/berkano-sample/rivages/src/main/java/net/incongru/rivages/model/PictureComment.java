package net.incongru.rivages.model;

import org.joda.time.DateTime;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class PictureComment {
    private long userId; // make either of userId or name mandatory ?
    private String name;
    private DateTime insertionDate;
    private String comment;

}
