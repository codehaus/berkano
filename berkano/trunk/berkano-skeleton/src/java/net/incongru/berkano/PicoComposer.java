package net.incongru.swaf;

import com.atlassian.seraph.auth.AuthenticationContextImpl;
import net.incongru.swaf.mail.ConstructableVelocityEngine;
import net.incongru.swaf.mail.MailConfig;
import net.incongru.swaf.mail.PropertiesMailConfig;
import net.incongru.swaf.mail.VelocityMailer;
import net.incongru.swaf.security.password.retrieval.MailNewPasswordRetrievalStrategy;
import net.incongru.swaf.security.password.matching.CleanPasswordMatchingStrategy;
import net.incongru.swaf.security.password.generator.BasicPasswordGenerator;
import net.incongru.swaf.security.hibernate.HibernatedRoleDAO;
import net.incongru.swaf.security.seraph.SwafSecurityConfig;
import net.incongru.swaf.security.seraph.SwafUserAuthenticator;
import net.incongru.swaf.security.seraph.UserGroupRoleMapper;
import net.incongru.swaf.security.seraph.UserRoleCache;
import net.incongru.swaf.security.seraph.filter.SwafLoginFilter;
import net.incongru.swaf.security.seraph.filter.SwafSecurityFilter;
import net.incongru.swaf.user.hibernate.HibernatedGroupDAO;
import net.incongru.swaf.user.hibernate.HibernatedUserDAO;
import net.incongru.swaf.user.hibernate.HibernatedUserPropertyAccessor;
import net.incongru.swaf.user.extensions.SwafSeraphUserPropertyHelper;
import net.incongru.swaf.sample.FirstSampleApp;
import net.incongru.swaf.sample.SecondSampleApp;
import net.incongru.swaf.app.ApplicationsProvider;
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

        pico.registerComponentImplementation(SwafSecurityFilter.class);
        pico.registerComponentImplementation(SwafLoginFilter.class);
        pico.registerComponentImplementation(SwafSecurityConfig.class);
        pico.registerComponentImplementation(SwafUserAuthenticator.class);
        pico.registerComponentImplementation(UserGroupRoleMapper.class);
        pico.registerComponentImplementation(UserRoleCache.class);
        //pico.registerComponentImplementation(AuthenticationContext.class, SwafAuthenticationContext.class);
        pico.registerComponentImplementation(AuthenticationContextImpl.class);//, AuthenticationContextImpl.class);

        pico.registerComponentImplementation(MailNewPasswordRetrievalStrategy.class);

        pico.registerComponentImplementation(HibernatedUserPropertyAccessor.class);
        pico.registerComponentImplementation(SwafSeraphUserPropertyHelper.class);
        
    }

    private void registerSessionScope(MutablePicoContainer pico) {
    }

    private void registerContextScope(MutablePicoContainer pico) {
        pico.registerComponentImplementation(net.incongru.swaf.bookmarksmgt.UserBookmarksAccessor.class);

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

        Properties swafMailConfig = new Properties();
        //swafMailConfig.setProperty("mail.host", "relay.skynet.be");
        swafMailConfig.setProperty("mail.host", "mail.chello.fr");
        swafMailConfig.setProperty("from.name", "Swaf");
        swafMailConfig.setProperty("from.email", "swaf@swaf.org");
        pico.registerComponentInstance("swafMailConfig", swafMailConfig);
        pico.registerComponentImplementation(MailConfig.class, PropertiesMailConfig.class, new Parameter[]{
                            new BasicComponentParameter("swafMailConfig")});
        pico.registerComponentImplementation(VelocityMailer.class);

        //pico.registerComponentImplementation(MailCurrentPasswordRetrievalStrategy.class);
        pico.registerComponentImplementation(BasicPasswordGenerator.class);

        pico.registerComponentImplementation(ApplicationsProvider.class);
        pico.registerComponentImplementation(FirstSampleApp.class);
        pico.registerComponentImplementation(SecondSampleApp.class);
    }
}
