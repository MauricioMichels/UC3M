package splittex.servlet;

import splittex.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.text.*;
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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	Connection connection = null;
    
    public void init(ServletConfig config) throws ServletException {
        
        super.init(config); //configuration file
      
    }

    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    
        System.out.println("GET Method of UserServlet");
                
        try (DBManager db = new DBManager()){
         
            //Invocar a los metodos que haga falta

            User user = new User();

            user = (User)request.getSession().getAttribute("user");

             if(user==null){
                response.sendRedirect("LogIn.jsp");
                return;
            }
            
            List<Group> groupList = db.userGroups(user.getId());
            List<Transaction> transactionList = db.userTransactions(user.getId());
            double toPay = db.userToPay(user.getId());
            double toRecieve = db.userToRecieve(user.getId());
            List<Invitation> invitationList = db.userInivitations(user.getId());
                
            request.getSession().setAttribute("user", user);
            request.setAttribute("groupList", groupList);
            request.setAttribute("invitationList", invitationList);
            request.setAttribute("transactionList", transactionList);
            request.setAttribute("toPay", toPay);
            request.setAttribute("toRecieve", toRecieve);
            
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/User.jsp");
            rd.forward(request, response);


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
				
        System.out.println("Post Method of UserServlet");
                
           

	}

}