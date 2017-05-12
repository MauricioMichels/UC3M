<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="splittex.model.*"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="user" class="splittex.model.User" scope="request"/>
<jsp:useBean id="group" class="splittex.model.Group" scope="request"/>
<jsp:useBean id="transaction" class="splittex.model.Transaction" scope="request"/>
<jsp:useBean id="usersInGroup" class="java.util.Vector" scope="request" />
<jsp:useBean id="transactionsInGroup" class="java.util.Vector" scope="request" />

<!DOCTYPE html>
<html>

	<%Group actualGroup = (Group)request.getAttribute("actualGroup");%>
	<%User actualUser = (User)session.getAttribute("user");%>
	<%List<User> allMyUsers = (List<User>)request.getAttribute("userList");%>
	<%List<Transaction> allMyTransactions = (List<Transaction>)request.getAttribute("transactionGroupList");%>
	<%List<Transaction> theyOweMeTransactionList = (List<Transaction>)request.getAttribute("theyOweMeTransactionGroupList");%>
	<%List<Transaction> iOweTransactionList = (List<Transaction>)request.getAttribute("iOweTransactionGroupList");%>
	<%List<Invitation> invitationsList = (List<Invitation>)request.getAttribute("invitationList");%>
	<%Double totalBalance = (Double)request.getAttribute("totalBalance");%>
	<%int varCloseGroup = (int)request.getAttribute("closeGroup");%>


	<%    
		int ok = 0;
		for(int i=0; i<allMyUsers.size(); i++){
			if(actualUser.getId() ==  allMyUsers.get(i).getId())
				ok = 1;
		}
		if(ok==0)
            response.sendRedirect("user");


        for(int i=0; i<theyOweMeTransactionList.size(); i++){

			int recieverId = theyOweMeTransactionList.get(i).getRecieverUser();
			
			for(int j=0; j<iOweTransactionList.size(); j++){

				int payerId = iOweTransactionList.get(j).getPayerUser();

				if (recieverId==payerId){

					double recieverAmount = theyOweMeTransactionList.get(i).getAmount();
					double payerAmount = iOweTransactionList.get(j).getAmount();

					double valor = recieverAmount - payerAmount;

					if(valor > 0){
						theyOweMeTransactionList.get(i).setAmount(Math.abs(valor));
						iOweTransactionList.get(j).setAmount(0);				//ESTA MAL!!
					}else{
						theyOweMeTransactionList.get(i).setAmount(0);
						iOweTransactionList.get(j).setAmount(Math.abs(valor));
					}
				}
			}
		}
	%>


	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
		<script src="js/jquery-3.2.1.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<title>Group: <%=actualGroup.getGroupName()%></title>
	</head>
	<body>

		<nav class="navbar" style="background-color:rgba(175, 224, 87,0.8)">
			<div class="container-fluid">
			  	<div class="navbar-header">
			    	<a class="navbar-brand" href="#" style="color: #47476b">
						SPLITTEX
					</a>
			  	</div>
			  	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			    	<ul class="nav navbar-nav navbar-right">
			      		<li><a href="user">User</a></li>
			      		<li><a href="exit">Log Out</a></li>
			    	</ul>
			  	</div>
			</div>
		</nav>
		<div>
			<div class="container-fluid">
				<div class="row" style="margin-bottom: 5vh">
					<div class="col-md-4 text-center">
						<img style="width: 45%" src="images/logo.png">
					</div>
					<div class="col-md-4">
						<div class="col-md-12" style="padding-left: 20%">
							<h1 style="justify-content: center;"><%=actualGroup.getGroupName()%></h1>
							<%
								if(actualGroup.getActualState()==1){
									out.println("<h3 style=\"justify-content: center;color:red;\">Inactive</h3>");
									if (actualUser.getId() == actualGroup.getCreator())
										out.println("<a href=\"activateGroup?groupId="+actualGroup.getId()+"\" class=\"btn btn-default\">Activate group</a>");
								}
								else if(actualGroup.getActualState()==2)
									out.println("<h3 style=\"justify-content: center;color:green;\">Active</h3>");
								else if(actualGroup.getActualState()==3)
									out.println("<h3 style=\"justify-content: center;color:gray;\">Closed</h3>");

								// if (actualUser.getId() == actualGroup.getCreator())
								// 		out.println("<button type=\"button\" style=\"bottom: 0px\" class=\"btn btn-danger btn-lg\">Delete group</button>");


							%>

						</div>
						<div class="col-md-6 text-right">
							<h4>Group total balance: 
								<%
									if(totalBalance > 0){
										out.println("<span style=\"color: green\">" + totalBalance + "€</span>");
									}else if(totalBalance < 0){
										out.println("<span style=\"color: red\">" + totalBalance + "€</span>");
									}
								%>
							</h4>

						</div>	
						<div class="col-md-6 text-right">
							<%
								if (actualUser.getId() == actualGroup.getCreator()){
									if(actualGroup.getActualState()==2){
										if(varCloseGroup <= 0)
											out.println("<span title=\"To close the group all users balance must be 0\"><a style=\"margin-top: 25px\" title=\"To close the group all users balance must be 0\" class=\"btn btn-danger btn-lg disabled\" href=\"closeGroup?groupId="+actualGroup.getId()+"\">Close group</a></span>");
										else
											out.println("<a style=\"margin-top: 25px\" title=\"To close the group all users balance must be 0\" class=\"btn btn-danger btn-lg\" href=\"closeGroup?groupId="+actualGroup.getId()+"\">Close group</a>");
									}
								}
							%>
						</div>							
					</div>
					<div class="col-md-4">
						<div class="col-md-12">
							<div class="panel panel-default">
								<div class="panel-heading text-center">
									<h4>Group members</h4>
						  		</div>
 								<div class="panel-body">
									<ul class="list-group text-left">

										<%
											if(actualGroup.getActualState()==1){
												if(invitationsList.size()==0){
											 		out.println("<li class=\"list-group-item\">No users in this group yet</li>");
											 	}else{
													for(int i=0; i<invitationsList.size(); i++){
											 			if(invitationsList.get(i).getResponseState() == 1)
												 			out.println("<li class=\"list-group-item\">UserID: "+invitationsList.get(i).getUserId()+" is INVITED</li>");
											 			else if(invitationsList.get(i).getResponseState() == 2)
															out.println("<li class=\"list-group-item\">UserID: "+invitationsList.get(i).getUserId()+" is ACCEPTED</li>");
											 			else if(invitationsList.get(i).getResponseState() == 3)
	 													out.println("<li class=\"list-group-item\">UserID: "+invitationsList.get(i).getUserId()+" is REJECTED</li>");
											 		}
											 	}
											}else{
												if(allMyUsers.size()==0){
											 		out.println("<li class=\"list-group-item\">No users in this group yet</li>");
											 	}else{
													for(int i=0; i<allMyUsers.size(); i++){
														out.println("<li class=\"list-group-item\">"+allMyUsers.get(i).getUserName()+"</li>");
													}
												}
											}


											// if(allMyUsers.size()==0 && invitationsList.size()==0){
											// 		out.println("<li class=\"list-group-item\">No users in this group yet</li>");
											// }else{

											// 	if(actualGroup.getActualState()==1){
											// 		for(int i=0; i<invitationsList.size(); i++){

													
											// 			if(invitationsList.get(i).getResponseState() == 1)
											// 			out.println("<li class=\"list-group-item\">UserID:"+invitationsList.get(i).getUserId()+" is INVITED</li>");
											// 			if(invitationsList.get(i).getResponseState() == 2)
											// 			out.println("<li class=\"list-group-item\">UserID:"+invitationsList.get(i).getUserId()+" is ACCEPTED</li>");
											// 			if(invitationsList.get(i).getResponseState() == 3)
											// 			out.println("<li class=\"list-group-item\">UserID:"+invitationsList.get(i).getUserId()+" is REJECTED</li>");
											// 		}
											// 	}else{
											// 		for(int i=0; i<allMyUsers.size(); i++){
											// 		out.println("<li class=\"list-group-item\">"+allMyUsers.get(i).getUserName()+"</li>");
											// 		}
											// 	}
												
											// }
										%>
									</ul>
						  		</div>
								<%
									if(actualGroup.getActualState()== 1)
										if (actualUser.getId() == actualGroup.getCreator())
											out.println("<div class=\"panel-footer text-center\"><button type=\"button\" data-toggle=\"modal\" data-target=\"#modalMembers\" class=\"btn btn-default btn-block\">Add group member</button></div>");
									
								%>

								<!-- Modal members-->
								<div class="modal fade" id="modalMembers" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
												<h3 class="modal-title text-center" id="myModalLabel">Add group member</h3>
											</div>
											<form method="get" action="addUsersInGroup">
												<div class="modal-body">
													<div class="input-group">
														<span class="input-group-addon" id="sizing-addon1">User e-mail</span>
														<input name="jspGroupId" type="hidden" value="<%=actualGroup.getId()%>">
														<input name="jspEmail" type="email" required class="form-control" aria-describedby="sizing-addon1">
													</div>
												</div>
												<div class="modal-footer text-center">
													<!-- <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button> -->
													<button type="submit" class="btn btn-success btn-block" >Add member</button>
												</div>
											</form>
										</div>
									</div>
								</div>



							</div>
						</div>
					</div>
			  	</div>
			  	<div class="row">
					<div class="col-md-8">
						<div class="col-md-12">
							<div class="panel panel-default">
						  		<div class="panel-heading text-center">
									<h2>Transactions</h2>
						  		</div>
 								<div class="panel-body">
			  						<div class="row text-center">
		 								<div class="col-md-12">
		 									<!-- <button type="button" class="btn btn-success btn-block" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add Expense</button> -->
		 									<div class="col-md-6">

												<%
													if(actualGroup.getActualState()==2)
														out.println("<button type=\"button\" class=\"btn btn-primary btn-block\" data-toggle=\"modal\" data-target=\"#myModal\"><span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>Add Payment</button>");
												%>
					  						</div>
		 									<div class="col-md-6">

		 										<%
													if(actualGroup.getActualState()==2)
														out.println("<button type=\"button\" class=\"btn btn-success btn-block\" data-toggle=\"modal\" data-target=\"#myModal2\"><span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>Add Group expense</button>");
												%>
												<!-- <button type="button" class="btn btn-success btn-block" data-toggle="modal" data-target="#myModal2"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add Group expense</button> -->
						  					</div>

											<!-- Modal -->
											<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
												<div class="modal-dialog" role="document">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
															<h3 class="modal-title text-center" id="myModalLabel">New payment</h3>
														</div>
														<form method="get" action="newTransaction">
															<div class="modal-body">
																	<input name="groupId" type="hidden" value="<%=actualGroup.getId()%>">
																	<div class="input-group">
																		<span class="input-group-addon" id="sizing-addon1">Subject</span>
																		<input name="subject" type="text" class="form-control" aria-describedby="sizing-addon1" required>
																	</div>
																	<br>
																	<div class="input-group">
																		<span class="input-group-addon" id="sizing-addon1">Amount</span>
																		<input name="amount" type="text" class="form-control" aria-describedby="sizing-addon1" required>
																	</div>
																	<br>
																    <div class="form-group">
																    	<label for="sel1">Payer</label>
																    	<select class="form-control" name="payer" id="sel1" required>
																    	<% 
																			if(allMyUsers.size() > 0){
																				for(int i=0; i<allMyUsers.size(); i++){
																					out.println("<option value="+allMyUsers.get(i).getUserName()+">"+allMyUsers.get(i).getUserName()+"</option>");
																				}
																			}
																    	%>
																      </select>
																	</div>
																	<!-- <br>
																	<div class="form-group">
																    	<label for="sel2">Reciever</label>
																    	<select class="form-control" name="reciever" id="sel2">
																    	<% 
																			if(allMyUsers.size() > 0){
																				for(int i=0; i<allMyUsers.size(); i++){
																					out.println("<option value="+allMyUsers.get(i).getUserName()+">"+allMyUsers.get(i).getUserName()+"</option>");
																				}
																			}
																    	%>
																      </select>
																	</div> -->



															</div>
															<div class="modal-footer text-center">
																<!-- <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button> -->
																<button type="submit" class="btn btn-primary btn-block" >Add payment</button>
															</div>
														</form>
													</div>
												</div>
											</div>




											<!-- Modal 2-->
											<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
												<div class="modal-dialog" role="document">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
															<h3 class="modal-title text-center" id="myModalLabel">New group expense</h3>
														</div>
														<form method="get" action="newGroupTransaction">
															<div class="modal-body">
																	<input name="groupId" type="hidden" value="<%=actualGroup.getId()%>">
																	<div class="input-group">
																		<span class="input-group-addon" id="sizing-addon1">Subject</span>
																		<input name="subject" type="text" class="form-control" aria-describedby="sizing-addon1" required>
																	</div>
																	<br>
																	<div class="input-group">
																		<span class="input-group-addon" id="sizing-addon1">Amount</span>
																		<input name="amount" type="text" class="form-control" aria-describedby="sizing-addon1" required>
																	</div>
																	<!-- <br>
																    <div class="form-group">
																    	<label for="sel1">Payer</label>
																    	<select class="form-control" name="payer" id="sel1">
																    	<% 
																			if(allMyUsers.size() > 0){
																				for(int i=0; i<allMyUsers.size(); i++){
																					out.println("<option value="+allMyUsers.get(i).getUserName()+">"+allMyUsers.get(i).getUserName()+"</option>");
																				}
																			}
																    	%>
																      </select>
																	</div> -->
																	<!-- <br>
																	<div class="form-group">
																    	<label for="sel2">Reciever</label>
																    	<select class="form-control" name="reciever" id="sel2">
																    	<% 
																			if(allMyUsers.size() > 0){
																				for(int i=0; i<allMyUsers.size(); i++){
																					out.println("<option value="+allMyUsers.get(i).getUserName()+">"+allMyUsers.get(i).getUserName()+"</option>");
																				}
																			}
																    	%>
																      </select>
																	</div> -->



															</div>
															<div class="modal-footer text-center">
																<!-- <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button> -->
																<button type="submit" class="btn btn-success btn-block" >Add group Expense</button>
															</div>
														</form>
													</div>
												</div>
											</div>






										</div>
									</div>
									<table class="table">
										<thead>
											<tr style="font-weight:bold;">
												<td>Subject</td>
												<td>Moment</td>
												<td>Payer</td>
												<td>Reciever</td>
												<td>Amount</td>
											</tr>
										</thead>
										<tbody id="ProductosSelected">
											<%
												double totalTransactions = 0.0;
												if(allMyTransactions.size()==0){
													out.println("<tr><td colspan='5'>No transactions</td></tr>");
												}else{
													for(int i=0; i<allMyTransactions.size(); i++){
														out.println("<tr>");
														out.println("<td>"+allMyTransactions.get(i).getSubject()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getMoment()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getPayerUser()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getRecieverUser()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getAmount()+"</td>");
														out.println("</tr>");
														totalTransactions += allMyTransactions.get(i).getAmount();
													}
												}
											%>
										</tbody>
									</table>
								</div>
								<div class="panel-footer">
			  						<div class="row">
										<div class="col-md-10"><b>Total group transactions amount: </b></div>
										<div class="col-md-2"><b><%=totalTransactions%>€</b></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="col-md-12">
							<div class="panel panel-success">
								<div class="panel-heading text-center">
									<h4>They owe me</h4>
						  		</div>
 								<div class="panel-body">
									<table class="table" style="margin-bottom: 0px">
										<tbody id="ProductosSelected">


										<%
											double totalTheyOwe = 0;

											if(theyOweMeTransactionList.size()==0){
												out.println("<tr><td colspan='5'>No transactions yet</td></tr>");
											}else{
												for(int i=0; i<theyOweMeTransactionList.size(); i++){
													if(theyOweMeTransactionList.get(i).getAmount()>0){
														totalTheyOwe += theyOweMeTransactionList.get(i).getAmount();
														out.println("<tr class=\"text-center\">");
														out.println("<td> User ID: "+theyOweMeTransactionList.get(i).getRecieverUser()+" owes me "+theyOweMeTransactionList.get(i).getAmount()+"€</td>");
														out.println("</tr>");
													}
												}
											}
										%>


										</tbody>
									</table>
						  		</div>
						  		<div class="panel-footer">
			  						<div class="row">
										<div class="col-md-12 text-center">
											<b>Total: <span style="color: green"><%=totalTheyOwe%>€</span></b>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-danger">
								<div class="panel-heading text-center">
									<h4>I owe</h4>
						  		</div>
						  		<div class="panel-body">
									<table class="table" style="margin-bottom: 0px">
										<tbody id="ProductosSelected">
											
										<%
											double totalIOwe = 0;

											if(iOweTransactionList.size() == 0){
												out.println("<tr><td colspan='5'>No transactions</td></tr>");
											}else{
												for(int i=0; i<iOweTransactionList.size(); i++){
													if(iOweTransactionList.get(i).getAmount()>0){
														totalIOwe += iOweTransactionList.get(i).getAmount();
														out.println("<tr class=\"text-center\">");
														out.println("<td> I owe "+iOweTransactionList.get(i).getAmount()+"€ to user ID: "+iOweTransactionList.get(i).getPayerUser()+"</td>");
														out.println("</tr>");
													}
												}
											}
										%>

										</tbody>
									</table>
						  		</div>
						  		<div class="panel-footer">
			  						<div class="row">
										<div class="col-md-12 text-center">
											<b>Total: <span style="color: red"><%=totalIOwe%>€</span></b>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>