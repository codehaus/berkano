package net.incongru.berkano.user;


/**
 * This is the implementation of MembershipManagemer interface.
 * 
 * @author fburlet
 */
public class MembershipManagerImpl implements MembershipManager {
    private final GroupDAO groupDAO;
    private final UserDAO userDAO;

    public MembershipManagerImpl(GroupDAO groupDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
    }
    
    public boolean isMemberOf(Long userId, Long groupId) {
        GroupImpl g = (GroupImpl) groupDAO.getGroupById(groupId);
        UserImpl u = (UserImpl) userDAO.getUserById(userId);
        return g.getUsers().contains(u);
    }
}
