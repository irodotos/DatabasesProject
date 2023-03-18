package hy360;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class closeAccount extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField input_Id;
	public Long account_id;
	private JLabel lblNewLabel;

	/* Create the frame */
	
	public closeAccount() {
		setTitle("CCC");
		this.setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 170);
		getContentPane().setLayout(null);
		
		input_Id = new JTextField();
		input_Id.setToolTipText("");
		input_Id.setBounds(85, 49, 208, 32);
		getContentPane().add(input_Id);
		input_Id.setColumns(10);
		
		////////////////////////////////////////////////////////////////////
		
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
				
				account_id = Long.parseLong(input_Id.getText());
				
				try {
					String queryDeleteAccount =new String("SELECT name FROM users WHERE account_id="+account_id);
					String queryDeleteAccount_TYPE =new String("SELECT type FROM users WHERE account_id="+account_id);
					
					Statement stmt0 = con.createStatement(); 
					Statement stmt1 = con.createStatement(); 
					
					ResultSet rs0 = stmt0.executeQuery(queryDeleteAccount);
					ResultSet rs1 = stmt1.executeQuery(queryDeleteAccount_TYPE);
					
					rs0.next();
					String del_name=rs0.getString("name");
					
					rs1.next();
					String del_type=rs1.getString("type");
										
					stmt0.close();
					stmt1.close();
					
			/////////////////////////////	DELITION	//////////////////////////////////////////////////////////////////////////
					
					if(del_type.equals("individual")) {
						
						String queryDeleteIndividual =new String("SELECT amount_owed FROM individual WHERE account_id="+account_id);
						
						Statement stmt2 = con.createStatement(); 
						ResultSet rs2 = stmt2.executeQuery(queryDeleteIndividual);
						rs2.next();
						
						Double dept=rs2.getDouble("amount_owed");
						
						stmt2.close();
						
						if(dept==0) {
							
							String queryDeleteInd =new String("DELETE FROM individual WHERE account_id="+account_id);
							String queryDeleteUser0 =new String("DELETE FROM users WHERE account_id="+account_id);
							
							Statement stmt3 = con.createStatement(); 
							Statement stmt4 = con.createStatement(); 
							
							stmt3.executeUpdate(queryDeleteInd);
							stmt4.executeUpdate(queryDeleteUser0);
							
							stmt3.close();
							stmt4.close();
							
							JOptionPane.showMessageDialog(null ,"User "+del_name+", account id: "+account_id+" deleted");
						}
						else if(dept>0) {
							JOptionPane.showMessageDialog(null ,"User "+del_name+", account id: "+account_id+" has a dept of: "+dept+" $");
						}
						
					}
					else if(del_type.equals("corporation")) {
					
						String queryDeleteCorporation =new String("SELECT amount_owed FROM corporation WHERE account_id="+account_id);
						
						Statement stmt5 = con.createStatement(); 
						ResultSet rs5 = stmt5.executeQuery(queryDeleteCorporation);
						rs5.next();
						
						Double dept=rs5.getDouble("amount_owed");
						
						stmt5.close();
						
						if(dept==0) {
							
							String queryDeleteCorp =new String("DELETE FROM corporation WHERE account_id="+account_id);
							String queryDeleteUser1 =new String("DELETE FROM users WHERE account_id="+account_id);
							
							Statement stmt6 = con.createStatement(); 
							Statement stmt7 = con.createStatement(); 
							
							stmt6.executeUpdate(queryDeleteCorp);
							stmt7.executeUpdate(queryDeleteUser1);
							
							stmt6.close();
							stmt7.close();
							
							JOptionPane.showMessageDialog(null ,"User "+del_name+", account id: "+account_id+" deleted");
						}
						else if(dept>0) {
							JOptionPane.showMessageDialog(null ,"User "+del_name+", account id: "+account_id+" has dept of: "+dept+" $");
						}
					}
					else if(del_type.equals("dealer")) {
						
						String queryDeleteDealer =new String("SELECT amount_owed FROM dealer WHERE account_id="+account_id);
						
						Statement stmt8 = con.createStatement(); 
						ResultSet rs8 = stmt8.executeQuery(queryDeleteDealer);
						rs8.next();
						
						Double dept=rs8.getDouble("amount_owed");
						
						stmt8.close();
						
						if(dept==0) {
							
							String queryDeleteDealerr =new String("DELETE FROM dealer WHERE account_id="+account_id);
							String queryDeleteUser2 =new String("DELETE FROM users WHERE account_id="+account_id);
							
							Statement stmt9 = con.createStatement(); 
							Statement stmt10 = con.createStatement(); 
							
							stmt9.executeUpdate(queryDeleteDealerr);
							stmt10.executeUpdate(queryDeleteUser2);
							
							stmt9.close();
							stmt10.close();
							
							JOptionPane.showMessageDialog(null ,"User "+del_name+", account id: "+account_id+" deleted");
						}
						else if(dept>0) {
							JOptionPane.showMessageDialog(null ,"User "+del_name+", account id: "+account_id+" has dept of: "+dept+" $");
						}
					}
										
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null ,"Wrong account id,please try again");
				}
				
				try {
					con.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
				
				setVisible(false);
			}
		});
		finish_button.setBounds(305, 85, 89, 34);
		getContentPane().add(finish_button);
		
		////////////////////////////////////////////////////////////////////

		JButton back_button = new JButton("Back");
		back_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		back_button.setBounds(10, 96, 89, 23);
		getContentPane().add(back_button);
		
		lblNewLabel = new JLabel("Give the ID of the account you want to close : ");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel.setBounds(48, 11, 306, 27);
		getContentPane().add(lblNewLabel);
	}
}
