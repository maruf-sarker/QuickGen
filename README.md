# QuickGen - Project Generator with Socket Communication

**QuickGen** is a desktop application that enables users to quickly create new projects based on selected technology stacks (e.g., React, Node.js) using socket-based communication between the frontend and backend. The tool generates the necessary folder structure and starter code for different tech stacks, making it easier to kickstart new development projects.

---

## Table of Contents

1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Project Structure](#project-structure)
4. [How It Works](#how-it-works)
5. [Installation](#installation)
6. [Usage](#usage)
7. [Future Improvements](#future-improvements)
8. [License](#license)

---

## Features

- **Tech Stack Selection**: Users can select from various tech stacks like **React (JS/TS)**, **Node.js MVC**, etc.
- **Socket Communication**: Frontend communicates with the backend via socket programming for project creation.
- **Dynamic Project Generation**: Based on the selected tech stack, the backend generates the appropriate folder structure and starter files.
- **Customizable Directories**: Users can specify the directory where the project should be created.
- **Error Handling**: The app handles errors during the creation process and provides feedback to the user.

---

## Technologies Used

- **Frontend**:  
  - **JavaFX** (for desktop GUI)
  - **Java** (for application logic)

- **Backend**:  
  - **Java Socket Programming** (using `java.net` package)
  - **Java I/O** (for creating project files and directories)

---

## Project Structure

```
/QuickGen
    /src
        /client
            - ProjectGeneratorClient.java   # Frontend code (GUI and client-side logic)
        /server
            - ProjectGeneratorServer.java   # Backend code (socket server logic)
    /assets
        - Resources (e.g., icons, etc.)
    /build
        - Compiled .class files
    /README.md
```

---

## How It Works

1. **Frontend (Client-side)**:
   - Users interact with a JavaFX-based GUI where they can select the desired technology stack (React, Node.js, etc.) and specify a directory for the project.
   - The frontend sends this information (selected stack and target directory) to the backend via a socket connection.

2. **Backend (Server-side)**:
   - The backend listens for incoming connections from the frontend.
   - Upon receiving a request, the backend processes the selected stack and directory, creating the appropriate folder structure and starter files.
   - The server sends a success or error message back to the frontend.

3. **Project Generation**:
   - Based on the stack chosen by the user, the backend creates the folder structure:
     - For React (JS/TS), it generates folders like `src/`, and creates starter files such as `index.js` or `index.ts`.
     - For Node.js MVC, it generates directories like `controllers/`, `models/`, `views/`, and creates a `server.js` file.
   - Once the project is created, the user gets feedback in the form of a success or failure message.

---

## Installation

1. **Clone the repository**:
   ```
   git clone https://github.com/maruf-sarker/QuickGen.git
   cd QuickGen
   ```

2. **Compile the project**:
   - Make sure you have **JDK** installed (Java 8 or higher).
   - Compile the Java files:
     ```
     javac -d build src/**/*.java
     ```

3. **Run the backend (Server)**:
   - Open a terminal and navigate to the `build` directory:
     ```
     java server.ProjectGeneratorServer
     ```
   - This will start the backend server on port `5000` (or any port you specify).

4. **Run the frontend (Client)**:
   - In a separate terminal, navigate to the `build` directory and run the client:
     ```
     java client.ProjectGeneratorClient
     ```
   - This will launch the GUI, allowing you to select the tech stack and target directory.

---

## Usage

1. **Select the Technology Stack**:  
   - Choose from available options (e.g., React with JS, React with TS, Node.js MVC) from the dropdown menu in the GUI.

2. **Choose the Directory**:  
   - Enter the path of the directory where you want the project to be created. If the directory does not exist, it will be created automatically.

3. **Generate the Project**:  
   - Click the **Generate Project** button to send the request to the backend.
   - Wait for the backend to process the request and send a response.

4. **Receive Feedback**:  
   - Once the backend successfully creates the project, you’ll receive a success message.
   - If there is an error (e.g., invalid directory, unsupported tech stack), an error message will be displayed.

---

## Future Improvements

- **More Tech Stack Options**: Add more tech stacks (e.g., Vue.js, Angular, Django, etc.) for a broader selection of templates.
- **Template Customization**: Allow users to customize the generated starter templates (e.g., choosing additional libraries or frameworks).
- **Configuration Files**: Use JSON or YAML files to store the tech stack configurations, making it easier to update and extend the supported stacks.
- **Error Logging**: Improve error handling with detailed logs, both on the server and client-side.
- **Multi-threading**: Implement multi-threading on the backend to handle multiple client requests simultaneously.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
