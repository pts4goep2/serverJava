/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Indra
 */
public class WebPage {

    private SQL SQL;

    protected void doGet(HttpServletRequest request, HttpServletResponse response, int calamity_id) throws ServletException, IOException {
        SQL = new SQL();
        ArrayList<String> calamity = SQL.retrieveCalamityWithID(calamity_id); // Obtain a calamity from the DB.
        
        Calamity cal = new Calamity(calamity.get(0), calamity.get(1), calamity.get(2), calamity.get(3));
        request.setAttribute("calamity", cal); // Store calamity in request scope.
        request.getRequestDispatcher("/web/index.jsp").forward(request, response); // Forward to JSP page.
    }

}
