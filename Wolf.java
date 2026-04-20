import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * A simple model of a wolf.
 * Wolves age, move, eat rabbits and foxes, and die.
 *
 * @author Joseph Schiavone
 * @version 2026.04.19
 */
public class Wolf extends Animal
{
    // The age at which a wolf can start breed.
    private static final int BREEDING_AGE = 20;
    // The age at which a wolf can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a wolf breeding. 
    private static final double BREEDING_PROBABILITY = 0.05;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The number of steps a wolf can go before it has to eat again.
    private static final int FOOD_VALUE = 12;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The wolf's food level, which is increased by eating rabbits and foxes.
    private int foodLevel;
    
    public Wolf(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = FOOD_VALUE;
        }
    }
    
    public void act(List<Animal> newWolves)
    {
        incrementAge();
        incrementHunger();
        
        if(isAlive()) {
            giveBirth(newWolves);
            Location newLocation = findFood();
            
            if(newLocation == null) {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                setDead();
            }
        }
    }
    
    /**
     * Decrease the wolf's food level.
     * The wolf dies if its food level reaches zero.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for prey in adjacent locations.
     * Wolves can eat both rabbits and foxes
     * 
     * @return The location of food if found, or null otherwise.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        
        for(Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            
            if(animal instanceof Rabbit || animal instanceof Fox) {
                Animal prey = (Animal) animal;
                if(prey.isAlive()) {
                    prey.setDead();
                    foodLevel = FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    private void giveBirth(List<Animal> newWolves)
    {
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newWolves.add(new Wolf(false, getField(), loc));
        }
    }
    
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    
    /**
     * Return the breeding age for wolves.
     * 
     * @return The breeding age.
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the maximum age for wolves.
     * 
     * @return The maximum age.
     */
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
}