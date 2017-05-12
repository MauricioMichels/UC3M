<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">

		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
		<script src="js/jquery-2.1.1.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<title>Sign Up</title>
	</head>
	<body>
		<div style="height: 100%;position: absolute;width: 100%;display: flex;justify-content: center; background-color:rgba(175, 224, 87,0.8)">
			<div id="init" class="panel panel-default" style=" max-width: 300px;align-self: center;">
				<div class="panel-heading" style="text-align: center;">
					<h3><b>Sign Up</b></h3>
				</div>  
				<div class="panel-body">

					<form method="post" action="addUser">

					  	<div class="input-group">
						  <span class="input-group-addon glyphicon glyphicon-pencil" id="basic-addon1"></span>
						  <input name="username" type="text" required class="form-control" placeholder="Username" aria-describedby="basic-addon1">
					  	</div>
					  	<br>
					  	<div class="input-group">
						  <span class="input-group-addon glyphicon glyphicon-user" id="basic-addon1"></span>
						  <input name="email" type="email" required class="form-control" placeholder="e-mail" aria-describedby="basic-addon1">
						</div>
						<br>
						<div class="input-group">
						  <span class="input-group-addon glyphicon glyphicon-lock" id="basic-addon1"></span>
					    	<input name="password" type="password" required class="form-control" id="exampleInputPassword1" placeholder="Password">
						</div>
						<br>
					  <div class="form-group text-center">
						<div class="alert alert-danger" role="alert">User or e-mail already exist!</div>

						<button type="submit" class="btn btn-primary col-md-12">Sign Up
						</button>

					</div>
					</form>
						
				</div>
				
			</div>
			
		</div>
		<!-- <script type="text/javascript" src="./config.js" ></script> -->
		<!-- <script type="text/javascript" src="./app.js" ></script> -->
	</body>
</html>