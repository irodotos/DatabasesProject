package hy360;

import java.util.ArrayList;
import java.sql.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JTextField;

public class Graphics {

	private JFrame frmCcc;
	private JTextField input_date;
	public static String current_date="null";

	/* Launch the application */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Graphics window = new Graphics();
					window.frmCcc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}    
	}

	/* Create the application */
	
	public Graphics() {
		initialize();
	}

	/*  Initialize the contents of the frame */
	
	private void initialize() {
		
		frmCcc = new JFrame();
		frmCcc.getContentPane().setForeground(Color.LIGHT_GRAY);
		frmCcc.setTitle("CCC");
		frmCcc.setResizable(false);
		frmCcc.getContentPane().setSize(new Dimension(600, 600));
		frmCcc.setBounds(100, 100, 570, 364);
		frmCcc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCcc.getContentPane().setLayout(null);
		
		JButton openAccount_button = new JButton("Open Account");
		openAccount_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new openAccount();
			}
		});
		openAccount_button.setBounds(116, 66, 146, 23);
		frmCcc.getContentPane().add(openAccount_button);
		
		JButton closeAccount_button = new JButton("Close Account");
		closeAccount_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new closeAccount();
			}
		});
		closeAccount_button.setBounds(302, 66, 146, 23);
		frmCcc.getContentPane().add(closeAccount_button);
		
		JButton buy_button = new JButton("Open Transaction");
		buy_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(current_date!="null") {
					new transaction();
				}
				else {
					JOptionPane.showMessageDialog(null , "Please enter a date \n");
				}
				
			}
		});
		buy_button.setBounds(116, 118, 146, 23);
		frmCcc.getContentPane().add(buy_button);
		
		
		JButton questions_button = new JButton("Questions");
		questions_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new questions();
			}
		});
		questions_button.setBounds(302, 217, 146, 23);
		frmCcc.getContentPane().add(questions_button);
		
		JButton pay_button = new JButton("Pay Dept");
		pay_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new pay();
			}
		});
		pay_button.setBounds(302, 118, 146, 23);
		frmCcc.getContentPane().add(pay_button);
		
