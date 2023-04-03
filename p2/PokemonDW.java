import java.util.NoSuchElementException;
import java.util.Optional;

public class PokemonDW implements IPokemon {

    private final String name; // cannot change; used to sort the RBT
    private final int pokedexNumber;
    private final String classification;
    private final PokemonType primaryType;
    private final Optional<PokemonType> secondaryType;
    // private final ITypeMatchups typeMatchups;
    private final boolean isLegendary;
    private final int baseStatTotal;
    private final int hp;
    private final int attack;
    private final int defense;
    private final int spAttack;
    private final int spDefense;
    private final int speed;
    private final String abilityNames;
    private final long experienceGrowthFactor;
    private final int baseStepsNeededToHatchEgg;
    private final int baseHappiness;
    private final Optional<Float> heightInMeters;
    private final Optional<Float> percentMale;

    /**
     * Verbose constructor for a PokemonDW.
     * Prefer using these methods, if possible, to make Pokemon:
     * <ul>
     *     <li>{@link PokemonDW#from(String, PokemonType)}</li>
     *     <li>{@link PokemonDW#from(String, PokemonType, PokemonType)}</li>
     * </ul>
     * @see PokemonDW#from(String, PokemonType)
     * @see PokemonDW#from(String, PokemonType, PokemonType)
     */
    public PokemonDW(String name, int pokedexNumber, String classification,
                     PokemonType primaryType, Optional<PokemonType> secondaryType,
                     boolean isLegendary,
                     int hp, int attack, int defense, int spAttack, int spDefense, int speed, int baseStatTotal,
                     String abilityNames, long experienceGrowthFactor, int baseStepsNeededToHatchEgg, int baseHappiness,
                     Optional<Float> heightInMeters, Optional<Float> percentMale) {
        this.name = name;
        this.pokedexNumber = pokedexNumber;
        this.classification = classification;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.isLegendary = isLegendary;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
        this.baseStatTotal = baseStatTotal;
        this.abilityNames = abilityNames;
        this.experienceGrowthFactor = experienceGrowthFactor;
        this.baseStepsNeededToHatchEgg = baseStepsNeededToHatchEgg;
        this.baseHappiness = baseHappiness;
        this.heightInMeters = heightInMeters;
        this.percentMale = percentMale;
    }

    /**
     * @return the name of the Pokemon (e.g. <i>Bulbasaur</i>).
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the Pokedex index number of the Pokemon, also known as its ID.
     */
    @Override
    public int getPokedexNumber() {
        return this.pokedexNumber;
    }

    /**
     * Returns a brief description of the Pokemon (e.g. <i>Seed Pokemon</i>).
     */
    @Override
    public String getClassification() {
        return this.classification;
    }

    /**
     * Gets the first type of the Pokemon (e.g. <i>Water</i>). Depending on the Pokemon, this may be their only type.
     *
     * @return the first type of the Pokemon
     */
    @Override
    public PokemonType getPrimaryType() {
        return this.primaryType;
    }

    /**
     * <p>
     * Pokemon may or may not have two types (e.g. Water and Flying). Since not all Pokemon have a second type,
     * we use an {@link Optional} to represent the possibility of absence—which generally is better practice than using {@code null}.
     * </p>
     *
     * <br>
     *
     * <h1>How to parse an Optional</h1>
     * <p>
     * Check that the {@code Optional} holds a value with {@link Optional#isPresent()}.
     *    <ul>
     *      <li>If it <b>does</b> contain a value, you can retrieve it with {@link Optional#get()}.</li>
     *      <li>If it doesn't contain a value, <b>do not</b> call {@code get()}—it will throw a {@link NoSuchElementException} if you do.</li>
     *    </ul>
     *   Account for both cases: (1) when a value is present, and (2) when a value is absent.
     * </p>
     *
     * <br><br>
     *
     * <h2>Examples</h2>
     * <h3>Branching with an if statement</h3>
     * <pre><code>
     *      Optional&lt;PokemonType&gt; optType = myPokemon.getSecondaryType();
     *      if(optType.isPresent()) {
     *          PokemonType type = optType.get();
     *          // do something (value exists)...
     *      } else {
     *          // do something (value doesn't exist)...
     *      }
     * </code></pre>
     *
     * <h3>Printing with ternary operator</h3>
     * <pre><code>
     *     Optional&lt;PokemonType&gt; optType = myPokemon.getSecondaryType();
     *     String s = optType.isEmpty() ? "(empty)" : optType.get().toString();
     *     System.out.println(s);
     * </code></pre>
     *
     * @return an {@link Optional} of the Pokemon's second type, which may or may not contain a type.
     * @see Optional
     */
    @Override
    public Optional<PokemonType> getSecondaryType() {
        return this.secondaryType;
    }

