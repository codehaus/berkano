package net.incongru.berkano.security.password.matching;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class Sha1PasswordMatchingStrategy extends AbstractMessageDigestPasswordMatchingStrategy {
    protected MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-1");
    }
}
