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
		int number = Integer.parseInt(JOptionPane.showInputDialog("Enter a number: "));

		try {

			BufferedReader br;

			Random rand = new Random();
			int ran = rand.nextInt(3999);

			for (int i = 0; i < number; i++) {
				br = new BufferedReader(new FileReader("src/dictionary.txt"));
				for (int j = 0; j < ran; j++) {
					br.readLine();
					list.add(br.readLine());
				}
				br.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int num = list.size();
		for (int i = 0; i < list.size(); i++) {
			h.stack.push(list.get(i));
		}

		if (h.stack.size() == num) {
			h.setup();
		}

	}

	void setup() {

		word = stack.pop();

		for (int i = 0; i < word.length(); i++) {
			label += "_";
		}

		label1.setText("guess a letter");
		wordLabel.setText(label);
		label2.setText("you have " + lives + " lives left");
		label3.setText("you have solved " + wordsSolved + " words");

		frame.pack();

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
