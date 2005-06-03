package net.incongru.berkano.user;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * todo : this is purely copied from XStreamUserDAO : refactor!
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class XStreamGroupDAO implements GroupDAO {
    public Group getGroupById(Long groupId) {
        File userFile = getGroupFile(groupId);
        try {
            FileReader reader = new FileReader(userFile);
            XStream xStream = getXStream();
            Group group = (Group) xStream.fromXML(reader);
            return group;
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    public Group getGroupByName(String groupName) {
        throw new RuntimeException("not implemented yet");
    }

    public GroupMutator newGroup() {
        return new GroupImpl();
    }

    public GroupMutator getGroupMutator(Group group) {
        if (!(group instanceof GroupImpl)) {
            throw new RuntimeException("Only supports GroupImpl, this is " + group.getClass());
        }
        return (GroupMutator) group;
    }

    public boolean saveGroup(GroupMutator mutatedGroup) {
        if (!(mutatedGroup instanceof GroupImpl)) {
            throw new RuntimeException("Only supports GroupImpl, this is " + mutatedGroup.getClass());
        }
        Group group = (Group) mutatedGroup;
        File outFile = getGroupFile(group.getGroupId());
        XStream xStream = getXStream();
        try {
            xStream.toXML(mutatedGroup, new FileWriter(outFile));
            return true;
        } catch (IOException e) {
            e.printStackTrace();  //todo
            return false;
        }
    }

    public boolean removeGroup(Long groupId) {
        throw new IllegalStateException("Not implemented yet");
    }

    public List listAllGroups() {
        throw new IllegalStateException("Not implemented yet");
    }

    private XStream getXStream() {
        XStream xStream = new XStream();
        xStream.alias("group", GroupImpl.class);
        return xStream;
    }

    private File getGroupFile(Long groupId) {
        File file = new File("d:/temp/groups", String.valueOf(groupId));
        return file;
    }

}
