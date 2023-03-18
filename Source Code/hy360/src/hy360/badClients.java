package hy360;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Color;

public class badClients extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public JTextArea bad_individual_output;
	public JTextArea bad_corporation_output;
	public JTextArea bad_dealer_output;

	public badClients() {
		setTitle("CCC");
		setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 993, 628);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Individuals");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lblNewLabel.setBounds(103, 54, 110, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Corporations");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lblNewLabel_1.setBounds(433, 54, 110, 30);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Dealers");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lblNewLabel_2.setBounds(773, 54, 110, 30);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Bad Clients");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel_3.setBounds(420, 11, 148, 30);
		getContentPane().add(lblNewLabel_3);
		
		bad_individual_output = new JTextArea();
		bad_individual_output.setBounds(10, 95, 295, 479);
		getContentPane().add(bad_individual_output);
		
		bad_corporation_output = new JTextArea();
		bad_corporation_output.setBounds(340, 95, 295, 479);
		getContentPane().add(bad_corporation_output);
		
		bad_dealer_output = new JTextArea();
		bad_dealer_output.setBounds(669, 95, 295, 479);
		getContentPane().add(bad_dealer_output);
	}
}