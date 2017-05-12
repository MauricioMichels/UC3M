package splittex.model;

import java.util.*;

public class User{

	private int id;
	private String userName;
	private String email;
	private String password;

	public int getId(){
		return id;
	} 
	public void setId(int id){
		this.id = id;
	}
	public String getUserName(){
		return userName;
	} 
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getEmail(){
		return email;
	} 
	public void setEmail(String email){
		this.email = email;
	}
	public String getPassword(){
		return password;
	} 
	public void setPassword(String contraseña){
		this.password = password;
	}

	public String createUser(User user){

		String query = "INSERT INTO Users (userName, email, password) VALUES (?,?,?);";
		
		//PreparedStatement 

		return query;
	}

	public String editUser(User user){

		String query = "UPDATE Users SET userName = "+user.getUserName()+", email = "+user.getEmail()+", password = "+user.getPassword()+";";

		return query;
	}

	public String deleteUser(User user){
		
		String query="DELETE FROM Users WHERE userName="+user.getUserName()+";";

		return query;
		
	}
	public String showUser(User user){
		
		String query="SELECT * FROM Users WHERE userName="+user.getUserName()+";";

		return query;
	}
	public String showAllUsers(){
		
		String query="SELECT * FROM Users;";

		return query;

	}

	public String checkUser(User user){

		String query="SELECT userName,password FROM Users WHERE userName="+user.getUserName()+" AND password="+user.getPassword()+";";

		return query;

	}
	
}