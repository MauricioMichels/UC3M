
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
		
		<title>SPLITTEX: Log In</title>
	</head>
	<body>
		<div style="height: 100%;position: absolute;width: 100%;display: flex;justify-content: center; background-color:rgba(175, 224, 87,0.8)">
			<div id="init" class="panel panel-default" style=" max-width: 300px;align-self: center;">
				<div class="panel-heading" style="text-align: center;">
					<h3><b>Log In</b></h3>
				</div>	
				<div class="panel-body">

					<form method="post" action="register">

						<div class="input-group">
							<span class="input-group-addon glyphicon glyphicon-user" id="basic-addon1"></span>
							<input name="username" required type="text" class="form-control" placeholder="Username" aria-describedby="basic-addon1">
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon glyphicon glyphicon-lock" id="basic-addon1"></span>
							<input name="password" required type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
						</div>
						<br>


					
						<div class="form-group text-center">
						<button type="submit" class="btn btn-primary col-md-12">Log In</button>
						<br>
						<p style="padding-top: 30px;font-size: 11px;">Not a member?<a href="signUp"> Click here</a></p>
					</div>
					</form>
						
				</div>
				
			</div>

		</div>		
				
	</body>

</html>