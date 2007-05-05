package net.incongru.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * A simple tool which will print a hierarchical description of a class' fields.
 *
 * Sample usage:
 * <code>
 * public static void main(String[] args) throws IOException, NoSuchFieldException {
 *     final ModelWriter modelWriter = new ModelWriter("com.foo.bar");
 *     final PrintWriter out = new PrintWriter(System.out);
 *     modelWriter.start(SomeBean.class, out);
 *     out.flush();
 *     out.close();
 * }
 * </code>
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class ModelWriter {
    private final String domain;

    public ModelWriter(String domain) {
        this.domain = domain;
    }

    public void start(Class root, Appendable out) throws IOException, NoSuchFieldException {
        out.append("* ");
        out.append(root.getSimpleName());
        describe(1, out, root);
    }

    private void describe(int level, Appendable out, Class clazz) throws IOException, NoSuchFieldException {
        final Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            final Class<?> propType = field.getType();

            if (propType.getName().startsWith(domain)) {
                if (propType.isEnum()) {
                    printFieldType("+ ", level, out, propType);
                    out.append(Arrays.asList(propType.getEnumConstants()).toString());
                    printFieldName(out, field);
                } else {
                    printFieldType("* ", level, out, propType);
                    printFieldName(out, field);
                    describe(level + 1, out, propType);
                }
            } else
            if (Collection.class.isAssignableFrom(propType) || Map.class.isAssignableFrom(propType))
            {
                printFieldType("* ", level, out, propType);
                printGenericParameterTypes(out, field);
                printFieldName(out, field);
                describeGenericParameterTypes(level, out, field);
            } else if (!propType.equals(Class.class)) {
                printFieldType("+ ", level, out, propType);
                printFieldName(out, field);
            }
        }
    }

    private void printGenericParameterTypes(Appendable out, Field field) throws IOException {
        final Type[] actualTypeArguments = getActualTypeArguments(field);
        out.append('<');
        for (int i = 0; i < actualTypeArguments.length; i++) {
            if (i > 0) {
                out.append(", ");
            }
            final Class paramType = (Class) actualTypeArguments[i];
            out.append(paramType.getSimpleName());
        }
        out.append('>');
    }

    private void describeGenericParameterTypes(int level, Appendable out, Field field) throws IOException, NoSuchFieldException {
        for (Type type : getActualTypeArguments(field)) {
            final Class clazz = (Class) type;
            if (clazz.getName().startsWith(domain)) {
                describe(level + 1, out, clazz);
            }
        }
    }

    private Type[] getActualTypeArguments(Field field) {
        final Type genericType = field.getGenericType();
        return ((ParameterizedType) genericType).getActualTypeArguments();
    }

    private void printFieldType(String prefix, int level, Appendable out, Class<?> propType) throws IOException {
        newLineAndIndent(level, out);
        out.append(prefix);
        out.append(propType.getSimpleName());
    }

    private void printFieldName(Appendable out, Field field) throws IOException {
        out.append(" : ");
        out.append(field.getName());
    }

    private void newLineAndIndent(int level, Appendable out) throws IOException {
        out.append('\n');
        for (int i = 0; i < level; i++) {
            out.append("  ");
        }
    }
}
