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
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	Connection connection = null;
    
    public void init(ServletConfig config) throws ServletException {
        
        super.init(config); //configuration file
      
    }

    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    RequestDispatcher rd = request.getRequestDispatcher("/User.jsp");
	    rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				
        System.out.println("Post Method of RegisterServlet");
                
        try (DBManager db = new DBManager()){
         
            //Invocar a los metodos que haga falta

            String jspUserName = request.getParameter("username");
            String jspPassword = request.getParameter("password");

            System.out.println(jspUserName);
            System.out.println(jspPassword);
            
            User user = new User();
            user = db.register(jspUserName, jspPassword);
            
            if(user != null){
                
                request.getSession().setAttribute("user", user);

                response.sendRedirect("user");

            }else{

                System.out.println("user null");
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/LogInFailed.jsp");
                rd.forward(request, response);
               
            }

            //response.sendRedirect(request.getContextPath + "/WEB-INF/jsp/User.jsp");

        

        } catch(NamingException | SQLException e) {

            e.printStackTrace();
            response.sendError(500);
             
        }

        

	}

}