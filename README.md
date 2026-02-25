🎲 Chelas Multi-Player Poker Dice

Chelas Multi-Player Poker Dice é uma aplicação de Poker Dice Multiplayer desenvolvida para Android, onde jogadores competem em uma partida de dados de poker em tempo real. Cada jogador utiliza seu próprio dispositivo para interagir no jogo. O jogo permite a criação de lobbies, onde os jogadores podem entrar e sair, e as partidas são jogadas ao longo de várias rodadas, com regras de Poker adaptadas para dados.

📱 Funcionalidades principais

Login: Autenticação de usuários para garantir acesso seguro à aplicação.

Tela Principal (Title Screen): Menu principal da aplicação com opções de navegação.

Lobbies: Exibição de lobbies disponíveis, permitindo que o jogador entre em um lobby ou crie um novo.

Lobby Creation: Criação de novos lobbies com parâmetros como nome, descrição, número de jogadores e rodadas.

Lobby: Sala de espera para o jogo, onde os jogadores aguardam o início da partida.

Game: A tela do jogo onde os jogadores interagem, rolando dados, realizando rerolls e terminando turnos.

Player Profile: Exibe informações e estatísticas pessoais do jogador.

About: Informações sobre o jogo e os desenvolvedores.

🧱 Arquitetura do Projeto

A arquitetura do aplicativo segue o padrão MVVM (Model-View-ViewModel), onde a lógica do jogo, a interface de usuário e a gestão de dados estão bem separadas.

Principais componentes:

Autenticação: Gerenciada através de um repositório de autenticação (AuthInfoRepo), garantindo que cada jogador se logue com credenciais válidas.

Game State: A interação do jogo é mantida e atualizada utilizando StateFlow para reatividade.

Backend: Comunicação com o backend via Ktor para sincronizar os dados entre os dispositivos dos jogadores.

UI: A interface gráfica é criada utilizando Jetpack Compose, proporcionando uma navegação fluída entre as telas.

Fluxo de navegação da aplicação:

A navegação entre as telas segue o fluxo descrito na imagem, garantindo uma experiência de usuário intuitiva.

Login Screen → Title Screen

Title Screen → Lobbies Screen / Player Profile Screen / About Screen

Lobbies Screen → Lobby Creation Screen / Lobby Screen

Lobby Screen → Game Screen

🧑‍🤝‍🧑 Funcionalidades de Jogo

Lobby:

Jogadores podem criar um lobby ou entrar em um já existente.

O host define os parâmetros do jogo, como número de rodadas e jogadores.

Quando o número necessário de jogadores se junta, o jogo começa.

Turnos e Rolagem de Dados:

Cada jogador tem até 3 rolamentos por turno.

Jogadores podem segurar dados e repetir a rolagem de outros dados.

Pontuação:

O vencedor de cada rodada é determinado pela combinação de dados mais forte, de acordo com a hierarquia tradicional de poker, adaptada para dados (Ex: "Five of a Kind", "Four of a Kind", etc.).

O jogador com a maior pontuação no final do jogo vence a partida.

<img width="586" height="319" alt="image" src="https://github.com/user-attachments/assets/5dda584f-68bf-4d37-86c1-f39bf863d090" />

🖥️ Tecnologias e Bibliotecas Utilizadas

Kotlin: Linguagem principal do projeto.

Jetpack Compose: UI declarativa para a construção das telas.

Ktor: Comunicação HTTP com o backend.

Kotlin Coroutines: Gerenciamento assíncrono de chamadas à API e atualização da UI.

StateFlow: Para atualização reativa do estado do jogo.

JUnit: Testes unitários e de UI.

Dependências principais:

ktor-client-core

androidx-compose-material3

androidx-core-ktx

🌐 Integração com o Backend

A comunicação entre o cliente e o servidor é feita através de HTTP requests utilizando Ktor. O cliente interage com o servidor para buscar o estado atual do jogo, incluindo os dados dos jogadores, o estado dos dados, e as ações realizadas durante a partida.

🧪 Testes

O aplicativo inclui testes para validar as funcionalidades principais, como o login, navegação entre telas e interações durante o jogo. JUnit é utilizado para testes unitários e de integração. A Compose Test é utilizada para validar a interface de usuário.

video de demostração: https://youtu.be/n7DIhjbBE6U
=======


>>>>>>> f8f88486dde1d8ca13fad015805d5af2e9ab504e
