# Codex Naturalis - 2024 Software Engineering Project 
![codex](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries/Images/codex_logo.png)
#### Team members:
[Simone Pizzelli](https://github.com/SimonePizzelli) <br>
[Elia Dallanoce](https://github.com/EliaDallanoce432) <br>
[Marco Pedretti](https://github.com/10736964) <br>
[Marcello Massari](https://github.com/marmas00) <br>

#### Features 

| Feature                     | Status |
|-----------------------------|--|
| UML                         | ✅ |
| Model (Complete game rules) | ✅ |
| Network | ✅ |
| Controller | ✅ |
| Client GUI                  |  ✅ |
| Client CLI                  | ✅ |
| Server CLI                  | ✅ |
| Multiple games              | ✅ |
| Persistence | ❌ |
| Chat | ❌ |
| Connection resilience | ❌ |

### Introduction
This project is the implementation of the board game "Codex Naturalis". It's a client-server application that manages multiple clients connected to a single server. Each game has a maximum of 4 players and a server can host multiple games.
### MVC pattern

The MVC is a software design pattern used for developing user interfaces that
divides the related program logic into three interconnected elements.
This is done to separate internal representations of information from the ways information is presented to and accepted from the user.

### Server

The application is server authoritative.
The server handles the multiple game functionality by gathering all clients in a common lobby that can manage the creation of multiple game instances
and allows other clients to join.

### Client
The client sends to the server user inputs and shows the results received from the server.
Each client can interact with the server by using a GUI or a CLI.
The GUI has been realised by using Javafx.
For more information and screenshots please check the view documentation.

### Usage

The precompiled jar can be used to run this application.

You can run the **Codex client GUI application** either by double-clicking on the **CodexClientGui.bat** file found in [Deliveries](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries) or by running the following command: `java -jar .\LB08-1.0-SNAPSHOT-jar-with-dependencies client`.

You can run the **Codex client CLI application** either by double-clicking on the **CodexClientCli.bat** file found in [Deliveries](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries) or by running the following command: `java -jar .\LB08-1.0-SNAPSHOT-jar-with-dependencies client --cli`.

To run the **Codex Server application**, double-click the **CodexServer.bat** file or running `java -jar .\LB08-1.0-SNAPSHOT-jar-with-dependencies server`.  

These files can be found in deliveries.

### Documentation

- [UML](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries/UML)
- [Javadoc](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries/Javadoc/LB08/module-summary.html) 


### 