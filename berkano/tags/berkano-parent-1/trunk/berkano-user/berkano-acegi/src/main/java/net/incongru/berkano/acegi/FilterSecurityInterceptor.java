package net.incongru.berkano.acegi;

import org.acegisecurity.AccessDecisionManager;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * A simple wrapper around Acegi's FilterSecurityInterceptor which
 * allows CDI.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class FilterSecurityInterceptor extends org.acegisecurity.intercept.web.FilterSecurityInterceptor {
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
//                                    --> authenticationDao(org.acegisecurity.providers.dao.memory.InMemoryDaoImpl)
//                                        --> userMap("admin=password,ROLE_ADMIN")
//
//      accessDecisionManager(org.acegisecurity.vote.UnanimousBased)
//                            -> allowIfAllAbstainDecisions(false)
//                               + decisionVoters[roleVoter (org.acegisecurity.vote.RoleVoter)]


}
