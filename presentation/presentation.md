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
</style>

# Practical Work 2 - TCP
Dani Tiago **Faria Dos Santos**
Nicolas **Duprat**
*DAI-TIC-C*



## Pokémon Octogone édition
### Outline

- **Objective**: Create a program who uses TCP sockets in Java
- **Project Goal**: Learn how to use sockets in Java and create a protocol
![bg right:40% vcenter contain](./images/logo.png)

## Key Features

<br>

- **Pokédex** Add and use any Pokémon (Yes, even Sephiroth) 
- **Teams**: Make a team from the Pokémons available
- **Trainer** :  Create your trainer
- **BATTLES !**: 1v1 a friend via Internet



## How did we split the work

<br>

**Dani** : Sockets, Connections, Trainer/Pokémon implementation, Menus & Docker
**Nicolas** : Battle, Refractoring & protocol design


## Pokédex

<br>

- Filled with Pokémons
- Loaded and saved in a .txt files



## Trainer
<br>
- Has a team
- Name
- Money
  

## What we used
<br>


- **Language**: Java
- **Tools**: Maven, Git, picoCLI & Docker

## Protocol
- The port it uses is the port number 28500.

![bg right:60% vcenter contain](./images/protocole.png)



## UML
![10% vcenter contain](./images/uml.png)

<br>


## Usage
#### Docker
- Server

#### Jar
- Client

<br>


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