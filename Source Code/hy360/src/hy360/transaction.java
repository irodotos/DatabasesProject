package hy360;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class transaction extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton finish_button;
	private JButton back_button;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JTextField input_clientName;
	private JTextField input_clientAccountID;
	private JTextField input_dealerName;
	private JTextField input_dealerAccountID;
	private JTextField input_transacntionType;
	private JTextField input_amount;
	private JTextField input_employeeID;
	
	public String clientName;
	public long clientAccountID;
	public String dealerName;
	public long dealerAccountID;
	public String transacntionType;
	public double amount;
	public String date;
	public long employeeID;
	
	boolean ok=false;
	
	
	/* Create Frame */
	
	public transaction() {
		setTitle("CCC");
		setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 505);
		getContentPane().setLayout(null);
		
		//////////////////////////////////////////////////////////////////////////////
		
		finish_button = new JButton("Finish");
		finish_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Connection con = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
					System.out.println("JDBC connection is established successfully\n");
				} catch (SQLException e) {
					System.out.println("JDBC connection is not established\n");
				}
				
				try {
					
					///////////////	SETUP NEW TRANSACTION'S ID	///////////////////////////////////////////////
					String query_max_transactionID =new String("SELECT MAX(transaction_id) transaction_id FROM transaction");
				
					Statement stmt0 = con.createStatement(); 
					ResultSet rs0 = stmt0.executeQuery(query_max_transactionID);
					rs0.next();
					
					long max_transactionID=rs0.getLong("transaction_id");
					
					stmt0.close();

					long new_transactionID = max_transactionID+1;
					
					///////////////////////	SETUP VARIABLES	////////////////////////////////////////////////////////
					
					clientName = input_clientName.getText();
					clientAccountID = Long.parseLong(input_clientAccountID.getText()) ;
					dealerName = input_dealerName.getText();
					dealerAccountID = Long.parseLong(input_dealerAccountID.getText()) ;
					transacntionType = input_transacntionType.getText();
					amount = Double.parseDouble(input_amount.getText()) ;
					
						
					String query_client_type =new String("SELECT type FROM users WHERE account_id="+clientAccountID);
					
					Statement stmt1 = con.createStatement(); 
					ResultSet rs1 = stmt1.executeQuery(query_client_type);
					rs1.next();
					
					String client_type=rs1.getString("type");
					
					stmt1.close();
					
					if(transacntionType.equals("charge")) {
						
						//////////////////////////INDIVIDUAL	CHARGE	TRANSACTION	///////////////////////////////////////////////	
						
						if(client_type.equals("individual")) {
							
							/////////////// CHECK FOR VALID ACCOUNT DATE	/////////////////////
							
							String query_client_EXPIRATION_DATE =new String("SELECT expiration_date FROM individual WHERE account_id="+clientAccountID);
							
							Statement ex = con.createStatement(); 
							ResultSet rex = ex.executeQuery(query_client_EXPIRATION_DATE);
							rex.next();
							
							String client_ex_date=rex.getString("expiration_date");
							
							String[] ex_date=client_ex_date.split("/"); 
							
							String[] curr_date= Graphics.current_date.split("/");
							
							ex.close();
							

							if(Integer.parseInt(ex_date[2]) > Integer.parseInt(curr_date[2])) {
								ok=true;
							}
							else if(Integer.parseInt(ex_date[2])<Integer.parseInt(curr_date[2])) {
								ok=false;
							}
							else {
								if(Integer.parseInt(ex_date[1]) > Integer.parseInt(curr_date[1])) {
									ok=true;
								}
								else if(Integer.parseInt(ex_date[1])<Integer.parseInt(curr_date[1])) {
									ok=false;
								}
								else {
									if(Integer.parseInt(ex_date[0]) > Integer.parseInt(curr_date[0])) {
										ok=true;
									}
									else if(Integer.parseInt(ex_date[0])<=Integer.parseInt(curr_date[0])) {
										ok=false;
									}
								}
							}
							
							if(ok==true) {
								
								String query_client_balance =new String("SELECT balance FROM individual WHERE account_id="+clientAccountID);
								
								Statement stmt2 = con.createStatement(); 
								ResultSet rs2 = stmt2.executeQuery(query_client_balance);
								rs2.next();
								
								double client_balance=rs2.getDouble("balance");
								
								stmt2.close();
								
								///////////////////// SUCCESSFULL	CHARGE	TRANSACTION	//////////////////////////////////////////////////
								
								if(client_balance>=amount) {
									
									PreparedStatement stmt3 = con.prepareStatement("INSERT INTO `transaction`(`transaction_id`,`date`,`type`,`amount`,`client_name`,`dealer_name`,`client_acc_id`,`dealer_acc_id`)"
											+ "VALUES("+new_transactionID+",?,?,"+amount+",?,?,"+clientAccountID+","+dealerAccountID+")");
									
									stmt3.setString(1,Graphics.current_date);
									stmt3.setString(2,transacntionType);
									stmt3.setString(3,clientName);
									stmt3.setString(4,dealerName);
									
									stmt3.executeUpdate();
									stmt3.close();
									
									//////new balance////////
									
									double new_balance=client_balance-amount;
									
									String query_new_individual_balance =new String("UPDATE individual SET balance="+new_balance+" WHERE account_id="+clientAccountID);
									
									Statement stmt4 = con.createStatement(); 
									stmt4.executeUpdate(query_new_individual_balance);
									stmt4.close();
		
									///////////////////////
									
									////////// new amount owed ///////////////////
									

									String query_individual_credit_limit =new String("SELECT credit_limit FROM individual WHERE account_id="+clientAccountID);
									
									Statement stmt40 = con.createStatement(); 
									ResultSet rs40 = stmt40.executeQuery(query_individual_credit_limit);
									rs40.next();
									
									double individual_credit_limit=rs40.getDouble("credit_limit");
									
									stmt40.close();
									
									
									double new_individual_dept=individual_credit_limit-new_balance;
									
									String query_new_individual_dept =new String("UPDATE individual SET amount_owed="+new_individual_dept+" WHERE account_id="+clientAccountID);
									
									Statement stmt42 = con.createStatement(); 
									stmt42.executeUpdate(query_new_individual_dept);
									stmt42.close();
									
									/////////////////////////////////////////////
		
								}
								/////////////////////	UNSUCCESSFUL	CHARGE	TRANSACTION	////////////////////////////////////////////////
								else {
									JOptionPane.showMessageDialog(null ,"Not enough balance");
								}
							}
							else {
								JOptionPane.showMessageDialog(null , "The user's account has expired");
							}
							/////////////////////////////////////////////////////////////////////////
						}
						
						//////////////////////////////CORPORATION	CHARGE	TRANSACTION///////////////////////////////////////////////////	
						
						else if(client_type.equals("corporation")) {
							
							/////////////// CHECK FOR VALID ACCOUNT DATE	/////////////////////
							
							String query_client_EXPIRATION_DATE =new String("SELECT expiration_date FROM corporation WHERE account_id="+clientAccountID);
							
							Statement ex = con.createStatement(); 
							ResultSet rex = ex.executeQuery(query_client_EXPIRATION_DATE);
							rex.next();
							
							String client_ex_date=rex.getString("expiration_date");
							
							String[] ex_date=client_ex_date.split("/"); 
							
							String[] curr_date= Graphics.current_date.split("/");
							
							ex.close();
							
							if(Integer.parseInt(ex_date[2]) > Integer.parseInt(curr_date[2])) {
								ok=true;
							}
							else if(Integer.parseInt(ex_date[2])<Integer.parseInt(curr_date[2])) {
								ok=false;
							}
							else {
								if(Integer.parseInt(ex_date[1]) > Integer.parseInt(curr_date[1])) {
									ok=true;
								}
								else if(Integer.parseInt(ex_date[1])<Integer.parseInt(curr_date[1])) {
									ok=false;
								}
								else {
									if(Integer.parseInt(ex_date[0]) > Integer.parseInt(curr_date[0])) {
										ok=true;
									}
									else if(Integer.parseInt(ex_date[0])<=Integer.parseInt(curr_date[0])) {
										ok=false;
									}
								}
							}
							
							if(ok==true) {
								
								String query_client_balance =new String("SELECT balance FROM corporation WHERE account_id="+clientAccountID);
								
								Statement stmt2 = con.createStatement(); 
								ResultSet rs2 = stmt2.executeQuery(query_client_balance);
								rs2.next();
								
								double client_balance=rs2.getDouble("balance");
								
								stmt2.close();
								
								///////////////////// SUCCESSFUL	CHARGE	TRANSACTION	//////////////////////////////////////////////////
								
								if(client_balance>=amount) {
									
									////////	CORPORATION EMPLOYEE	CHARGE	////////////
									
									if(!input_employeeID.getText().equals("")) {
										employeeID = Long.parseLong(input_employeeID.getText()) ;
										
										PreparedStatement stmt5 = con.prepareStatement("INSERT INTO `transaction`(`transaction_id`,`date`,`type`,`amount`,`client_name`,`dealer_name`,`client_acc_id`,`dealer_acc_id`,`corp_emp_id`)"
												+ "VALUES("+new_transactionID+",?,?,"+amount+",?,?,"+clientAccountID+","+dealerAccountID+","+employeeID+")");
										
										stmt5.setString(1,Graphics.current_date);
										stmt5.setString(2,transacntionType);
										stmt5.setString(3,clientName);
										stmt5.setString(4,dealerName);
										
										stmt5.executeUpdate();
										stmt5.close();
									}
									////////	CORPORATION		CHARGE	///////////////////
									else {
										
										PreparedStatement stmt3 = con.prepareStatement("INSERT INTO `transaction`(`transaction_id`,`date`,`type`,`amount`,`client_name`,`dealer_name`,`client_acc_id`,`dealer_acc_id`)"
												+ "VALUES("+new_transactionID+",?,?,"+amount+",?,?,"+clientAccountID+","+dealerAccountID+")");
										
										stmt3.setString(1,Graphics.current_date);
										stmt3.setString(2,transacntionType);
										stmt3.setString(3,clientName);
										stmt3.setString(4,dealerName);
										
										stmt3.executeUpdate();
										stmt3.close();
									}
						
									//////new balance////////
									
									double new_balance=client_balance-amount;
									
									String query_new_corporation_balance =new String("UPDATE corporation SET balance="+new_balance+" WHERE account_id="+clientAccountID);
									
									Statement stmt4 = con.createStatement(); 
									stmt4.executeUpdate(query_new_corporation_balance);
									stmt4.close();
		
									///////////////////////
									
									////////// new amount owed ///////////////////
									
									String query_corporation_credit_limit =new String("SELECT credit_limit FROM corporation WHERE account_id="+clientAccountID);
									
									Statement stmt40 = con.createStatement(); 
									ResultSet rs40 = stmt40.executeQuery(query_corporation_credit_limit);
									rs40.next();
									
									double corporation_credit_limit=rs40.getDouble("credit_limit");
									
									stmt40.close();

									double new_corporation_dept=corporation_credit_limit-new_balance;
									
									String query_new_corporation_dept =new String("UPDATE corporation SET amount_owed="+new_corporation_dept+" WHERE account_id="+clientAccountID);
									
									Statement stmt42 = con.createStatement(); 
									stmt42.executeUpdate(query_new_corporation_dept);
									stmt42.close();
									
									/////////////////////////////////////////////

								}
								/////////////////////	UNSUCCESSFUL	CHARGE	TRANSACTION	////////////////////////////////////////////////
								else {
									JOptionPane.showMessageDialog(null ,"Not enough balance");
								}
							}
							else {
								JOptionPane.showMessageDialog(null , "The user's account has expired");
							}
							
							/////////////////////////////////////////////////////////////////////////		
						}
						
						////// SETUP DEALER CHARGE	///////
						
						String query_dealer_dept =new String("SELECT amount_owed FROM dealer WHERE account_id="+dealerAccountID);
						
						Statement stmt10 = con.createStatement(); 
						ResultSet rs10 = stmt10.executeQuery(query_dealer_dept);
						rs10.next();
						
						double dealer_dept=rs10.getDouble("amount_owed");
						
						stmt10.close();
						
						String query_dealer_commision =new String("SELECT commission FROM dealer WHERE account_id="+dealerAccountID);
						
						Statement stmt11 = con.createStatement(); 
						ResultSet rs11 = stmt11.executeQuery(query_dealer_commision);
						rs11.next();
						
						double dealer_commission=rs11.getDouble("commission");
						
						stmt11.close();
						
						double new_dealer_dept=dealer_dept+(amount*(dealer_commission)/100);
						
						String query_new_dealer_dept =new String("UPDATE dealer SET amount_owed="+new_dealer_dept+" WHERE account_id="+dealerAccountID);
						
						Statement stmt12 = con.createStatement(); 
						stmt12.executeUpdate(query_new_dealer_dept);
						stmt12.close();
						
						String query_dealer_profit =new String("SELECT profit FROM dealer WHERE account_id="+dealerAccountID);
						
						Statement stmt13 = con.createStatement(); 
						ResultSet rs13 = stmt13.executeQuery(query_dealer_profit);
						rs13.next();
						
						double dealer_profit=rs13.getDouble("profit");
						
						stmt13.close();
						
						double new_dealer_profit=dealer_profit+amount;
						
						String query_new_dealer_profit =new String("UPDATE dealer SET profit="+new_dealer_profit+" WHERE account_id="+dealerAccountID);
						
						Statement stmt14 = con.createStatement(); 
						stmt14.executeUpdate(query_new_dealer_profit);
						stmt14.close();
				
						//////////	END SETUP DEALER CHARGE	////////////
						
						JOptionPane.showMessageDialog(null , "Done");
						
					}
					else if(transacntionType.equals("credit"))  {
						
						////////////////////INDIVIDUAL	CREDIT	TRANSACTION///////////////////////////////////////////////		
						
						if(client_type.equals("individual")) {
							
							/////////////// CHECK FOR VALID ACCOUNT DATE	/////////////////////
							
							String query_client_EXPIRATION_DATE =new String("SELECT expiration_date FROM individual WHERE account_id="+clientAccountID);
							
							Statement ex = con.createStatement(); 
							ResultSet rex = ex.executeQuery(query_client_EXPIRATION_DATE);
							rex.next();
							
							String client_ex_date=rex.getString("expiration_date");
							
							String[] ex_date=client_ex_date.split("/"); 
							
							String[] curr_date= Graphics.current_date.split("/");
							
							ex.close();
							
							if(Integer.parseInt(ex_date[2]) > Integer.parseInt(curr_date[2])) {
								ok=true;
							}
							else if(Integer.parseInt(ex_date[2])<Integer.parseInt(curr_date[2])) {
								ok=false;
							}
							else {
								if(Integer.parseInt(ex_date[1]) > Integer.parseInt(curr_date[1])) {
									ok=true;
								}
								else if(Integer.parseInt(ex_date[1])<Integer.parseInt(curr_date[1])) {
									ok=false;
								}
								else {
									if(Integer.parseInt(ex_date[0]) > Integer.parseInt(curr_date[0])) {
										ok=true;
									}
									else if(Integer.parseInt(ex_date[0])<=Integer.parseInt(curr_date[0])) {
										ok=false;
									}
								}
							}
							
							if(ok==true) {
								
								String query_client_balance =new String("SELECT balance FROM individual WHERE account_id="+clientAccountID);
								
								Statement stmt2 = con.createStatement(); 
								ResultSet rs2 = stmt2.executeQuery(query_client_balance);
								rs2.next();
								
								double client_balance=rs2.getDouble("balance");
								
								stmt2.close();
								
								PreparedStatement stmt3 = con.prepareStatement("INSERT INTO `transaction`(`transaction_id`,`date`,`type`,`amount`,`client_name`,`dealer_name`,`client_acc_id`,`dealer_acc_id`)"
										+ "VALUES("+new_transactionID+",?,?,"+amount+",?,?,"+clientAccountID+","+dealerAccountID+")");
								
								stmt3.setString(1,Graphics.current_date);
								stmt3.setString(2,transacntionType);
								stmt3.setString(3,clientName);
								stmt3.setString(4,dealerName);
								
								stmt3.executeUpdate();
								stmt3.close();
								
								//////new balance////////
								
								double new_balance=client_balance+amount;
								
								String query_new_individual_balance =new String("UPDATE individual SET balance="+new_balance+" WHERE account_id="+clientAccountID);
								
								Statement stmt4 = con.createStatement(); 
								stmt4.executeUpdate(query_new_individual_balance);
								stmt4.close();

								///////////////////////	
								
								////////// new amount owed ///////////////////
								
								String query_individual_credit_limit =new String("SELECT credit_limit FROM individual WHERE account_id="+clientAccountID);
								
								Statement stmt40 = con.createStatement(); 
								ResultSet rs40 = stmt40.executeQuery(query_individual_credit_limit);
								rs40.next();
								
								double individual_credit_limit=rs40.getDouble("credit_limit");
								
								stmt40.close();
								
								
								double new_individual_dept=individual_credit_limit-new_balance;
								
								String query_new_individual_dept =new String("UPDATE individual SET amount_owed="+new_individual_dept+" WHERE account_id="+clientAccountID);
								
								Statement stmt42 = con.createStatement(); 
								stmt42.executeUpdate(query_new_individual_dept);
								stmt42.close();
								
								/////////////////////////////////////////////
							}
							else {
								JOptionPane.showMessageDialog(null , "The user's account has expired");
							}
							
							///////////////////////////////////////////////////////////////////////////////////////////////////

						}
						
						//////////////////////////////CORPORATION	CREDIT	TRANSACTION///////////////////////////////////////////////////	
						
						else if(client_type.equals("corporation")) {
							
							/////////////// CHECK FOR VALID ACCOUNT DATE	/////////////////////
							
							String query_client_EXPIRATION_DATE =new String("SELECT expiration_date FROM corporation WHERE account_id="+clientAccountID);
							
							Statement ex = con.createStatement(); 
							ResultSet rex = ex.executeQuery(query_client_EXPIRATION_DATE);
							rex.next();
							
							String client_ex_date=rex.getString("expiration_date");
							
							String[] ex_date=client_ex_date.split("/"); 
							
							String[] curr_date= Graphics.current_date.split("/");
							
							ex.close();
							
							if(Integer.parseInt(ex_date[2]) > Integer.parseInt(curr_date[2])) {
								ok=true;
							}
							else if(Integer.parseInt(ex_date[2])<Integer.parseInt(curr_date[2])) {
								ok=false;
							}
							else {
								if(Integer.parseInt(ex_date[1]) > Integer.parseInt(curr_date[1])) {
									ok=true;
								}
								else if(Integer.parseInt(ex_date[1])<Integer.parseInt(curr_date[1])) {
									ok=false;
								}
								else {
									if(Integer.parseInt(ex_date[0]) > Integer.parseInt(curr_date[0])) {
										ok=true;
									}
									else if(Integer.parseInt(ex_date[0])<=Integer.parseInt(curr_date[0])) {
										ok=false;
									}
								}
							}
							
							if(ok==true) {
								
								String query_client_balance =new String("SELECT balance FROM corporation WHERE account_id="+clientAccountID);
								
								Statement stmt2 = con.createStatement(); 
								ResultSet rs2 = stmt2.executeQuery(query_client_balance);
								rs2.next();
								
								double client_balance=rs2.getDouble("balance");
								
								stmt2.close();
								
								////////		CORPORATION EMPLOYEE	CREDIT	////////////
			
								if(!input_employeeID.getText().equals("")) {
									employeeID = Long.parseLong(input_employeeID.getText()) ;
									
									PreparedStatement stmt5 = con.prepareStatement("INSERT INTO `transaction`(`transaction_id`,`date`,`type`,`amount`,`client_name`,`dealer_name`,`client_acc_id`,`dealer_acc_id`,`corp_emp_id`)"
											+ "VALUES("+new_transactionID+",?,?,"+amount+",?,?,"+clientAccountID+","+dealerAccountID+","+employeeID+")");
									
									stmt5.setString(1,Graphics.current_date);
									stmt5.setString(2,transacntionType);
									stmt5.setString(3,clientName);
									stmt5.setString(4,dealerName);
									
									stmt5.executeUpdate();
									stmt5.close();
								}
								////////	CORPORATION		CREDIT	///////////////////
								else {
									
									PreparedStatement stmt3 = con.prepareStatement("INSERT INTO `transaction`(`transaction_id`,`date`,`type`,`amount`,`client_name`,`dealer_name`,`client_acc_id`,`dealer_acc_id`)"
											+ "VALUES("+new_transactionID+",?,?,"+amount+",?,?,"+clientAccountID+","+dealerAccountID+")");
									
									stmt3.setString(1,Graphics.current_date);
									stmt3.setString(2,transacntionType);
									stmt3.setString(3,clientName);
									stmt3.setString(4,dealerName);
									
									stmt3.executeUpdate();
									stmt3.close();
								}
					
								//////new balance////////
								
								double new_balance=client_balance+amount;
								
								String query_new_corporation_balance =new String("UPDATE corporation SET balance="+new_balance+" WHERE account_id="+clientAccountID);
								
								Statement stmt4 = con.createStatement(); 
								stmt4.executeUpdate(query_new_corporation_balance);
								stmt4.close();

								////////////////////////
								
								////////// new amount owed ///////////////////
	
								String query_corporation_credit_limit =new String("SELECT credit_limit FROM corporation WHERE account_id="+clientAccountID);
								
								Statement stmt40 = con.createStatement(); 
								ResultSet rs40 = stmt40.executeQuery(query_corporation_credit_limit);
								rs40.next();
								
								double corporation_credit_limit=rs40.getDouble("credit_limit");
								
								stmt40.close();

								double new_corporation_dept=corporation_credit_limit-new_balance;
								
								String query_new_corporation_dept =new String("UPDATE corporation SET amount_owed="+new_corporation_dept+" WHERE account_id="+clientAccountID);
								
								Statement stmt42 = con.createStatement(); 
								stmt42.executeUpdate(query_new_corporation_dept);
								stmt42.close();
								
								/////////////////////////////////////////////
							}
							else {
								JOptionPane.showMessageDialog(null , "The user's account has expired");
							}	
						}
						
						////// SETUP DEALER CREDIT	///////

						String query_dealer_dept =new String("SELECT amount_owed FROM dealer WHERE account_id="+dealerAccountID);
						
						Statement stmt10 = con.createStatement(); 
						ResultSet rs10 = stmt10.executeQuery(query_dealer_dept);
						rs10.next();
						
						double dealer_dept=rs10.getDouble("amount_owed");
						
						stmt10.close();
						
						String query_dealer_commision =new String("SELECT commission FROM dealer WHERE account_id="+dealerAccountID);
						
						Statement stmt11 = con.createStatement(); 
						ResultSet rs11 = stmt11.executeQuery(query_dealer_commision);
						rs11.next();
						
						double dealer_commission=rs11.getDouble("commission");
						
						stmt11.close();
						
						double new_dealer_dept=dealer_dept-(amount*(dealer_commission)/100);
						
						if(new_dealer_dept<=0) {
							new_dealer_dept=0;
						}
						
						String query_new_dealer_dept =new String("UPDATE dealer SET amount_owed="+new_dealer_dept+" WHERE account_id="+dealerAccountID);
						
						Statement stmt12 = con.createStatement(); 
						stmt12.executeUpdate(query_new_dealer_dept);
						stmt12.close();
						
						String query_dealer_profit =new String("SELECT profit FROM dealer WHERE account_id="+dealerAccountID);
						
						Statement stmt13 = con.createStatement(); 
						ResultSet rs13 = stmt13.executeQuery(query_dealer_profit);
						rs13.next();
						
						double dealer_profit=rs13.getDouble("profit");
						
						stmt13.close();
						
						double new_dealer_profit=dealer_profit-amount;
						
						String query_new_dealer_profit =new String("UPDATE dealer SET profit="+new_dealer_profit+" WHERE account_id="+dealerAccountID);
						
						Statement stmt14 = con.createStatement(); 
						stmt14.executeUpdate(query_new_dealer_profit);
						stmt14.close();
				
						//////////	END SETUP DEALER CREDIT	////////////
						
						JOptionPane.showMessageDialog(null , "Done");
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null ,"Something is wrong,please try again");
					e.printStackTrace();
				} 	
				setVisible(false);
			}
		});
		
		//////////////////////////////////////////////////////////////////////////////

		finish_button.setFont(new Font("Tahoma", Font.PLAIN, 13));
		finish_button.setBounds(370, 411, 154, 41);
		getContentPane().add(finish_button);
		
		back_button = new JButton("Back");
		back_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		back_button.setBounds(10, 410, 96, 45);
		getContentPane().add(back_button);
		
		input_clientName = new JTextField();
		input_clientName.setBounds(248, 63, 206, 20);
		getContentPane().add(input_clientName);
		input_clientName.setColumns(10);
		
		input_clientAccountID = new JTextField();
		input_clientAccountID.setBounds(248, 105, 206, 20);
		getContentPane().add(input_clientAccountID);
		input_clientAccountID.setColumns(10);
		
		input_dealerName = new JTextField();
		input_dealerName.setBounds(248, 144, 206, 20);
		getContentPane().add(input_dealerName);
		input_dealerName.setColumns(10);
		
		input_dealerAccountID = new JTextField();
		input_dealerAccountID.setBounds(248, 189, 206, 20);
		getContentPane().add(input_dealerAccountID);
		input_dealerAccountID.setColumns(10);
		
		lblNewLabel = new JLabel("New Transaction");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel.setBounds(180, 11, 154, 31);
		getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Client's account ID :");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_1.setBounds(26, 105, 146, 20);
		getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Dealer's account  ID :");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_2.setBounds(26, 187, 146, 20);
		getContentPane().add(lblNewLabel_2);
		
		lblNewLabel_4 = new JLabel("Amount :");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_4.setBounds(26, 264, 110, 20);
		getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Client's Name :");
		lblNewLabel_5.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_5.setBounds(26, 66, 175, 20);
		getContentPane().add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("Dealer's Name :");
		lblNewLabel_6.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_6.setBounds(26, 144, 146, 20);
		getContentPane().add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("Type :");
		lblNewLabel_7.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_7.setBounds(26, 230, 130, 20);
		getContentPane().add(lblNewLabel_7);
		
		input_transacntionType = new JTextField();
		input_transacntionType.setBounds(248, 230, 206, 20);
		getContentPane().add(input_transacntionType);
		input_transacntionType.setColumns(10);
		
		input_amount = new JTextField();
		input_amount.setBounds(248, 264, 206, 20);
		getContentPane().add(input_amount);
		input_amount.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Employee Id (if exists ) :");
		lblNewLabel_8.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_8.setBounds(26, 346, 159, 20);
		getContentPane().add(lblNewLabel_8);
		
		input_employeeID = new JTextField();
		input_employeeID.setBounds(248, 346, 206, 20);
		getContentPane().add(input_employeeID);
		input_employeeID.setColumns(10);
	}
}
