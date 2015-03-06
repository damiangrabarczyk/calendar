package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import CalendarInterface.ServerConstants;
import CalendarInterface.loginInterface;
/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class calendarPanel extends JPanel {

	private static final long serialVersionUID = -4513094546043594076L;
	private JLabel lbYear;
	private JLabel lbMonth;
	@SuppressWarnings("unused")
	private JLabel lbDay;
	private JTable tblCalendar;
	private JButton btnNextMonth;
	private JButton btnprvMonth;
	@SuppressWarnings({ "rawtypes", "unused" })
	private JComboBox cmbMonth;

	// private Container container;

	private DefaultTableModel mtblCalendar; // Table model
	private JScrollPane stblCalendar; // The scrollpane

	@SuppressWarnings("unused")
	private int realYear, realMonth, realDay, currentDay, currentYear,
	currentMonth;

	public calendarPanel() {
		setLayout(null);
		monthView();
		dayView();

	}

	public void dayView() { 
	}

	public void monthView() {
		lbYear = new JLabel ("2014");
		lbMonth = new JLabel("January");
		mtblCalendar = new DefaultTableModel() {
			private static final long serialVersionUID = 5818524952595405594L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		ImageIcon next = new ImageIcon("images\\next.png");
		btnNextMonth = new JButton(next);
		ImageIcon previous = new ImageIcon("images\\previous.png");
		btnprvMonth = new JButton(previous);

		Dimension size = getPreferredSize();
		size.width = 750;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Calendar"));
		setLayout(null);

		// Set bounds
		lbYear.setBounds(390 - lbYear.getPreferredSize().width / 2, 10, 100,
				25);
		lbMonth.setBounds(390 - lbMonth.getPreferredSize().width / 2, 25, 100,
				25);
		stblCalendar.setBounds(10, 50, 730, 455);   // can change 50 to move
		// vertically
		btnprvMonth.setBounds(10, 25, 50, 25);
		btnNextMonth.setBounds(725 - 36, 25, 50, 25);
		//cmbMonth.setBounds(500, 500, 80, 20);

		//cmbMonth.addActionListener(new cmbMonth_Action());
		btnprvMonth.addActionListener(new btnprvMonth_Action());
		btnNextMonth.addActionListener(new btnNextMonth_Action());

		// Add components to the panel
		add(lbYear);
		add(lbMonth);
		add(stblCalendar);
		add(btnprvMonth);
		add(btnNextMonth);
		// add(cmbMonth);

		// Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); // Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
		realMonth = cal.get(GregorianCalendar.MONTH); // Get month
		realYear = cal.get(GregorianCalendar.YEAR); // Get year
		currentDay = realDay;
		currentMonth = realMonth; // Match month and year
		currentYear = realYear;

		// Add headers
		String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" }; // All
		// headers
		for (int i = 0; i < 7; i++) {
			mtblCalendar.addColumn(headers[i]);
		}

		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); // Set
		// background

		// No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		// Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Set row/column count
		tblCalendar.setRowHeight(38);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);

		// Refresh calendar
		// refreshCalendar (realMonth, realYear); //Refresh calendar
		refreshCalendar(currentMonth, currentYear); // need the top month to change
	}

	public void refreshCalendar(int month, int year) {
		// Variables
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
		"December" };
		int nod, som; // Number Of Days, Start Of Month

		lbMonth.setText(months[month]); // Refresh the month label (at the top)
		lbMonth.setBounds(390 - lbMonth.getPreferredSize().width / 2, 25, 180,
				25); // Re-align label with calendar
		lbYear.setText(String.valueOf(year)); //Select the correct year in the combo box
		lbYear.setBounds(390 - lbYear.getPreferredSize().width / 2, 10, 100,
				25);

		// Clear table
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				mtblCalendar.setValueAt(null, i, j);
			}
		}

		// Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Draw calendar
		for (int i = 1; i <= nod; i++) {
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			mtblCalendar.setValueAt(i, row, column);
		}

		// Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new
				tblCalendarRenderer());
	}

	/*class cmbMonth_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (cmbMonth.getSelectedItem() != null) {
				String b = cmbMonth.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}*/

	class btnprvMonth_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentMonth == 0) {
				currentMonth = 11;
				currentYear--;
			} else {
				currentMonth--;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}

	class btnNextMonth_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentMonth == 11) {
				currentMonth = 0;
				currentYear++;
			} else {
				currentMonth++;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}

	class tblCalendarRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 2669573120988924026L;

		@SuppressWarnings("deprecation")
		public Component getTableCellRendererComponent(JTable table,Object value, boolean selected, boolean focused, int row,int column) {

			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(ServerConstants.host,ServerConstants.RMI_PORT);
				final loginInterface remote = (loginInterface) registry.lookup(ServerConstants.RMI_ID);
				super.getTableCellRendererComponent(table, value, selected,focused, row, column);

				if (value != null) {
					if (Integer.parseInt(value.toString()) == realDay
							&& currentMonth == realMonth && currentYear == realYear) {
					}
					setBorder(null);
					setForeground(Color.black);
					//-----------------------------------------------------------------------------------------------------
					//Getting event for selected date
					Date selectedDate = new Date(currentYear - 1900, currentMonth, (int) Integer.parseInt(value.toString()));
					System.out.println(selectedDate);
					remote.getEvents(selectedDate);
					//currentEventPanel.repaint();
					addMouseListener(new MouseAdapter() { 
						public void mousePressed(MouseEvent me) { 
							try {
								createEvent(new actionEvent(this,true,"mouse"));
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} 
					}); 
					//-------------------------------------------------------------------------------------------------------

				}
			} catch (RemoteException | SQLException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return this;

		}
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
