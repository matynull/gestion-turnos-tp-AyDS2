package paquete.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Ventana extends JFrame {

	private JPanel PanelPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		PanelPrincipal = new JPanel();
		PanelPrincipal.setBorder(new TitledBorder(null, "Totem", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(PanelPrincipal);
		PanelPrincipal.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		PanelPrincipal.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 4, 0, 0));
		
		JButton btnNewButton1 = new JButton("1");
		panel.add(btnNewButton1);
		
		JButton btnNewButton2 = new JButton("2");
		panel.add(btnNewButton2);
		
		JButton btnNewButton3 = new JButton("3");
		panel.add(btnNewButton3);
		
		JButton btnNewButtonBorrar = new JButton("Borrar");
		panel.add(btnNewButtonBorrar);
		
		JButton btnNewButton4 = new JButton("4");
		panel.add(btnNewButton4);
		
		JButton btnNewButton5 = new JButton("5");
		panel.add(btnNewButton5);
		
		JButton btnNewButton6 = new JButton("6");
		panel.add(btnNewButton6);
		
		JLabel LabelVacio = new JLabel("");
		panel.add(LabelVacio);
		
		JButton btnNewButton7 = new JButton("7");
		panel.add(btnNewButton7);
		
		JButton btnNewButton8 = new JButton("8");
		panel.add(btnNewButton8);
		
		JButton btnNewButton_9 = new JButton("9");
		panel.add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("Registrarse");
		panel.add(btnNewButton_10);
		
		JTextPane textPane = new JTextPane();
		PanelPrincipal.add(textPane, BorderLayout.NORTH);
	}

}
