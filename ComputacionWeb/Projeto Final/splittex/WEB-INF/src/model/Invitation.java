package splittex.model;

import java.util.*;

public class Invitation{


	private int userId;
	private int groupId;
	private int responseState;
	private Date invitationDate;
	private Date responseDate;
	
	
	public int getUserId(){
		return userId;
	} 
	public void setUserId(int userId){
		this.userId = userId;
	}
	public int getGroupId(){
		return groupId;
	} 
	public void setGroupId(int groupId){
		this.groupId = groupId;
	}
	public int getResponseState(){
		return responseState;
	} 
	public void setResponseState(int responseState){
		this.responseState = responseState;
	}
	public Date getInvitationDate(){
		return invitationDate;
	} 
	public void setInvitationDate(Date invitationDate){
		this.invitationDate = invitationDate;
	}
	public Date getResponseDate(){
		return responseDate;
	} 
	public void setResponseDate(Date responseDate){
		this.responseDate = responseDate;
	}

	public String createInvitation(Invitation invitation){

		String query = "INSERT INTO Invitations (userId, groupId, responseState, invitationDate, responseDate) VALUES ("+invitation.getUserId()+","+invitation.getGroupId()+",noResponse,NOW(),NOW());";
		
		return query;
	}

	public String answerInvitation(String answer, Invitation invitation){

		String query = "UPDATE Invitations SET responseState = "+answer+" WHERE userId="+invitation.getUserId()+" AND groupId="+invitation.getGroupId()+";";
		
		return query;
	}
}