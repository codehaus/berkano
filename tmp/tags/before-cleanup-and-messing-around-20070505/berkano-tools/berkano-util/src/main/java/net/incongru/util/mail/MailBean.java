package net.incongru.util.mail;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class MailBean {
    private String toEmail;
    private String toName;
    private String subject;
    private String templateName;
    private Map values;
    private String replyToEmail;
    private String fromEmail;
    private String fromName;
    private Locale locale;

    public MailBean() {
    }

    public MailBean(String toEmail, String toName, String subject, String templateName, Map values) {
        this.toEmail = toEmail;
        this.toName = toName;
        this.subject = subject;
        this.templateName = templateName;
        this.values = values;
    }

    public MailBean(String toEmail, String toName, String subject, String templateName, Map values, String replyToEmail) {
        this.toEmail = toEmail;
        this.toName = toName;
        this.subject = subject;
        this.templateName = templateName;
        this.values = values;
        this.replyToEmail = replyToEmail;
    }

    public MailBean(String toEmail, String toName, String subject, String templateName, Map values, String replyToEmail, String fromEmail, String fromName) {
        this.toEmail = toEmail;
        this.toName = toName;
        this.subject = subject;
        this.templateName = templateName;
        this.values = values;
        this.replyToEmail = replyToEmail;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map getValues() {
        return values;
    }

    public void setValues(Map values) {
        this.values = values;
    }

    public String getReplyToEmail() {
        return replyToEmail;
    }

    public void setReplyToEmail(String replyToEmail) {
        this.replyToEmail = replyToEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    // generated equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MailBean mailBean = (MailBean) o;

        if (fromEmail != null ? !fromEmail.equals(mailBean.fromEmail) : mailBean.fromEmail != null)
            return false;
        if (fromName != null ? !fromName.equals(mailBean.fromName) : mailBean.fromName != null)
            return false;
        if (locale != null ? !locale.equals(mailBean.locale) : mailBean.locale != null) return false;
        if (replyToEmail != null ? !replyToEmail.equals(mailBean.replyToEmail) : mailBean.replyToEmail != null)
            return false;
        if (subject != null ? !subject.equals(mailBean.subject) : mailBean.subject != null)
            return false;
        if (templateName != null ? !templateName.equals(mailBean.templateName) : mailBean.templateName != null)
            return false;
        if (toEmail != null ? !toEmail.equals(mailBean.toEmail) : mailBean.toEmail != null)
            return false;
        if (toName != null ? !toName.equals(mailBean.toName) : mailBean.toName != null) return false;
        if (values != null ? !values.equals(mailBean.values) : mailBean.values != null) return false;

        return true;
    }

    // generated hashCode
    public int hashCode() {
        int result;
        result = (toEmail != null ? toEmail.hashCode() : 0);
        result = 29 * result + (toName != null ? toName.hashCode() : 0);
        result = 29 * result + (subject != null ? subject.hashCode() : 0);
        result = 29 * result + (templateName != null ? templateName.hashCode() : 0);
        result = 29 * result + (values != null ? values.hashCode() : 0);
        result = 29 * result + (replyToEmail != null ? replyToEmail.hashCode() : 0);
        result = 29 * result + (fromEmail != null ? fromEmail.hashCode() : 0);
        result = 29 * result + (fromName != null ? fromName.hashCode() : 0);
        result = 29 * result + (locale != null ? locale.hashCode() : 0);
        return result;
    }
}
