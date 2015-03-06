package View;

import java.awt.Dimension;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.GregorianCalendar;
import java.util.List;


//import javax.management.modelmbean.ModelMBean;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import CalendarInterface.ServerConstants;
import CalendarInterface.loginInterface;

/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class friendPanel extends JPanel{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public JTabbedPane tb = new JTabbedPane();
	public JScrollPane searchpanel;
	public JScrollPane friendPane;
	@SuppressWarnings("rawtypes")
	public JList friendList;
	private JButton btnSearch;
	private JTextField txtSearch;
	private JLabel lbltest;
	

	

	@SuppressWarnings({ "rawtypes" })
	public friendPanel() throws RemoteException, NotBoundException, SQLException{
		/*Registry registry = LocateRegistry.getRegistry("localhost",ServerConstants.RMI_PORT);
		final loginInterface remote = (loginInterface) registry.lookup(ServerConstants.RMI_ID);*/
		//dimensions and layout of the panel
		Dimension size = getPreferredSize();
		size.width = 250;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Friends"));
		setLayout(null);

		tb = new JTabbedPane();
		searchpanel = new JScrollPane();
		friendPane = new JScrollPane();
		friendList = new JList();
		
		//btnSearch = new JButton("Search");
		ImageIcon search = new ImageIcon("images\\newSearchIcon.png");
		btnSearch = new JButton(search);
		txtSearch = new JTextField();
		lbltest = new JLabel("List of results");
		
		btnSearch.setHorizontalAlignment(SwingConstants.CENTER);		
		
		//Setting bounds of components 
		tb.setBounds(25,50 , 200, 400);
		searchpanel.setBounds(25, 50, 200, 400);
		friendPane.setBounds(25, 50, 200, 400);
		friendList.setBounds(0, 0, 200, 400);
		btnSearch.setBounds(177, 465, 45, 24);
		txtSearch.setBounds(25, 465, 155, 25);
		
		
		
		
		//Setting up components of friendpane

		friendList.setVisibleRowCount(1000);
		friendList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		friendPane.add(friendList);
		
		
		//Setting up components of Search panel
		searchpanel.setLayout(null);
		lbltest.setBounds(0,25,100,50);
		searchpanel.add(lbltest);
		
		
		
		//Adding panels to the tabbed panel
		tb.add("Friend list",friendPane );
		tb.add("search",searchpanel );
		
		
		
		//add components to the main panel
		add(tb);
		add(btnSearch);
		add(txtSearch);

	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void populate() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost",ServerConstants.RMI_PORT);
		final loginInterface remote = (loginInterface) registry.lookup(ServerConstants.RMI_ID);
		
		List flist = new ArrayList();
		flist = remote.getFriends();
		for(int i=0; i<flist.size(); i++){
			System.out.println(flist.get(i) + " test passed ");
		}
		
		DefaultListModel model = new DefaultListModel();
		for(int i=0; i<flist.size(); i++){
			model.addElement(flist.get(i));
		}
		friendList.setModel(model);
		friendList.setVisible(true);
		friendList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
	}

}
