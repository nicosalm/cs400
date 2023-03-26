/**
 * Represents 1 of the 18 Pokemon types.
 */
public enum PokemonType {
    NORMAL("Normal"),
    FIRE("Fire"),
    WATER("Water"),
    GRASS("Grass"),
    ELECTRIC("Electric"),
    ICE("Ice"),
    FIGHTING("Fighting"),
    POISON("Poison"),
    GROUND("Ground"),
    FLYING("Flying"),
    PSYCHIC("Psychic"),
    BUG("Bug"),
    ROCK("Rock"),
    GHOST("Ghost"),
    DARK("Dark"),
    DRAGON("Dragon"),
    STEEL("Steel"),
    FAIRY("Fairy");

    private final String val;

    PokemonType(String val) {
        this.val = val; // <- `val` (e.g. "Grass") is returned by toString().
    }

    @Override
    public String toString() {
        return this.val;
    }
}
