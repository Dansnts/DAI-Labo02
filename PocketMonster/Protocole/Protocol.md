# The "Pokémon Octogone" protocol
## Section 1: Overview
The "Pokémon Octogone" protocol is a communication protocol 
that permits two client to have a pokémon battle online ,
with their favorite pokémon.

## Section 2: Transport Protocol
The "Pokémon Octogone" protocol is a text transport protocol. 
It uses the TCP transport protocol to ensure the reliability of 
data transmission.\
The port it uses is the port number 28500.

All messages are encoded in UTF-8 and are delimited by a newline 
character(`\n`).
The messages are treated as text messages.

It is the client that initiates the communication with the server.
And it is the client that wants to join a battle that initiate
the communication between him and the one that is hosting.

On an unknown message, the server must send an error to the client.
Once the client is done with a battle or just creating his team, 
he can `HOST` or `JOIN` any game. Finally he can `QUIT` 
which will close the connection on the server side. 
>[!note]
> 
> here the client is the only being able to close the connection.

## Section 3: Messages
### Join a battle
The client is looking to battle someone
#### Request
```
JOIN <Username>
```
- `<Username>`: The Username of the client, who is hosting, 
that he wants to challenge
#### Responses
- `Giovanni,...`: The client didn't send any Username, so the 
server sends him a message containing all the other clients 
willing to host a game
- `OK`: The Username sent by the client is valid and the 
server connects the client and the Host. 
- `ERROR <code>`: an error occurred while sending the message. 
The error code is an integer between 1 and 1 inclusive. 
The error code is as follow:
  - 1: The Username sent doesn't exist.

### Host a battle
The client is waiting for someone to challenge him
#### Request
```
HOST
```
#### Responses
- `OK`: The client is able to host a game, 
he is now waiting for someone to join.
- `ERROR <code>`: an error occurred while sending the message.
The error code is an integer between 1 and 1 inclusive.
The error code is as follow:
  - 1: The client is already in a battle.

### Check the pokédex
The client wants to interact with the pokédex
#### Request
```
POKEDEX <name>
```
- `<name>`: The name of the pokémon you want to see
#### Responses
- `- bulbasaur,...`:The client didn't specify any name so the 
server sends him the list of all the pokémon currently in the 
pokédex
- `STATS <name> <types> <stats>`:The client asked to see a pokémon that 
is currently in the pokédex so the server sends him his data.
- `ERROR <code>`:an error occurred while sending the message.
The error code is an integer between 1 and 1 inclusive.
The error code is as follow: 
  - 1: The name sent by the client isnt currently in the pokédex

### Adding a pokémon
The client wants to add a pokémon to the actual pokédex
#### Request
```
POKEDEX ADD  
```
#### Responses
- `enter the name...`: The server will ask question so that it can register the pokemon.
- `ERROR <code>`:an error occurred while sending the message.
The error code is an integer between 1 and 2 inclusive.
The error code is as follow:
  - 1: Too much parameter

### Changing your team
The client wants to change his team
#### Request
```
Change <number> <pokémon>
```
- `<number>`: The place in the team of the pokémon to be swapped
- `<pokémon>`: The pokémon to be placed in the team
#### Responses
- `YOUR team (1. bulbizzare, 2. carapuce,...`: The client didn't 
put any number or any pokémon so the server shows him his actual 
team
- `NEW team (1.herbizzare, 2.carapuce,...`: The client wanted to 
change his bulbasaur for a herbizzare
- `ERROR <code>`:an error occurred while sending the message.
  The error code is an integer between 1 and 2 inclusive.
  The error code is as follow:
  - 1: The pokémon wanted doesn't exist
  - 2: The place the pokémon is wanted isnt allowed
(ex. place 7 or 0)
  - 3: lacks a parameter

### Attack mid-fight
During the combat the client wants to attack his ennemi
#### Request
```
ATTACK <move>
```
- `<move>`: the move that the active pokémon is supposed to use
#### Responses
- `ATTACK herbasaur (1.tackle, 2.strugle,...`: The client didn't 
send any attack for the active pokémon so the server shows him 
the different moves that the active pokemon can do
- `the <pokemon> has/hasn't hit and dealt <number> damage`: 
The client entered a legitimate move that his active pokemon can do. 
The server then determines if the attack hits and tells both client
how much damage has been dealt.
- `ERROR <code>`:an error occurred while sending the message.
  The error code is an integer between 1 and 2 inclusive.
  The error code is as follow:
  - 1: The pokemon doesn't have this move
  
### Change pokemon mid-fight
During the combat the client wants to change hi active pokemon
#### Request
```
CHANGE <pokémon>
```
- `<pokemon>`: The pokemon the client wants to switch in
#### Responses
- `YOUR team (1.herbizzare, 2.carapuce,..`: The client didn't 
send any pokemon to switch in so the server shows him the state 
of his actual team
- `the client has switched his pokemon for <pokemon>`: The client entered a pokemon 
that is not dead and is in the team, the server then actualise 
the combat and send back the state of the fight to both clients.
- `ERROR <code>`:an error occurred while sending the message.
  The error code is an integer between 1 and 2 inclusive.
  The error code is as follow:
  - 1: The pokemon is dead
  - 2: The pokemon isn't in your team

### Quit
The client wants to quit
#### Request
```
QUIT
```
#### Responses
none.
