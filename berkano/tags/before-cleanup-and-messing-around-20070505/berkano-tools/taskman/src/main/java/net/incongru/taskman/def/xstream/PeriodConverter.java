package net.incongru.taskman.def.xstream;

import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;

/**
 * A simple xstream converter for Periods, allowing to get
 * human-readable Period from/to xml.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class PeriodConverter extends AbstractBasicConverter {
    public boolean canConvert(Class type) {
        return type.equals(Period.class);
    }

    protected String toString(Object obj) {
        return PeriodFormat.getDefault().print((Period) obj);
    }

    protected Object fromString(String str) {
        return PeriodFormat.getDefault().parsePeriod(str);
    }
}
