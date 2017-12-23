import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Hangman implements KeyListener {

	Stack<String> stack;
	JFrame frame;
	JPanel panel;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel wordLabel;

	String word;
	String label;

	int lives;
	int wordsSolved;
	int number;

	Hangman() {

		stack = new Stack<String>();
		frame = new JFrame();
		panel = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		wordLabel = new JLabel();

		word = "";
		label = "";

		lives = 10;
		wordsSolved = 0;
		
		frame.addKeyListener(this);
		frame.add(panel);
		frame.setVisible(true);
		
		panel.add(label1);
		panel.add(wordLabel);
		panel.add(label2);
		panel.add(label3);

	}

	public static void main(String[] args) {

		Hangman h = new Hangman();

		ArrayList<String> list = new ArrayList<String>();
		h.number = Integer.parseInt(JOptionPane.showInputDialog("Enter a number: "));

		try {

			BufferedReader br;

			Random rand = new Random();
			int ran = rand.nextInt(2999);

			for (int i = 0; i < h.number; i++) {
				br = new BufferedReader(new FileReader("src/dictionary.txt"));
				for (int j = 0; j < ran; j++) {
					br.readLine();
				}
				list.add(br.readLine());
				ran = rand.nextInt(2999);
				br.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < list.size(); i++) {
			h.stack.push(list.get(i));
		}

		h.setup();

	}

	void setup() {

		if (!stack.isEmpty()) {
			word = stack.pop();
	
			for (int i = 0; i < word.length(); i++) {
				label += "_";
			}
	
			label1.setText("guess a letter");
			wordLabel.setText(label);
			label2.setText("you have " + lives + " lives left");
			label3.setText("you have solved " + wordsSolved + " words");
	
			frame.pack();
		} else if (number <= 3){
			JOptionPane.showMessageDialog(null, "You win, but you only had " + number + " words");
		} else if (number == 4 || number == 5) {
			JOptionPane.showMessageDialog(null, "You win, with " + number + " words. eh");
		} else if (number > 5 && number < 10) {
			JOptionPane.showMessageDialog(null, "You win and solved " + number + "words");
		} else if (number >= 10 && number < 20){
			JOptionPane.showMessageDialog(null, "You win! Impressive, you solved " + number + " words");
		} else if (number > 20) {
			JOptionPane.showMessageDialog(null, "YAY! you solved " + number + " words! YOU WIN!!");
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (word.contains("" + e.getKeyChar()) && lives > 0) {
			String label2 = "";
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == e.getKeyChar()) {
					label2 += e.getKeyChar();
				} else if (label.charAt(i) != '_') {
					label2 += word.charAt(i);
				} else {
					label2 += "_";
				}
			}
			label = label2;
			wordLabel.setText(label);
			if (!label.contains("_")) {
				JOptionPane.showMessageDialog(null, "SOLVED");
				lives = 10;
				wordsSolved++;
				label = "";
				word = "";
				setup();
			}
		} else if (lives > 0) {
			lives -= 1;
			if (lives == 0) {
				wordLabel.setText(word);
				JOptionPane.showMessageDialog(null, "game over");
			}
			label2.setText("you have " + lives + " lives left");
		}
		frame.pack();
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
