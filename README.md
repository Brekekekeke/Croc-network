# Croc-network


/~~~~~~~~~ MAKE ~~~~~~~~~/

~~~To launch server:

-Open terminal in src directory.

-use make build

-use make startServer


~~~To launch client:

-open terminal in src directory

-use make build

-use make startClient


~~~You can clean, recompile all and launch server using:

-make tryAgain

To configure port you actually need to change variable PORTSERVEUR in Makefile


/~~~~~~~~~ Structure: src/ ~~~~~~~~~/


<<exec: dossier de test utilisé par le makefile.

<<net/headers: les interfaces remote.

-------ComputeInterface- les methodes accessibles au client pour contacter le server.

-------GameStateInterface- l'objet callback utilisé par le serveur pour communiquer au client.


<<net/client: le client

-------Client- le main, connexion a un serveur,  création de partie, ajout de joueurs/bots, demande de resolution. Devra initier un serveur puis s'y connecter si le joueur veut heberger une partie.

-------GameStateImpl- l'objet callback. A utiliser pour obtenir des informations sur l'état de la partie. Principalement des getters/setters.


<<net/server le serveur

-------ComputeEngine- le main, ouvre le serveur et lance l'écoute. Attend l'appel aux fonctions implémentées de ComputeInterface et les résout a l'aide de methodes internes. Crée un GameResolver alimenté par les appels des clients (notamment playCard(int numCard)). Retourne l'état de la partie au client via les appels aux methodes de GameStateInterface.


<<engine/ introduction d'une interface Remote pour les Player afin d'utiliser le meme objet du Game au Client.