/////////////////////////////////	GOOD	CLIENTS	//////////////////////////////////////////////////////
		
		JButton stateOfGoodClients_button = new JButton("Good Clients");
		stateOfGoodClients_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				goodClients good = new goodClients();
				
				Connection con = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
					System.out.println("JDBC connection is established successfully\n");
				} catch (SQLException e) {
					System.out.println("JDBC connection is not established\n");
				}
					
				try {
					
					////////////////	GOOD INDIVIDUAL CLIENT	//////////////////////////////////////
					
					ArrayList<String> good_indiv_name_list=new ArrayList<String>();
					ArrayList<Long> good_indiv_account_id_list=new ArrayList<Long>();
					
					String queryGoodClient =new String("SELECT account_id FROM individual WHERE amount_owed=0");
					
					Statement stmt0 = con.createStatement();
					ResultSet rs0 = stmt0.executeQuery(queryGoodClient);
					
					while(rs0.next()) {
						
						Long temp_account_id=rs0.getLong("account_id");
						
						good_indiv_account_id_list.add(temp_account_id);
						
						String queryGoodUser =new String("SELECT name FROM users WHERE account_id="+temp_account_id);
						
						Statement stmt1 = con.createStatement();
						ResultSet rs1 = stmt1.executeQuery(queryGoodUser);
						rs1.next();
						
						String temp_individual_name=rs1.getString("name");
						
						good_indiv_name_list.add(temp_individual_name);
											
						stmt1.close();
					}
					stmt0.close();
					
					
					int size0=good_indiv_name_list.size();
					int i=0;
					while(i<size0) {
						good.individual_output.append(good_indiv_name_list.get(i).toString()+" "+good_indiv_account_id_list.get(i).toString()+"\n");
						i++;
					}
					
					////////////////	GOOD CORPORATION CLIENT	//////////////////////////////////////
					
					ArrayList<String> good_corp_name_list=new ArrayList<String>();
					ArrayList<Long> good_corp_account_id_list=new ArrayList<Long>();
					
					String queryGoodClient1 =new String("SELECT account_id FROM corporation WHERE amount_owed=0");
					
					Statement stmt2 = con.createStatement();
					ResultSet rs2 = stmt2.executeQuery(queryGoodClient1);
					
					while(rs2.next()) {
						
						Long temp_account_id1=rs2.getLong("account_id");
						
						good_corp_account_id_list.add(temp_account_id1);
						
						String queryGoodUser1 =new String("SELECT name FROM users WHERE account_id="+temp_account_id1+" ");
						
						Statement stmt3 = con.createStatement();
						ResultSet rs3 = stmt3.executeQuery(queryGoodUser1);
						rs3.next();
						
						String temp_corporation_name=rs3.getString("name");
						
						good_corp_name_list.add(temp_corporation_name);
													
						stmt3.close();
					}
					stmt2.close();
					
					int size1=good_corp_name_list.size();
					int j=0;
					while(j<size1) {
						good.corporation_output.append(good_corp_name_list.get(j).toString()+" "+good_corp_account_id_list.get(j).toString()+"\n");
						j++;
					}
					
					
					////////////////	GOOD DEALER	CLIENT	//////////////////////////////////////
					
					ArrayList<String> good_dealer_name_list=new ArrayList<String>();
					ArrayList<Long> good_dealer_account_id_list=new ArrayList<Long>();
					
					String queryGoodClient2 =new String("SELECT account_id FROM dealer WHERE amount_owed=0");
					
					Statement stmt4 = con.createStatement();
					ResultSet rs4 = stmt4.executeQuery(queryGoodClient2);
					
					while(rs4.next()) {
						
						Long temp_account_id2=rs4.getLong("account_id");
						
						good_dealer_account_id_list.add(temp_account_id2);
						
						String queryGoodUser2 =new String("SELECT name FROM users WHERE account_id="+temp_account_id2+" ");
						
						Statement stmt5 = con.createStatement();
						ResultSet rs5 = stmt5.executeQuery(queryGoodUser2);
						rs5.next();
						
						String temp_dealer_name=rs5.getString("name");
						
						good_dealer_name_list.add(temp_dealer_name);
						
						stmt5.close();
					}
					stmt4.close();
					
					int size2=good_dealer_name_list.size();
					int k=0;
					while(k<size2) {
						good.dealer_output.append(good_dealer_name_list.get(k).toString()+" "+good_dealer_account_id_list.get(k).toString()+"\n");
						k++;
					}
					
					//////////////////////////////////////////////////////////////////////////////////////
					

				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null ,"Error at GOOD client");
					e.printStackTrace();
				}
				
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		stateOfGoodClients_button.setBounds(116, 176, 146, 23);
		frmCcc.getContentPane().add(stateOfGoodClients_button);
		
		
