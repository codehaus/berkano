package net.incongru.rivages.model;

import org.joda.time.DateTime;

import java.util.Set;
import java.util.Map;
import java.util.List;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class Picture {
    /** unique id */
    private long id;

    private String title;
    private String description;

    private DateTime shootDate;
    private DateTime insertionDate;

    private Map metadata;

    private List<PictureComment> comments;
    private Set<String> keywords;
    private List<Album> albums;

}
