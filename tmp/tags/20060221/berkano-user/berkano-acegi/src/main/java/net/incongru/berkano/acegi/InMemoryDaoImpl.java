package net.incongru.berkano.acegi;

import net.sf.acegisecurity.providers.dao.memory.UserMap;

/**
 * A simple wrapper around Acegi's InMemoryDaoImpl which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class InMemoryDaoImpl extends net.sf.acegisecurity.providers.dao.memory.InMemoryDaoImpl {
    public InMemoryDaoImpl(UserMap userMap) {
        setUserMap(userMap);
    }
}
