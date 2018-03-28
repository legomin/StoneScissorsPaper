# StoneScissorsPaper
stone-scissors-paper game algorythm implementation

## Algorythm description

It storage stats of game between start game & finish game. 
stat - <pattern, stat> pair, where 
  stat - what user do where pattern occurs (stoneCount, scissorsCount, paperCount)
  pattern - <userMotion, compMotion> pairs array, where 0 element - previous game lap, 1 - before previous and so on

algorythm based on fix pattern debth, it analyze subpatterns from 1 to N size (N - pattern debth) 
 and suggest most probable motion  

## Some restrictions

1. PR didn't fully covered by tests, only some game simulations.
  I don't do that in prod code ))
  
2. There are some OCP(sOlid) restrictions. F.e. it's not obvious to implement strategy based on only user motions 
 in current architecture (except some hacks with hashcode & equals)  I can solve it ))
 
3. The code can't be used concurrently. It can be solved, but will add some complexity  