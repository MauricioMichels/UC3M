package splittex.model;

import java.util.*;

public class Transaction{

	private int id;
	private String subject;
	private Date moment;
	private int groupId;
	private int payerUser;
	private int recieverUser;
	private double amount;
	
	public int getId(){
		return id;
	} 
	public void setId(int id){
		this.id = id;
	}
	public String getSubject(){
		return subject;
	} 
	public void setSubject(String subject){
		this.subject = subject;
	}
	public Date getMoment(){
		return moment;
	} 
	public void setMoment(Date moment){
		this.moment = moment;
	}
	public int getGroupId(){
		return groupId;
	} 
	public void setGroupId(int groupId){
		this.groupId = groupId;
	}
	public int getPayerUser(){
		return payerUser;
	} 
	public void setPayerUser(int payerUser){
		this.payerUser = payerUser;
	}
	public int getRecieverUser(){
		return recieverUser;
	} 
	public void setRecieverUser(int recieverUser){
		this.recieverUser = recieverUser;
	}
	public double getAmount(){
		return amount;
	} 
	public void setAmount(double amount){
		this.amount = amount;
	}
	

	public String createTransaction(Transaction transaction){

		String query = "INSERT INTO Transactions (subject, moment, groupId, payerUser, recieverUser, amount) VALUES ("+transaction.getSubject()+",NOW(),"+transaction.getGroupId()+","+transaction.getPayerUser()+","+transaction.getRecieverUser()+","+transaction.getAmount()+");";
		
		//PreparedStatement 

		return query;
	}

	public String editTransactionSubject(Transaction transaction){

		String query = "UPDATE Transactions SET groupName = "+transaction.getSubject()+" WHERE group = "+transaction.getGroupId()+";";

		return query;
	}

	public String deleteTransaction(Transaction transaction){
		
		String query="DELETE FROM Transactions WHERE subject="+transaction.getSubject()+";";

		return query;
		
	}

	public String showTransaction(Transaction transaction){
		
		String query="SELECT * FROM Transactions WHERE subject="+transaction.getSubject()+";";

		return query;
	}

	public String showTransactionsByGroup(Group group){
		
		String query="SELECT * FROM Transactions WHERE group="+group.getId()+";";

		return query;
	}

	public String showTransactionsByUser(User user){
		
		String query="SELECT TOP 10 * FROM Transactions WHERE payerUser="+user.getId()+" AND recieverUser="+user.getId()+";";

		return query;
	}

	public String showTransactionsByPayer(User user){
		
		String query="SELECT * FROM Transactions WHERE payerUser="+user.getUserName()+";";

		return query;
	}

	public String showTransactionsByReciever(User user){
		
		String query="SELECT * FROM Transactions WHERE RecieverUser="+user.getUserName()+";";

		return query;
	}

	public String showAllTransactions(){
		
		String query="SELECT * FROM Transactions;";

		return query;

	}

	public String editAmount(Transaction transaction){

		String query = "UPDATE Transactions SET amount = "+transaction.getAmount()+" WHERE subject = "+transaction.getSubject()+";";

		return query;
	}
}