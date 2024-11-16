/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maruf
 */

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;

public class ProjectGeneratorClient {
    public static void main(String[] args) {
        // Create and display the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Project Generator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            frame.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

            // Title label with larger font and bold
            JLabel titleLabel = new JLabel("Project Generator Tool");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(new Color(50, 50, 50)); // Dark gray color for title
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

            // Add title to the grid
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 0;
            frame.add(titleLabel, gbc);

            // Create a combo box with technology stack options
            String[] techOptions = { "React with JS", "React with TS", "NodeJS MVC" };
            JComboBox<String> techComboBox = new JComboBox<>(techOptions);
            techComboBox.setBackground(new Color(255, 255, 255));
            techComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

            // Create a text field for the user to enter the directory path
            JTextField directoryField = new JTextField(20);
            directoryField.setEditable(false);  // Make it non-editable, as directory will be selected via Browse button
            directoryField.setBackground(new Color(255, 255, 255));
            directoryField.setFont(new Font("Arial", Font.PLAIN, 14));

            // Create a text field for the user to input the project name
            JTextField projectNameField = new JTextField(20);
            projectNameField.setBackground(new Color(255, 255, 255));
            projectNameField.setFont(new Font("Arial", Font.PLAIN, 14));

            // Create a Browse button to open the file chooser dialog
            JButton browseButton = new JButton("Browse");
            browseButton.setBackground(new Color(60, 120, 240));
            browseButton.setForeground(Color.WHITE);
            browseButton.setFont(new Font("Arial", Font.PLAIN, 14));
            browseButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            browseButton.setFocusPainted(false);
            browseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Create a button to trigger the project generation
            JButton generateButton = new JButton("Generate Project");
            generateButton.setBackground(new Color(50, 150, 50));
            generateButton.setForeground(Color.WHITE);
            generateButton.setFont(new Font("Arial", Font.PLAIN, 14));
            generateButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            generateButton.setFocusPainted(false);
            generateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Action listener for the "Browse" button to open the directory chooser dialog
            browseButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Target Directory");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                // Show the file chooser and get the result
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    directoryField.setText(selectedDirectory.getAbsolutePath());  // Set the selected directory to the text field
                }
            });

            // Action listener for the "Generate Project" button
            generateButton.addActionListener(e -> {
                String selectedTech = (String) techComboBox.getSelectedItem();
                String targetDirectory = directoryField.getText().trim();
                String projectName = projectNameField.getText().trim();

                // Validate user input
                if (selectedTech == null || targetDirectory.isEmpty() || projectName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select a tech stack, specify a directory, and enter a project name.");
                    return;
                }

                // Connect to the server and send the data
                try (Socket socket = new Socket("localhost", 5000);  // Connect to the server at localhost on port 5000
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                     DataInputStream input = new DataInputStream(socket.getInputStream())) {

                    // Send selected tech stack, project name, and directory to the server
                    output.writeUTF(selectedTech);
                    output.writeUTF(projectName);  // Send project name
                    output.writeUTF(targetDirectory);

                    // Receive and display the response from the server
                    String response = input.readUTF();
                    JOptionPane.showMessageDialog(frame, response);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Connection error: " + ex.getMessage());
                }
            });

            // Layout setup for the frame
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            frame.add(new JLabel("Select Technology Stack:"), gbc);

            gbc.gridx = 1;
            frame.add(techComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            frame.add(new JLabel("Enter Project Name:"), gbc);

            gbc.gridx = 1;
            frame.add(projectNameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            frame.add(new JLabel("Target Directory:"), gbc);

            gbc.gridx = 1;
            frame.add(directoryField, gbc);

            gbc.gridx = 2;
            frame.add(browseButton, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 3;
            frame.add(generateButton, gbc);

            // Set spacing between components
            gbc.insets = new Insets(10, 10, 10, 10);

            // Final frame setup
            frame.pack();
            frame.setLocationRelativeTo(null);  // Center the window on screen
            frame.setVisible(true);
        });
    }
}