package net.incongru.berkano.security;

import net.incongru.berkano.user.User;


/**
 * A SecurityGateway will often be specific to a berkano subapplication?.. then the getRoles
 * method would only return the roles of that app...
 * And each app would register its own SecurityGateway in its own picocontainer?? that sounds interesting
 *
 * See the Gateway pattern in M.Fowler's book. It might also be seen as some sort of Adapter(GOF)
 * Also check Mapper(Fowler) and ServiceStub(Fowler)
 * 
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 *
 * @deprecated currently unused - staying there for now just as a reminder
 */
public interface SecurityGateway {
    /**
     * Mimics the JAAS authentication, with Callback and CallbackHandler. Here, the authenticator class should be
     * constructed with a ServletRequest, for example)
     */
    public boolean authenticate(Authenticator authenticator);

    public boolean logout(Authenticator authenticator);

    //public boolean hasPermission(User user, Permission permission);
    public boolean hasPermission(User user, String permission);
}
