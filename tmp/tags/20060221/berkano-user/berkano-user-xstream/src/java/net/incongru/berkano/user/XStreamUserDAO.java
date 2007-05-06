package net.incongru.berkano.user;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * This implementation is currently suboptimal.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.8 $
 */
public class XStreamUserDAO implements UserDAO {

    public XStreamUserDAO() {
    }

    public User getUserById(Long userId) throws UnknownUserException {
        File userFile = getUserFile(userId);
        try {
            FileReader reader = new FileReader(userFile);
            XStream xStream = getXStream();
            User user = (User) xStream.fromXML(reader);
            return user;
        } catch (FileNotFoundException ex) {
            throw new UnknownUserException(userId);
        }
    }

    public User getUserByName(String userName) {
        throw new IllegalStateException("not implemented");
    }

    public User getUserByEmail(String email) {
        throw new IllegalStateException("not implemented");
    }

    /*public UserMutator newUser() {
        return new UserImpl();
    }

    public UserMutator getUserMutator(User user) {
        if (!(user instanceof UserImpl)) {
            throw new RuntimeException("Only supports UserImpl, this is " + user.getClass());
        }
        return (UserMutator) user;
    }

    public boolean saveUser(UserMutator mutatedUser) {
        if (!(mutatedUser instanceof UserImpl)) {
            throw new RuntimeException("Only supports UserImpl, this is " + mutatedUser.getClass());
        }
        User user = (User) mutatedUser;
        File outFile = getUserFile(user.getUserId());
        XStream xStream = getXStream();
        try {
            xStream.toXML(mutatedUser, new FileWriter(outFile));
            return true;
        } catch (IOException e) {
            e.printStackTrace();  //todo
            return false;
        }
    }*/

    public List listAllUsers() {
        throw new IllegalStateException("not implemented");
    }

    public boolean removeUser(Long userId) {
        throw new IllegalStateException("not implemented");
    }

    public void addProperty(Long userId, String propertyKey, Object value) throws UnknownUserException {
        throw new IllegalStateException("not implemented");
    }

    public void removeProperty(Long userId, String propertyKey) throws UnknownUserException {
        throw new IllegalStateException("not implemented");
    }

    public User newUser(String userName, String password, String email, String fullName) {
        throw new IllegalStateException("not implemented");
    }

    public User updateUser(Long userId, String userName, String email, String fullName) throws UnknownUserException {
        throw new IllegalStateException("not implemented");
    }

    public void changePassword(Long userId, String newPassword) throws UnknownUserException {
        throw new IllegalStateException("not implemented");
    }

    public void addToGroup(Long userId, Long groupId) throws UnknownUserException {
        throw new IllegalStateException("not implemented");
    }

    public void removeFromGroup(Long userId, Long groupId) throws UnknownUserException {
        throw new IllegalStateException("not implemented");
    }

    private XStream getXStream() {
        XStream xStream = new XStream();
        xStream.alias("user", UserImpl.class);
        return xStream;
    }

    private File getUserFile(Long userid) {
        File file = new File("d:/temp/users", String.valueOf(userid));
        return file;
    }
}
