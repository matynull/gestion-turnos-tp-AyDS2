package paquete.vista;

import paquete.controlador.ControladorTVApp;
import paquete.modelo.Cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.table.DefaultTableModel;

public class VentanaTV extends JFrame {

	private JPanel PanelPrincipal;
	private JList<Cliente> jList;
	private ControladorTVApp controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTV frame = new VentanaTV();
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
	public VentanaTV() {
		this.controlador = new ControladorTVApp(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		PanelPrincipal = new JPanel();
		PanelPrincipal.setBorder(new TitledBorder(null, "TV", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PanelPrincipal.setLayout(new BorderLayout(0, 0));
		setContentPane(PanelPrincipal);

		this.jList = new JList<Cliente>();
		PanelPrincipal.add(this.jList, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		PanelPrincipal.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);

		JLabel LabelCliente = new JLabel("Cliente");
		panel_1.add(LabelCliente);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);

		JLabel LabelBox = new JLabel("Box");
		panel_2.add(LabelBox);
	}

	public void RefreshTable(LinkedList<Cliente> clientesAtendidos, LinkedList<Cliente> clientes){
		int i=0;
		if(!clientes.isEmpty()) {
			Cliente [] dnis = new Cliente[10];
			Iterator<Cliente> it= clientes.iterator();
			while (it.hasNext())
				dnis[i++]=it.next();
			jList.setListData(dnis);
		}
	}

}
