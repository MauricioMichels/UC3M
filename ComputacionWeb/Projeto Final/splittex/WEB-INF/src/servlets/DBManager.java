package splittex.servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import splittex.model.*;


public class DBManager implements AutoCloseable {

    private Connection connection;

    public DBManager() throws NamingException, SQLException {
        connect();
    }
    
  /**
     * Open the connection to the database.
     *
     */
    private void connect() throws NamingException, SQLException {
        // TODO: program this method
	    
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jdbc/Splittex");
        connection = ds.getConnection();
    }

    /**
     * Close the connection to the database if it is still open.
     *
     */
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }


    //Añade un new user
    public User addUser(String jspUserName, String jspEmail, String jspPassword) throws SQLException {
   
        User user = new User();

        Statement stmt = null;
            
        String query1 = "INSERT INTO Users (userName, email, password) VALUES (?,?,?);";
           
        try (PreparedStatement st = connection.prepareStatement(query1)) {
            
            st.setString(1,jspUserName);
            st.setString(2,jspEmail);
            st.setString(3,jspPassword);
            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        user.setUserName(jspUserName);
        user.setEmail(jspEmail);
        user.setPassword(jspPassword);
     
        return user;
    }

    //Añade un new user
    public int checkUser(String jspUserName, String jspEmail) throws SQLException {
   
        int user = 0;

        Statement stmt = null;
            
        String query1 = "SELECT userName, email FROM Users WHERE userName=? OR email=?;";
           
        try (PreparedStatement st = connection.prepareStatement(query1)) {
            
            st.setString(1,jspUserName);
            st.setString(2,jspEmail);
            
            ResultSet rs = st.executeQuery () ; 

            if (rs.next())  
                user=1;
                           
           
        }catch (SQLException e) {
            e.printStackTrace();
        }
     
        return user;
    }

    //Comprueba el log in
    public User register(String jspUserName, String jspPassword) throws SQLException {
   
        User user = new User();

        Statement stmt = null;
            
        String query = "SELECT * FROM Users WHERE userName = ? AND password = ?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setString(1,jspUserName);
            st.setString(2,jspPassword);

            ResultSet rs = st.executeQuery () ;  

            if (rs.next()) {
                
                user.setId(rs.getInt("id"));            
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                                       
            }else{
                System.out.println("nada");
                user = null;

            }
           
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }

    //Comprueba con el groupName todo el grupo
    public Group whichGroup(int jspGroupId) throws SQLException {
   
        Group group = new Group();

        Statement stmt = null;
            
        String query = "SELECT * FROM Groups WHERE id = ?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspGroupId);

            ResultSet rs = st.executeQuery () ;  

            if (rs.next()) {
                
                group.setId(rs.getInt("id"));            
                group.setGroupName(rs.getString("groupName"));
                group.setCreator(rs.getInt("creator"));
                group.setCreationDate(rs.getDate("creationDate"));
                group.setLastChangeDate(rs.getDate("lastChangeDate"));
                group.setActualState(rs.getInt("actualState"));
                              
            }else{
                System.out.println("nada");

            }
           
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return group;
    }

    //Comprueba con el groupName todo el grupo
    public User whichUser(String jspEmail) throws SQLException {
   
        User user = new User();

        Statement stmt = null;
            
        String query = "SELECT * FROM Users WHERE email = ?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setString(1,jspEmail);

            ResultSet rs = st.executeQuery () ;  

            if (rs.next()) {
                
                user.setId(rs.getInt("id"));            
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                
                              
            }else{
                System.out.println("nada");

            }
           
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }

    //Añade un user al grupo
    public User addUserInGroup(int jspUserId, int jspGroupId) throws SQLException {
   
        User user = new User();

        Statement stmt = null;
            
        String query1 = "INSERT INTO GroupUser (userId, groupId, state, balance) VALUES (?,?,1,0);";
           
        try (PreparedStatement st = connection.prepareStatement(query1)) {
            
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        user.setId(jspUserId);

        String query2 = "INSERT INTO Invitations (userId, groupId, responseState, invitationDate) VALUES (?,?,1,NOW());";
           
        try (PreparedStatement st = connection.prepareStatement(query2)) {
            
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }
     
        return user;
    }

    //Comprueba el las invitations de cada user
    public List<Invitation> userInivitations(int jspUserId) throws SQLException {
   
        ArrayList<Invitation> invitationList = new ArrayList<Invitation>();

        Statement stmt = null;
            
        String query = "SELECT userId, groupId, invitationDate FROM Invitations WHERE responseState=1 AND userId=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspUserId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                Invitation invitation = new Invitation();
                invitation.setUserId(rs.getInt("userId"));
                invitation.setGroupId(rs.getInt("groupId"));
                invitation.setInvitationDate(rs.getDate("invitationDate"));
                invitationList.add(invitation);  
                                       
            }
        }
        return invitationList;
    }
    //Comprueba el las invitations de cada group
    public List<Invitation> groupInivitations(int jspGroupId) throws SQLException {
   
        ArrayList<Invitation> invitationList = new ArrayList<Invitation>();

        Statement stmt = null;
            
        String query = "SELECT userId, groupId, responseState FROM Invitations WHERE groupId=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspGroupId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                Invitation invitation = new Invitation();
                invitation.setUserId(rs.getInt("userId"));
                invitation.setGroupId(rs.getInt("groupId"));
                invitation.setResponseState(rs.getInt("responseState"));
                invitationList.add(invitation);  
                                       
            }
        }
        return invitationList;
    }

    //Comprueba el los groups de cada user
    public List<Group> userGroups(int jspUserId) throws SQLException {
   
        ArrayList<Group> groupList = new ArrayList<Group>();

        Statement stmt = null;
            
        String query = "SELECT id, groupName, actualState, balance FROM Groups INNER JOIN GroupUser ON id=groupId WHERE userId=? AND state=2;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspUserId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setGroupName(rs.getString("groupName"));
                group.setActualState(rs.getInt("actualState"));
                groupList.add(group);  
                                       
            }
        }
        return groupList;
    }

    //Comprueba el los users de cada group
    public List<User> groupUsers(int jspGroupId) throws SQLException {
   
        ArrayList<User> userList = new ArrayList<User>();

        Statement stmt = null;
            
        String query = "SELECT id, userName, email FROM Users INNER JOIN GroupUser ON id=userId WHERE groupId=? AND state=2;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspGroupId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("email"));
                userList.add(user);  
                                       
            }
        }
        return userList;
    }

    //Comprueba el las transactions de cada user
    public List<Transaction> userTransactions(int jspUserId) throws SQLException {
   
        ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();

        Statement stmt = null;
            
        String query = "SELECT subject, moment, groupId, payerUser, recieverUser, amount FROM Transactions INNER JOIN Users AS u ON u.id=payerUser OR u.id=recieverUser WHERE u.id=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspUserId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                Transaction transaction = new Transaction();
                transaction.setSubject(rs.getString("subject"));
                transaction.setMoment(rs.getDate("moment"));
                transaction.setGroupId(rs.getInt("groupId"));
                transaction.setPayerUser(rs.getInt("payerUser"));
                transaction.setRecieverUser(rs.getInt("recieverUser"));
                transaction.setAmount(rs.getDouble("amount"));
                transactionsList.add(transaction);  
                                       
            }
        }
        return transactionsList;
    }

    //Comprueba el las transactions de cada user
    public List<Transaction> groupTransactions(int jspGroupId) throws SQLException {
   
        ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();

        Statement stmt = null;
            
        String query = "SELECT subject, moment, groupId, payerUser, recieverUser, amount FROM Transactions WHERE groupId=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspGroupId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                Transaction transaction = new Transaction();
                transaction.setSubject(rs.getString("subject"));
                transaction.setMoment(rs.getDate("moment"));
                transaction.setGroupId(rs.getInt("groupId"));
                transaction.setPayerUser(rs.getInt("payerUser"));
                transaction.setRecieverUser(rs.getInt("recieverUser"));
                transaction.setAmount(rs.getDouble("amount"));
                transactionsList.add(transaction);  
                                       
            }
        }
        return transactionsList;
    }

    //Comprueba el 'I OWE' de cada user
    public double userToPay(int jspUserId) throws SQLException {
   
        double toPay = 0;

        Statement stmt = null;
            
        String query = "SELECT amount FROM Transactions WHERE recieverUser=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspUserId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                toPay += rs.getDouble("amount");
                                       
            }
        }
        return toPay;
    }

    //Comprueba el 'THEY OWE ME' de cada user
    public double userToRecieve(int jspUserId) throws SQLException {
   
        double toRecieve = 0;

        Statement stmt = null;
            
        String query = "SELECT amount FROM Transactions WHERE payerUser=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspUserId);
            
            ResultSet rs = st.executeQuery ();  

            while (rs.next()) {
                
                toRecieve += rs.getDouble("amount");
                                       
            }
        }
        return toRecieve;
    }


    //Añade un nuevo grupo
    public Group newGroup(String jspGroupName, int jspCreator) throws SQLException {
   
        Group group = new Group();

        Statement stmt = null;
            
        String query1 = "INSERT INTO Groups (groupName, creator, creationDate, lastChangeDate, actualState) VALUES (?,?,NOW(),NOW(),1);";
           
        try (PreparedStatement st = connection.prepareStatement(query1)) {
            
            st.setString(1,jspGroupName);
            st.setInt(2,jspCreator);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        group.setGroupName(jspGroupName);
        group.setCreator(jspCreator);
        group.setActualState(1);

        String query2 = "SELECT id FROM Groups WHERE groupName=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query2)) {
           
            st.setString(1,jspGroupName);
            
            ResultSet rs = st.executeQuery ();  

            if(rs.next()) {
                
                group.setId(rs.getInt("id"));
                                       
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        String query3 = "INSERT INTO GroupUser (userId, groupId, state, balance) VALUES (?,?,2,0);";
        
        try (PreparedStatement st = connection.prepareStatement(query3)) {
            
            st.setInt(1,jspCreator);
            st.setInt(2,group.getId());

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        String query4 = "INSERT INTO Invitations (userId, groupId, responseState, invitationDate) VALUES (?,?,2,NOW());";
        
        try (PreparedStatement st = connection.prepareStatement(query4)) {
            
            st.setInt(1,jspCreator);
            st.setInt(2,group.getId());

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }


        return group;
    }

    //Añade un nuevo grupo
    public Transaction newTransaction(String jspTransactionSubject, int jspGroupId, int jspPayer, int jspReciever, double amount) throws SQLException {
   
        Transaction transaction = new Transaction();

        Statement stmt = null;
            
        String query1 = "INSERT INTO Transactions (subject, moment, groupId, payerUser, recieverUser, amount) VALUES (?,NOW(),?,?,?,?);";
           
        try (PreparedStatement st = connection.prepareStatement(query1)) {
            
            st.setString(1,jspTransactionSubject);
            st.setInt(2,jspGroupId);
            st.setInt(3,jspPayer);
            st.setInt(4,jspReciever);
            st.setDouble(5,amount);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        transaction.setSubject(jspTransactionSubject);
        transaction.setPayerUser(jspPayer);
        transaction.setRecieverUser(jspReciever);
        transaction.setAmount(amount);


        String query2 = "UPDATE GroupUser SET balance=balance+? WHERE userId=? AND groupId=?;";
           
        try (PreparedStatement st = connection.prepareStatement(query2)) {
            
            st.setDouble(1,amount);
            st.setInt(2,jspPayer);
            st.setInt(3,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        String query3 = "UPDATE GroupUser SET balance=balance-? WHERE userId=? AND groupId=?;";
           
        try (PreparedStatement st = connection.prepareStatement(query3)) {
            
            st.setDouble(1,amount);
            st.setInt(2,jspReciever);
            st.setInt(3,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }


        return transaction;
    }


    public int userNameToId(String userName)throws SQLException{

        int userNameId = 0;

        Statement stmt = null;
            
        String query = "SELECT id FROM Users WHERE userName=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setString(1,userName);
            
            ResultSet rs = st.executeQuery ();  

            if (rs.next()) {
                
                userNameId = rs.getInt("id");
                                       
            }
        }
        return userNameId;
    }

     //Acepta la invitacion para un grupo
    public Invitation acceptInvitation(int jspUserId, int jspGroupId) throws SQLException {
   
        Invitation invitation = new Invitation();

        Statement stmt = null;
            
        String query1 = "UPDATE Invitations SET responseState=2, responseDate=NOW() WHERE userId=? AND groupId=?;";
           
        try (PreparedStatement st = connection.prepareStatement(query1)) {
            
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        invitation.setUserId(jspUserId);
        invitation.setGroupId(jspGroupId);

        String query2 = "UPDATE GroupUser SET state=2 WHERE userId=? AND groupId=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query2)) {
           
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);
            
            st.executeUpdate () ; 

        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return invitation;
    }

    //Reject la invitacion para un grupo
    public Invitation rejectInvitation(int jspUserId, int jspGroupId) throws SQLException {
   
        Invitation invitation = new Invitation();

        Statement stmt = null;
            
        String query1 = "UPDATE Invitations SET responseState=3, responseDate=NOW() WHERE userId=? AND groupId=?;";
           
        try (PreparedStatement st = connection.prepareStatement(query1)) {
            
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        invitation.setUserId(jspUserId);
        invitation.setGroupId(jspGroupId);

        String query2 = "UPDATE GroupUser SET state=3 WHERE userId=? AND groupId=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query2)) {
           
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);
            
            st.executeUpdate () ; 

        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return invitation;
    }

    //cierra un grupo
    public Group closeGroup(int jspGroupId) throws SQLException {
   
        Group group = new Group();

        Statement stmt = null;
            
        String query = "UPDATE Groups SET actualState=3 WHERE id=?;";
           
        try (PreparedStatement st = connection.prepareStatement(query)) {
            
            st.setInt(1,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        group.setId(jspGroupId);
        group.setActualState(3);
        
        return group;
    }

    //activa un grupo
    public Group activateGroup(int jspGroupId) throws SQLException {
   
        Group group = new Group();

        Statement stmt = null;
            
        String query1 = "UPDATE Groups SET actualState=2 WHERE id=?;";
           
        try (PreparedStatement st = connection.prepareStatement(query1)) {
            
            st.setInt(1,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        String query2 = "UPDATE Invitations SET responseState=3, responseDate=NOW() WHERE groupId=? AND responseState=1;";
           
        try (PreparedStatement st = connection.prepareStatement(query2)) {
            
            st.setInt(1,jspGroupId);

            st.executeUpdate () ;  
           
        }catch (SQLException e) {
            e.printStackTrace();
        }

        group.setId(jspGroupId);
        group.setActualState(2);
        
        return group;
    }

    public List<Transaction> oweMe(int jspUserId, int jspGroupId)throws SQLException{

        ArrayList<Transaction> theyOweMeTransactionList = new ArrayList<Transaction>();

        Statement stmt = null;
            
        String query = "SELECT recieverUser, SUM(amount) FROM Transactions WHERE payerUser=? AND groupId=? GROUP BY recieverUser;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                Transaction transaction = new Transaction();
                transaction.setRecieverUser(rs.getInt("recieverUser"));
                transaction.setAmount(rs.getDouble("SUM(amount)"));
                theyOweMeTransactionList.add(transaction);  
            }
        }
        return theyOweMeTransactionList;
    }

    public List<Transaction> iOwe(int jspUserId, int jspGroupId)throws SQLException{

        ArrayList<Transaction> iOweTransactionList = new ArrayList<Transaction>();

        Statement stmt = null;
            
        String query = "SELECT payerUser, SUM(amount) FROM Transactions WHERE recieverUser=? AND groupId=? GROUP BY payerUser;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);
            
            ResultSet rs = st.executeQuery () ;  

            while (rs.next()) {
                
                Transaction transaction = new Transaction();
                transaction.setPayerUser(rs.getInt("payerUser"));
                transaction.setAmount(rs.getDouble("SUM(amount)"));
                iOweTransactionList.add(transaction);  
            }
        }
        return iOweTransactionList;
    }

    public double totalBalance(int jspUserId, int jspGroupId)throws SQLException{

        double totalBalance = 0;

        Statement stmt = null;
            
        String query = "SELECT SUM(balance) FROM GroupUser WHERE userId=? AND groupId=?;";
                
        try (PreparedStatement st = connection.prepareStatement(query)) {
           
            st.setInt(1,jspUserId);
            st.setInt(2,jspGroupId);
            
            ResultSet rs = st.executeQuery () ;  

            if (rs.next())
                
                totalBalance = rs.getDouble("SUM(balance)");
                
        }
        return totalBalance;
    }

}




// select recieverUser, sum(amount) from Transactions where groupId=2 and payerUser=5 group by recieverUser