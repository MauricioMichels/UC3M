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
@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	Connection connection = null;
    

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				
        System.out.println("Post Method of AddUserServlet");
                
        try (DBManager db = new DBManager()){
         
            //Invocar a los metodos que haga falta

            String jspUserName = request.getParameter("username");
            String jspEmail = request.getParameter("email");
            String jspPassword = request.getParameter("password");
            
            User user = new User();
            int check = db.checkUser(jspUserName,jspEmail);
            
            if(check==0){
            
                user = db.addUser(jspUserName, jspEmail, jspPassword);
            
                request.getSession().setAttribute("user", user);

                response.sendRedirect("user");

            }else{

                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/SignUpFailed.jsp");
                rd.forward(request, response);

            }

            //response.sendRedirect(request.getContextPath + "/WEB-INF/jsp/User.jsp");

        

        } catch(NamingException | SQLException e) {

            e.printStackTrace();
            response.sendError(500);
             
        }

        

	}

}