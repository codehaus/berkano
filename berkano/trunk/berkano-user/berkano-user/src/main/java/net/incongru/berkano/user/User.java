package net.incongru.berkano.user;

import java.security.Principal;
import java.util.Date;
import java.util.Set;

/**
 * This interface defines the basic user functionalities and attributes.
 *
 * todo : Maybe some are useless or superfluous(email, fullName, ...), we'll refine that later.
 * todo : do we need preferences that are not application specific or shared between applications?
 * todo : should we foresee sharing of preferences by grouped applications, or just "global" prefs?
 *
 * @see UserDAO
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.13 $
 */
public interface User extends Principal {
    public Long getUserId();

    public String getUserName();

    /**
     * TODO : This is to be discussed: do we need to expose getPassword here?
     * I think yes, because some people might be annoyed by a "change-only"
     * password system (no existing password sent by mail), but implementations
     * might skip it (make it throw IllegalStateException for exemple)
     *
     * @return the password of the user
     */
    public String getPassword() throws UnsupportedOperationException;

    /**
     * TODO : how could we possibly prevent people playing with the returned set?
     * It would probably not hurt anyway, but it should be mandatory to use the
     * UserDAO object to add groups to a user. Implementations could
     * return Collection.unmodifiableSet instances, but this can not be enforced
     * by this interface can it?
     */
    public Set getGroups(); // Set<Group>

    /**
     * This returns the user name as it should be displayed.
     * In most implementations this will just return the same as getUserName,
     * but some implementations might decide to replace _ by space for example, etc...
     * Some other implementations will also allow users to define this themselves
     *
     * @return the username as it should be displayed
     */
    public String getFullName();

    public String getEmail();

    public Date getCreationTimestamp();

    public Date getUpdateTimestamp();
}
