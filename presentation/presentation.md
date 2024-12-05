---
marp: true
theme: gaia
paginate: true
headingDivider: 2
backgroundImage: url('https://marp.app/assets/hero-background.svg')

---

<style>
pre {
  background-color: #2e2e2e;  /* Dark background */
  color: #f8f8f2;            /* Light text color */
  font-size: 0.95em;         /* Font size */
  border-radius: 8px;        /* Rounded corners */
  padding: 12px;             /* Padding inside the code block */
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5); /* Shadow for depth */
  width: 100%;               /* Full width */
  margin: 0;                 /* Remove default margins */
}

pre code {
  background: none; /* Remove any background on the code element */
  display: block;
}

/* Optional: Make sure code blocks stretch to the edges */
.marp-slide {
  padding: 0 !important; /* Override default slide padding */
}


img[alt~="center"] {
  display: block;
  margin: 0 auto;
}



img[alt~="right"] {
  display: block;
  margin: -200px 0 0 700px;
}
</style>

# Practical Work 2 - TCP
Dani Tiago **Faria Dos Santos**
Nicolas **Duprat**
*DAI-TIC-C*



## Pokémon Octogone édition
### Outline

- **Objective**: Create a program who uses TCP sockets in Java
- **Project Goal**: Learn how to use sockets and threads in Java and create a protocol
![bg right:40% vcenter contain](./images/logo.png)

## How did we split the work
<br>

**Dani** : Sockets, Connections, Trainer/Pokémon implementation, Menus & Docker
**Nicolas** : Battle, Refractoring & protocol design

## What did we used
<br>

- **Language**: Java
- **Tools**: Maven, Git, picoCLI & Docker

## Key Features
<br>

- **Pokédex** : Add and use any Pokémon (Yes, even Sephiroth) 
- **Teams** : Make a team from the Pokémons available
- **BATTLES !** : 1v1 a friend via Internet

## Pokédex
<br>

- Filled with Pokémons
- Loaded and saved in a .txt files

## Trainer
<br>

- Has a team
- Name
- Money
![w:200 right](./images/trainer.png)



## Protocol
- The port it uses is the port number 28500.

![bg right:60% vcenter contain](./images/protocole.png)


## Protocol
- Use the command **<HOST>** to create a lobby.
- Use the command **<JOIN> <hostId>** to join a lobby.


![bg right:60% vcenter contain](./images/host.png)




## UML
![w:700 center](./images/uml.png)

<br>


## Usage
#### Docker
- Server
- Client

#### Jar [Optional]
- Server
- Client

<br>

## Things we want to improve

- Create a custom trainer and save it
- Have a GUI
- Add sprites
- Add audio

# Demonstration
<style scoped>
section {
  display: flex;
  justify-content: center; /* Center horizontally */
  align-items: center;     /* Center vertically */
}
</style>

# Questions ?
<style scoped>
section {
  display: flex;
  justify-content: center; /* Center horizontally */
  align-items: center;     /* Center vertically */
}
</style>

# Thank you for your attention
<style scoped>
section {
  display: flex;
  justify-content: center; /* Center horizontally */
  align-items: center;     /* Center vertically */
}
</style>

## Acknowledgments
- **Pokémon** is a trademark of *Nintendo*, *Game Freak*, and *Creatures Inc*. All rights to images, characters, and related intellectual property are owned by their respective copyright holders.
- **Final Fantasy VII** and related characters, images, and assets are trademarks of *Square Enix*. All rights reserved.
- **UFC** (Ultimate Fighting Championship) is a registered trademark of *Zuffa, LLC*. All rights to images, videos, logos, and related intellectual property are owned by their respective copyright holders.