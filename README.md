🎲 Poker Dice
Android Multiplayer Application of the Poker Dice game

Chelas Multi-Player Poker Dice is a Multiplayer Poker Dice application developed for Android, where players compete in a real-time poker dice match. Each player uses their own device to interact with the game. The game allows the creation of lobbies, where players can enter and leave, and matches are played over several rounds, featuring Poker rules adapted for dice.

📱 Main Features
Login: User authentication to ensure secure access to the application.

Main Screen (Title Screen): The main menu of the application with navigation options.

Lobbies: Display of available lobbies, allowing the player to join an existing lobby or create a new one.

Lobby Creation: Creation of new lobbies with parameters such as name, description, number of players, and rounds.

Lobby: The waiting room for the game, where players wait for the match to start.

Game: The game screen where players interact by rolling dice, performing rerolls, and ending turns.

Player Profile: Displays the player's personal information and statistics.

About: Information about the game and the developers.

🧱 Project Architecture
The application's architecture follows the MVVM (Model-View-ViewModel) pattern, where the game logic, user interface, and data management are cleanly separated.

Main Components:
Authentication: Managed through an authentication repository (AuthInfoRepo), ensuring each player logs in with valid credentials.

Game State: Game interactions are maintained and updated using StateFlow for reactivity.

Backend: Communication with the backend via Ktor to synchronize data between players' devices.

UI: The graphical interface is built using Jetpack Compose, providing fluid navigation between screens.

Application Navigation Flow:
Navigation between screens follows the flow described below, ensuring an intuitive user experience:

Login Screen → Title Screen

Title Screen → Lobbies Screen / Player Profile Screen / About Screen

Lobbies Screen → Lobby Creation Screen / Lobby Screen

Lobby Screen → Game Screen

🧑‍🤝‍🧑 Gameplay Features
Lobby:
Players can create a lobby or join an existing one.

The host defines the game parameters, such as the number of rounds and players.

Once the required number of players joins, the game begins.

Turns and Dice Rolling:
Each player has up to 3 rolls per turn.

Players can hold dice and reroll the remaining ones.

Scoring:
The winner of each round is determined by the strongest dice combination, according to the traditional poker hierarchy adapted for dice (e.g., "Five of a Kind", "Four of a Kind", etc.).

The player with the highest score at the end of the game wins the match.

🖥️ Technologies and Libraries Used
Kotlin: The primary language of the project.

Jetpack Compose: Declarative UI for building the screens.

Ktor: HTTP communication with the backend.

Kotlin Coroutines: Asynchronous management of API calls and UI updates.

StateFlow: For reactive updates of the game state.

JUnit: Unit and UI testing.

Main Dependencies:
ktor-client-core

androidx-compose-material3

androidx-core-ktx

🌐 Backend Integration
Communication between the client and the server is handled via HTTP requests using Ktor. The client interacts with the server to fetch the current state of the game, including player data, dice states, and actions performed during the match.

🧪 Testing
The application includes tests to validate core functionalities such as login, screen navigation, and in-game interactions. JUnit is used for unit and integration testing. Compose Test is utilized to validate the user interface.

Demonstration Video:
https://youtu.be/n7DIhjbBE6U
