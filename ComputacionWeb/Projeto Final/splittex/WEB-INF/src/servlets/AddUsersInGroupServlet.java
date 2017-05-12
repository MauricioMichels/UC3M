package splittex.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.servlet.*;


import splittex.model.*;


@WebServlet("/addUsersInGroup")
public class AddUsersInGroupServlet extends HttpServlet{

/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        try (DBManager db = new DBManager()){
           
        	//Invocar a los metodos que haga falta

        	System.out.println("Get Method of GroupServlet");

            Group group = new Group();
	        User user = new User();

	        int jspGroupId = Integer.parseInt(request.getParameter("jspGroupId"));
	        String jspEmail = request.getParameter("jspEmail");
	        
	        group = db.whichGroup(jspGroupId);
	        user = db.whichUser(jspEmail);

	        user = db.addUserInGroup(user.getId(), group.getId());


	        response.sendRedirect("group?groupId=" + jspGroupId);


        } catch(NamingException | SQLException e) {

            e.printStackTrace();
            response.sendError(500);
             
        }
               

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				

	}

}