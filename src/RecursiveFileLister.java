import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static java.nio.file.StandardOpenOption.CREATE;


public class RecursiveFileLister extends JFrame {
    public static void main(String[] args) {
        RecursiveFileLister runner = new RecursiveFileLister();
    }
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JFileChooser chooser = new JFileChooser();
    JPanel mainPnl = new JPanel(new BorderLayout());
    JPanel filePnl = new JPanel(new GridLayout());
    JPanel fileViewerPnl = new JPanel(new GridLayout());
    File selectedDirectory;
    JTextArea textArea = new JTextArea(50,50);
    JScrollPane scroller = new JScrollPane(textArea);
    JPanel cmdPnl = new JPanel(new GridLayout());
    Font buttonFont = new Font("Arial", Font.BOLD, 20);

    private RecursiveFileLister(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Recursive File Lister!");

        add(mainPnl);

        generateFileViewerPnl();
        mainPnl.add(fileViewerPnl, BorderLayout.CENTER);

        generateFilePnl();
        mainPnl.add(filePnl, BorderLayout.NORTH);

        generateCmdPnl();
        mainPnl.add(cmdPnl,BorderLayout.SOUTH);

        pack();
        setSize((int)(.5*screenSize.width),(int)(.80*screenSize.height));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generateFilePnl(){
        JButton fileChooserBtn = new JButton("Choose a file!");
        fileChooserBtn.addActionListener(e -> {
            textArea.setText("");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                File workingDirectory = new File(System.getProperty("user.dir"));

                chooser.setCurrentDirectory(workingDirectory);

                if(chooser.showOpenDialog(fileViewerPnl) == JFileChooser.APPROVE_OPTION)
                {
                    selectedDirectory = chooser.getSelectedFile();
                        searchPattern(selectedDirectory);
                }
        });
        filePnl.setPreferredSize(new Dimension(0,50));
        fileChooserBtn.setFont(buttonFont);
        filePnl.add(fileChooserBtn);
    }
    private void generateFileViewerPnl(){
        textArea.setEditable(false);
        textArea.setMargin(new Insets(10,10,0,5));
        fileViewerPnl.add(scroller);
    }

    private void generateCmdPnl(){
        JButton quitBtn = new JButton("Quit!");
        quitBtn.addActionListener(e -> System.exit(0));
        quitBtn.setFont(buttonFont);
        cmdPnl.add(quitBtn);
        cmdPnl.setPreferredSize(new Dimension(0,75));
    }

    private void searchPattern(File currentFile) {
        if (currentFile.isFile()) {
            textArea.append(currentFile.getName() + "\n");
        } else {
            File[] deeperFiles = currentFile.listFiles();
            if(deeperFiles != null){
                for (File deepFile : deeperFiles) {
                    searchPattern(deepFile);
                }
            }
        }
    }
}
