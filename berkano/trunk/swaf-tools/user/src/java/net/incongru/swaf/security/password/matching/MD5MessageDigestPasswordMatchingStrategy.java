package net.incongru.swaf.security.password.matching;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class MD5MessageDigestPasswordMatchingStrategy extends AbstractMessageDigestPasswordMatchingStrategy {
    protected MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5");
    }
}
