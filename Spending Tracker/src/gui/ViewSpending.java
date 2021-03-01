package gui;
import gui.Category;
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class ViewSpending extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JLabel label;
	private JDateChooser dateChooser_1;
	Category category;
	
	private void displayCategory()
	{
		try
		{
		ResultSet rs=db.DbConnection.stmt.executeQuery("select * from category");
		while(rs.next())
		{
			
			category.addItem(rs.getString("category"));
		}
	    }
		catch(Exception ex)
		{
		   JOptionPane.showMessageDialog(null, ex);
		}
	}
	
	
	/**
	 * Create the frame.
	 */
	public ViewSpending() {
//		dateChooser.setDate(new java.util.Date());
//		dateChooser_1.setDate(new java.util.Date());
//		dateChooser_2.setDate(new java.util.Date());
//		dateChooser_3.setDate(new java.util.Date());
//      display Category();

		setBackground(Color.RED);
		setTitle("View Spendings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 622, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 160, 122));
		panel.setBounds(26, 11, 269, 388);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 11, 249, 62);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("View Spending Date Wise");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel.setBounds(22, 11, 202, 31);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("From :");
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 98, 62, 23);
		panel.add(lblNewLabel_1);
		
		JLabel lblTo = new JLabel("To :");
		lblTo.setFont(new Font("Calibri", Font.BOLD, 14));
		lblTo.setBounds(10, 132, 62, 23);
		panel.add(lblTo);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				
//				dateChooser_1.setSelectableDateRange(dateChooser.getDate(), new java.util.Date());
//				dateChooser_1.setDate(dateChooser.getDate());
			}
		});
		dateChooser.setBounds(104, 98, 96, 23);
		dateChooser.setDate(new java.util.Date());
		panel.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(104, 132, 96, 23);
		dateChooser_1.setDate(new java.util.Date());

		panel.add(dateChooser_1);
		
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					javax.swing.table.DefaultTableModel dtm =(javax.swing.table.DefaultTableModel)table.getModel();
					int rc=dtm.getRowCount();
					while(rc--!=0)
					{
						dtm.removeRow(0);
					}
				
				java.sql.Date dt1= new java.sql.Date(dateChooser.getDate().getTime());
				java.sql.Date dt2= new java.sql.Date(dateChooser_1.getDate().getTime());
				
 				ResultSet rs=db.DbConnection.stmt.executeQuery("select * from spendings "
 						+ "sdate>='"+ dt1+"' and sdate<='"+dt2 +"' order by sdate asc");
              
 				int total=0;
 				while(rs.next())
                {
 					int t= rs.getInt("amount");
 					total+=t;
                
                	Object o[]= {rs.getDate("sdate"),rs.getString("category"),rs.getInt("amount")};
                	dtm.addRow(o);
				}
				
				label.setText(total+"");
				
			    }catch(Exception ex)
				{
			    	JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		
		btnSearch.setFont(new Font("Calibri", Font.BOLD, 14));
		btnSearch.setBounds(10, 179, 89, 23);
		panel.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 230, 249, 147);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Category", "Amount"
			}
		));
		table.setFont(new Font("Calibri", Font.PLAIN, 12));
		
		JLabel lblTotalAmount = new JLabel("Total Amount");
		lblTotalAmount.setFont(new Font("Calibri", Font.BOLD, 14));
		lblTotalAmount.setBounds(112, 183, 88, 17);
		panel.add(lblTotalAmount);
		
		JLabel label = new JLabel("0");
		label.setFont(new Font("Calibri", Font.PLAIN, 12));
		label.setBounds(210, 183, 46, 14);
		panel.add(label);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(123, 104, 238));
		panel_2.setLayout(null);
		panel_2.setBounds(327, 11, 269, 388);
		contentPane.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(10, 11, 249, 62);
		panel_2.add(panel_3);
		
		JLabel lblViewSpendingCategory = new JLabel("View Spending Category Wise");
		lblViewSpendingCategory.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 18));
		lblViewSpendingCategory.setBounds(10, 11, 229, 31);
		panel_3.add(lblViewSpendingCategory);
		
		JLabel label_2 = new JLabel("From :");
		label_2.setFont(new Font("Calibri", Font.BOLD, 14));
		label_2.setBounds(13, 139, 62, 23);
		panel_2.add(label_2);
		
		JLabel label_3 = new JLabel("To :");
		label_3.setFont(new Font("Calibri", Font.BOLD, 14));
		label_3.setBounds(13, 173, 62, 23);
		panel_2.add(label_3);
		
		JDateChooser dateChooser_2 = new JDateChooser();
		dateChooser_2.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
//				dateChooser_3.setSelectableDateRange(dateChooser_2.getDate(), new java.util.Date());
//				dateChooser_3.setDate(dateChooser_2.getDate());
			}
			
		});
		dateChooser_2.setBounds(112, 139, 106, 23);
		dateChooser_2.setDate(new java.util.Date());

		panel_2.add(dateChooser_2);
		
		JDateChooser dateChooser_3 = new JDateChooser();
		dateChooser_3.setBounds(112, 173, 106, 23);
		dateChooser_3.setDate(new java.util.Date());

		panel_2.add(dateChooser_3);
		
		JButton button = new JButton("SEARCH");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					javax.swing.table.DefaultTableModel dtm =(javax.swing.table.DefaultTableModel)table_1.getModel();
					int rc=dtm.getRowCount();
					while(rc--!=0)
					{
						dtm.removeRow(0);
					}
				 
//				String c=(String)comboBox.getSelectedItem();
				java.sql.Date dt1= new java.sql.Date(dateChooser_2.getDate().getTime());
				java.sql.Date dt2= new java.sql.Date(dateChooser_3.getDate().getTime());
				
 				ResultSet rs=db.DbConnection.stmt.executeQuery("select * from spendings "
					+ "sdate>='"+ dt1+"' and sdate<='"+dt2 +"'"
					// 	and category='"+c+"'
							+ " order by sdate asc"); 
					
              
 				int total=0;
 				while(rs.next())
                {
 					int t= rs.getInt("amount");
 					total+=t;
                
                	Object o[]= {rs.getDate("sdate"),rs.getString("category"),rs.getInt("amount")};
                	dtm.addRow(o);
				}
				
				label.setText(total+"");
				
			    }catch(Exception ex)
				{
			    	JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
			
	
		button.setFont(new Font("Calibri", Font.BOLD, 14));
		button.setBounds(13, 212, 89, 23);
		panel_2.add(button);
		
		JLabel label_4 = new JLabel("Total Amount");
		label_4.setFont(new Font("Calibri", Font.BOLD, 14));
		label_4.setBounds(112, 215, 88, 17);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("0");
		label_5.setFont(new Font("Calibri", Font.PLAIN, 12));
		label_5.setBounds(210, 215, 46, 14);
		panel_2.add(label_5);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Calibri", Font.BOLD, 14));
		lblCategory.setBounds(10, 107, 65, 14);
		panel_2.add(lblCategory);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(112, 103, 106, 22);
		panel_2.add(comboBox);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 246, 249, 131);
		panel_2.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date", "Category", "Amount"
			}
		));
		table_1.setFont(new Font("Calibri", Font.PLAIN, 12));
	}
	
}
