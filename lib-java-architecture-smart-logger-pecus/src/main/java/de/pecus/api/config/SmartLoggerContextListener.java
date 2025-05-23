package de.pecus.api.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.pecus.api.log.SmartLogger;

/**
 * Listener needed to successfully terminated the SmartLogger ThreadPoolExecutor
 * @author Juan Carlos Contreras
 *
 */
@WebListener
public class SmartLoggerContextListener implements ServletContextListener {
    
    private static final Logger LOG = LogManager.getLogger(SmartLoggerContextListener.class);

    /**
     * Print welcome message form SmartLogger
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        printWelcomeMessage();
    }

    /**
     * When the application is finished, the SmartLogger ThreadPoolExecutor is terminated
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SmartLogger.shutdown();
        LOG.info("SmartLogger closed");
    }
    
    /**
     * Prints welcome message
     */
    private void printWelcomeMessage() {
        LOG.info("=============================================================");
        LOG.info("              _        _____    _    _   _____               ");
        LOG.info("      /\\     | |      |  __ \\  | |  | | |  __ \\      /\\      ");
        LOG.info("     /  \\    | |      | |__) | | |  | | | |__) |    /  \\     ");
        LOG.info("    / /\\ \\   | |      |  ___/  | |  | | |  _  /    / /\\ \\    ");
        LOG.info("   / ____ \\  | |____  | |      | |__| | | | \\ \\   / ____ \\   ");
        LOG.info("  /_/    \\_\\ |______| |_|       \\____/  |_|  \\_\\ /_/    \\_\\  ");
        LOG.info("                                 - Printed by SmartLogger -  ");
        LOG.info("=============================================================");
    }
    
}