import java.util.Optional;

public class PokemonAE implements IPokemon {

    private final String name;

    public PokemonAE(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(IPokemon o) {
        return this.getName().compareTo(o.getName());
    }

    // (..other dummy method implementations omitted)
    @Override
    public int getPokedexNumber() {
        throw new UnsupportedOperationException("Unimplemented method 'getPokedexNumber'");
    }

    @Override
    public String getClassification() {
        throw new UnsupportedOperationException("Unimplemented method 'getClassification'");
    }

    @Override
    public PokemonType getPrimaryType() {
        throw new UnsupportedOperationException("Unimplemented method 'getPrimaryType'");
    }

    @Override
    public Optional<PokemonType> getSecondaryType() {
        throw new UnsupportedOperationException("Unimplemented method 'getSecondaryType'");
    }

    @Override
    public double getDamageMultiplierFor(PokemonType attackerType) {
        throw new UnsupportedOperationException("Unimplemented method 'getDamageMultiplierFor'");
    }

    @Override
    public boolean isLegendary() {
        throw new UnsupportedOperationException("Unimplemented method 'isLegendary'");
    }

    @Override
    public int getBaseStatTotal() {
        throw new UnsupportedOperationException("Unimplemented method 'getBaseStatTotal'");
    }

    @Override
    public int getHp() {
        throw new UnsupportedOperationException("Unimplemented method 'getHp'");
    }

    @Override
    public int getAttackStat() {
        throw new UnsupportedOperationException("Unimplemented method 'getAttackStat'");
    }

    @Override
    public int getDefenseStat() {
        throw new UnsupportedOperationException("Unimplemented method 'getDefenseStat'");
    }

    @Override
    public int getSpecialAttackStat() {
        throw new UnsupportedOperationException("Unimplemented method 'getSpecialAttackStat'");
    }

    @Override
    public int getSpecialDefenseStat() {
        throw new UnsupportedOperationException("Unimplemented method 'getSpecialDefenseStat'");
    }

    @Override
    public int getSpeedStat() {
        throw new UnsupportedOperationException("Unimplemented method 'getSpeedStat'");
    }

    @Override
    public String[] getAbilityNames() {
        throw new UnsupportedOperationException("Unimplemented method 'getAbilityNames'");
    }

    @Override
    public long getExperienceGrowthFactor() {
        throw new UnsupportedOperationException("Unimplemented method 'getExperienceGrowthFactor'");
    }

    @Override
    public int getBaseStepsNeededToHatchEgg() {
        throw new UnsupportedOperationException("Unimplemented method 'getBaseStepsNeededToHatchEgg'");
    }

    @Override
    public int getBaseHappiness() {
        throw new UnsupportedOperationException("Unimplemented method 'getBaseHappiness'");
    }

    @Override
    public Optional<Float> getHeightInMeters() {
        throw new UnsupportedOperationException("Unimplemented method 'getHeightInMeters'");
    }

    @Override
    public Optional<Float> getPercentageMale() {
        throw new UnsupportedOperationException("Unimplemented method 'getPercentageMale'");
    }

    @Override
    public String toString() {
        return "PokemonAE{" +
                "name='" + name + '\'' +
                '}';
    }
}