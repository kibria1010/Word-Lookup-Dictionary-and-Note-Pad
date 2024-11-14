package dictionary;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotePad implements ActionListener {

    Frame frame;
    Panel panel;
    TextArea textArea;
    Button[] button;
    Label label;
    FileDialog fileDialog;

    public NotePad() {
        frame = new Frame("NotePad");
        panel = new Panel();
        textArea = new TextArea("");
        button = new Button[10];
        label = new Label("NotePad Application", Label.CENTER);
        fileDialog = new FileDialog(fileDialog);

        frame.setVisible(true);
        frame.setBounds(500, 400, 600, 400);
        frame.setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout(0, 5));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                we.getWindow().dispose();
            }

        });

        panel.setLayout(new GridLayout(1, 5, 10, 0));
        panel.setBackground(Color.white);
        addButton();
        frame.add(panel, BorderLayout.NORTH);

        frame.add(textArea);
        textArea.setBackground(Color.white);
        textArea.setForeground(Color.BLACK);

        label.setBackground(Color.DARK_GRAY);
        frame.setForeground(Color.LIGHT_GRAY);
        frame.add(label, BorderLayout.PAGE_END);

    }

    void addButton() {
        for (int i = 0; i < button.length; i++) {
            switch (i) {
                case 0:
                    panel.add(button[i] = new Button("clear"));
                    break;
                case 1:
                    panel.add(button[i] = new Button("save"));
                    break;
                case 2:
                    panel.add(button[i] = new Button("open"));
                    break;
                case 3:
                    panel.add(button[i] = new Button("Search"));
                    break;
                case 4:
                    panel.add(button[i] = new Button("exit"));
                    break;
                case 5:
                default:
                    break;
            }
            if (i < 5) {
                button[i].setBackground(Color.DARK_GRAY);
                button[i].setForeground(Color.WHITE);
                button[i].setActionCommand("" + i);
                button[i].addActionListener(this);
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "0":
                textArea.setText("");
                break;
            case "1":
                saveToFile();
                break;
            case "2":
                openFromFile();
                break;
            case "3":
                searchWord();
                break;
            case "4":
                System.exit(0);
                break;
            case "5":
            default:
                break;
        }
    }

    public static void main(String[] args) {
        new NotePad();
    }

    private void saveToFile() {
        fileDialog.setVisible(true);
        File file = new File("" + fileDialog.getDirectory() + "", "" + fileDialog.getFile() + "");
        fileDialog.setVisible(false);
        
        String[] lines;
        lines = textArea.getText().split("\\n");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void openFromFile() {
        fileDialog.setVisible(true);
        File file = new File("" + fileDialog.getDirectory() + "", "" + fileDialog.getFile() + "");
        fileDialog.setVisible(false);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lines = "", line;
            while ((line = reader.readLine()) != null) {
                lines += line + "\n";
            }
            reader.close();
            textArea.setText(lines);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void searchWord() {
        new WordSearchApp();
    }
}
