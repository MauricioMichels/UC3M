package splittex.model;

import java.util.*;

public class Group{

	private int id;
	private String groupName;
    private int creator;
    private Date creationDate;
    private Date lastChangeDate;
    private int actualState;

	public int getId(){
		return id;
	} 
	public void setId(int id){
		this.id = id;
	}
	public String getGroupName(){
		return groupName;
	} 
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	public int getCreator(){
		return creator;
	} 
	public void setCreator(int creator){
		this.creator = creator;
	}
	public Date getCreationDate(){
		return creationDate;
	} 
	public void setCreationDate(Date creationDate){
		this.creationDate = creationDate;
	}
	public Date getLastChangeDate(){
		return lastChangeDate;
	} 
	public void setLastChangeDate(Date lastChangeDate){
		this.lastChangeDate = lastChangeDate;
	}
	public int getActualState(){
		return actualState;
	} 
	public void setActualState(int actualState){
		this.actualState = actualState;
	}
	

	public String createGroup(Group group){

		String query = "INSERT INTO Groups (groupName, creator, creationDate, lastChangeDate, actualState) VALUES ("+group.getGroupName()+","+group.getCreator()+",NOW(),NOW(),inactive);";
		
		//PreparedStatement 

		return query;
	}

	public String editGroupName(Group group){

		String query = "UPDATE Groups SET groupName = "+group.getGroupName()+" WHERE id = "+group.getId()+";";

		return query;
	}

	public String deleteGroup(Group group){
		
		String query="DELETE FROM Groups WHERE id="+group.getId()+";";

		return query;
		
	}
	public String showGroup(Group group){
		
		String query="SELECT * FROM Groups WHERE id="+group.getId()+";";

		return query;
	}
	public String showAllGroups(){
		
		String query="SELECT * FROM Groups;";

		return query;

	}

	public String editActualState(Group group){

		String query = "UPDATE Groups SET actualState = "+group.getActualState()+" WHERE id = "+group.getId()+";";

		return query;
	}
	
}