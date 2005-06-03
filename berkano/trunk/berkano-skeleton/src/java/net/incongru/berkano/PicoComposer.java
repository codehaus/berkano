package net.incongru.berkano;

import com.atlassian.seraph.auth.AuthenticationContextImpl;
import net.incongru.berkano.mail.ConstructableVelocityEngine;
import net.incongru.berkano.mail.MailConfig;
import net.incongru.berkano.mail.PropertiesMailConfig;
import net.incongru.berkano.mail.VelocityMailer;
import net.incongru.berkano.security.password.retrieval.MailNewPasswordRetrievalStrategy;
import net.incongru.berkano.security.password.matching.CleanPasswordMatchingStrategy;
import net.incongru.berkano.security.password.generator.BasicPasswordGenerator;
import net.incongru.berkano.security.hibernate.HibernatedRoleDAO;
import net.incongru.berkano.security.seraph.BerkanoSecurityConfig;
import net.incongru.berkano.security.seraph.BerkanoUserAuthenticator;
import net.incongru.berkano.security.seraph.UserGroupRoleMapper;
import net.incongru.berkano.security.seraph.UserRoleCache;
import net.incongru.berkano.security.seraph.filter.BerkanoLoginFilter;
import net.incongru.berkano.security.seraph.filter.BerkanoSecurityFilter;
import net.incongru.berkano.user.hibernate.HibernatedGroupDAO;
import net.incongru.berkano.user.hibernate.HibernatedUserDAO;
import net.incongru.berkano.user.hibernate.HibernatedUserPropertyAccessor;
import net.incongru.berkano.user.extensions.BerkanoSeraphUserPropertyHelper;
import net.incongru.berkano.sample.FirstSampleApp;
import net.incongru.berkano.sample.SecondSampleApp;
import net.incongru.berkano.app.ApplicationsProvider;
import org.apache.velocity.app.VelocityEngine;
import org.nanocontainer.integrationkit.ContainerComposer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.defaults.BasicComponentParameter;

import java.util.Properties;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.16 $
 */
public class PicoComposer implements ContainerComposer {
    public void composeContainer(MutablePicoContainer pico, Object assemblyScope) {
        if (assemblyScope instanceof javax.servlet.ServletContext) {
            registerContextScope(pico);
            System.out.println("**** Webapp-level pico components registered by PicoComposer");
        } else if (assemblyScope instanceof javax.servlet.http.HttpSession) {
            registerSessionScope(pico);
            System.out.println("**** Session-level pico components registered by PicoComposer");
        } else if (assemblyScope instanceof javax.servlet.ServletRequest) {
            registerRequestScope(pico);
            System.out.println("**** Request-level pico components registered by PicoComposer");
        }
    }

    private void registerRequestScope(MutablePicoContainer pico) {
        pico.registerComponentImplementation(HibernatedUserDAO.class);
        pico.registerComponentImplementation(HibernatedGroupDAO.class);
        pico.registerComponentImplementation(HibernatedRoleDAO.class);
        pico.registerComponentImplementation(org.nanocontainer.hibernate.FailoverSessionDelegator.class);
        pico.registerComponentImplementation(org.nanocontainer.hibernate.SessionLifecycle.class);

        pico.registerComponentImplementation(BerkanoSecurityFilter.class);
        pico.registerComponentImplementation(BerkanoLoginFilter.class);
        pico.registerComponentImplementation(BerkanoSecurityConfig.class);
        pico.registerComponentImplementation(BerkanoUserAuthenticator.class);
        pico.registerComponentImplementation(UserGroupRoleMapper.class);
        pico.registerComponentImplementation(UserRoleCache.class);
        //pico.registerComponentImplementation(AuthenticationContext.class, BerkanoAuthenticationContext.class);
        pico.registerComponentImplementation(AuthenticationContextImpl.class);//, AuthenticationContextImpl.class);

        pico.registerComponentImplementation(MailNewPasswordRetrievalStrategy.class);

        pico.registerComponentImplementation(HibernatedUserPropertyAccessor.class);
        pico.registerComponentImplementation(BerkanoSeraphUserPropertyHelper.class);
        
    }

    private void registerSessionScope(MutablePicoContainer pico) {
    }

    private void registerContextScope(MutablePicoContainer pico) {
        pico.registerComponentImplementation(net.incongru.berkano.bookmarksmgt.UserBookmarksAccessor.class);

        pico.registerComponentImplementation(org.nanocontainer.hibernate.ConstructableConfiguration.class);
        pico.registerComponentImplementation(org.nanocontainer.hibernate.SessionFactoryDelegator.class);
        pico.registerComponentImplementation(org.nanocontainer.hibernate.SessionFactoryLifecycle.class);

        pico.registerComponentImplementation(CleanPasswordMatchingStrategy.class);
        //pico.registerComponentImplementation(BookmarksDAO.class);
        //pico.registerComponentImplementation(AuthenticationContext.class, AuthenticationContextImpl.class);

        Properties velocityConfig = new Properties();
        velocityConfig.setProperty("resource.loader", "classpath");
        velocityConfig.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
        velocityConfig.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        pico.registerComponentInstance("velocityConfig", velocityConfig);
        pico.registerComponentImplementation(VelocityEngine.class, ConstructableVelocityEngine.class,
                new Parameter[]{new BasicComponentParameter("velocityConfig")});

        Properties berkanoMailConfig = new Properties();
        //berkanoMailConfig.setProperty("mail.host", "relay.skynet.be");
        berkanoMailConfig.setProperty("mail.host", "mail.chello.fr");
        berkanoMailConfig.setProperty("from.name", "Berkano");
        berkanoMailConfig.setProperty("from.email", "berkano@berkano.org");
        pico.registerComponentInstance("berkanoMailConfig", berkanoMailConfig);
        pico.registerComponentImplementation(MailConfig.class, PropertiesMailConfig.class, new Parameter[]{
                            new BasicComponentParameter("berkanoMailConfig")});
        pico.registerComponentImplementation(VelocityMailer.class);

        //pico.registerComponentImplementation(MailCurrentPasswordRetrievalStrategy.class);
        pico.registerComponentImplementation(BasicPasswordGenerator.class);

        pico.registerComponentImplementation(ApplicationsProvider.class);
        pico.registerComponentImplementation(FirstSampleApp.class);
        pico.registerComponentImplementation(SecondSampleApp.class);
    }
}
