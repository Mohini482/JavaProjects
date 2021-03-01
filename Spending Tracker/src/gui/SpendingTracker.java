package gui;
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SpendingTracker extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JComboBox comboBox;
	private JLabel label;
	private JDateChooser dateChooser;
	

	private void displayCategory(){
		   
		   try 
		   {
			   comboBox.removeAllItems();
			   ResultSet rs= db.DbConnection.stmt.executeQuery("select * from category");
			   while(rs.next())
			   {
				   
				comboBox.addItem(rs.getString("category"));
			   }
		   }
		   catch(Exception ex)
		   {	
     		   JOptionPane.showMessageDialog(null, ex);
		   }
	}
	
	private void getEntries()
	{
		try 
		{
			javax.swing.table.DefaultTableModel dtm= (javax.swing.table.DefaultTableModel)table.getModel();
			int rc=dtm.getRowCount();
			while(rc--!=0)
			{
				dtm.removeRow(0);
			}
//			Current date (Local date is latest in java while util is older )
			java.time.LocalDate cd= java.time.LocalDate.now();
//			Before date
			java.time.LocalDate bd= cd.minusDays(20);

			ResultSet rs= db.DbConnection.stmt.executeQuery("select * from spendings where sdate<='"+ cd+"'and sdate>='"+bd+"'");
		    int total=0;
			while(rs.next())
		    {
				int t=rs.getInt("amount");
				total+=t;
				
			  Object o[]= {rs.getInt("sid"),rs.getString("category"),rs.getDate("sdate"),t};
		    	dtm.addRow(o);
		    }


			label.setText(total+"");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex);
		}
	}
  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpendingTracker frame = new SpendingTracker();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SpendingTracker() {
//		this will open the frame in full window
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
//		To get the current date in datechooser field automatically
//		dateChooser.setDate(new java.util.Date());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				getEntries();
				displayCategory();
			}
		});
		
	
		setBackground(new Color(255, 255, 0));
		setVisible(true);
		setTitle("Spending Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 434);
		
	
		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBackground(new Color(153, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 35, 627, 110);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 153, 102));
		panel_1.setBounds(0, 0, 627, 40);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAddMoreExpense = new JLabel("Add New Expense");
		lblAddMoreExpense.setForeground(SystemColor.desktop);
		lblAddMoreExpense.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 18));
		lblAddMoreExpense.setBounds(243, 11, 159, 18);
		panel_1.add(lblAddMoreExpense);
		
		JLabel lblDate = new JLabel("Date :");
		lblDate.setForeground(SystemColor.desktop);
		lblDate.setFont(new Font("Calibri", Font.BOLD, 14));
		lblDate.setBounds(10, 58, 46, 14);
		panel.add(lblDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setFont(new Font("Calibri", Font.PLAIN, 12));
		dateChooser.setBounds(51, 55, 92, 24);
		dateChooser.setSelectableDateRange(null, new java.util.Date());
		panel.add(dateChooser);
		
		JLabel lblNewLabel = new JLabel(" Amount :");
		lblNewLabel.setForeground(SystemColor.desktop);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		lblNewLabel.setBounds(158, 58, 63, 14);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!Character.isDigit(ch))
				{
					e.consume();
				}
			}
		});
		textField.setBounds(223, 53, 100, 24);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblCategory = new JLabel("Category :");
		lblCategory.setForeground(SystemColor.desktop);
		lblCategory.setFont(new Font("Calibri", Font.BOLD, 14));
		lblCategory.setBounds(333, 58, 68, 14);
		panel.add(lblCategory);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 12));
		comboBox.setBounds(403, 53, 129, 24);
		
		panel.add(comboBox);
	   
		
		
		JButton btnNewCategory = new JButton("Add New Category");
		btnNewCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category c = new Category();
				c.setVisible(true);
				
				
			}
		});
		btnNewCategory.setFont(new Font("Calibri", Font.PLAIN, 11));
		btnNewCategory.setBounds(403, 83, 129, 24);
		panel.add(btnNewCategory);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					java.util.Date dt= dateChooser.getDate();
					String s1=textField.getText();
					String c=(String)comboBox.getSelectedItem();
					if(dt!=null && !s1.contentEquals("") && !c.equals(""))
					{
						int amount =Integer.parseInt(s1);
						java.sql.Date date = new java.sql.Date(dt.getTime());
						db.DbConnection.stmt.executeUpdate("insert into spendings(category ,sdate, amount) values ('"+c+"','"+ date+"','"+amount+"'");
						JOptionPane.showMessageDialog(null, "Expense added sucessfully");
						   
//						Calling getEntries method on add button clicked event
					    getEntries();

					}
					else
					{
						   JOptionPane.showMessageDialog(null, "Please fill all the fields");	

					}
				}
				catch(Exception ex)
				{
				   JOptionPane.showMessageDialog(null, ex);	
				}
			}
		});
		btnAdd.setForeground(Color.RED);
		btnAdd.setBackground(new Color(153, 204, 204));
		btnAdd.setFont(new Font("Calibri", Font.BOLD, 14));
		btnAdd.setBounds(541, 51, 63, 23);
		panel.add(btnAdd);
		
		JButton btnRefresh = new JButton("REFRESH");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnRefresh.setFont(new Font("Calibri", Font.BOLD, 14));
		btnRefresh.setBounds(310, 83, 89, 23);
		panel.add(btnRefresh);
		
		JLabel lblNewLael = new JLabel("Last 20 days Spendings :");
		lblNewLael.setForeground(new Color(0, 0, 0));
		lblNewLael.setFont(new Font("Calibri", Font.BOLD, 14));
		lblNewLael.setBounds(10, 167, 441, 22);
		contentPane.add(lblNewLael);
		
		JButton btnNewButton = new JButton("REMOVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ri=table.getSelectedRow();
//				If row is not selected and we press remove then it will give -1 and if row is selected then
//				it will take that row index.
				if(ri!=-1)
				{
					 int r= JOptionPane.showConfirmDialog(null, "Do you really want to delete ?",
				     "Deletion Confirmation", JOptionPane.YES_NO_OPTION);
					 
					 if(r==JOptionPane.YES_OPTION)
                     {
         				
					int id=(int)table.getValueAt(ri, 0);
					try
					{
					db.DbConnection.stmt.executeUpdate("delete * from spendings where sid =" +id);
					JOptionPane.showMessageDialog(null, "Sucessfully deleted");
//					After deletion of the row it must show all tha available entries in tha table 
					getEntries();

				    }
				catch(Exception ex) 
					{
					JOptionPane.showMessageDialog(null, ex);
				    }
			  }
			}
		}
	});
		btnNewButton.setFont(new Font("Calibri", Font.BOLD, 14));
		btnNewButton.setBounds(472, 167, 87, 22);
		contentPane.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setForeground(Color.GRAY);
		panel_2.setBounds(10, 340, 627, 44);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblTotalAmount = new JLabel("Total Amount :");
		lblTotalAmount.setFont(new Font("Calibri", Font.BOLD, 14));
		lblTotalAmount.setBounds(10, 11, 92, 14);
		panel_2.add(lblTotalAmount);
		
		JLabel label = new JLabel("0");
		label.setFont(new Font("Calibri", Font.PLAIN, 12));
		label.setBounds(124, 11, 46, 14);
		panel_2.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 205, 627, 110);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				 "ID", "Date", "Category","Amount"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(10, 4, 131, 22);
		contentPane.add(menuBar);
		
		JMenu mnMaster = new JMenu("Master");
		menuBar.add(mnMaster);
		
		JMenuItem mntmViewSpendings = new JMenuItem("View Spendings");
		mntmViewSpendings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewSpending vs =new ViewSpending();
				vs.setVisible(true);
			}
		});
		mnMaster.add(mntmViewSpendings);
		
		JMenuItem mntmAddView = new JMenuItem("Add / View Category");
		mntmAddView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category c = new Category();
				c.setVisible(true);
				
			}
		});
		mnMaster.add(mntmAddView);
		
		JMenuItem mntmExitApp = new JMenuItem("Exit App");
		mntmExitApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
			}
		});
		mnMaster.add(mntmExitApp);
		
		JSeparator separator = new JSeparator();
		mnMaster.add(separator);
		
		JMenu mnMoreOptions = new JMenu("More Options");
		mnMaster.add(mnMoreOptions);
		
		JMenuItem mntmAboutApp = new JMenuItem("About App");
		mntmAboutApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Design & Developed By : Mohini Parolkar");
			}
		});
		mnMoreOptions.add(mntmAboutApp);
	
	
	
		
	}
}
