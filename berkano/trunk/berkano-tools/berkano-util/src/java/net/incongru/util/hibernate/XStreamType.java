package net.incongru.util.hibernate;

import com.thoughtworks.xstream.XStream;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.UserType;

import java.io.Reader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * An hibernate type which serializes data using xstream to a CLOB.
 * Override the getXStream method to provide your aliases.
 * 
 * <class name="Blah" table="blah">
 *   <id name="id/>
 *   <property name="pouet" column="pouet" type="net.incongru.util.hibernate.XStreamType"/>
 * </class>
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class XStreamType implements UserType {
    public int[] sqlTypes() {
        return new int[]{Types.CLOB};
    }

    public Class returnedClass() {
        return Object.class;
    }

    public boolean equals(Object x, Object y) {
        return (x == y) || (x != null && x.equals(y));
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        // seems to fail with mysql
        //Clob clob = rs.getClob(names[0]);
        //Reader reader = clob.getCharacterStream();
        // from a comment on http://www.hibernate.org/76.html :Clob clob = rs.getClob(names[0]);
        Reader reader = rs.getCharacterStream(names[0]);
        XStream xStream = getXStream();
        return xStream.fromXML(reader);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        XStream xStream = getXStream();
        String xml = xStream.toXML(value);
//        st.setClob(index, Hibernate.createClob(xml)); // seems to fail with mysql
        // from a comment on http://www.hibernate.org/76.html :
        StringReader r = new StringReader(xml);
        st.setCharacterStream(index, r, xml.length());

    }

    protected XStream getXStream() {
        XStream xStream = new XStream();
        return xStream;
    }

    public Object deepCopy(Object value) {
        return value;
    }

    public boolean isMutable() {
        return false;
    }
}