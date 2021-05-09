package paquete.vista;

import paquete.controlador.ControladorEmpleadoApp;
import paquete.modelo.Empleado;
import paquete.modelo.EmpleadoApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;

public class VentanaEmpleado extends JFrame {

	private JPanel contentPane;
	private ControladorEmpleadoApp controlador;
	public JLabel empleado;
	public JLabel box;
	public JLabel cliente;

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
		EmpleadoApp.getInstance().setControlador(controlador);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Empleado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		empleado = new JLabel("Empleado");
		panel.add(empleado);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		
		box = new JLabel("Box");
		panel_2.add(box);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		cliente = new JLabel("Cliente");
		panel_1.add(cliente);
		
		JButton BotonTomarTurno = new JButton("Tomar Turno");
		BotonTomarTurno.addActionListener(controlador);
		BotonTomarTurno.setActionCommand("TURNO");
		contentPane.add(BotonTomarTurno);
		this.setLocationRelativeTo(null);

		empleado.setText(JOptionPane.showInputDialog("Escriba su nombre"));
		box.setText(JOptionPane.showInputDialog("Escriba el box en el que se encuentra"));
		controlador.setEmpleadoApp(empleado.getText(),Integer.parseInt(box.getText()));
	}

	public void errorClientes(){
		JOptionPane.showMessageDialog(this,"No hay clientes en la cola");
	}

	public void errorServidor(){
		JOptionPane.showMessageDialog(this,"No se pudo conectar al servidor");
	}
}
