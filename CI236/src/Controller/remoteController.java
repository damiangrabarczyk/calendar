package Controller;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import CalendarInterface.loginInterface;



public class remoteController extends UnicastRemoteObject implements loginInterface{

	private static final long serialVersionUID = 1L;
	public String user_name;
	public String pass_word;
	public int eventid=0;
	public remoteController() throws RemoteException {
		super();

	}

	public int eventID(String desc) throws SQLException, ClassNotFoundException{
		int res=0;
		Connection con = null;
		Statement stmt = null;

		System.out.println("loading the jdbc driver");
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con = DriverManager.getConnection("jdbc:odbc:CI236");
		System.out.println("connected to the database 2");
		String SQL_search = "SELECT eventid FROM events WHERE eventdescription = '" + desc+"'";
		stmt =	con.createStatement(); 
		stmt.executeQuery(SQL_search);
		res = Integer.parseInt(SQL_search);
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean isLoginValid(String username, String password) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		this.user_name = username;
		this.pass_word = password;

		List chkUserName = new ArrayList();
		List chkUPassWord = new ArrayList();

		try{
			// establish connection.
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database");

			String SQL_search = "SELECT * from memberlogin";
			stmt =	con.createStatement(); 
			rs = stmt.executeQuery(SQL_search);
			System.out.println(user_name + " " + pass_word);
			while(rs.next()){
				chkUserName.add( rs.getString(1));
				chkUPassWord.add(rs.getString(3));
				System.out.println(user_name + " and " + pass_word);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		for(int i=0; i<chkUserName.size();i++){
			if(user_name.equals(chkUserName.get(i)) && pass_word.equals(chkUPassWord.get(i))){
				return true;
			}
		}
		return false;
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFriends() throws RemoteException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List fList = new ArrayList();
		try{
			// establish connection.
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database 2");

			String SQL_search = "select  firstname from member inner join memberfriendships on member.memberid = memberfriendships.friendid where memberfriendships.memberid = (select memberid from memberlogin where loginid = '" + user_name + "' )";
			stmt =	con.createStatement(); 
			rs = stmt.executeQuery(SQL_search);

			while(rs.next()){
				fList.add(rs.getString(1));
			}
			for(int i=0; i<fList.size(); i++){
				System.out.println(fList.get(i));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return fList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getFriends(int userid) throws RemoteException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List fList = new ArrayList();
		try{
			// establish connection.
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database 2");

			String SQL_search = "select  firstname from member inner join memberfriendships on member.memberid = memberfriendships.friendid where memberfriendships.memberid = (select memberid from memberlogin where loginid = '" + user_name + "' )";
			stmt =	con.createStatement(); 
			rs = stmt.executeQuery(SQL_search);

			while(rs.next()){
				fList.add(rs.getString(1));
			}
			for(int i=0; i<fList.size(); i++){
				System.out.println(fList.get(i));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return fList;
	}



	public boolean addFriend(int userid, int friendid){
		Connection con = null;
		Statement stmt = null;
		@SuppressWarnings("unused")
		ResultSet rs   = null;
		try{
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database 2");

			String SQL_search = "INSERT INTO memberfriendships VALUES ('" + userid + "','" + friendid + "')";
			stmt =	con.createStatement(); 
			stmt.executeUpdate(SQL_search);
			return true;
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public boolean addEvent(String eventdescription, String attending,
			String location, String date, String time) throws RemoteException, SQLException {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs  = null;
		int memberid = 0;

		try{
			// establish connection.
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database addevent");

			//insert event into events table
			String SQL_search = "insert into events(eventdescription,attending,location,date_of_event,time_of_event) values ('" + eventdescription + "','" + attending + "','"+location+"','"+date+"','"+time+"')";
			stmt =	con.createStatement(); 
			stmt.executeUpdate(SQL_search);
			//get memberid
			String SQL_memberid = "SELECT memberid from memberlogin where loginid = '"+user_name+"'";
			stmt =	con.createStatement(); 
			rs = stmt.executeQuery(SQL_memberid);
			if(rs.next()){
				memberid = rs.getInt(1);
				System.out.println(memberid);
			}

			//get last added eventid
			String SQL_add = "SELECT TOP 1 eventid FROM events ORDER BY eventid desc";
			stmt =	con.createStatement(); 
			rs = stmt.executeQuery(SQL_add);
			if(rs.next()){
				eventid = rs.getInt(1);
			}

			//insert into memberevent
			String SQL_member = "insert into memberevents(memberid,eventid) values ('" + memberid + "','" + eventid + "')";
			stmt =	con.createStatement(); 
			stmt.executeUpdate(SQL_member);
			return true;

		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean getNextEvent(){
		Connection con = null;
		Statement stmt = null;

		try{
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database next event");

			String SQL_search = "SELECT  * FROM events WHERE eventid = '" + eventid + "'";
			stmt =	con.createStatement(); 
			stmt.executeQuery(SQL_search);

			eventid++; // update current eventid
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//test --------------------------------------------
	public boolean getEvent(int evID){
		Connection con = null;
		Statement stmt = null;

		try{
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database get event");
			String SQL_search = "SELECT  * FROM events WHERE eventid = '" + evID + "'";
			stmt =	con.createStatement(); 
			stmt.executeQuery(SQL_search);
			eventid = evID; // update current eventid
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public int numRecords(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		int res = 0;
		try{
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database - count");

			String SQL_search = "SELECT COUNT(eventid) AS NumEvent FROM events";
			stmt =	con.createStatement(); 
			rs = stmt.executeQuery(SQL_search);
			if(rs.next()){
				res = rs.getInt(1);
			}

		} catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}

	public int numFriends(int userID) {
		Connection con = null;
		Statement stmt = null;
		int res = 0;
		try{
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database 2");
			String SQL_search = "SELECT COUNT(friendid) AS NumFriends FROM memberfriendship WHERE memberid = '" + userID + "'";
			stmt =	con.createStatement(); 
			stmt.executeQuery(SQL_search);
			res = Integer.parseInt(SQL_search);
		} catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}


	public boolean editEvent(int eventid, String desc, String attend, String room, String date, String time) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		try{
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database 2");

			if ( desc.equals("")){ // Checking for blank description
				String SQL_search = "SELECT eventdescription FROM events WHERE eventid = '" + eventid + "'";
				stmt =	con.createStatement(); 
				rs = stmt.executeQuery(SQL_search);
				desc = rs.toString();
			} else if ( attend.equals("")){
				String SQL_search = "SELECT attending FROM events WHERE eventid = '" + eventid + "'";
				stmt =	con.createStatement(); 
				rs = stmt.executeQuery(SQL_search);
				attend = rs.toString();
			} else if ( room.equals("")){
				String SQL_search = "SELECT room FROM events WHERE eventid = '" + eventid + "'";
				stmt =	con.createStatement(); 
				rs = stmt.executeQuery(SQL_search);
				room = rs.toString();
			} else if (date.equals("")){
				String SQL_search = "SELECT date FROM events WHERE eventid = '" + eventid + "'";
				stmt =	con.createStatement(); 
				rs = stmt.executeQuery(SQL_search);
				date = rs.toString();
			} else if (time.equals("")){
				String SQL_search = "SELECT time FROM events WHERE eventid = '" + eventid + "'";
				stmt =	con.createStatement(); 
				rs = stmt.executeQuery(SQL_search);
				time = rs.toString();
			}

			String SQL_search = "UPDATE events SET eventdescription = '" + desc + "', attending = '" + attend + "', room = '" + room + "', date = '" + date + "', time = '" + time + "' WHERE eventid = '" + eventid + "'";
			stmt =	con.createStatement(); 
			stmt.executeQuery(SQL_search);
			return true;

		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	
	//
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEvents(Date d) throws RemoteException {
		Date selectedDate = null;
		selectedDate = d;

		//db connection
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		int memberid = 0;
		List eventsList = new ArrayList();

		try{
			// establish connection.
			System.out.println("loading the jdbc driver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:CI236");
			System.out.println("connected to the database - getting events");

			//query to get member ID 
			String SQL_memberid = "SELECT memberid from memberlogin where loginid = '"+user_name+"'";
			stmt =	con.createStatement(); 
			rs = stmt.executeQuery(SQL_memberid);
			if(rs.next()){
				memberid = rs.getInt(1);
				System.out.println(memberid);
			}
			//----------------------------------------------------------------------------------------------------

			//query to get events
			String SQL_search = "select * from events inner join memberevents on memberevents.eventid = events.eventid where memberevents.memberid = '"+memberid+"' and  events.date_of_event = '" + selectedDate + "'";
			stmt =	con.createStatement(); 
			rs = stmt.executeQuery(SQL_search);

			while(rs.next()){

				eventsList.add((String) rs.getString(2) + "     "
						+ rs.getString(3) + "     "
						+ rs.getString(4) + "     "
						+ rs.getString(5) +  "     "
						+ rs.getString(6) );
			}
			for(int i=0; i<eventsList.size(); i++){
				System.out.println(eventsList.get(i));
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		return eventsList;
	}

	public void DisplayEventsOnDay(int selectedDay, int selectedMonth, int selectedYear) {

	}



}

