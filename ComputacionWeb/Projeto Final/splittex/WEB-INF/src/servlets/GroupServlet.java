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


@WebServlet("/group")
public class GroupServlet extends HttpServlet{

/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	 
		try (DBManager db = new DBManager()){
           
        	//Invocar a los metodos que haga falta

        	System.out.println("Get Method of GroupServlet");

            User user = new User();
            user = (User)request.getSession().getAttribute("user");

            if(user==null){
                response.sendRedirect("LogIn.jsp");
                return;
            }

            Group group = new Group();

        	int jspGroupId = Integer.parseInt(request.getParameter("groupId"));

            group = db.whichGroup(jspGroupId);
        	
            Double totalBalance = db.totalBalance(user.getId(),jspGroupId);
            List<User> userList = db.groupUsers(group.getId());
            List<Transaction> transactionGroupList = db.groupTransactions(group.getId());
            List<Transaction> theyOweMeTransactionGroupList = db.oweMe(user.getId(), group.getId());
            List<Transaction> iOweTransactionGroupList = db.iOwe(user.getId(), group.getId());
            List<Invitation> invitationList = db.groupInivitations(jspGroupId);
            int closeGroup = 1;
            Double varBalance = 0.0;

            for (int i=0;i<userList.size();i++){
                varBalance = db.totalBalance(userList.get(i).getId(),jspGroupId);
                if(varBalance != 0){
                    closeGroup = 0;
                    break;
                }
            }


            request.setAttribute("closeGroup", closeGroup);
            request.setAttribute("invitationList", invitationList);
            request.setAttribute("userList", userList);
            request.setAttribute("transactionGroupList", transactionGroupList);
            request.setAttribute("theyOweMeTransactionGroupList", theyOweMeTransactionGroupList);
            request.setAttribute("iOweTransactionGroupList", iOweTransactionGroupList);
            request.setAttribute("totalBalance", totalBalance);


            request.setAttribute("actualGroup", group);

                // String jspGroupName = request.getParameter("groupname");
                // //conseguir el userName del que hace el grupo
                // String jspCreator = request.getParameter("creator");

        		// User jspCreator = new User();
        		// jspCreator = request.getSession().getAttribute("user");
                

                
                // group.groupName = setGroupName(jspUserName);
                // group.creator = setPassword(jspPassword);

                // String query = createGroup(group);
                
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/Group.jsp");
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
				

	}

}