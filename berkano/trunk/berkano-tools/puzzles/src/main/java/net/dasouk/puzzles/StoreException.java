package net.dasouk.puzzles;

/**
 * Created by IntelliJ IDEA.
 * User: olivier
 * Date: Nov 9, 2005
 * Time: 4:27:54 PM
 * To change this template use File | Settings | File Templates.
 *
 * @todo documentation
 */
public class StoreException extends Exception {
    public StoreException() {
    }

    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreException(Throwable cause) {
        super(cause);
    }
}
