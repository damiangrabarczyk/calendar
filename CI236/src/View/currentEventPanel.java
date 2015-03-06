package View;

//import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Dimension;
//import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
//import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
//import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
//import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import CalendarInterface.ServerConstants;
import CalendarInterface.loginInterface;
/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class currentEventPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnNewEvent = new JButton("New Event");
	private JButton btnDelete = new JButton("Edit Event");
	private JButton btnEdit = new JButton("Delete");
	private JButton btnLogOut = new JButton("Log out");
	public JScrollPane contentPane = new JScrollPane();
	@SuppressWarnings("rawtypes")
	public JList currentEventList = new JList();
	
	public currentEventPanel() throws RemoteException, NotBoundException, SQLException{
		Dimension size = getPreferredSize();
		size.height = 200;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Current Events"));
		setLayout(null);
		
		//Set Bounds
		contentPane.setBounds(10, 25, 800, 150);
		currentEventList.setBounds(2, 2, 780, 146);
		btnNewEvent.setBounds(850, 25, 100, 25);
		btnDelete.setBounds(850, 55, 100, 25);
		btnEdit.setBounds(850, 85, 100, 25);
		btnLogOut.setBounds(850,150, 100, 25);
		
		contentPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Add components to the pane
		add(contentPane);
		add(btnNewEvent);
		add(btnEdit);
		add(btnDelete);
		add(btnLogOut);
		// current date test
		String currentdate = null;
		//Get current date
		GregorianCalendar cal = new GregorianCalendar(); // Create calendar
		int realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
		int realMonth = cal.get(GregorianCalendar.MONTH)+ 1; // Get month
		int realYear = cal.get(GregorianCalendar.YEAR); // Get year
		 
		//String builder
		currentdate = realYear + "-" +  realMonth  + "-" + realDay;
		System.out.println(currentdate);
		
		
		currentEventList.setVisibleRowCount(1000);
		currentEventList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		contentPane.add(currentEventList);
		
		//Logout Action Listener
		ActionListener btnAction = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == btnLogOut){
					try {
						createEvent(new actionEvent(this,true,"logout"));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(e.getSource() == btnNewEvent){
					try {
						createEvent(new actionEvent(this,true,"new"));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		
		
		btnLogOut.addActionListener(btnAction);
		btnNewEvent.addActionListener(btnAction);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getCurrentEvent() throws RemoteException, NotBoundException, SQLException{
		Registry registry = LocateRegistry.getRegistry("localhost",ServerConstants.RMI_PORT);
		final loginInterface remote = (loginInterface) registry.lookup(ServerConstants.RMI_ID);
					
		
		//Get current date
		Date currentdate = null;
		GregorianCalendar cal = new GregorianCalendar(); // Create calendar
		int realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
		int realMonth = cal.get(GregorianCalendar.MONTH) ; // Get month
		int realYear = cal.get(GregorianCalendar.YEAR); // Get year
		currentdate = new Date(realYear - 1900, realMonth, realDay);
		System.out.println(currentdate + "testing date");
		
		
		//setting up current events
				
		List eventList = new ArrayList();
		eventList = remote.getEvents(currentdate);
		DefaultListModel model = new DefaultListModel();
		
		for(int i=0; i<eventList.size(); i++){
			model.addElement(eventList.get(i));
			System.out.println(eventList.get(i) + " event(s) found ");
		}
		
		currentEventList.setModel(model);
		currentEventList.setVisible(true);
		currentEventList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

	}


	public void currentEventView(){
		setLocale(Locale.UK);
        setLayout(null);
        
	}
	
	public void createEvent(actionEvent event) throws SQLException{
		Object[] listeners = listenerList.getListenerList();
		for(int i=0; i <listeners.length; i +=2){
			if(listeners[i] == btnListener.class ){
				((btnListener) listeners[i+1]).actionEventOccured(event);
			}
		}
	}
	
	public void addBtnListener(btnListener listener){
		listenerList.add(btnListener.class, listener);
	}
	public void refresh(){
		this.revalidate();
		this.repaint();
	}

}
