/* SecuritySettingsPanel.java -- Display possible security settings.
Copyright (C) 2010 Red Hat

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package net.adoptopenjdk.icedteaweb.client.controlpanel.panels;

import net.adoptopenjdk.icedteaweb.Assert;
import net.adoptopenjdk.icedteaweb.client.controlpanel.NamedBorderPanel;
import net.adoptopenjdk.icedteaweb.i18n.Translator;
import net.sourceforge.jnlp.config.DeploymentConfiguration;

import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import static net.sourceforge.jnlp.config.ConfigurationConstants.KEY_SECURITY_SERVER_WHITELIST;

/**
 * This provides a way for the user to display the server white list defined in <code>deployment.properties</code>.
 */
@SuppressWarnings("serial")
public class ServerWhitelistPanel extends NamedBorderPanel {

    /**
     * This creates a new instance of the server white list panel.
     *
     * @param config Loaded DeploymentConfiguration file.
     */
    public ServerWhitelistPanel(final DeploymentConfiguration config) {
        super(Translator.R("CPServerWhitelist"), new BorderLayout());

        Assert.requireNonNull(config, "config");

        final List<String> whitelist = config.getPropertyAsList(KEY_SECURITY_SERVER_WHITELIST);
        final JList<String> jList = new JList<>(new Vector<>(whitelist));
        jList.setFixedCellHeight(20);
        add(new JScrollPane(jList), BorderLayout.CENTER);
    }
}
