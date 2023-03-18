package hy360;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;

public class openAccount extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField input_Name;
	private JTextField input_Ind_Corp;
	private JTextField input_AccountNumber;
	private JTextField input_ExpiredDate;
	private JTextField input_CreditLimit;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	
	public String name;
	public String individual_corporation ;
	public long accountNumber ;
	public double creditLimit ;
	public String expiredDate;


	/* Create the frame */
	
	public openAccount() {
		setTitle("CCC");
		setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 461, 526);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 444, 621);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		
		////////////////////////////////////////////////////////////////////
		
		JButton finish_button = new JButton("Finish");
		finish_button.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
		finish_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 Connection con = null;
					try {
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc","root","");
						System.out.println("JDBC connection is established successfully\n");
					} catch (SQLException e) {
						System.out.println("JDBC connection is not established\n");
					}
				
				name = input_Name.getText();
				individual_corporation = input_Ind_Corp.getText() ;
				
				accountNumber = Long.parseLong(input_AccountNumber.getText()) ;
				expiredDate = input_ExpiredDate.getText();
				creditLimit = Double.parseDouble(input_CreditLimit.getText());
				

				if(individual_corporation.equals("individual")) {
								
					try {
						
						PreparedStatement stmt0=con.prepareStatement("INSERT INTO `users` (`account_id`,`name`,`type`) VALUES("+accountNumber+",?,?)");
						
						stmt0.setString(1,name); 
						stmt0.setString(2,"individual");
						
						PreparedStatement stmt1 = con.prepareStatement("INSERT INTO `individual` (`account_id`, `expiration_date`, `amount_owed`, `balance`, `credit_limit`) "
								+ "VALUES ("+ accountNumber +", ? ,"+ 0 +"," + creditLimit +","+ creditLimit+")");
						
						stmt1.setString(1,expiredDate);
					
						stmt0.executeUpdate();
						stmt1.executeUpdate(); 
						stmt0.close();
						stmt1.close();
						
						JOptionPane.showMessageDialog(null , "Done");
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					} 
					
				}
				else if(individual_corporation.equals("corporation")) {
					
					try {
						
						PreparedStatement stmt2=con.prepareStatement("INSERT INTO `users` (`account_id`,`name`,`type`) VALUES("+accountNumber+",?,?)");
						
						stmt2.setString(1,name); 
						stmt2.setString(2,"corporation");
						
						PreparedStatement stmt3 = con.prepareStatement("INSERT INTO `corporation` (`account_id`, `expiration_date`, `amount_owed`, `balance`, `credit_limit`) "
								+ "VALUES ("+ accountNumber +", ? ,"+ 0 +"," + creditLimit +","+creditLimit+")");
						
						stmt3.setString(1,expiredDate);
						
						stmt2.executeUpdate();
						stmt3.executeUpdate(); 
						stmt2.close();
						stmt3.close();
						
						JOptionPane.showMessageDialog(null , "Done");
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null ,"Please type individual or corporation");
				}
				
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				setVisible(false);
			}
		});
		
		finish_button.setBounds(232, 427, 183, 31);
		panel.add(finish_button);
		
		////////////////////////////////////////////////////////////////////
		
		
		JButton back_button = new JButton("Back");
		back_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		back_button.setBounds(11, 420, 89, 46);
		panel.add(back_button);
		
		input_Name = new JTextField();
		input_Name.setBounds(180, 64, 235, 20);
		panel.add(input_Name);
		input_Name.setColumns(10);
		
		input_Ind_Corp = new JTextField();
		input_Ind_Corp.setBounds(180, 95, 235, 20);
		panel.add(input_Ind_Corp);
		input_Ind_Corp.setColumns(10);
		
		input_AccountNumber = new JTextField();
		input_AccountNumber.setBounds(180, 140, 235, 20);
		panel.add(input_AccountNumber);
		input_AccountNumber.setColumns(10);
		
		input_ExpiredDate = new JTextField();
		input_ExpiredDate.setBounds(180, 175, 235, 20);
		panel.add(input_ExpiredDate);
		input_ExpiredDate.setColumns(10);
		
		input_CreditLimit = new JTextField();
		input_CreditLimit.setBounds(180, 220, 235, 20);
		panel.add(input_CreditLimit);
		input_CreditLimit.setColumns(10);
		
		lblNewLabel = new JLabel("Name :");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 64, 160, 20);
		panel.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("New Account");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1.setBounds(144, 11, 183, 20);
		panel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Individual or Corporation :");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 95, 171, 20);
		panel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("AccountNumber :");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_3.setBounds(10, 140, 160, 20);
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Expired Date :");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_4.setBounds(10, 175, 160, 20);
		panel.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Credit Limit :");
		lblNewLabel_5.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_5.setBounds(10, 220, 90, 20);
		panel.add(lblNewLabel_5);
	}
}
