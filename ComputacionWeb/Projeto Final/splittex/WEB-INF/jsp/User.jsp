<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="splittex.model.*"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="user" class="splittex.model.User" scope="request"/>
<jsp:useBean id="myGroups" class="java.util.Vector" scope="request"/>
<jsp:useBean id="myTransactions" class="java.util.Vector" scope="request"/>

<!DOCTYPE html>
<html>
	
	<%User actualUser = (User)request.getSession().getAttribute("user");%>
	<%List<Group> allMyGroups = (List<Group>)request.getAttribute("groupList");%>
	<%List<Transaction> allMyTransactions = (List<Transaction>)request.getAttribute("transactionList");%>
	<%List<Invitation> allMyInvitations = (List<Invitation>)request.getAttribute("invitationList");%>
	<%double toPay = (double)request.getAttribute("toPay");%>
	<%double toRecieve = (double)request.getAttribute("toRecieve");%>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
		<!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"/> -->
		<script src="js/jquery-3.2.1.min.js"></script>
		<!-- <script src="js/jquery-2.1.1.min.js"></script> -->
		<script src="js/bootstrap.min.js"></script>
		<title>User Page: <%=actualUser.getUserName()%></title>
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
						<!-- <li><a href="#">Notifications<span class="badge">3</span></a></li> -->
						<li>
							<a href="" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								Notifications
								<%
									if(allMyInvitations.size() > 0)
										out.println("<span class=\"badge\">"+allMyInvitations.size()+"</span>");
								%>
							</a>
							<!-- DropDown -->
							<ul class="dropdown-menu" style="min-width: 300px" aria-labelledby="dropdownMenu1">
								<li role="separator" class="divider"></li>
								<%
									for(int i=0; i<allMyInvitations.size(); i++){
										out.println("<li class=\"text-center\" style=\"margin: 5px\"><div class=\"row\"><div class=\"col-md-12\">You have an invitation for the grupo "+allMyInvitations.get(i).getGroupId()+"</div><div class=\"col-md-6\"><a class=\"btn btn-success btn-sm btn-block\" href=\"acceptInvitation?groupId="+allMyInvitations.get(i).getGroupId()+"\">Accept</a></div><div class=\"col-md-6\"><a class=\"btn btn-danger btn-sm btn-block\"href=\"rejectInvitation?groupId="+allMyInvitations.get(i).getGroupId()+"\">Refuse</a></div></div></li><li role=\"separator\" class=\"divider\"></li>");
									}
								%>



							</ul>
						</li>
						<li><a href="exit">Log Out</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<div>
			<div class="container-fluid">
				<div class="row" style="margin-bottom: 5vh">

					<div class="col-md-8">
						<div class="col-md-12">
							<div class="panel panel-default">
								<div class="panel-heading text-center">
									<h2>Personal Balance</h2>
								</div>							
								<div class="panel-body">
									<div class="row text-center">
										<div class="col-md-6">
											<div class="panel panel-success">
												<div class="panel-heading text-center">
													<h4>They owe me</h4>
												</div>
												<div class="panel-body">
													<span style="font-size: 36px"><%=toRecieve%> €</span>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="panel panel-danger">
												<div class="panel-heading text-center">
													<h4>I owe</h4>
												</div>
												<div class="panel-body">
													<span style="font-size: 36px"><%=toPay%> €</span>
												</div>
											</div>


										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-4" style="height: 100%">
						<div class="col-md-12">
							<div class="panel panel-default">
								<div class="panel-heading text-center">
									<h2>WELCOME <%=actualUser.getUserName()%></h2>
								</div>
								<div class="panel-body">
									<div class="row text-center">
										<div class="col-md-12">
											<span>User ID: <%=actualUser.getId()%></span>
										</div>
										<div class="col-md-12">
											<span>User e-mail: <%=actualUser.getEmail()%></span>
										</div>
										<br>
										<div class="col-md-8">
											<span><img src="images/logo.png"></span>
										</div>
										<div class="col-md-4">
											<img src="images/User.jpg" class="img-thumbnail">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>

				<div class="row">

					<div class="col-md-6">
						<div class="col-md-12">
							<div class="panel panel-default">
								<div class="panel-heading text-center">
									<h2>Your Groups</h2>
								</div>
								<div class="panel-body">
									
									<ul class="list-group text-left">
										<%
											if(allMyGroups.size()==0){
													out.println("<li class=\"list-group-item\">No groups yet</li>");
											}else{
												for(int i=0; i<allMyGroups.size(); i++){
													out.println("<li class=\"list-group-item text-center\"><a href=\"group?groupId="+allMyGroups.get(i).getId()+"\"><b>"+allMyGroups.get(i).getGroupName());
													if(allMyGroups.get(i).getActualState() == 1)
														out.println(" <span style=\"color: red\">(Inactive)</span>");
													if(allMyGroups.get(i).getActualState() == 3)
														out.println(" <span style=\"color: gray\">(Closed)</span>");
													out.println("</b></a></li>");
												}
											}
										%>
									</ul>

								</div>
								<div class="panel-footer text-center">

									<!-- <button type="button" class="btn btn-default btn-block">+ Add new group</button> -->

									<button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-default btn-block">+ Add new group</button>
									
									<!-- <div>Not a member?<a data-toggle="modal" data-target="#myModal"> Sign up</a></div> -->

									<!-- Modal -->
									<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
										<div class="modal-dialog" role="document">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
													<h3 class="modal-title text-center" id="myModalLabel">New group</h3>
												</div>
												<form method="get" action="newGroup">
													<div class="modal-body">
															<div class="input-group input-group-lg">
																<span class="input-group-addon" id="sizing-addon1">Group name</span>
																<input name="groupName" type="text" class="form-control" required aria-describedby="sizing-addon1">
															</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
														<button type="submit" class="btn btn-primary" >Create group</button>
													</div>
												</form>
											</div>
										</div>
									</div>
									
									<!-- <div class="row">
										<button type="button" class="btn col-md-12">Action</button>
									</div> -->
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<div class="col-md-12">
							<div class="panel panel-info">
								<div class="panel-heading text-center">
									<h3 style="margin-bottom: 0px;">My transactions </h3>

								</div>
								<div class="panel-body">
									<table class="table" style="margin-bottom: 0px">
										<thead>
											<tr style="font-weight:bold;">
												<td>Subject</td>
												<td>Moment</td>
												<td>Group</td>
												<td>Payer</td>
												<td>Reciever</td>
												<td>Amount</td>
											</tr>
										</thead>
										<tbody id="ProductosSelected">

											<%
												if(allMyTransactions.size()==0){
													out.println("<tr><td colspan='5'>No transactions</td></tr>");
												}else{
													for(int i=0; i<allMyTransactions.size(); i++){
														out.println("<tr>");
														out.println("<td>"+allMyTransactions.get(i).getSubject()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getMoment()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getGroupId()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getPayerUser()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getRecieverUser()+"</td>");
														out.println("<td>"+allMyTransactions.get(i).getAmount()+" €</td>");
														out.println("</tr>");
													}
												}
											%>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>

		</div>

	</body>
</html>