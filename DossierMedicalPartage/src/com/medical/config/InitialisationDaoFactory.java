package com.medical.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.medical.dao.DaoFactory;

public class InitialisationDaoFactory implements ServletContextListener {
    private static final String ATT_DAO_FACTORY = "daofactory";

    private DaoFactory          daoFactory;

    @Override
    public void contextInitialized( ServletContextEvent event ) {
        /* Récupération du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        /* Instanciation de notre DAOFactory */
        this.daoFactory = DaoFactory.getInstance();
        /*
         * Enregistrement dans un attribut ayant pour portée toute l'application
         */
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
    }
}
