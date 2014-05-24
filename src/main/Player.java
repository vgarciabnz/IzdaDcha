package main;

import javax.swing.JPanel;	
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Font;
import java.util.Random;

public class Player extends JPanel implements Runnable, ChangeListener {
	
	private JButton rightButton;
	private JButton leftButton;
	private int numberLoops = 10;
	private JPanel centerPanel;
	private JLabel speaker;
	
	public boolean isLeft;
	private boolean buttonPushed = false;
	private int rightAnswers = 0;
	private int wrongAnswers = 0;
	private int timeOuts = 0;
	
	public Player() {
		setPreferredSize(new Dimension(600, 300));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		add(panel);
		
		leftButton = new JButton("");
		leftButton.setLocation(0, 0);
		leftButton.setSize(new Dimension(195, 300));
		leftButton.setMaximumSize(leftButton.getSize());
		leftButton.setEnabled(false);
		leftButton.addChangeListener(this);
		panel.setLayout(null);
		panel.add(leftButton);
		
		centerPanel = new JPanel();
		add(centerPanel);
		centerPanel.setLayout(null);
		
		speaker = new JLabel("New label");
		speaker.setOpaque(true);
		speaker.setFont(new Font("Dialog", Font.BOLD, 20));
		speaker.setHorizontalAlignment(SwingConstants.CENTER);
		speaker.setBounds(0, 0, 200, 300);
		centerPanel.add(speaker);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		rightButton = new JButton("");
		rightButton.setBounds(0, 0, 195, 300);
		rightButton.setEnabled(false);
		rightButton.addChangeListener(this);
		panel_1.setLayout(null);
		panel_1.add(rightButton);

		this.setVisible(true);
	}
	
	@Override
	public void run() {
		try {
			for(int i = 3; i > 0; i--){
				speaker.setText("Comienza en " + i);
				Thread.sleep(1000);
			}
			Random random = new Random(System.currentTimeMillis());
			for(int i = 0; i < numberLoops; i++){
				enableButtons();
				isLeft = random.nextBoolean();
				speaker.setOpaque(false);
				if (isLeft){
					speaker.setText("izquierda");
				} else {
					speaker.setText("derecha");
				}
				Thread.sleep(IzdaDcha.level);
				disableButtons();
				if(!buttonPushed){
					timeOuts++;
					speaker.setOpaque(true);
					speaker.setBackground(Color.ORANGE);
					speaker.setText("Tiempo");
				}
				buttonPushed = false;
				Thread.sleep(1000);
			}
			saveValues();		
		} catch (InterruptedException e) {
		}
	}
	
	public void enableButtons(){
		leftButton.setEnabled(true);
		rightButton.setEnabled(true);
	}
	
	public void disableButtons(){
		leftButton.setEnabled(false);
		rightButton.setEnabled(false);
	}
	
	private void saveValues(){
		IzdaDcha.log.info(rightAnswers + "-" + wrongAnswers + "-" + timeOuts);
	}
	
	@Override
	public void stateChanged (ChangeEvent arg0) {
		if (arg0.getSource().equals(leftButton) && leftButton.getModel().isRollover()  && isLeft){
			rightAnswers++;
			speaker.setOpaque(true);
			speaker.setBackground(Color.GREEN);
			speaker.setText("¡Correcto!");
			buttonPushed = true;
			disableButtons();
		} else if (arg0.getSource().equals(rightButton) && rightButton.getModel().isRollover() && !isLeft){
			rightAnswers++;
			speaker.setOpaque(true);
			speaker.setBackground(Color.GREEN);
			speaker.setText("¡Correcto!");
			buttonPushed = true;
			disableButtons();
		} else if ((arg0.getSource().equals(rightButton) && rightButton.getModel().isRollover())|| 
				(arg0.getSource().equals(leftButton) && leftButton.getModel().isRollover())){
			System.out.println("Fallo");
			speaker.setOpaque(true);
			speaker.setBackground(Color.RED);
			wrongAnswers++;
			buttonPushed = true;
			disableButtons();
		}
	}
	
}
