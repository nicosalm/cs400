import java.util.Optional;

public class PokemonBD implements IPokemon
{
	
	public String getName()
	{
		return "Pokemon name";
	}

    /**
     * @return the Pokedex index number of the Pokemon, also known as its ID.
     */
	public int getPokedexNumber()
	{
		return 10;
	}

    /**
     * Returns a brief description of the Pokemon (e.g. <i>Seed Pokemon</i>).
     */
	public String getClassification()
	{
		return "Classification called";
	}

    /**
     * Gets the first type of the Pokemon (e.g. <i>Water</i>). Depending on the Pokemon, this may be their only type.
     *
     * @return the first type of the Pokemon
     */
	public PokemonType getPrimaryType()
	{
		return PokemonType.WATER;
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
     *      <li>If it doesn't contain a value, <b>do not</b> call {@code get()}—it will throw a {@link java.util.NoSuchElementException} if you do.</li>
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
	public Optional<PokemonType> getSecondaryType()
	{
		return Optional.of(PokemonType.NORMAL);
	}

    // NOTE: This method will only be used if we decide to scope up the project. Feel free to use a stub implementation for this
    //       method (e.g. by returning a dummy value) and ignore it unless we're told we need to scope up.
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
	public double getDamageMultiplierFor(PokemonType attackerType)
	{
		return 5.0;
	}

    /**
     * @return true if the Pokemon is legendary, false otherwise
     */
	public boolean isLegendary()
	{
		return false;
	}

    /**
     * The base stat total can also be seen as an overall "combat power" rating. Pokemon with better base stat totals
     * are generally stronger than others.
     *
     * @return the base stat total of the Pokemon.
     */
	public int getBaseStatTotal()
	{
		return 3;
	}

    /**
     * Also known as health. In an actual Pokemon battle, when this reaches zero, the Pokemon faints.
     *
     * @return the total hit-points of the Pokemon.
     */
	public int getHp()
	{
		return 2;
	}

    /**
     * @return the Pokemon's attack stat.
     */
	public int getAttackStat()
	{
		return 1;
	}

    /**
     * @return the Pokemon's defense stat.
     */
	public int getDefenseStat()
	{
		return 8;
	}

    /**
     * @return the Pokemon's special attack stat.
     */
	public int getSpecialAttackStat()
	{
		return 88;
	}

    /**
     * @return the Pokemon's special defense stat.
     */
	public int getSpecialDefenseStat()
	{
		return 12;
	}

    /**
     * @return the Pokemon's speed stat.
     */
	public int getSpeedStat()
	{
		return 15;
	}

    /**
     * Gets the <b>ability names</b> corresponding to the Pokemon.
     * Each ability name is represented as a string.
     *
     * @return each ability in string form, represented wholly as an array
     */
	public String[] getAbilityNames()
	{
		String[] string = {"throw", "catch", "die"};
		return string;
	}

    /**
     * Gets the experience growth factor; this represents how quickly the Pokemon levels
     * up in comparison to other Pokemon.
     *
     * @return the XP growth factor
     */
	public long getExperienceGrowthFactor()
	{
		return 55;
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
	public int getBaseStepsNeededToHatchEgg()
	{
		return 156;
	}

    /**
     * Each Pokemon has a happiness value associated with their trainer over time.
     * This method returns the base happiness; i.e. the default happiness they start with the moment they meet a trainer.
     *
     * @return the Pokemon's base happiness.
     */
	public int getBaseHappiness()
	{
		return 166;
	}

    /**
     * @return the height of the Pokemon, in meters
     */
	public Optional<Float> getHeightInMeters()
	{
		return Optional.empty();
	}

    /**
     * @return a ratio from [0, 1] representing how often this Pokemon, when hatched or encountered in the wild, is male.
     */
    public Optional<Float> getPercentageMale()
    {
    	return Optional.empty();
    }

	@Override
	public int compareTo(IPokemon o) {
	
		return 13;
	}
	
}
