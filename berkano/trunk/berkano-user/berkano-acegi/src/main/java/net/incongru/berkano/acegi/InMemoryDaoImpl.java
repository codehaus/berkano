package net.incongru.berkano.acegi;

import org.acegisecurity.userdetails.memory.UserMap;

/**
 * A simple wrapper around Acegi's InMemoryDaoImpl which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class InMemoryDaoImpl extends org.acegisecurity.userdetails.memory.InMemoryDaoImpl {
    public InMemoryDaoImpl(UserMap userMap) {
        setUserMap(userMap);
    }
}
