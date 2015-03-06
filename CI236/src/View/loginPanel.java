package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

import CalendarInterface.ServerConstants;
import CalendarInterface.loginInterface;
/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class loginPanel extends JPanel {

	private EventListenerList listenerList = new EventListenerList();
	private static final long serialVersionUID = 1L;
	private JLabel lbLogin = new JLabel("Username: ");
	private JLabel lbPassword = new JLabel("Password: ");
	private JLabel lbError = new JLabel("Incorrect username and/or password");
	private JTextField txtLogin = new JTextField(10);
	private JPasswordField txtPassword = new JPasswordField();
	private JButton btnLogin = new JButton("Login");
	private JButton btnExit = new JButton("Exit");
	private String userName = new String();
	private String passWord = new String();
	public loginPanel() throws RemoteException, NotBoundException{
		//Server connection for the view
		Registry registry = LocateRegistry.getRegistry(ServerConstants.host,ServerConstants.RMI_PORT);
		final loginInterface remote = (loginInterface) registry.lookup(ServerConstants.RMI_ID);
		
		//--------------------------------------------------------------------------
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Login"));
		
		//set bounds
		lbLogin.setBounds(400,300,100,25);
		lbPassword.setBounds(400,330,100,25);
		lbPassword.getMouseMotionListeners();
		lbError.setBounds(410,270,250,25); //hidden until needed
		txtLogin.setBounds(480,300,150,25);
		txtPassword.setBounds(480,330,150,25);
	
		btnLogin.setBounds(560,365,70,20);
		btnExit.setBounds(400,365,70,20);
		
		//add components 
		add(lbLogin);
		add(lbPassword);
		add(txtLogin);
		add(txtPassword);
		add(btnLogin);
		add(btnExit);
		//----------------------------------------------------------------
		//add action listeners
		ActionListener btnAction = new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == btnLogin){
					
					System.out.println("pressed");//testing
					
					userName = txtLogin.getText();
					passWord = txtPassword.getText();
					
					try {
						if(remote.isLoginValid(userName,passWord)){
							System.out.println("Connected to server");
							createEvent(new actionEvent(this,true,"login"));
							System.out.println(userName + " " + passWord);
						}else {
							lbError.setForeground(Color.RED);
							add(lbError);
							repaint();
						}
					} catch (RemoteException | SQLException e1) {
						e1.printStackTrace();
					}
				}else if(e.getSource() == btnExit){
					System.exit(0);
				}
			}
		};

		//Adding listeners to the buttons 
		btnExit.addActionListener(btnAction);
		btnLogin.addActionListener(btnAction);
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
