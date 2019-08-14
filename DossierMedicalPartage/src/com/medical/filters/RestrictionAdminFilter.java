package com.medical.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RestrictionAdminFilter implements Filter {

    public static final String VUE_CONNEXION     = "/WEB-INF/connexion.jsp";
    public static final String ATT_SESSION_ADMIN = "sessionAdmin";

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain )
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        /* Non-filtrage des ressources statiques */
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        System.out.println( "chemin : " + chemin );
        if ( chemin.startsWith( "/inc" ) ) {
            chain.doFilter( request, response );
            return;
        }

        HttpSession session = request.getSession();

        if ( session.getAttribute( ATT_SESSION_ADMIN ) == null ) {
            request.getRequestDispatcher( VUE_CONNEXION ).forward( request, response );
        } else {
            chain.doFilter( request, response );
        }

    }

}
