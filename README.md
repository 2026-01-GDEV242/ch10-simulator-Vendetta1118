# Ch10-Simulator

 CHANGES
Moved age Field to Animal
- The age field was originally duplicated in both the Fox and Rabbit classes.
- It was moved into the abstract Animal class to eliminate redundancy.
- Getter and setter methods (getAge, setAge) were added.
- All direct access to age in subclasses was replaced with these methods.

Moved canBreed() to Animal
- The canBreed() method was duplicated in both subclasses.
  It was moved into Animal and rewritten to use:
    - getAge()
    - getBreedingAge()
- Each subclass (Fox, Rabbit) now has its own getBreedingAge().

 Moved incrementAge() to Animal
- The incrementAge() method was also duplicated.
- It was moved into Animal and rewritten to use:
    - getAge() and setAge()
    - getMaxAge()
- Each subclass defines its own getMaxAge() value.

New Feature - Wolf Class:

Description

A new animal type, Wolf, was added as a subclass of Animal.

Behavior
- Wolves act as a top predator in the simulation.
They can hunt and eat:
    - Rabbits
    - Foxes
- Wolves lose food over time and must eat to survive.
- They can reproduce

Assumptions
- Wolves compete with foxes for food.
- Wolves are stronger predators and eat the foxes.
- Wolves move similarly to foxes, prioritizing, nearby prey. 
- If no food is found, wolves move randomly. 
- Starvation occurs if a wolf does not eat within a certain number of steps.