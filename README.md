# Codex Naturalis - 2024 Software Engineering Project 
![codex](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries/Images/codex_logo.png)
#### Team members:
[Simone Pizzelli](https://github.com/SimonePizzelli) <br>
[Elia Dallanoce](https://github.com/EliaDallanoce432) <br>
[Marco Pedretti](https://github.com/10736964) <br>
[Marcello Massari](https://github.com/MarcelloMassari) <br>

#### Features 

| Requirements | Status |
|--|--|
| complete game rules | ✅ |
| GUI |  ✅ |
| CLI | ✅ |
| Multiple games | ✅ |

### Introduction
This project is the implementation of the board game "Codex Naturalis". It's a client-server application that manages multiple clients connected to a single server. Each game has a maximum of 4 players and a server can host multiple games. Each client can interacts with the server with GUI or CLI.
### MVC pattern

The MVC is a software design pattern used for developing user interfaces that
divides the related program logic into three interconnected elements.
This is done to separate internal representations of information from the ways information is presented to and accepted from the user.

### Server

The server application is likely to be the central piece managing game logic, player interactions and communication. Since it follows the MVC pattern, we can assume the following structure:
- Model: This component holds the core game data like player information, game state (game field, turn information, etc.), and game rules.
- View: ...
- Controller: This is the heart of the server. It receives requests from clients (such as player actions), interacts with the model to update the game state based on game rules, and sends responses back to connected clients.

### Client

The client application, also following the MVC pattern, is responsible for user interaction and displaying the game to the user. Here's a breakdown of its structure:
- Model: The client-side model likely stores information relevant to the specific user's view of the game, such as their player data, hand of cards, and a representation of the visible game state received from the server.
- View: This is the user interface, which can be either a GUI (Graphical User Interface) or a CLI (Command-Line Interface). It displays the game information received from the model in a user-friendly way and allows players to interact with the game through buttons, menus, or text commands.
- Controller: The client-side controller handles user input from the view (like button clicks or text commands). It translates this user input into messages or requests sent to the server and updates the client-side model with the information received back from the server.

### Network

The network section likely handles communication between the server and clients. Here's a breakdown of the main functionalities:
- ClientConnectionManager: This class is likely responsible for managing connections with clients. It might handle tasks like accepting new connections, keeping track of connected clients, and possibly terminating connections when needed.
- ClientHandler: This class likely represents an individual client connected to the server. It might be responsible for receiving messages from a specific client, processing those messages, and sending responses back to that client.
- NetworkInterface: This inteface defines methods for sending and receiving messages over the network, potentially allowing for flexibility in using different network protocols.
- ServerNetworkObserver: This class likely acts as an observer for network events on the server-side.
- ServerWelcomeSocket: This class is likely responsible for creating and managing the server's welcome socket, which listens for incoming connection requests from clients.
- Pinger: This class might be responsible for sending ping messages to connected clients at regular intervals. This can be used to check if clients are still active and avoid keeping inactive connections open.
- PongObserver: This class likely acts as an observer for pong responses received from clients. When a client receives a ping message, it might respond with a pong message to indicate it's still active. This observer would be notified whenever a pong message is received.
- InputHandler: This class likely deals with handling user input on the client-side. It might take user input from the CLI, translate it into network messages, and send them to the server through the network interface.
- NetworkInputObserver: This class likely acts as an observer for network messages received by the client. Whenever a message arrives from the server, this observer would be notified, allowing the client-side controller to process the message and update the client's model and view accordingly.

### Usage

The precompiled jar can be used to run this application.

You can run the **Codex client GUI application** either by double-clicking on the **CodexClientGui.bat** file found in [Deliveries](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries) or running the following command: `java -jar .\LBO8.jar client`.

To run the **Codex Server application**, double-click the **CodexServer.bat** file or running `java -jar .\LB08 server`.  

### Documentation

- [UML](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries/UML)
- [Javadoc](https://github.com/EliaDallanoce432/IS24-LB08/blob/master/Deliveries/Javadoc/LB08/module-summary.html) 
