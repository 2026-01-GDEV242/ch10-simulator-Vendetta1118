import java.util.List;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Joseph Schiavone
 * @version 2026.04.16
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age.
    //Moved from subclasses (Fox and Rabbit) to eliminate duplication.
    private int age;
    
    /**
     * Create a new animal at location in field.
     * the animal is intitialized as alive with age 0.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        age = 0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);
    
    /**
     * Return the age of this animal
     * 
     * @return The current age of the animal.
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * Set the age of this animal.
     * 
     * Used by subclasses instead of directly accessing the age field.
     * 
     * @param age The new age to assign.
     */
    protected void setAge(int age)
    {
        this.age = age;
    }
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Return the breeding age of this animal.
     * 
     * @return The age at which this animal can start breeding.
     */
    protected abstract int getBreedingAge();
    
    /**
     * Determine whether this animal is old enough to breed.
     * This method was moved to the animal class to remove duplication.
     * It relies on the subclass defined breeding age.
     * 
     * @return true if the animal can breed, false otherwise
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the maximum age of this animal. 
     * Eachsubclass must define its own lifespan.
     * @return The maximum age this animal can reach.
     */
    protected abstract int getMaxAge();
    
    /**
     * Increase the age of the animal by one step.
     * 
     * If the animal exceeds its maximum age, it dies.
     * This method was moved to the Animal class to remove duplication.
     */
    protected void incrementAge()
    {
        setAge(getAge() + 1);
        if(getAge() > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
