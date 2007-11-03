package net.incongru.berkano.security.password.generator;

import net.incongru.berkano.security.password.PasswordGenerator;
import org.apache.commons.lang.RandomStringUtils;

/**
 * This simple {@link net.incongru.berkano.security.password.PasswordGenerator}
 * is based on RandomStringUtils from jakarta-commons-lang, and generates
 * a random password of 6 characters composed of letters and numbers only.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class BasicPasswordGenerator implements PasswordGenerator {
    public String generate() {
        return RandomStringUtils.random(6, true, true);
    }
}
