import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class WordCountGUI {
    private JTextArea textArea;
    private JLabel statusLabel;
    private Map<String, Integer> wordFrequency;

    public WordCountGUI() {
        // Initialize wordFrequency map
        wordFrequency = new HashMap<>();

        // Create the main frame
        JFrame frame = new JFrame("This is a Word Count Program");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for user input
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel inputLabel = new JLabel("Pick an option:");
        JButton manualInputButton = new JButton("Enter the text manually");
        JButton fileInputButton = new JButton("Enter the file path");
        inputPanel.add(inputLabel);
        inputPanel.add(manualInputButton);
        inputPanel.add(fileInputButton);

        // Create a panel for displaying word count results
        JPanel resultPanel = new JPanel(new BorderLayout());
        textArea = new JTextArea(15, 40);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        resultPanel.add(statusLabel, BorderLayout.SOUTH);

        // Add panels to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);

        // Add action listeners to buttons
        manualInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = JOptionPane.showInputDialog(frame, "Enter the text:");
                if (inputText != null && !inputText.trim().isEmpty()) {
                    textArea.setText(inputText);
                    countWords(inputText);
                } else {
                    statusLabel.setText("Given Text input is empty, Please enter valid Text.");
                }
            }
        });

        fileInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String fileContent = readFile(selectedFile.getAbsolutePath());
                        textArea.setText(fileContent);
                        countWords(fileContent);
                    } catch (IOException ex) {
                        statusLabel.setText("Error occured when reading the file: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void countWords(String inputText) {
        String[] words = inputText.split("[\\s.,!?;]+"); // Split by spaces and punctuation
        int totalWordCount = words.length;

        // Clear previous word frequency data
        wordFrequency.clear();

        // Define common stop words to be ignored
        String[] stopWords = { "the", "and", "is", "in", "on", "it", "with", "of", "for" };

        for (String word : words) {
            word = word.toLowerCase(); // Convert to lowercase for case-insensitive comparison
            if (!Arrays.asList(stopWords).contains(word)) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }

        // Update the status label with word count statistics
        statusLabel.setText("Total no.of Words: " + totalWordCount + " | Unique no.of Words: " + wordFrequency.size());

        // Display word frequency statistics
        StringBuilder frequencyText = new StringBuilder("\nWord Frequency:\n");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            frequencyText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        textArea.append(frequencyText.toString());
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(" ");
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordCountGUI();
            }
        });
    }
}
