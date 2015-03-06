package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class calendarView extends JFrame  {

	private static final long serialVersionUID = 1L;
	private friendPanel friendpanel;
	private currentEventPanel currentEventPanel;
	private calendarPanel calendarPanel;
	private loginPanel loginView;
	private newEventPanel newEventPanel;
	private JPanel mainpanel = new JPanel();
	public calendarView(String title) throws RemoteException, NotBoundException, SQLException{
		super(title);
		final CardLayout cardlayout = new CardLayout();
		final Container container = getContentPane();

		//Layout out of the frame
		setLayout(cardlayout);

		//Panels
		mainpanel.setLayout(new BorderLayout());
		loginView = new loginPanel();
		friendpanel = new friendPanel();
		currentEventPanel = new currentEventPanel();
		calendarPanel = new calendarPanel();
		newEventPanel = new newEventPanel();
		
		//Add components 
		mainpanel.add(calendarPanel, BorderLayout.CENTER);
		mainpanel.add(friendpanel , BorderLayout.WEST);
		mainpanel.add(currentEventPanel , BorderLayout.SOUTH);
		
		container.add(loginView);
		container.add(mainpanel);

		//Action listeners for login button
		loginView.addBtnListener(new btnListener() {
			public void actionEventOccured(actionEvent event) throws SQLException{
				if(event.getClicked()){
					
					cardlayout.next(container);
					calendarPanel.setVisible(true);
					try {
						friendpanel.populate();
						System.out.println("passed point 1");
						
						currentEventPanel.getCurrentEvent();
						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					friendpanel.refresh();
					currentEventPanel.refresh();
				}
			}
		});
		//Action listeners for log out button
		currentEventPanel.addBtnListener(new btnListener(){
			public void actionEventOccured(actionEvent event){
				if(event.getClicked()){
				cardlayout.previous(container);
				newEventPanel.setVisible(false);
			
				}
			}
		});
		//Action listeners for new event button
		currentEventPanel.addBtnListener(new btnListener(){
			public void actionEventOccured(actionEvent event){
				if(event.getNewEventClicked()){
					System.out.println("pressed");
					calendarPanel.setVisible(false);
					mainpanel.add(newEventPanel);
					newEventPanel.setVisible(true);
				}
			}
		});
		//Action listeners for save button
		newEventPanel.addBtnListener(new btnListener(){
			public void actionEventOccured(actionEvent event){
				if(event.getSaveClicked()){
					System.out.println("Savepressed");
					calendarPanel.setVisible(true);
					newEventPanel.setVisible(false);
				}
			}
		});
		//Action listeners for cancel button
		newEventPanel.addBtnListener(new btnListener(){
			public void actionEventOccured(actionEvent event){
				if(event.getCancelClicked()){
					System.out.println("Cancelpressed");
					calendarPanel.setVisible(true);
					newEventPanel.setVisible(false);
				}
			}
		});
		//action for mouse testing -----------------------------
		calendarPanel.addBtnListener(new btnListener(){
			public void actionEventOccured(actionEvent event){
				if(event.getMousePressed()){
					currentEventPanel.refresh();
				}
			}
		});
		

	}

}

