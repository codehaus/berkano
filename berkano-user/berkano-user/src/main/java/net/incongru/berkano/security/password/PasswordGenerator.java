package net.incongru.berkano.security.password;

/**
 * Implementations of this interface generate a random (or not...) password
 * and return it in clear form. Whatever does anything with it will encrypt it
 * if necessary using a {@link PasswordMatchingStrategy}.
 * 
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface PasswordGenerator {
    public String generate();
}
