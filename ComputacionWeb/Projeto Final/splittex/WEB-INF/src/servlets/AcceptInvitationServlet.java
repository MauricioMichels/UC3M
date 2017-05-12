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


@WebServlet("/acceptInvitation")
public class AcceptInvitationServlet extends HttpServlet{

/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	 
		try (DBManager db = new DBManager()){
           
        	//Invocar a los metodos que haga falta

        	System.out.println("Get Method of AcceptInvitationServlet");

            Invitation invitation = new Invitation();
            User user = new User();
			
			user = (User)request.getSession().getAttribute("user");
            
            int jspUserId = user.getId();
            int jspGroupId = Integer.parseInt(request.getParameter("groupId")) ;
            
            invitation = db.acceptInvitation(jspUserId,jspGroupId);
            
            response.sendRedirect("user");

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