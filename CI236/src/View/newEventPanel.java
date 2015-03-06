package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CalendarInterface.ServerConstants;
import CalendarInterface.loginInterface;
/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class newEventPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbDate = new JLabel("Date:");
	private JLabel lbTime = new JLabel("Time:");
	private JLabel lbLocation = new JLabel("Location:");
	private JLabel lbAttending = new JLabel("Attending:");
	private JLabel lbDescription = new JLabel("Description:");
	private JTextField txtDate = new JTextField();
	private JTextField txtTime = new JTextField();
	private JTextField txtLocation = new JTextField();
	private JTextField txtAttending = new JTextField();
	private JTextField txtDescription = new JTextField();
	private JButton btnSaveEvent;
	private JButton btnCancelEvent;
	
	
	private String eventdescription,attending,location,date,time;
	
	public newEventPanel() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost",ServerConstants.RMI_PORT);
		final loginInterface remote = (loginInterface) registry.lookup(ServerConstants.RMI_ID);
		
		
		Dimension size = getPreferredSize();
		size.width = 750;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("New Event"));
		setLayout(null);
		
		//set buttons
		ImageIcon save = new ImageIcon("images\\save.png");
		btnSaveEvent = new JButton(save);
		ImageIcon cancel = new ImageIcon("images\\cancel.png");
		btnCancelEvent = new JButton(cancel);
		
		//set bounds
		
		lbDate.setBounds(150,130,100,25);
		lbTime.setBounds(150,160,100,25);
		lbLocation.setBounds(150,190,100,25);
		lbAttending.setBounds(150,220,100,25);
		lbDescription.setBounds(150,250,100,25);
		
		txtDate.setBounds(225,135,120,25);
		txtTime.setBounds(225,165,120,25);
		txtLocation.setBounds(225,195,200,25);
		txtAttending.setBounds(225,225,200,25);
		txtDescription.setBounds(225,255,200,100);
		
		
		btnCancelEvent.setBounds(225, 405, 50, 25);
		btnSaveEvent.setBounds(370, 405, 50, 25);
		
		//add components to panel
		
		add(lbDate);
		add(txtDate);
		
		add(lbTime);
		add(txtTime);
		
		add(lbLocation);
		add(txtLocation);
		
		add(lbAttending);
		add(txtAttending);
		
		add(lbDescription);
		add(txtDescription);
		
		add(btnSaveEvent);
		add(btnCancelEvent);

		
		// add action to btn
		ActionListener btnAction = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == btnSaveEvent){
					
					 
					try {
						eventdescription = txtDescription.getText();
						attending = txtAttending.getText();
						location = txtLocation.getText();
						date = txtDate.getText();
						time = txtTime.getText();
						remote.addEvent(eventdescription,attending,location,date,time);//add event to the database
						createEvent(new actionEvent(this,true,"save"));
					} catch (RemoteException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}else if(e.getSource() == btnCancelEvent){
					try {
						createEvent(new actionEvent(this,true,"cancel"));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		
		btnSaveEvent.addActionListener(btnAction);
		btnCancelEvent.addActionListener(btnAction);
		
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

}

