package hy360;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Color;

public class goodClients extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public JTextArea individual_output;
	public JTextArea corporation_output;
	public JTextArea dealer_output;
	

	public goodClients() {
		setTitle("CCC");
		setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 765, 628);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Individuals");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lblNewLabel.setBounds(71, 54, 110, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Corporations");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lblNewLabel_1.setBounds(305, 54, 110, 30);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Dealers");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lblNewLabel_2.setBounds(584, 54, 110, 30);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Good Clients");
		lblNewLabel_3.setForeground(new Color(0, 128, 0));
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel_3.setBounds(291, 11, 148, 30);
		getContentPane().add(lblNewLabel_3);
		
		individual_output = new JTextArea();
		individual_output.setBounds(10, 95, 236, 479);
		getContentPane().add(individual_output);
		
		corporation_output = new JTextArea();
		corporation_output.setBounds(256, 95, 236, 479);
		getContentPane().add(corporation_output);
		
		dealer_output = new JTextArea();
		dealer_output.setBounds(502, 95, 236, 479);
		getContentPane().add(dealer_output);
	}
}
