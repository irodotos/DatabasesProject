package hy360;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class questions extends JFrame {


	private static final long serialVersionUID = 1L;
	private JTextField input_query;
	public JTextArea output_results;
	public String query;
	private JTextField input_account_id;
	private JTextField input_type_of_transaction;
	private JTextField input_emp_id;
	private JTextField intput_initial_date;
	private JTextField input_deadline_date;
	
	public long account_id;
	public String type_of_transaction;
	public String type_of_user;
	public String initial_date;
	public String deadline_date;
	public long employee_id;
	private JTextField input_type_of_user;


	/* Create the frame */
	public questions() {
		setResizable(false);
		setTitle("CCC");
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1212, 611);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Questions");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(373, 11, 96, 38);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Query : ");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 386, 57, 37);
		getContentPane().add(lblNewLabel_1);
		
		input_query = new JTextField();
		input_query.setBounds(10, 434, 429, 37);
		getContentPane().add(input_query);
		input_query.setColumns(10);
		
		output_results = new JTextArea();
		output_results.setBounds(469, 73, 717, 475);
		getContentPane().add(output_results);
		
		JLabel lblNewLabel_2 = new JLabel("Results : ");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_2.setBounds(793, 35, 57, 27);
		getContentPane().add(lblNewLabel_2);
		
		////////////// 	QUERY	////////////////////////////////////////////////////
		
		JButton ok_input_button = new JButton("OK");
		ok_input_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
					System.out.println("JDBC connection is established successfully\n");
				} catch (SQLException e1) {
					System.out.println("JDBC connection is not established\n");
					e1.printStackTrace();
				}
				
				try {
					query = input_query.getText();
					
					Statement stmt0 = con.createStatement();
					ResultSet rs0 = stmt0.executeQuery(query);
					ResultSetMetaData rsmd = rs0.getMetaData();

					int i = rsmd.getColumnCount();
					int count=1;

					while(rs0.next()) {
						
						while(count<=i) {
							if(rs0.getObject(count)!=null) {
							output_results.append(rs0.getObject(count).toString()+" ");
							count++;
							}
							else {
								break;
							}
						}
						output_results.append("\n");
						count=1;
					}
					stmt0.close();
	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
		ok_input_button.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
		ok_input_button.setBounds(157, 502, 84, 23);
		getContentPane().add(ok_input_button);
		
		JLabel lblNewLabel_3 = new JLabel("Account ID : ");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_3.setBounds(10, 65, 158, 27);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Type of transaction : ");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_4.setBounds(10, 103, 158, 27);
		getContentPane().add(lblNewLabel_4);
		
		input_account_id = new JTextField();
		input_account_id.setBounds(180, 70, 170, 20);
		getContentPane().add(input_account_id);
		input_account_id.setColumns(10);
		
		input_type_of_transaction = new JTextField();
		input_type_of_transaction.setBounds(180, 108, 170, 20);
		getContentPane().add(input_type_of_transaction);
		input_type_of_transaction.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Employee ID (if exists) : ");
		lblNewLabel_5.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_5.setBounds(10, 181, 158, 27);
		getContentPane().add(lblNewLabel_5);
		
		input_emp_id = new JTextField();
		input_emp_id.setBounds(180, 186, 170, 20);
		getContentPane().add(input_emp_id);
		input_emp_id.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Initial Date : ");
		lblNewLabel_6.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_6.setBounds(10, 219, 158, 27);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Deadline Date : ");
		lblNewLabel_7.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_7.setBounds(10, 257, 158, 27);
		getContentPane().add(lblNewLabel_7);
		
		intput_initial_date = new JTextField();
		intput_initial_date.setBounds(180, 224, 170, 20);
		getContentPane().add(intput_initial_date);
		intput_initial_date.setColumns(10);
		
		input_deadline_date = new JTextField();
		input_deadline_date.setBounds(180, 262, 170, 20);
		getContentPane().add(input_deadline_date);
		input_deadline_date.setColumns(10);
		
		JButton by_date_trans_button = new JButton("By Date Transactions");
		by_date_trans_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
					System.out.println("JDBC connection is established successfully\n");
					
					account_id=Long.parseLong(input_account_id.getText()) ;
					type_of_transaction=input_type_of_transaction.getText();
					type_of_user=input_type_of_user.getText();
					initial_date=intput_initial_date.getText();
					deadline_date=input_deadline_date.getText();
					
					String[] start_date=initial_date.split("/"); 
					String new_start_date_string=start_date[2]+""+start_date[1]+""+start_date[0];
					int new_start_date=Integer.parseInt(new_start_date_string);
					
					String[] end_date= deadline_date.split("/");
					String new_end_date_string=end_date[2]+""+end_date[1]+""+end_date[0];
					int new_end_date=Integer.parseInt(new_end_date_string);

					////////////////////	DEALER ACCOUNT ID //////////////////////////////////////
					
					if(type_of_user.equals("dealer")) {
						if(type_of_transaction.equals("charge")) {
							
							PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND dealer_acc_id= ? ");
							
							stmt0.setString(1,type_of_transaction);
							stmt0.setLong(2,account_id);
							
							ResultSet rs0 = stmt0.executeQuery();
							ResultSetMetaData rsmd = rs0.getMetaData();
							
							int i = rsmd.getColumnCount();
							int count=1;
							Object temp;
							Object date;
							
							String check_date[];
							int new_check_date;
							
							while(rs0.next()) {
								date=rs0.getObject(2);
								
								check_date=((String) date).split("/");
								String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
								new_check_date=Integer.parseInt(new_check_date_string);
						
								while(count<=i) {
									temp = rs0.getObject(count);
									if(temp!=null) {
										
										if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
											output_results.append(temp.toString()+"  //  ");
										}
									}
									else {
										break;
									}
									count++;
								}
								output_results.append("\n");
								count=1;
							}	
							stmt0.close();
						}
						else if(type_of_transaction.equals("credit")) {
							PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND dealer_acc_id= ? ");
							stmt0.setString(1,type_of_transaction);
							stmt0.setLong(2,account_id);
							
							ResultSet rs0 = stmt0.executeQuery();
							ResultSetMetaData rsmd = rs0.getMetaData();
							
							int i = rsmd.getColumnCount();
							int count=1;
							
							Object temp;
							Object date;
							
							String check_date[];
							int new_check_date;

							while(rs0.next()) {
								date=rs0.getObject(2);
								
								check_date=((String) date).split("/");
								String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
								new_check_date=Integer.parseInt(new_check_date_string);
						
								while(count<=i) {
									temp = rs0.getObject(count);
									if(temp!=null) {
										
										if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
											output_results.append(temp.toString()+"  //  ");
										}
									}
									else {
										break;
									}
									count++;
								}
								output_results.append("\n");
								count=1;
							}	
							stmt0.close();
						}
						else if(type_of_transaction.equals("all")) {
							
							PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE dealer_acc_id= ? ");
							stmt0.setLong(1,account_id);
							ResultSet rs0 = stmt0.executeQuery();
							ResultSetMetaData rsmd = rs0.getMetaData();
							
							int i = rsmd.getColumnCount();
							int count=1;
							Object temp;
							Object date;
							
							String check_date[];
							int new_check_date;
							
							while(rs0.next()) {
								
								date=rs0.getObject(2);
								
								check_date=((String) date).split("/");
								String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
								new_check_date=Integer.parseInt(new_check_date_string);
						
								while(count<=i) {
									temp = rs0.getObject(count);
									if(temp!=null) {
										
										if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
											output_results.append(temp.toString()+"  //  ");
										}
									}
									else {
										break;
									}
									count++;
								}
								output_results.append("\n");
								count=1;
							}	
							stmt0.close();
						}
					}
					//////////////////////	CLIENTS ACCOUNT ID /////////////////////////////////
					else if(type_of_user.equals("client")){
						
						if(!input_emp_id.getText().equals("")) {
							employee_id=Long.parseLong(input_emp_id.getText()) ;
							
							if(type_of_transaction.equals("charge")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND client_acc_id= ? AND corp_emp_id= ? ");
								
								stmt0.setString(1,type_of_transaction);
								stmt0.setLong(2,account_id);
								stmt0.setLong(3,employee_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;
								Object date;
								
								String check_date[];
								int new_check_date;
								
								while(rs0.next()) {
									date=rs0.getObject(2);
									
									check_date=((String) date).split("/");
									String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
									new_check_date=Integer.parseInt(new_check_date_string);
							
									while(count<=i) {
										temp = rs0.getObject(count);
										if(temp!=null) {
											
											if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
												output_results.append(temp.toString()+"  //  ");
											}
										}
										else {
											break;
										}
										count++;
									}
									output_results.append("\n");
									count=1;
								}	
								stmt0.close();
							}
							else if(type_of_transaction.equals("credit")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND client_acc_id= ? AND corp_emp_id= ? ");
								stmt0.setString(1,type_of_transaction);
								stmt0.setLong(2,account_id);
								stmt0.setLong(3,employee_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;
								Object date;
								
								String check_date[];
								int new_check_date;
								
								while(rs0.next()) {
									date=rs0.getObject(2);
									
									check_date=((String) date).split("/");
									String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
									new_check_date=Integer.parseInt(new_check_date_string);
														
									while(count<=i) {
										temp = rs0.getObject(count);
										if(temp!=null) {
											
											if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
												output_results.append(temp.toString()+"  //  ");
											}
										}
										else {
											break;
										}
										count++;
									}
									output_results.append("\n");
									count=1;
								}		
								stmt0.close();
							}
							else if(type_of_transaction.equals("all")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE client_acc_id= ? AND corp_emp_id= ? ");
								stmt0.setLong(1,account_id);
								stmt0.setLong(2,employee_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;
								Object date;
								
								String check_date[];
								int new_check_date;
								
								while(rs0.next()) {
									date=rs0.getObject(2);
									
									check_date=((String) date).split("/");
									String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
									new_check_date=Integer.parseInt(new_check_date_string);
									
									while(count<=i) {
										temp = rs0.getObject(count);
										if(temp!=null) {
											
											if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
												output_results.append(temp.toString()+"  //  ");
											}
										}
										else {
											break;
										}
										count++;
									}
									output_results.append("\n");
									count=1;
								}		
								stmt0.close();
							}
							
						}
						else {
							if(type_of_transaction.equals("charge")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND client_acc_id= ? ");
								
								stmt0.setString(1,type_of_transaction);
								stmt0.setLong(2,account_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;
								Object date;
								
								String check_date[];
								int new_check_date;
								
								while(rs0.next()) {
									date=rs0.getObject(2);
									
									check_date=((String) date).split("/");
									String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
									new_check_date=Integer.parseInt(new_check_date_string);
													
									while(count<=i) {
										temp = rs0.getObject(count);
										if(temp!=null) {
											
											if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
												output_results.append(temp.toString()+"  //  ");
											}
										}
										else {
											break;
										}
										count++;
									}
									output_results.append("\n");
									count=1;
								}		
								stmt0.close();
							}
							else if(type_of_transaction.equals("credit")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND client_acc_id= ? ");
								stmt0.setString(1,type_of_transaction);
								stmt0.setLong(2,account_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;

								Object date;							
								String check_date[];
								int new_check_date;
								
								while(rs0.next()) {
									date=rs0.getObject(2);
									
									check_date=((String) date).split("/");
									String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
									new_check_date=Integer.parseInt(new_check_date_string);
															
									while(count<=i) {
										temp = rs0.getObject(count);
										if(temp!=null) {
											
											if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
												output_results.append(temp.toString()+"  //  ");
											}
										}
										else {
											break;
										}
										count++;
									}
									output_results.append("\n");
									count=1;
								}		
								stmt0.close();
							}
							else if(type_of_transaction.equals("all")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE client_acc_id= ? ");
								stmt0.setLong(1,account_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;
								Object date;
								
								String check_date[];
								int new_check_date;
								
								while(rs0.next()) {
									date=rs0.getObject(2);
									
									check_date=((String) date).split("/");
									String new_check_date_string=check_date[2]+""+check_date[1]+""+check_date[0];
									new_check_date=Integer.parseInt(new_check_date_string);
							
									
									while(count<=i) {
										temp = rs0.getObject(count);
										if(temp!=null) {
											
											if((new_start_date<=new_check_date) && (new_check_date<=new_end_date)) {
												output_results.append(temp.toString()+"  //  ");
											}
										}
										else {
											break;
										}
										count++;
									}
									output_results.append("\n");
									count=1;
								}		
								stmt0.close();
							}
						}		
					}
	
				} catch (SQLException e1) {
					System.out.println("JDBC connection is not established\n");
					e1.printStackTrace();
				}				
			}
		});
		by_date_trans_button.setBounds(192, 309, 158, 23);
		getContentPane().add(by_date_trans_button);
		
		JButton all_trans_button = new JButton("All Transactions");
		all_trans_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
					System.out.println("JDBC connection is established successfully\n");
					
					
					account_id=Long.parseLong(input_account_id.getText()) ;
					type_of_transaction=input_type_of_transaction.getText();
					type_of_user=input_type_of_user.getText();
					
										
					////////////////////	DEALER ACCOUNT ID //////////////////////////////////////
					
					if(type_of_user.equals("dealer")) {
						
						if(type_of_transaction.equals("charge")) {
							
							PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND dealer_acc_id= ? ");
							
							stmt0.setString(1,type_of_transaction);
							stmt0.setLong(2,account_id);
							
							ResultSet rs0 = stmt0.executeQuery();
							ResultSetMetaData rsmd = rs0.getMetaData();
							
							int i = rsmd.getColumnCount();
							int count=1;
							Object temp;

							while(rs0.next()) {
								
								while(count<=i) {
									
									temp = rs0.getObject(count);
									if(temp!=null) {
										output_results.append(temp.toString()+"  //  ");
										count++;
									}
									else {
										break;
									}
								}
								output_results.append("\n");
								count=1;
							
							}	
							stmt0.close();
						}
						else if(type_of_transaction.equals("credit")) {
							
							PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND dealer_acc_id= ? ");
							stmt0.setString(1,type_of_transaction);
							stmt0.setLong(2,account_id);
							
							ResultSet rs0 = stmt0.executeQuery();
							ResultSetMetaData rsmd = rs0.getMetaData();
							
							int i = rsmd.getColumnCount();
							int count=1;
							Object temp;

							while(rs0.next()) {
								
								while(count<=i) {
									
									temp = rs0.getObject(count);
									if(temp!=null) {
										output_results.append(temp.toString()+"  //  ");
										count++;
									}
									else {
										break;
									}
								}
								output_results.append("\n");
								count=1;
							
							}	
							stmt0.close();
						}
						else if(type_of_transaction.equals("all")) {
							
							PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE dealer_acc_id= ? ");
							stmt0.setLong(1,account_id);
							
							ResultSet rs0 = stmt0.executeQuery();
							ResultSetMetaData rsmd = rs0.getMetaData();
							
							int i = rsmd.getColumnCount();
							int count=1;
							Object temp;

							while(rs0.next()) {
								
								while(count<=i) {
									
									temp = rs0.getObject(count);
									if(temp!=null) {
										output_results.append(temp.toString()+"  //  ");
										count++;
									}
									else {
										break;
									}
								}
								output_results.append("\n");
								count=1;
							
							}	
							stmt0.close();
						}
					}
					//////////////////////	CLIENTS ACCOUNT ID /////////////////////////////////
					else if(type_of_user.equals("client")){
						
						if(!input_emp_id.getText().equals("")) {
							
							employee_id=Long.parseLong(input_emp_id.getText()) ;
							
							if(type_of_transaction.equals("charge")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND client_acc_id= ? AND corp_emp_id= ? ");
								
								stmt0.setString(1,type_of_transaction);
								stmt0.setLong(2,account_id);
								stmt0.setLong(3,employee_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;

								while(rs0.next()) {
									
									while(count<=i) {
										
										temp = rs0.getObject(count);
										if(temp!=null) {
											output_results.append(temp.toString()+"  //  ");
											count++;
										}
										else {
											break;
										}
									}
									output_results.append("\n");
									count=1;
								}	
								stmt0.close();
							}
							else if(type_of_transaction.equals("credit")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND client_acc_id= ? AND corp_emp_id= ? ");
								stmt0.setString(1,type_of_transaction);
								stmt0.setLong(2,account_id);
								stmt0.setLong(3,employee_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;

								while(rs0.next()) {
									
									while(count<=i) {
										
										temp = rs0.getObject(count);
										if(temp!=null) {
											output_results.append(temp.toString()+"  //  ");
											count++;
										}
										else {
											break;
										}
									}
									output_results.append("\n");
									count=1;			
								}	
								stmt0.close();
							}
							else if(type_of_transaction.equals("all")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE client_acc_id= ? AND corp_emp_id= ? ");
								stmt0.setLong(1,account_id);
								stmt0.setLong(2,employee_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;

								while(rs0.next()) {
									
									while(count<=i) {
										
										temp = rs0.getObject(count);
										if(temp!=null) {
											output_results.append(temp.toString()+"  //  ");
											count++;
										}
										else {
											break;
										}
									}
									output_results.append("\n");
									count=1;
								}	
								stmt0.close();
							}
						}
						else {
							if(type_of_transaction.equals("charge")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND client_acc_id= ? ");
								
								stmt0.setString(1,type_of_transaction);
								stmt0.setLong(2,account_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;

								while(rs0.next()) {
									
									while(count<=i) {
										
										temp = rs0.getObject(count);
										if(temp!=null) {
											output_results.append(temp.toString()+"  //  ");
											count++;
										}
										else {
											break;
										}
									}
									output_results.append("\n");
									count=1;
								
								}	
								stmt0.close();
							}
							else if(type_of_transaction.equals("credit")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE type= ? AND client_acc_id= ? ");
								stmt0.setString(1,type_of_transaction);
								stmt0.setLong(2,account_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;

								while(rs0.next()) {
									
									while(count<=i) {
										
										temp = rs0.getObject(count);
										if(temp!=null) {
											output_results.append(temp.toString()+"  //  ");
											count++;
										}
										else {
											break;
										}
									}
									output_results.append("\n");
									count=1;
								}	
								stmt0.close();
							}
							else if(type_of_transaction.equals("all")) {
								
								PreparedStatement stmt0 = con.prepareStatement("SELECT * FROM transaction WHERE client_acc_id= ? ");
								stmt0.setLong(1,account_id);
								
								ResultSet rs0 = stmt0.executeQuery();
								ResultSetMetaData rsmd = rs0.getMetaData();
								
								int i = rsmd.getColumnCount();
								int count=1;
								Object temp;

								while(rs0.next()) {
									
									while(count<=i) {
										
										temp = rs0.getObject(count);
										if(temp!=null) {
											output_results.append(temp.toString()+"  //  ");
											count++;
										}
										else {
											break;
										}
									}
									output_results.append("\n");
									count=1;
								}	
								stmt0.close();
							}
						}
					}
	
				} catch (SQLException e1) {
					System.out.println("JDBC connection is not established\n");
					e1.printStackTrace();
				}				
			}
		});
		all_trans_button.setBounds(10, 309, 158, 23);
		getContentPane().add(all_trans_button);
		
		JLabel lblNewLabel_8 = new JLabel("Type of user : ");
		lblNewLabel_8.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_8.setBounds(10, 141, 158, 27);
		getContentPane().add(lblNewLabel_8);
		
		input_type_of_user = new JTextField();
		input_type_of_user.setBounds(180, 139, 170, 20);
		getContentPane().add(input_type_of_user);
		input_type_of_user.setColumns(10);
	}
}
