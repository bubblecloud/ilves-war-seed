/**
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */
package org.bubblecloud.ilves;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.bubblecloud.ilves.comment.CommentingComponent;
import org.bubblecloud.ilves.module.audit.AuditModule;
import org.bubblecloud.ilves.module.content.ContentModule;
import org.bubblecloud.ilves.module.customer.CustomerModule;
import org.bubblecloud.ilves.site.*;
import org.bubblecloud.ilves.site.view.valo.DefaultValoView;
import org.bubblecloud.ilves.util.PersistenceUtil;
import org.bubblecloud.ilves.util.PropertiesUtil;
import org.eclipse.jetty.server.Server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Seed implementation servlet context listener for setting up Ilves in a WAR project.
 *
 * @author Tommi S.E. Laukkanen
 */
public class IlvesServletContextListener implements ServletContextListener {
    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(IlvesServletContextListener.class);
    /** The properties file prefix.*/
    public static final String PROPERTIES_FILE_PREFIX = "site";
    /** The localization bundle. */
    public static final String LOCALIZATION_BUNDLE_PREFIX = "custom-localization";
    /** The persistence unit to be used. */
    public static final String PERSISTENCE_UNIT = "custom";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // Configure logging.
        //DOMConfigurator.configure("log4j.xml");

        final long startTimeMillis = System.currentTimeMillis();
        LOGGER.info("Ilves servlet context initializing.");

        if(!PROPERTIES_FILE_PREFIX.equals("site")) {
            PropertiesUtil.setCategoryRedirection("site", PROPERTIES_FILE_PREFIX);
        }

        DefaultSiteUI.setEntityManagerFactory(PersistenceUtil.getEntityManagerFactory(PERSISTENCE_UNIT, "site"));
        DefaultSiteUI.setSecurityProvider(new SecurityProviderSessionImpl(new String[]{"administrator", "user"}));
        DefaultSiteUI.setContentProvider(new DefaultContentProvider());
        if(!"site-localization".equals(LOCALIZATION_BUNDLE_PREFIX)) {
            DefaultSiteUI.setLocalizationProvider(new LocalizationProviderBundleImpl(new String[]{"site-localization", LOCALIZATION_BUNDLE_PREFIX}));
        } else {
            DefaultSiteUI.setLocalizationProvider(new LocalizationProviderBundleImpl(new String[]{"site-localization"}));
        }

        // Initialize modules
        Ilves.initializeModule(AuditModule.class);
        Ilves.initializeModule(CustomerModule.class);
        Ilves.initializeModule(ContentModule.class);

        Ilves.addRootPage(0, "custom", DefaultValoView.class);
        Ilves.setPageComponent("custom", Slot.CONTENT, WelcomeComponent.class);
        Ilves.setPageComponent("custom", Slot.FOOTER, CommentingComponent.class);
        Ilves.setDefaultPage("custom");

        LOGGER.info("Ilves servlet context initialized in " + (System.currentTimeMillis() - startTimeMillis ) + " ms.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
