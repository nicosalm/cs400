import java.util.Optional;

public class PokemonAE implements IPokemon {

    private String name;
    private int pokedexNumber;
    private String classification;

    public PokemonAE(String name, int pokedexNumber, String classification) {
        this.name = name;
        this.pokedexNumber = pokedexNumber;
        this.classification = classification;
    }

    @Override
    public int compareTo(IPokemon o) {
        if (this.getPokedexNumber() > o.getPokedexNumber()) {
            return 1;
        } else if (this.getPokedexNumber() < o.getPokedexNumber()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPokedexNumber() {
        return pokedexNumber;
    }

    @Override
    public String getClassification() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClassification'");
    }

    @Override
    public PokemonType getPrimaryType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrimaryType'");
    }

    @Override
    public Optional<PokemonType> getSecondaryType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSecondaryType'");
    }

    @Override
    public double getDamageMultiplierFor(PokemonType attackerType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDamageMultiplierFor'");
    }

    @Override
    public boolean isLegendary() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isLegendary'");
    }

    @Override
    public int getBaseStatTotal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBaseStatTotal'");
    }

    @Override
    public int getHp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHp'");
    }

    @Override
    public int getAttackStat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAttackStat'");
    }

    @Override
    public int getDefenseStat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDefenseStat'");
    }

    @Override
    public int getSpecialAttackStat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpecialAttackStat'");
    }

    @Override
    public int getSpecialDefenseStat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpecialDefenseStat'");
    }

    @Override
    public int getSpeedStat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpeedStat'");
    }

    @Override
    public String[] getAbilityNames() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAbilityNames'");
    }

    @Override
    public long getExperienceGrowthFactor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExperienceGrowthFactor'");
    }

    @Override
    public int getBaseStepsNeededToHatchEgg() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBaseStepsNeededToHatchEgg'");
    }

    @Override
    public int getBaseHappiness() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBaseHappiness'");
    }

    @Override
    public Optional<Float> getHeightInMeters() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHeightInMeters'");
    }

    @Override
    public Optional<Float> getPercentageMale() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPercentageMale'");
    }

}
