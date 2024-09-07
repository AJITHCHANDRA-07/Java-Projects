import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeCalculater extends JFrame {
    private JTextField[] markFields;
    private JLabel totalMarksLabel;
    private JLabel averagePercentageLabel;
    private JLabel gradeLabel;
    private int numSubjects = 5;

    public StudentGradeCalculater() {
        setTitle("Student Grade Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel promptLabel = new JLabel("Enter marks for each subject (out of 100):");
        promptLabel.setBounds(50, 20, 300, 20);
        add(promptLabel);

        markFields = new JTextField[numSubjects];
        for (int i = 0; i < numSubjects; i++) {
            markFields[i] = new JTextField();
            markFields[i].setBounds(50, 50 + (i * 30), 100, 20);
            add(markFields[i]);
        }

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(50, 200, 100, 20);
        add(calculateButton);

        totalMarksLabel = new JLabel("Total Marks: ");
        totalMarksLabel.setBounds(50, 230, 300, 20);
        add(totalMarksLabel);

        averagePercentageLabel = new JLabel("Average Percentage: ");
        averagePercentageLabel.setBounds(50, 260, 300, 20);
        add(averagePercentageLabel);

        gradeLabel = new JLabel("Grade: ");
        gradeLabel.setBounds(50, 290, 300, 20);
        add(gradeLabel);

        calculateButton.addActionListener(new CalculateButtonListener());
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int totalMarks = 0;
                for (JTextField markField : markFields) {
                    totalMarks += Integer.parseInt(markField.getText());
                }
                int averagePercentage = totalMarks / numSubjects;
                String grade = calculateGrade(averagePercentage);

                totalMarksLabel.setText("Total Marks: " + totalMarks);
                averagePercentageLabel.setText("Average Percentage: " + averagePercentage + "%");
                gradeLabel.setText("Grade: " + grade);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid marks for all subjects.");
            }
        }

        private String calculateGrade(int averagePercentage) {
            if (averagePercentage >= 90) {
                return "A";
            } else if (averagePercentage >= 80) {
                return "B";
            } else if (averagePercentage >= 70) {
                return "C";
            } else if (averagePercentage >= 60) {
                return "D";
            } else {
                return "F";
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeCalculater calculator = new StudentGradeCalculater();
            calculator.setVisible(true);
        });
    }
}
