package com.pamirs.dbplus.web.logback;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author piaohailin
 * 
 */
public class LogbackConfigListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        LogbackWebConfigurer.initLogging(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
        LogbackWebConfigurer.shutdownLogging(event.getServletContext());
    }
}
