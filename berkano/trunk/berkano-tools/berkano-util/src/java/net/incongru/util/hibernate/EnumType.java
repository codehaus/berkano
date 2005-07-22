package net.incongru.util.hibernate;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * An hibernate user type to store java5 enums.
 * Stores the enum if the form of "pack.age.EnumClass/constantName"
 * in a VARCHAR field.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class EnumType implements UserType {
    private static final int[] SQL_TYPES = new int[]{Types.VARCHAR};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return Enum.class;
    }

    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == o1;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        if (value != null) {
            int slashIdx = value.indexOf('/');
            if (slashIdx < 1 || slashIdx >= value.length() - 1) {
                throw new HibernateException("Value [" + value + "] is not valid for EnumType");
            }
            String className = value.substring(0, slashIdx);
            String constantName = value.substring(slashIdx + 1);
            try {
                Class clazz = Class.forName(className);
                return Enum.valueOf(clazz, constantName);
            } catch (ClassNotFoundException e) {
                throw new HibernateException(className + " can not be found: " + e.getMessage(), e);
            }
        }
        return null;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        String clazz = value.getClass().getName();
        Enum enumValue = (Enum) value;
        String constantName = enumValue.name();
        String output = clazz + '/' + constantName;
        st.setString(index, output);
    }

    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    public boolean isMutable() {
        return false;
    }
}
