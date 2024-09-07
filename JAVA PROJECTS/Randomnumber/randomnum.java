import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame1 extends JFrame {
    private int randomNumber;
    private int attempts;
    private int maxAttempts = 10;
    private int score;
    private JTextField guessField;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public NumberGuessingGame1() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel promptLabel = new JLabel("Guess a number between 1 and 100:");
        promptLabel.setBounds(50, 50, 300, 20);
        add(promptLabel);

        guessField = new JTextField();
        guessField.setBounds(50, 80, 100, 20);
        add(guessField);

        JButton guessButton = new JButton("Guess");
        guessButton.setBounds(160, 80, 80, 20);
        add(guessButton);

        feedbackLabel = new JLabel("");
        feedbackLabel.setBounds(50, 110, 300, 20);
        add(feedbackLabel);

        attemptsLabel = new JLabel("Attempts left: " + maxAttempts);
        attemptsLabel.setBounds(50, 140, 300, 20);
        add(attemptsLabel);

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(50, 170, 300, 20);
        add(scoreLabel);

        guessButton.addActionListener(new GuessButtonListener());

        startNewRound();
    }

    private void startNewRound() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        attempts = 0;
        feedbackLabel.setText("");
        attemptsLabel.setText("Attempts left: " + maxAttempts);
        guessField.setText("");
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;
                if (guess == randomNumber) {
                    feedbackLabel.setText("Correct! The number was " + randomNumber);
                    score++;
                    scoreLabel.setText("Score: " + score);
                    int response = JOptionPane.showConfirmDialog(null, "Play again?", "Round Over", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        startNewRound();
                    } else {
                        System.exit(0);
                    }
                } else if (guess < randomNumber) {
                    feedbackLabel.setText("Too low!");
                } else {
                    feedbackLabel.setText("Too high!");
                }
                attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
                if (attempts >= maxAttempts) {
                    feedbackLabel.setText("Out of attempts! The number was " + randomNumber);
                    int response = JOptionPane.showConfirmDialog(null, "Play again?", "Round Over", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        startNewRound();
                    } else {
                        System.exit(0);
                    }
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGuessingGame1 game = new NumberGuessingGame1();
            game.setVisible(true);
        });
    }
}
