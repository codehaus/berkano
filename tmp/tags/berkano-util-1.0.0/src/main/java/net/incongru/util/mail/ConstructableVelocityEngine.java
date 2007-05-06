package net.incongru.util.mail;

import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

/**
 * A simple subclass of VelocityEngine that allows
 * configuration/initialization through constructors.
 *
 * <strong>This will be deprecated once velocity-1.5 is generally available</strong>
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class ConstructableVelocityEngine extends VelocityEngine {
    public ConstructableVelocityEngine() throws Exception {
        super();
        init();
    }

    public ConstructableVelocityEngine(Properties p) throws Exception {
        super();
        init(p);
    }
}
