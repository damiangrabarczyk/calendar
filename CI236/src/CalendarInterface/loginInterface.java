package CalendarInterface;
/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("rawtypes")
public interface loginInterface extends Remote  {

	public boolean isLoginValid(String username, String password) throws RemoteException, SQLException;
	public java.util.List getFriends()throws RemoteException;
	public java.util.List getEvents(Date d)throws RemoteException, SQLException;
	public boolean addEvent(String eventdescription,String attending, String location,String date,String time)throws RemoteException, SQLException;
}
