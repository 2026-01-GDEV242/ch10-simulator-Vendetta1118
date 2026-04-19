import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * Write a description of class Wolf here.
 *
 * @author Joseph Schiavone
 * @version 2026.04.19
 */
public class Wolf extends Animal
{
    private static final int BREEDING_AGE = 20;
    private static final int MAX_AGE = 200;
    private static final double BREEDING_PROBABILITY = 0.05;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int FOOD_VALUE = 12;

    private static final Random rand = Randomizer.getRandom();
    
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
    
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
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
    
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
}