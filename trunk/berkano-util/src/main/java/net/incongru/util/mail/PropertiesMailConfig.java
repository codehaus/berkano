package net.incongru.util.mail;

import java.util.Properties;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class PropertiesMailConfig implements MailConfig {
    private Properties properties;

    public PropertiesMailConfig(Properties properties) {
        this.properties = properties;
    }

    public String getMailHost() {
        return properties.getProperty("mail.host");
    }

    public String getFromName() {
        return properties.getProperty("from.name");
    }

    public String getFromEmail() {
        return properties.getProperty("from.email");
    }
}
