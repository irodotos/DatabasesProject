package hy360;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class pay extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField input_account_Id;
	private JTextField input_amount_to_pay;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

	/**
	 * Create the frame.
	 */
	public pay() {
		
		setTitle("CCC");
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		input_account_Id = new JTextField();
		input_account_Id.setBounds(148, 86, 149, 20);
		contentPane.add(input_account_Id);
		input_account_Id.setColumns(10);
		
		input_amount_to_pay = new JTextField();
		input_amount_to_pay.setBounds(148, 140, 149, 20);
		contentPane.add(input_amount_to_pay);
		input_amount_to_pay.setColumns(10);
		
		JButton back_button = new JButton("Back");
		back_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		back_button.setBounds(10, 213, 89, 37);
		contentPane.add(back_button);
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		JButton finish_button = new JButton("Finish");
		finish_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Connection con = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
					System.out.println("JDBC connection is established successfully\n");
				} catch (SQLException e) {
					System.out.println("JDBC connection is not established\n");
				}
				
				Long account_id = Long.parseLong(input_account_Id.getText()) ;
				Double pay = Double.parseDouble(input_amount_to_pay.getText());
				
				try {
					
					String query_user_TYPE =new String("SELECT type FROM users WHERE account_id="+account_id);
					
					Statement stmt0 = con.createStatement(); 
					ResultSet rs0 = stmt0.executeQuery(query_user_TYPE);
					rs0.next();
					
					String user_type=rs0.getString("type");
										
		//////////////////////////////		PAY			//////////////////////////////////////////////////////////
		
					/////////////// INDIVIDUAL	//////////////////////////////////
					
					if(user_type.equals("individual")) {
						
						String query_Individual_pay =new String("SELECT amount_owed FROM individual WHERE account_id="+account_id);
						String query_Individual_balance =new String("SELECT balance FROM individual WHERE account_id="+account_id);
						
						Statement stmt1 = con.createStatement(); 
						ResultSet rs1 = stmt1.executeQuery(query_Individual_pay);
						rs1.next();
						
						Statement stmt2 = con.createStatement(); 
						ResultSet rs2 = stmt2.executeQuery(query_Individual_balance);
						rs2.next();
						
						double amount_owed=rs1.getDouble("amount_owed");
						double balance=rs2.getDouble("balance");
						
						stmt1.close();
						stmt2.close();
						
						if((pay > amount_owed) || pay<0) {
							JOptionPane.showMessageDialog(null ,"Wrong amount to pay,please try again");
						}
						else {
							
							double new_amount_owed=amount_owed-pay;
							double new_balance=balance+pay;
							
							if(new_amount_owed<=0) {
								new_amount_owed=0;
							}
							
							String query_new_amount_owed =new String("UPDATE individual SET amount_owed="+ new_amount_owed+",balance="+new_balance+" WHERE account_id="+account_id);
							
							Statement stmt3 = con.createStatement(); 
							stmt3.executeUpdate(query_new_amount_owed);
							stmt3.close();
							
							JOptionPane.showMessageDialog(null ,"Done");
						}
						
					}
					/////////////// CORPORATION	//////////////////////////////////
					else if(user_type.equals("corporation")) {
						
						String query_corporation_pay =new String("SELECT amount_owed FROM corporation WHERE account_id="+account_id);
						String query_corporation_balance =new String("SELECT balance FROM corporation WHERE account_id="+account_id);
						
						Statement stmt1 = con.createStatement(); 
						ResultSet rs1 = stmt1.executeQuery(query_corporation_pay);
						rs1.next();
						
						Statement stmt2 = con.createStatement(); 
						ResultSet rs2 = stmt2.executeQuery(query_corporation_balance);
						rs2.next();
						
						double amount_owed=rs1.getDouble("amount_owed");
						double balance=rs2.getDouble("balance");
						
						stmt1.close();
						stmt2.close();
						
						if((pay > amount_owed) || pay<0) {
							JOptionPane.showMessageDialog(null ,"Wrong amount to pay,please try again");
						}
						else {
							
							double new_amount_owed=amount_owed-pay;
							double new_balance=balance+pay;
							
							if(new_amount_owed<=0) {
								new_amount_owed=0;
							}
							
							String query_new_amount_owed =new String("UPDATE corporation SET amount_owed="+ new_amount_owed+",balance="+new_balance+" WHERE account_id="+account_id);
							
							Statement stmt3 = con.createStatement(); 
							stmt3.executeUpdate(query_new_amount_owed);
							stmt3.close();
							JOptionPane.showMessageDialog(null ,"Done");
						}
					}
					/////////////// DEALER	//////////////////////////////////
					else if(user_type.equals("dealer")) {

						String query_dealer_pay =new String("SELECT amount_owed FROM dealer WHERE account_id="+account_id);
						
						Statement stmt1 = con.createStatement(); 
						ResultSet rs1 = stmt1.executeQuery(query_dealer_pay);
						rs1.next();
						
						double amount_owed=rs1.getDouble("amount_owed");
						
						stmt1.close();
						
						if((pay > amount_owed) || pay<0) {
							JOptionPane.showMessageDialog(null ,"Wrong amount to pay,please try again");
						}
						else {
							
							double new_amount_owed=0;
							
							if(dealerOfTheMonth.dealer_of_the_month==account_id) {
								new_amount_owed=amount_owed-(amount_owed*5/100)-pay;
							}
							else {
								new_amount_owed=amount_owed-pay;
							}
							if(new_amount_owed<=0) {
								new_amount_owed=0;
							}
							
							String query_new_amount_owed =new String("UPDATE dealer SET amount_owed="+ new_amount_owed+" WHERE account_id="+account_id);
							
							Statement stmt3 = con.createStatement(); 
							stmt3.executeUpdate(query_new_amount_owed);
							stmt3.close();
							
							JOptionPane.showMessageDialog(null ,"Done");
						}
					}
				} 
				catch (SQLException e) {
					JOptionPane.showMessageDialog(null ,"Wrong account id,please try again");
					e.printStackTrace();
				}
				setVisible(false);
			}
		});
		
		///////////////////////////////////////////////////////////////////////////////////////////

		finish_button.setBounds(300, 209, 124, 45);
		contentPane.add(finish_button);
		
		lblNewLabel = new JLabel("Pay bill");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(169, 11, 128, 25);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Account Id :");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 86, 103, 20);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Amount to pay :");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 140, 103, 20);
		contentPane.add(lblNewLabel_2);
	}
}
