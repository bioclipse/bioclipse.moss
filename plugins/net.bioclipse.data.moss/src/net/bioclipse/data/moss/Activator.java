/* Copyright (c) 2010  Egon Willighagen <egonw@users.sf.net>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at <a
 * href="http://www.eclipse.org/epl-v10.html">www.eclipse.org/legal/epl-v10.html</a>.
 * 
 * Contact: http://www.bioclipse.net/
 */
package net.bioclipse.data.moss;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the MoSS Example data's plugin life cycle.
 */
public class Activator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "net.bioclipse.data.moss";

    private static Activator activatedPlugin;

    public void start(BundleContext context) throws Exception {
        super.start(context);
        activatedPlugin = this;
    }

    public void stop(BundleContext context) throws Exception {
        activatedPlugin = null;
        super.stop(context);
    }

    public static Activator getDefault() {
        return activatedPlugin;
    }

}
