package dictionary;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;

public class WordSearchApp extends Frame {

    private TextField wordField;
    private TextArea resultArea;
    private Button searchButton, openButton;
    private File selectedFile;

    public WordSearchApp() {
        // Setup the UI
        setLayout(new FlowLayout());

        openButton = new Button("Open File");
        openButton.addActionListener(new OpenFileListener());
        add(openButton);

        wordField = new TextField(20);
        add(new Label("Enter word to search:"));
        add(wordField);

        searchButton = new Button("Search");
        searchButton.addActionListener(new SearchListener());
        add(searchButton);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Close the window when the close button is clicked
                dispose();
            }
        });

        resultArea = new TextArea(10, 50);
        resultArea.setEditable(false);
        add(resultArea);

        setSize(600, 300);
        setTitle("Word Search App");
        setVisible(true);
    }

    Dictionary dictionary = new Dictionary();

    private class OpenFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileDialog fileDialog = new FileDialog(WordSearchApp.this, "Open File", FileDialog.LOAD);
            fileDialog.setVisible(true);
            String directory = fileDialog.getDirectory();
            String filename = fileDialog.getFile();
            if (filename != null) {
                selectedFile = new File(directory, filename);
                resultArea.setText("Selected file: " + selectedFile.getAbsolutePath());
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        dictionary.loadDictionary(Arrays.asList(words));
                    }
                }
                reader.close();
            } catch (IOException ex) {
                resultArea.setText("Error reading file: " + ex.getMessage());
            }
        }
    }

    private class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedFile == null) {
                resultArea.setText("No file selected.");
                return;
            }

            String searchWord = wordField.getText();
            resultArea.setText("");  // Clear previous results
            if (searchWord.isEmpty()) {
                resultArea.setText("Please enter a word to search.");
                return;
            }
            if (dictionary.isValidWord(searchWord)) {
                resultArea.setText("Found: " + searchWord);
            } else {
                resultArea.setText("Word not found in file.\n");
                java.util.List<String> s = dictionary.getSuggestions(searchWord);
                int flag = 1;
                for (String line : s) {
                    if (flag == 1) {
                        resultArea.setText(resultArea.getText() + "\nDid you mean: \n");
                        flag = 0;
                    }
                    resultArea.setText(resultArea.getText() + line + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        new WordSearchApp();
    }
}
