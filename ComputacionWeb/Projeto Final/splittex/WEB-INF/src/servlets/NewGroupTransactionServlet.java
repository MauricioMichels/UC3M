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


@WebServlet("/newGroupTransaction")
public class NewGroupTransactionServlet extends HttpServlet{

/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	 
		try (DBManager db = new DBManager()){
           
        	//Invocar a los metodos que haga falta
            System.out.println("Get Method of NewGroupTransactionServlet");

            User user = new User();
            user = (User)request.getSession().getAttribute("user");

            int jspPayer = user.getId();
            int jspGroupId = Integer.parseInt(request.getParameter("groupId")) ;
            String jspTransactionSubject = request.getParameter("subject");
            double amount = Double.parseDouble(request.getParameter("amount"));

            List<User> userList = db.groupUsers(jspGroupId);

            for(int i=0; i<userList.size(); i++){

                Transaction transaction = new Transaction();
                
                int jspReciever = userList.get(i).getId();

                double amountSplited = amount/userList.size();

                if (jspPayer != userList.get(i).getId())
                    transaction = db.newTransaction(jspTransactionSubject, jspGroupId, jspPayer, jspReciever, amountSplited);

            }
            
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