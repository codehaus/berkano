package net.incongru.berkano.user.hibernate;

import junit.framework.TestCase;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.SessionFactory;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public class HibernatedGroupDAOTest extends TestCase {
    private SessionFactory sessionFactory;

    public void setUp() throws Exception {
        /*
        Configuration cfg = new Configuration();
        cfg.addResource("net/incongru/berkano/user/User.hbm", TestCase.class.getClassLoader());
        cfg.addResource("net/incongru/berkano/user/Group.hbm", TestCase.class.getClassLoader());
        //cfg.addResource("net/incongru/berkano/security/Role.hbm", TestCase.class.getClassLoader());
        sessionFactory = cfg.buildSessionFactory();
        */
    }

    public void testDummy() throws HibernateException {
/*
        Session s = sessionFactory.openSession();

        GroupImpl g = new GroupImpl();
        g.setGroupName("the best yo group");
        g.addRole(DummyRole.bigRole);
        g.addRole(DummyRole.littleRole);
//        //Set roleset = new HashSet();
//        g.getRoles().add("DummyRole.bigRole");
//        g.getRoles().add("DummyRole.littleRole");
//        g.getRoles().add(DummyRole.bigRole);
//        g.getRoles().add(DummyRole.littleRole);
//        String[] roleset = new String[2];
//        roleset[0] = "rolalalacon"; //(Role) DummyRole.rolalacon;
//        roleset[1] = "rolnulll"; //(Role) DummyRole.rolnul;
        //g.setRoles(roleset);

        s.save(g);
        s.flush();
        System.out.println("plop");
        s.close();

        s = sessionFactory.openSession();
        List resList = s.find("from plop in class GroupImpl");
        s.close();

        assertEquals(1, resList.size());
        GroupImpl res = (GroupImpl) resList.get(0);
        assertEquals("the best yo group", res.getGroupName());
        Set roles = res.getRoles();
        assertEquals(2, roles.size());
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            Role r = (Role) it.next();
            System.out.println("r = " + r);
        }
//        String[] roles = res.getRoles();
//        assertEquals(2, roles.length);
//        for (int i = 0; i < roles.length; i++) {
//            System.out.println("roles[i] = " + roles[i]);
//        }
    }

    public void testUser() throws Exception {
        UserImpl um = new UserImpl();
        um.setUserName("greg");
        um.setEmail("greg@incongru.net");
        um.setPassword("secret");
        um.setFullName("Grégory Joseph");
        GroupImpl gr = new GroupImpl();
        gr.setGroupName("TESTGRP");
        Set groups = new HashSet();
        groups.add(gr);
        um.setGroups(groups);
        Session s = sessionFactory.openSession();
        s.save(gr);
        s.flush();
        Serializable id = s.save(um);
        System.out.println("user saved = " + id);
*/
    }
}
