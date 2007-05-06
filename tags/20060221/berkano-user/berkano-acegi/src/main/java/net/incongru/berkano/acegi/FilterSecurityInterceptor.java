package net.incongru.berkano.acegi;

import net.sf.acegisecurity.AccessDecisionManager;
import net.sf.acegisecurity.AuthenticationManager;
import net.sf.acegisecurity.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEvent;

/**
 * A simple wrapper around Acegi's FilterSecurityInterceptor which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class FilterSecurityInterceptor extends net.sf.acegisecurity.intercept.web.FilterSecurityInterceptor {
    public FilterSecurityInterceptor(AuthenticationManager authenticationManager, AccessDecisionManager accessDecisionManager, FilterInvocationDefinitionSource filterInvocationDefinitionSource) {
        setObjectDefinitionSource(filterInvocationDefinitionSource);
        setAccessDecisionManager(accessDecisionManager);
        setAuthenticationManager(authenticationManager);
        setApplicationEventPublisher(new ApplicationEventPublisher() {
            public void publishEvent(ApplicationEvent applicationEvent) {
                System.out.println(">>>>>>applicationEvent = " + applicationEvent);
            }
        });
    }



// objectDefinitionSource (
//                CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
//                \A/parcel/.*\Z=ROLE_ADMIN

//
//      authenticationManager -> DaoAuthenticationProvider
//                                    --> authenticationDao(net.sf.acegisecurity.providers.dao.memory.InMemoryDaoImpl)
//                                        --> userMap("admin=password,ROLE_ADMIN")
//
//      accessDecisionManager(net.sf.acegisecurity.vote.UnanimousBased)
//                            -> allowIfAllAbstainDecisions(false)
//                               + decisionVoters[roleVoter (net.sf.acegisecurity.vote.RoleVoter)]


}