    /**
     * <p>
     * Gets the damage multiplier that would be applied to an attack against this Pokemon.
     * The higher the damage multiplier, the weaker this Pokemon is to the type {@code attackerType}.
     * </p>
     * <br>
     *
     * <h1>How to interpret this method</h1>
     * As a general rule of thumb, for a given damage multiplier {@code m}...
     * <ul>
     *   <li>if {@code m >= 2}, the attacker's type is <b>super effective</b> against this Pokemon.</li>
     *   <li>if {@code m >= 1}, the attacker's type is <b>effective</b> against this Pokemon.</li>
     *   <li>if {@code m > 0}, the attacker's type is <b>weak</b> against this Pokemon.</li>
     *   <li>if {@code m = 0}, the attacker's type has <b>no effect</b> against this Pokemon.</li>
     * </ul>
     *
     * @param attackerType the type this Pokemon is attacked by
     * @return the damage multiplier that would be applied to an attack against this Pokemon
     */
    @Override
    public double getDamageMultiplierFor(PokemonType attackerType) {
        return 1;
        // return typeMatchups.getDamageMultiplierFromAnAttackBy(attackerType);
    }

    /**
     * @return true if the Pokemon is legendary, false otherwise
     */
    @Override
    public boolean isLegendary() {
        return this.isLegendary;
    }

    /**
     * The base stat total can also be seen as an overall "combat power" rating. Pokemon with better base stat totals
     * are generally stronger than others.
     *
     * @return the base stat total of the Pokemon.
     */
    @Override
    public int getBaseStatTotal() {
        return this.baseStatTotal;
    }

    /**
     * Also known as health. In an actual Pokemon battle, when this reaches zero, the Pokemon faints.
     *
     * @return the total hit-points of the Pokemon.
     */
    @Override
    public int getHp() {
        return this.hp;
    }

    /**
     * @return the Pokemon's attack stat.
     */
    @Override
    public int getAttackStat() {
        return this.attack;
    }

    /**
     * @return the Pokemon's defense stat.
     */
    @Override
    public int getDefenseStat() {
        return this.defense;
    }

    /**
     * @return the Pokemon's special attack stat.
     */
    @Override
    public int getSpecialAttackStat() {
        return this.spAttack;
    }

    /**
     * @return the Pokemon's special defense stat.
     */
    @Override
    public int getSpecialDefenseStat() {
        return this.spDefense;
    }

    /**
     * @return the Pokemon's speed stat.
     */
    @Override
    public int getSpeedStat() {
        return this.speed;
    }

    /**
     * Gets the <b>ability names</b> corresponding to the Pokemon.
     * Each ability name is represented as a string.
     *
     * @return each ability in string form, represented wholly as an array
     */
    @Override
    public String[] getAbilityNames() {
        return this.abilityNames
                .replaceAll("'", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "")
                .split(",");
    }

    /**
     * Gets the experience growth factor; this represents how quickly the Pokemon levels
     * up in comparison to other Pokemon.
     *
     * @return the XP growth factor
     */
    @Override
    public long getExperienceGrowthFactor() {
        return this.experienceGrowthFactor;
    }

    /**
     * Pokemon eggs can hatch every time a trainer takes one step.
     *
     * <p>
     * This method returns the number of steps needed to hatch an egg for a specific Pokemon.
     * </p>
     *
     * @return the minimum number of steps a trainer needs to the take
     * for this Pokemon egg to have a chance hatching.
     */
    @Override
    public int getBaseStepsNeededToHatchEgg() {
        return this.baseStepsNeededToHatchEgg;
    }

    /**
     * Each Pokemon has a happiness value associated with their trainer over time.
     * This method returns the base happiness; i.e. the default happiness they start with the moment they meet a trainer.
     *
     * @return the Pokemon's base happiness.
     */
    @Override
    public int getBaseHappiness() {
        return this.baseHappiness;
    }

    /**
     * @return the height of the Pokemon, in meters
     */
    @Override
    public Optional<Float> getHeightInMeters() {
        return this.heightInMeters;
    }

    /**
     * @return a ratio from [0, 1] representing how often this Pokemon, when hatched or encountered in the wild, is male.
     */
    @Override
    public Optional<Float> getPercentageMale() {
        return this.percentMale;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(IPokemon o) {
        return this.name.compareTo(o.getName());
    }

    public static IPokemon from(String name) {
        return new PokemonDW(name, 0, "",
                PokemonType.NORMAL, Optional.empty(), false,
                0, 0, 0, 0, 0, 0, 0,
                "", 0, 0, 0, Optional.empty(), Optional.empty());
    }

    public static IPokemon from(String name, PokemonType primaryType) {
        return new PokemonDW(name, 0, "",
                primaryType, Optional.empty(), false,
                0, 0, 0, 0, 0, 0, 0,
                "", 0, 0, 0, Optional.empty(), Optional.empty());
    }

    public static IPokemon from(String name, PokemonType primaryType, PokemonType secondaryType) {
        return new PokemonDW(name, 0, "",
                primaryType, Optional.of(secondaryType), false,
                0, 0, 0, 0, 0, 0, 0,
                "", 0, 0, 0,
                Optional.empty(), Optional.empty());
    }
}
