# ReversiXT – Game AI Client

**AI client for ReversiXT, a multi-player Reversi variant, built as a team project in the course
"Rechnerübung / Software Lab" (summer term 2024, OTH Regensburg, group 04).**

The client connects to the course's game server over TCP, receives the map and the opponents'
moves and has to answer every move request within a strict time or depth limit, against up to seven
other AI clients.

## The game

ReversiXT extends classic Reversi with:

- **2–8 players** on arbitrary maps (holes, irregular shapes)
- **Transitions:** portals that connect two board edges, so lines of stones can "wrap around"
- **Special tiles:** bonus (extra bomb or overwrite stone), choice (swap colours with a player),
  inversion (rotate all colours)
- **Overwrite stones:** place a stone on an occupied tile
- **Two phases:** a build phase (placing stones) followed by a bomb phase (blowing up areas)

## How the AI works

- **Search:** Best-Reply Search (BRS+) with alpha-beta pruning. Instead of simulating every
  opponent in turn (paranoid search, exponential in the number of players), only the single
  strongest opponent reply is considered per layer, which makes deeper searches feasible with
  many players.
- **Iterative deepening** under a time budget: the client deepens the search until the
  estimated time for the next depth would exceed the remaining time and keeps the best move
  of the last completed depth.
- **Move ordering:** a quick heuristic pre-sort of the moves so that alpha-beta cuts off early;
  bad candidates are pruned before the expensive search.
- **Evaluation function:** combines
  - static tile weights (corners and stable tiles high, tiles next to them low)
  - mobility with a logarithmic gradient (the difference between 0 and 5 moves matters much
    more than between 40 and 45)
  - number of stones, bonus items and the ranking against the other players
- **Bomb phase:** separate greedy evaluation of the bomb positions.

## Project structure

```
src/main/java/
  board/        map parsing, tiles, transitions
  game/         game state, move generation (MoveCalculator) and execution (MoveExecutor)
  move/         move types: normal, overwrite, bonus, choice, inversion, bomb
  evaluation/   heuristic evaluation, move ordering, tile weights
  clients/      search (BRS+ / alpha-beta / iterative deepening) and time management
  network/      server protocol
src/intTest/    integration tests that play complete games against the server on test maps
maps/           test and competition maps
```

## Build & run

Requires Java 17+.

```bash
./gradlew jar                       # → build/libs/client04.jar
java -jar build/libs/client04.jar -i 127.0.0.1 -p 7777
java -jar build/libs/client04.jar -h   # all options (colours, quiet mode, log level)
```

The game server itself was provided by the course and is not part of this repository.

## Team

Group project by Maximilian Strauß, Ludwig Schmidt and Samuel Kroiß.
