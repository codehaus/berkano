package net.incongru.berkano.user;

/**
 * This is the interface to manage link between users and groups.
 * 
 * @author fburlet
 */
public interface MembershipManager {
    
    /**
     * This is checking whether a user is a member of a given group.
     * @param userId the id of the user
     * @param groupId the id of the group
     * @return true, if the user is a member of the group, false otherwise.
     */
    public boolean isMemberOf(Long userId, Long groupId);

}
