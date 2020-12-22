# Ex2 oop-
## general explanation on the project:
This project is a game about Pokémon’s and agents, thera are 24 different levels and the goal of the agents is to catch as many pokemons they can while they walking on a Directed weighted graph, in a limited amount of time.
Each level has a different number of agents and pokemons, different graph and a different amount of time.
Every Directed weighted graph built from Nodes and Edges that connecting between the nodes.
Each Node has its own key, and each Edge has its own Src,Dest and weight.
as much as the weight of the edge is bigger, the amount of time for agent to croos this edge is longer.
In order for the agent to catch the pokemon, he needs to pass by the pokemon edge.
when an agent catches a pokemon, the pokemon spawn in an other loction on the graph.


## Explanantion on the code:
The main program is Ex2, when we start Ex2 the login frame is openning and we need to insert an id, and a level number.
When we click the button the game frame (myFrame2) shown and the game start.

## Explanantion on the algorithm:
Each agent has its own thread that responsible for finding the clossest pokemon to this agents and than calculating the shortest path to him.
when the agent cathes a pokemon, he searching for the next clossest pokemon to catch.
the information about the agents and the pokemons is updating 10 times every second while the game is running.



