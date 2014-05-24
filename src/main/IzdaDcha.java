package main;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JComboBox;

public class IzdaDcha {
	
	private static IzdaDcha instance;
	private static Player player;
	private static Thread playerThread;
	private JButton startButton;
	private JButton stopButton;
	public JFrame frame;
	public JComboBox levelComboBox;
	public static String logFile;
	public static Logger log;
	
	public static int level;
	public Level[] levels = {new Level("Bajo",2000), new Level("Medio",1000), 
			new Level("Alto",500), new Level("Muy alto",300)};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		instance = new IzdaDcha();
		instance.init();
	}
	
	private void init(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600, 400));
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(panel);
		
		startButton = new JButton("Iniciar");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listenerStart();
			}
		});
		panel.add(startButton);
		
		stopButton = new JButton("Borrar");
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				listenerStop();
			}
		});
		stopButton.setEnabled(false);
		panel.add(stopButton);
		
		JLabel levelLabel = new JLabel("Nivel:");
		panel.add(levelLabel);
		
		levelComboBox = new JComboBox();
		levelComboBox.setModel(new DefaultComboBoxModel(levels));
		levelComboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				level = ((Level) levelComboBox.getSelectedItem()).milliseconds;
			}
			
		});
		levelComboBox.setSelectedIndex(0);
		panel.add(levelComboBox);
		

		JButton chartButton = new JButton("Estadísticas");
		chartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread chart = new Thread(new Charter());
				chart.start();
			}
		});
		panel.add(chartButton);

		String name = JOptionPane.showInputDialog("Introduce tu nombre");
		createLog(name);
		
		JLabel nameLabel = new JLabel("Nombre: ");
		panel.add(nameLabel);
		JLabel nameText = new JLabel(name);
		panel.add(nameText);
		
		//frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void createLog (String name){
		log = Logger.getLogger("main");
		try {
			FileHandler fh = new FileHandler(name + ".log", true);
			logFile = name + ".log";
			fh.setFormatter(new MyLoggerFormatter());
			log.addHandler(fh);
			log.setUseParentHandlers(false);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	private void listenerStart(){
		if (player == null){
			player = new Player();
			frame.getContentPane().add(player);
			frame.revalidate();
			playerThread = new Thread(player);
			playerThread.start();
			stopButton.setEnabled(true);
			startButton.setEnabled(false);
		}
	}
	
	private void listenerStop(){
		if(player != null){
			playerThread.interrupt();
			playerThread = null;
			frame.getContentPane().remove(player);
			frame.revalidate();
			player = null;
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
		}
	}
}
