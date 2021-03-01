package gui;
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Category extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
  
	
	
   private  void getEntries() {
		
		try 
		{
			
     	   javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)table.getModel();
            int rc =dtm.getRowCount();
            while (rc--!=0)
            {
            	dtm.removeRow(0);
            }
			
            ResultSet rs = db.DbConnection.stmt.executeQuery("select * from category");
            int sno=0;
            while (rs.next()) {
            	String category = rs.getString("category");
            	
//              	Using Objects :-
//            	   javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)table.getModel();
//                 object o[] = {++sno , category };
//                 dtm.addRow(o);
//            
//                 Using Collection :-
                   java.util.Vector row = new java.util.Vector();
                   row.add(++sno);
                   row.add(category);
                   dtm.addRow(row);
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
	public Category() {
		
			
		setForeground(Color.LIGHT_GRAY);
		setTitle("Category");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 457, 386);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 127, 80));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    
            
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 102, 255));
		panel.setBounds(10, 11, 411, 120);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 51));
		panel_1.setBounds(0, 11, 411, 59);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAddNewCategory = new JLabel("Add New Category");
		lblAddNewCategory.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 18));
		lblAddNewCategory.setBounds(142, 11, 205, 21);
		panel_1.add(lblAddNewCategory);
		
		JLabel lblCategory = new JLabel("Category :");
		lblCategory.setFont(new Font("Calibri", Font.BOLD, 14));
		lblCategory.setBounds(10, 91, 88, 22);
		panel.add(lblCategory);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					String category = textField.getText();
					if(!category.equals(""))
					{
						db.DbConnection.stmt.executeUpdate("insert into category values ('" + category + "')");
						JOptionPane.showMessageDialog(null, "Category Addded Suceesfully");
						getEntries();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter the category !!");
					}
					
				}
				catch(SQLIntegrityConstraintViolationException ex)
				{
					JOptionPane.showMessageDialog(null, " Category already exists");
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		textField.setBounds(82, 91, 124, 22);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					String category = textField.getText();
					if(!category.equals(""))
					{
						db.DbConnection.stmt.executeUpdate("insert into category values ('" + category + "')");
						JOptionPane.showMessageDialog(null, "Category Addded Suceesfully");
						getEntries();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter the category !!");
					}

					
				
					
				}
				catch(SQLIntegrityConstraintViolationException ex)
				{
					JOptionPane.showMessageDialog(null, " Category already exists");
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAdd.setFont(new Font("Calibri", Font.BOLD, 14));
		btnAdd.setBounds(226, 91, 89, 23);
		panel.add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 153, 411, 145);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
				"S.No.", "Category"
			}
		));
		
		 
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ri=table.getSelectedRow();
//				If row is not selected and we press remove then it will give -1 and if row is selected then
//				it will take that row index.
				if(ri!=-1) {
				 int r= JOptionPane.showConfirmDialog(null, "Do you really want to delete ?", "Deletion Confirmation", JOptionPane.YES_NO_OPTION);
                      if(r==JOptionPane.YES_OPTION)
                      {
          				
                    	  
          				  String category=(String)table.getValueAt(ri, 1);
          				  try
          				  {
          					
                              db.DbConnection.stmt.executeUpdate("delete from category where category ='" + category +"'");
          					  JOptionPane.showMessageDialog(null, "Category deleted sucessfully");
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
	
		btnDelete.setFont(new Font("Calibri", Font.BOLD, 14));
		btnDelete.setBounds(172, 309, 89, 23);
		contentPane.add(btnDelete);
		
			}


	public static void addItem(String string) {
		// TODO Auto-generated method stub
		
	}

}
	


	
	
	 

