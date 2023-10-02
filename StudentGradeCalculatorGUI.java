import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class StudentGradeCalculatorGUI extends JFrame {
    private JTextField[] scoreFields;
    private JButton calculateButton;
    private JLabel totalMarksLabel, averagePercentageLabel, gradeLabel;
    public StudentGradeCalculatorGUI() {
        setTitle("Student Grade Calculator");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2, 10, 10));

        JLabel[] subjectLabels = new JLabel[5];
        scoreFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            subjectLabels[i] = new JLabel("Subject " + (i + 1) + " Marks:");
            scoreFields[i] = new JTextField(10);
            add(subjectLabels[i]);
            add(scoreFields[i]);
        }

        calculateButton = new JButton("Calculate");
        add(calculateButton);
        totalMarksLabel = new JLabel("Total Marks: ");
        averagePercentageLabel = new JLabel("Average Percentage: ");
        gradeLabel = new JLabel("Grade: ");

        add(totalMarksLabel);
        add(averagePercentageLabel);
        add(gradeLabel);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGrade();
            }
        });
    }

    private void calculateGrade() {
        try {
            int totalMarks = 0;
            int numOfSubjects = 0;
            for (int i = 0; i < 5; i++) {
                String scoreText = scoreFields[i].getText();
                if (!scoreText.isEmpty()) {
                    int score = Integer.parseInt(scoreText);
                    totalMarks += score;
                    numOfSubjects++;
                }
            }

            if (numOfSubjects == 0) {
                JOptionPane.showMessageDialog(this, "Please enter at least one subject's marks.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double averagePercentage = (double) totalMarks / (numOfSubjects * 100) * 100;
            String grade = calculateGradeFromPercentage(averagePercentage);
            totalMarksLabel.setText("Total Marks: " + totalMarks);
            averagePercentageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
            gradeLabel.setText("Grade: " + grade);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid marks for each subject.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String calculateGradeFromPercentage(double percentage) {
        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentGradeCalculatorGUI().setVisible(true);
            }
        });
    }
}
