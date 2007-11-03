package net.incongru.util.hibernate;

import com.thoughtworks.xstream.XStream;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Reader;
import java.io.Serializable;
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
 * @author gjoseph
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

    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        // seems to fail with mysql
        //Clob clob = rs.getClob(names[0]);
        //Reader reader = clob.getCharacterStream();
        // from a comment on http://www.hibernate.org/76.html :Clob clob = rs.getClob(names[0]);
        final Reader reader = rs.getCharacterStream(names[0]);
        if (reader == null) {
            return null;
        }
        final XStream xStream = getXStream();
        return xStream.fromXML(reader);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.CLOB);
            return;
        }
        final XStream xStream = getXStream();
        final String xml = xStream.toXML(value);
//        st.setClob(index, Hibernate.createClob(xml)); // seems to fail with mysql
        // from a comment on http://www.hibernate.org/76.html :
        final StringReader r = new StringReader(xml);
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

    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
