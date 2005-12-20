package net.incongru.util.mail;

import java.util.Map;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public interface Mailer {
    void mail(String toEmail, String toName, String subject, String templateName, Map values) throws Exception;
}
