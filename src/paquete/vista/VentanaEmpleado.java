package paquete.vista;

import paquete.controlador.ControladorEmpleadoApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

public class VentanaEmpleado extends JFrame {

	private JPanel contentPane;
	private ControladorEmpleadoApp controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEmpleado frame = new VentanaEmpleado();
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
	public VentanaEmpleado() {
		this.controlador = new ControladorEmpleadoApp(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Empleado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		JLabel LabelEmpleado = new JLabel("Empleado");
		panel.add(LabelEmpleado);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		
		JLabel LabelBox = new JLabel("Box");
		panel_2.add(LabelBox);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		JLabel LabelVacio = new JLabel("");
		panel_1.add(LabelVacio);
		
		JButton BotonTomarTurno = new JButton("Tomar Turno");
		contentPane.add(BotonTomarTurno);
	}

}
