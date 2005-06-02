package net.incongru.swaf.security.password.matching;

import net.incongru.swaf.security.password.PasswordMatchingStrategy;
import net.incongru.swaf.user.User;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public abstract class AbstractMessageDigestPasswordMatchingStrategy implements PasswordMatchingStrategy {
    public boolean matches(String givenPassword, User user) {
        String hash = encode(givenPassword);
        return hash.equals(user.getPassword());
    }

    public String encode(String clearPassword) {
        try {
            // TODO : would it be any interesting to make this MessageDigest instance static or thread-local ? is it thread safe?
            MessageDigest md = getMessageDigest();
            byte[] digest = md.digest(clearPassword.getBytes());
            //byte[] base64 = Base64.encodeBase64(digest);
            char[] hex = Hex.encodeHex(digest);
            return new String(hex);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract MessageDigest getMessageDigest() throws NoSuchAlgorithmException;

}
