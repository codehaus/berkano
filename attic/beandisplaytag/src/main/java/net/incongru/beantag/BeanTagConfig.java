package net.incongru.beantag;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
public class BeanTagConfig implements Serializable {
    private static final String APPLICATION_CONTEXT_KEY = "net.incongru.beantag.Options";
    private static final String DEFAULT_CONFIG_FILE = "/WEB-INF/beandisplaytag.properties";

    private static final String PROPERTY_DEFAULTWRITER = "writer.class";
    static final String OUTPUT_EMPTYTABLE_NOTICE = "emptytable.notice";
    static final String OUTPUT_EMPTYTABLE_NOTICE_TEXT = "beandisplaytag.emptytable.notice";

    static BeanTagConfig getConfig(PageContext pageContext) {
        return getConfig(pageContext.getServletContext());
    }

    static BeanTagConfig getConfig(ServletContext ctx) {
        BeanTagConfig cfg = (BeanTagConfig) ctx.getAttribute(APPLICATION_CONTEXT_KEY);
        if (cfg == null) {
            Properties p = getDefaultProperties();

            String configFilePath = ctx.getInitParameter(APPLICATION_CONTEXT_KEY);
            if (configFilePath != null) {
                InputStream in = ctx.getResourceAsStream(configFilePath);
                if (in == null) {
                    throw new RuntimeException("Can't load BeanTagConfig from " + configFilePath);
                }
                try {
                    p.load(in);
                } catch (IOException e) {
                    throw new RuntimeException("Can't load BeanTagConfig from " + configFilePath + " : " + e.getMessage(), e);
                }
            } else if (configFilePath == null) {
                InputStream in = ctx.getResourceAsStream(DEFAULT_CONFIG_FILE);
                if (in != null) {
                    try {
                        p.load(in);
                    } catch (IOException e) {
                        throw new RuntimeException("Can't load BeanTagConfig from " + configFilePath + " : " + e.getMessage(), e);
                    }
                }
            }

            cfg = new BeanTagConfig(p);
            ctx.setAttribute(APPLICATION_CONTEXT_KEY, cfg);
        }
        return cfg;
    }

    private static Properties getDefaultProperties() {
        Properties p = new Properties();
        p.setProperty(PROPERTY_DEFAULTWRITER, "net.incongru.beantag.OgnlTableWriter");//BeanUtilsTableWriter");

        return p;
    }

    private Properties props;

    BeanTagConfig(Properties p) {
        this.props = p;
    }

    String getProperty(String name) {
        return props.getProperty(name);
    }

    TableWriter instanciateDefaultWriter() {
        String writerClass = getProperty(PROPERTY_DEFAULTWRITER);
        return instanciateWriter(writerClass);
    }

    TableWriter instanciateWriter(String writerClass) {
        TableWriter tableWriter = (TableWriter) instanciate(writerClass);
        tableWriter.setConfig(this);
        return tableWriter;
    }

    PropertyDecorator instanciatePropertyDecorator(String decoratorClass) {
        return (PropertyDecorator) instanciate(decoratorClass);
    }

    private Object instanciate(String className) {
        try {
            Class c = Class.forName(className);
            return c.newInstance();
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