/////////////////////////////////	BAD	CLIENTS //////////////////////////////////////////////////////////
		
		JButton stateOfBadClients_button = new JButton("Bad Clients");
		stateOfBadClients_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				badClients bad= new badClients();
				
				Connection con = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
					System.out.println("JDBC connection is established successfully\n");
				} catch (SQLException e) {
					System.out.println("JDBC connection is not established\n");
				}		
				
				try {
					
					////////////////	BAD INDIVIDUAL CLIENT	//////////////////////////////////////
					
					ArrayList<String> bad_individual_name_list=new ArrayList<String>();
					ArrayList<Long> bad_individual_account_id_list=new ArrayList<Long>();
					ArrayList<String> bad_individual_dept_list=new ArrayList<String>();
					
					String queryBadClient =new String("SELECT account_id FROM individual WHERE amount_owed>0 ORDER BY amount_owed");
					
					Statement stmt0 = con.createStatement();
					ResultSet rs0 = stmt0.executeQuery(queryBadClient);
					
					while(rs0.next()) {
						
						Long temp_account_id=rs0.getLong("account_id");
						
						bad_individual_account_id_list.add(temp_account_id);
						
						String querybadUser =new String("SELECT name FROM users WHERE account_id="+temp_account_id);
						String querybadUser_dept =new String("SELECT amount_owed FROM individual WHERE account_id="+temp_account_id);
						
						Statement stmt1 = con.createStatement();
						ResultSet rs1 = stmt1.executeQuery(querybadUser);
						rs1.next();
						
						Statement stmt_dept = con.createStatement();
						ResultSet rs_dept = stmt_dept.executeQuery(querybadUser_dept);
						rs_dept.next();
						
						String temp_individual_name=rs1.getString("name");
						String temp_individual_dept=rs_dept.getString("amount_owed");
						
						bad_individual_name_list.add(temp_individual_name);
						bad_individual_dept_list.add(temp_individual_dept);
						
						stmt1.close();
					}
					stmt0.close();
					
					int size0=bad_individual_name_list.size();
					int i=0;
					while(i<size0) {
						bad.bad_individual_output.append(bad_individual_name_list.get(i).toString()+" // "+bad_individual_account_id_list.get(i).toString()+" //  "+bad_individual_dept_list.get(i).toString()+"$ \n");
						i++;
					}					
							
					////////////////	BAD CORPORATION CLIENT	//////////////////////////////////////
					
					ArrayList<String> bad_corporation_name_list=new ArrayList<String>();
					ArrayList<Long> bad_corporation_account_id_list=new ArrayList<Long>();
					ArrayList<String> bad_corporation_dept_list=new ArrayList<String>();
					
					String queryBadClient1 =new String("SELECT account_id FROM corporation WHERE amount_owed>0 ORDER BY amount_owed");
					
					Statement stmt2 = con.createStatement();
					ResultSet rs2 = stmt2.executeQuery(queryBadClient1);
					
					while(rs2.next()) {
						
						Long temp_account_id1=rs2.getLong("account_id");
						
						bad_corporation_account_id_list.add(temp_account_id1);
						
						String queryBadUser1 =new String("SELECT name FROM users WHERE account_id="+temp_account_id1+" ");
						String querybadUser_dept1 =new String("SELECT amount_owed FROM corporation WHERE account_id="+temp_account_id1);
						
						Statement stmt3 = con.createStatement();
						ResultSet rs3 = stmt3.executeQuery(queryBadUser1);
						rs3.next();
						
						Statement stmt_dept1 = con.createStatement();
						ResultSet rs_dept1 = stmt_dept1.executeQuery(querybadUser_dept1);
						rs_dept1.next();
						
						String temp_corporation_name=rs3.getString("name");
						String temp_corporation_dept=rs_dept1.getString("amount_owed");
						
						bad_corporation_name_list.add(temp_corporation_name);
						bad_corporation_dept_list.add(temp_corporation_dept);
						
						stmt3.close();
					}
					stmt2.close();
					
					int size1=bad_corporation_name_list.size();
					int j=0;
					while(j<size1) {
						bad.bad_corporation_output.append(bad_corporation_name_list.get(j).toString()+" // "+bad_corporation_account_id_list.get(j).toString()+" //  "+bad_corporation_dept_list.get(j).toString()+"$ \n");
						j++;
					}	
					
				
					////////////////	BAD DEALER	CLIENT	//////////////////////////////////////
					
					ArrayList<String> bad_dealer_name_list=new ArrayList<String>();
					ArrayList<Long> bad_dealer_account_id_list=new ArrayList<Long>();
					ArrayList<String> bad_dealer_dept_list=new ArrayList<String>();
					
					String querybadClient2 =new String("SELECT account_id FROM dealer WHERE amount_owed>0 ORDER BY amount_owed");
					
					Statement stmt4 = con.createStatement();
					ResultSet rs4 = stmt4.executeQuery(querybadClient2);
					
					while(rs4.next()) {
						
						Long temp_account_id2=rs4.getLong("account_id");
						
						bad_dealer_account_id_list.add(temp_account_id2);
						
						String queryBadUser2 =new String("SELECT name FROM users WHERE account_id="+temp_account_id2);
						String querybadUser_dept2 =new String("SELECT amount_owed FROM dealer WHERE account_id="+temp_account_id2);
						
						Statement stmt5 = con.createStatement();
						ResultSet rs5 = stmt5.executeQuery(queryBadUser2);
						rs5.next();
						
						Statement stmt_dept2 = con.createStatement();
						ResultSet rs_dept2 = stmt_dept2.executeQuery(querybadUser_dept2);
						rs_dept2.next();
						
						String temp_dealer_name=rs5.getString("name");
						String temp_dealer_dept=rs_dept2.getString("amount_owed");
						
						bad_dealer_name_list.add(temp_dealer_name);
						bad_dealer_dept_list.add(temp_dealer_dept);
						
						stmt5.close();
					}
					stmt4.close();
					
					int size2=bad_dealer_name_list.size();
					int k=0;
					while(k<size2) {
						bad.bad_dealer_output.append(bad_dealer_name_list.get(k).toString()+" // "+bad_dealer_account_id_list.get(k).toString()+" //  "+bad_dealer_dept_list.get(k).toString()+"$ \n");
						k++;
					}
				
					//////////////////////////////////////////////////////////////////////////////////////
				

				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null ,"Error at BAD client");
					e.printStackTrace();
				}
				
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		stateOfBadClients_button.setBounds(302, 176, 146, 23);
		frmCcc.getContentPane().add(stateOfBadClients_button);
		
		//////////////////////////		DATE BUTTON		///////////////////////////
		JButton date_ok_button = new JButton("OK");
		date_ok_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				current_date=input_date.getText();
				
			}
		});
		date_ok_button.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
		date_ok_button.setBounds(156, 275, 66, 21);
		frmCcc.getContentPane().add(date_ok_button);
		
		//////////////////////////	MONTH DEALER		///////////////////////////
		JButton bestDealer_button = new JButton("Month Dealer");
		bestDealer_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(current_date!="null") {
					dealerOfTheMonth top_dealer=new dealerOfTheMonth();
					
					int previous_date=0;
					
					if(current_date.equals("null")) {
						JOptionPane.showMessageDialog(null ,"Please enter a date");
					}
					else {
						String[] date=current_date.split("/");  
						
						int month=Integer.parseInt(date[1]);
						int year=Integer.parseInt(date[2]);
						
						if(month>12) {
							JOptionPane.showMessageDialog(null ,"Please enter a valid date");
						}
						else if(month==1) {
							previous_date=12;
							year=year-1;
						}
						else {
							previous_date=month-1;
						}
						
						Connection con = null;
						try {
							con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
							System.out.println("JDBC connection is established successfully\n");
							
							String new_month=String.valueOf(previous_date);
							String new_year=String.valueOf(year);
							
							
							String new_date_0="1/"+new_month+"/"+new_year;
							String new_date_1="31/"+new_month+"/"+new_year;

				
							PreparedStatement stmt0 = con.prepareStatement("SELECT dealer_acc_id,COUNT(dealer_acc_id) as month_dealer FROM transaction "
									+ "WHERE type='charge' AND date> ? AND date< ? GROUP BY dealer_acc_id ORDER BY month_dealer DESC");
							
							stmt0.setString(1,new_date_0);
							stmt0.setString(2,new_date_1);
							
							ResultSet rs0=stmt0.executeQuery();
							rs0.next();
				
							dealerOfTheMonth.dealer_of_the_month=rs0.getLong("dealer_acc_id");
							
							String dealer_of_the_month_string=String.valueOf(dealerOfTheMonth.dealer_of_the_month);
							
							top_dealer.id_area.append(dealer_of_the_month_string);
						
						} catch (SQLException e) {
							System.out.println("JDBC connection is not established\n");
							e.printStackTrace();
						}
					
					}
				}
				else {
					JOptionPane.showMessageDialog(null , "Please enter a date \n");
				}
				
			}
		});
		bestDealer_button.setBounds(116, 217, 146, 23);
		frmCcc.getContentPane().add(bestDealer_button);
		
		JLabel lblNewLabel = new JLabel("CCC APPLICATION");
		lblNewLabel.setFont(new Font("Lucida Console", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel.setBounds(156, 11, 240, 27);
		frmCcc.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Accounts : ");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 66, 86, 23);
		frmCcc.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Transactions :");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 118, 86, 23);
		frmCcc.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("General :");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_3.setBounds(10, 176, 86, 19);
		frmCcc.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Date : ");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_4.setBounds(10, 273, 47, 23);
		frmCcc.getContentPane().add(lblNewLabel_4);
		
		input_date = new JTextField();
		input_date.setBounds(55, 276, 86, 20);
		frmCcc.getContentPane().add(input_date);
		input_date.setColumns(10);
	}
}
