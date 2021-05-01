package paquete.vista;

import paquete.controlador.ControladorTotemApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;

public class VentanaTotem extends JFrame implements ActionListener {

	private JPanel PanelPrincipal;
	private ControladorTotemApp controlador;
	private JTextField textField;
	private JButton botonR;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTotem frame = new VentanaTotem();
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
	public VentanaTotem() {
		this.controlador = new ControladorTotemApp(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		PanelPrincipal = new JPanel();
		PanelPrincipal.setBorder(new TitledBorder(null, "Totem", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(PanelPrincipal);
		PanelPrincipal.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		PanelPrincipal.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 3, 0, 0));
		
		JButton btnNewButton1 = new JButton("1");
		btnNewButton1.addActionListener(this);
		btnNewButton1.setActionCommand("1");
		panel.add(btnNewButton1);
		
		JButton btnNewButton2 = new JButton("2");
		btnNewButton2.addActionListener(this);
		btnNewButton2.setActionCommand("2");
		panel.add(btnNewButton2);
		
		JButton btnNewButton3 = new JButton("3");
		btnNewButton3.addActionListener(this);
		btnNewButton3.setActionCommand("3");
		panel.add(btnNewButton3);
		
		JButton btnNewButton4 = new JButton("4");
		btnNewButton4.addActionListener(this);
		btnNewButton4.setActionCommand("4");
		panel.add(btnNewButton4);
		
		JButton btnNewButton5 = new JButton("5");
		btnNewButton5.addActionListener(this);
		btnNewButton5.setActionCommand("5");
		panel.add(btnNewButton5);
		
		JButton btnNewButton6 = new JButton("6");
		btnNewButton6.addActionListener(this);
		btnNewButton6.setActionCommand("6");
		panel.add(btnNewButton6);
		
		JButton btnNewButton7 = new JButton("7");
		btnNewButton7.addActionListener(this);
		btnNewButton7.setActionCommand("7");
		panel.add(btnNewButton7);
		
		JButton btnNewButton8 = new JButton("8");
		btnNewButton8.addActionListener(this);
		btnNewButton8.setActionCommand("8");
		panel.add(btnNewButton8);
		
		JButton btnNewButton_9 = new JButton("9");
		btnNewButton_9.addActionListener(this);
		btnNewButton_9.setActionCommand("9");
		panel.add(btnNewButton_9);

		JButton btnNewButtonBorrar = new JButton("Borrar");
		btnNewButtonBorrar.addActionListener(this);
		btnNewButtonBorrar.setActionCommand("BORRAR");
		panel.add(btnNewButtonBorrar);

		JButton btnNewButton0 = new JButton("0");
		btnNewButton0.addActionListener(this);
		btnNewButton0.setActionCommand("0");
		panel.add(btnNewButton0);
		
		JButton btnNewButton_10 = new JButton("Registrarse");
		this.botonR =btnNewButton_10;
		btnNewButton_10.addActionListener(controlador);
		btnNewButton_10.setEnabled(false);
		btnNewButton_10.setActionCommand("REGISTRO");
		panel.add(btnNewButton_10);
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		this.textField=textField;
		PanelPrincipal.add(textField, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(textField.getText().equals("Registro exitoso!!!") || textField.getText().equals("No se logro registrar exitosamente"))
			textField.setText("");
		if(e.getActionCommand().equals("BORRAR")){
			if(textField.getText().length()>0)
				textField.setText(textField.getText().substring(0,textField.getText().length()-1));
		}else {
			textField.setText(textField.getText() + e.getActionCommand());
		}
		if(textField.getText().length()==8)
			botonR.setEnabled(true);
		else
			botonR.setEnabled(false);
	}

	public String getDNI(){
		String text = textField.getText();
		return text;
	}

	public void RegistroExitoso(){
		textField.setText("Registro exitoso!!!");

	}
	public void RegistroFallido(){
		textField.setText("No se logro registrar exitosamente");
	}
}
