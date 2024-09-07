import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class QuizApplication extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeLimit = 10; // seconds
    private Timer timer;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private JTextArea resultArea;

    public QuizApplication() {
        questions = new ArrayList<>();
        loadQuestions();

        setTitle("Quiz Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 400, 20);
        add(questionLabel);

        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBounds(50, 80 + (i * 30), 400, 20);
            optionsGroup.add(optionButtons[i]);
            add(optionButtons[i]);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(50, 220, 100, 20);
        add(submitButton);

        timerLabel = new JLabel("Time left: " + timeLimit);
        timerLabel.setBounds(200, 220, 100, 20);
        add(timerLabel);

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(50, 250, 100, 20);
        add(scoreLabel);

        resultArea = new JTextArea();
        resultArea.setBounds(50, 280, 400, 60);
        resultArea.setEditable(false);
        add(resultArea);

        submitButton.addActionListener(new SubmitButtonListener());

        displayQuestion();
        startTimer();
    }

    private void loadQuestions() {
        questions.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, 0));
        questions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1));
        questions.add(new Question("What is the largest planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 2));
        // Add more questions as needed
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);
            questionLabel.setText(q.question);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(q.options[i]);
            }
            optionsGroup.clearSelection();
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int timeLeft = timeLimit;

            @Override
            public void run() {
                if (timeLeft > 0) {
                    timerLabel.setText("Time left: " + --timeLeft);
                } else {
                    timer.cancel();
                    handleSubmit();
                }
            }
        }, 0, 1000);
    }

    private void handleSubmit() {
        Question q = questions.get(currentQuestionIndex);
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected() && i == q.correctAnswer) {
                score++;
                break;
            }
        }
        scoreLabel.setText("Score: " + score);
        currentQuestionIndex++;
        displayQuestion();
        startTimer();
    }

    private void endQuiz() {
        timer.cancel();
        resultArea.setText("Quiz Over! Your final score is: " + score);
        for (JRadioButton button : optionButtons) {
            button.setEnabled(false);
        }
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel();
            handleSubmit();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApplication quiz = new QuizApplication();
            quiz.setVisible(true);
        });
    }
}
