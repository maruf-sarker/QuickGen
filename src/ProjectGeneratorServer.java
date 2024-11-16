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
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

public class ProjectGeneratorServer {
    public static void main(String[] args) {
        // Port on which the server will listen for client connections
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                // Accept incoming client connections
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());

                // Handle the client in a separate thread
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Read the selected technology stack, project name, and target directory from the client
            String selectedTech = input.readUTF();     // Selected tech stack (e.g., React with JS)
            String projectName = input.readUTF();      // Project name
            String targetDirectory = input.readUTF();  // Target directory where the project should be created

            // Generate the project structure based on selected technology stack
            boolean success = generateProjectStructure(selectedTech, projectName, targetDirectory);

            // Send response back to the client
            if (success) {
                output.writeUTF("Project '" + projectName + "' created successfully!");
            } else {
                output.writeUTF("Error: Failed to create project.");
            }

            // Close the connection after processing
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate project structure based on the selected technology stack.
     *
     * @param tech              The selected technology stack.
     * @param projectName       The name of the project.
     * @param targetDirectory   The target directory where the project will be created.
     * @return true if project generation is successful, false otherwise.
     */
    private boolean generateProjectStructure(String tech, String projectName, String dir) {
        try {
            // Create the target project directory (if it doesn't already exist)
            Path targetPath = Paths.get(dir, projectName);
            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath);  // Create the project directory
            }

            // Copy template files based on selected tech stack
            switch (tech) {
                case "React with JS":
                    return copyTemplate("templates/react_js", targetPath);
                case "React with TS":
                    return copyTemplate("templates/react_ts", targetPath);
                case "NodeJS MVC":
                    return copyTemplate("templates/node_mvc", targetPath);
                default:
                    return false;  // Return false if the tech stack is unknown
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;  // Return false if there was an error during project creation
        }
    }

    /**
     * Copy the template files from the selected tech stack to the target project directory.
     *
     * @param templateDir The directory where the templates for the selected tech stack are stored.
     * @param targetDir   The target directory where files should be copied.
     * @return true if files are copied successfully, false otherwise.
     */
    private boolean copyTemplate(String templateDir, Path targetDir) {
        try {
            Path templatePath = Paths.get(templateDir);  // Path to the selected template
            if (!Files.exists(templatePath)) {
                System.out.println("Template directory not found: " + templateDir);
                return false;
            }

            // Recursively copy files from the template directory to the target directory
            Files.walkFileTree(templatePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // Create the corresponding directory in the target directory
                    Path targetSubDir = targetDir.resolve(templatePath.relativize(dir));
                    if (!Files.exists(targetSubDir)) {
                        Files.createDirectories(targetSubDir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Copy the file to the target directory
                    Path targetFile = targetDir.resolve(templatePath.relativize(file));
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}