import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogReaderDW implements ICatalogReader {

    private static final String HEADER_LINE = "abilities,against_bug,against_dark,against_dragon,against_electric,against_fairy,against_fight,against_fire,against_flying,against_ghost,against_grass,against_ground,against_ice,against_normal,against_poison,against_psychic,against_rock,against_steel,against_water,attack,base_egg_steps,base_happiness,base_total,capture_rate,classfication,defense,experience_growth,height_m,hp,japanese_name,name,percentage_male,pokedex_number,sp_attack,sp_defense,speed,type1,type2,weight_kg,generation,is_legendary";
    private static final int HEADER_LINE_COLUMNS = 41;

    /**
     * @param r the reader to continue reading from
     * @return a list of tokens, i.e., all comma-separated values from the line
     * @throws IOException if an I/O error occurs when reading
     */
    private static List<String> readOneLineIntoTokens(Reader r) throws IOException {
        List<String> tokens = new ArrayList<>();
        int ch = r.read();

        while (ch == '\r') ch = r.read();
        if (ch < 0) return tokens; // if end-of-file (EOF) reached, return tokens

        boolean inQuotes = false;
        boolean isParsingToken = false;
        StringBuilder token = new StringBuilder();

        while (ch >= 0) {

            if (inQuotes) {
                isParsingToken = true;
                if (ch == '\"') inQuotes = false;
                else token.append((char) ch);
                ch = r.read(); // onto next char
                continue;
            }

            // at this point, we're not in quotes

            if (ch == '\"') {
                inQuotes = true;
                if (isParsingToken) token.append('\"'); // quote found in between two quotes
            } else if (ch == ',') { // comma encountered outside of quotes
                tokens.add(token.toString());
                token = new StringBuilder(); // now reset the token after inserting it
                isParsingToken = false;
            } else if (ch != '\r') { // <- deliberately skips/ignores the carriage return char `\r`
                if (ch == '\n') break; // end-of-line (EOL) reached; break.
                token.append((char) ch); // otherwise, append the character.
            }
            ch = r.read(); // onto next char
        }
        tokens.add(token.toString());
        return tokens;
    }

    /**
     * Helper method that reads a CSV line and returns the number of columns.
     *
     * @param line the CSV line to count tokens in
     * @return the number of columns in the line
     * @throws IOException if an I/O error occurs while reading the line
     */
    private static int countTokensIn(String line) throws IOException {
        int tokens = 1;
        try (var sr = new StringReader(line)) {
            int ch = sr.read();

            while (ch == '\r') ch = sr.read();
            if (ch < 0) return 0; // if end-of-line reached, return 0 tokens

            boolean inQuotes = false;

            while (ch >= 0) {

                if (inQuotes) {
                    if (ch == '\"') inQuotes = false;
                    ch = sr.read(); // onto next char
                    continue;
                }

                // at this point, we're not in quotes

                if (ch == '\"') {
                    inQuotes = true;
                } else if (ch == ',') { // comma encountered outside of quotes
                    tokens += 1;
                } else if (ch != '\r') { // <- deliberately skips/ignores the carriage return char `\r`
                    if (ch == '\n') break; // end-of-line (EOL) reached; break.
                }
                ch = sr.read(); // onto next char
            }
            return tokens;
        }
    }

    /**
     * Helper method that reads all CSV lines in the reader and returns a list of strings. This method will advance the state of the reader until nothing is left to read.
     *
     * @param r the source to read from
     * @return a string representing the line being read
     * @throws IOException if an I/O error occurs while reading the line or the line's tokens are malformed (i.e. in a format that cannot be parsed)
     */
    private static List<String> readLines(Reader r) throws IOException {

        List<String> lst = new ArrayList<>();
        String headerLine = readLine(r);
        int numCols = countTokensIn(headerLine); // read the header line to get the number of columns

        long lineNum = 0;
        while (true) {
            lineNum += 1;

            String line = readLine(r);
            int lineTokens = countTokensIn(line);
            if (lineTokens == 0) break; // <- implies end of file is reached
            if (lineTokens != numCols) throw new IllegalArgumentException("input is malformed at line " + lineNum);

            lst.add(line); // if not empty or malformed, add this to the list.
        }

        return lst;
    }

    /**
     * Helper method that reads a CSV line and returns a string. This method will advance the state of the reader.
     *
     * @param r the source to read from
     * @return a string representing the line being read
     * @throws IOException if an I/O error occurs while reading the line
     */
    private static String readLine(Reader r) throws IOException {
        int ch = r.read();

        while (ch == '\r') ch = r.read();
        if (ch < 0) return ""; // if end-of-file (EOF) reached, return empty string

        StringBuilder sb = new StringBuilder();

        while (ch != '\n') {
            sb.append((char) ch);
            ch = r.read();
        }

        return sb.toString();
    }

    /**
     * @param filename the name of the CSV file to read from
     * @return a list of Pokemon, serialized (i.e. read) from the file
     * @throws FileNotFoundException    if no file is found
     * @throws IllegalArgumentException if the file is not in CSV format, or is malformed (e.g. missing columns)
     * @throws IOException              if an I/O error occurs while reading
     * @throws SecurityException        if the program doesn't have permission to read the file
     */
    @Override
    public List<IPokemon> readFromFile(String filename) throws FileNotFoundException, IllegalArgumentException, IOException, SecurityException {
        String[] filenameParts = filename.split("\\.");
        if (filenameParts.length != 2 || (!filenameParts[1].equals("csv")) && !filenameParts[1].equals("csv~")) {
            throw new IllegalArgumentException("file extension not .csv");
        }

        // this try block is only here to close the InputStreamReader when reading is done/fails. It doesn't catch any exception.
        try (Reader r = new InputStreamReader(new FileInputStream(filename))) {
            List<IPokemon> pkmList = new ArrayList<>();
            for (var s : readLines(r)) pkmList.add(readCsvLineIntoPokemon(s));
            return pkmList;
        }
    }

    /**
     * Deserializes (stores) a list of Pokemon to a {@code .csv} file.
     *
     * @param filename    the name of the file to write to.
     *                    If the file already exists, its contents will be overridden.
     * @param listToStore the list of Pokemon to write
     * @throws FileNotFoundException if the file path doesn't exist, or points to a directory rather than a regular file
     * @throws SecurityException     if the program doesn't have permission to create and write to the file
     * @throws IOException           if an I/O error occurs while writing
     */
    @Override
    public void writeToFile(String filename, List<IPokemon> listToStore) throws FileNotFoundException, IOException, SecurityException {

        // 40 commas; 41 entries
        try (Writer w = new BufferedWriter(new FileWriter(filename))) {

            w.write(HEADER_LINE + "\n"); // <- write header line

            for (IPokemon pk : listToStore) {
                String[] ability_names_array = pk.getAbilityNames();
                String attack = String.valueOf(pk.getAttackStat());
                String base_egg_steps = String.valueOf(pk.getBaseStepsNeededToHatchEgg());
                String base_happiness = String.valueOf(pk.getBaseHappiness());
                String base_total = String.valueOf(pk.getBaseStatTotal());
                String classification = pk.getClassification();
                String defense = String.valueOf(pk.getDefenseStat());
                String experience_growth = String.valueOf(pk.getExperienceGrowthFactor());
                var opt_height_m = pk.getHeightInMeters(); // nullable
                String height_m = opt_height_m.isPresent() ? String.valueOf(opt_height_m.get()) : "";
                String hp = String.valueOf(pk.getHp());
                String name = pk.getName();
                var opt_percentage_male = pk.getPercentageMale(); // nullable for ungendered pokemon
                String percentage_male = opt_percentage_male.isPresent() ? String.valueOf(opt_percentage_male.get()) : "";
                String pokedex_number = String.valueOf(pk.getPokedexNumber());
                String sp_attack = String.valueOf(pk.getSpecialAttackStat());
                String sp_defense = String.valueOf(pk.getSpecialDefenseStat());
                String speed = String.valueOf(pk.getSpeedStat());
                String type1 = pk.getPrimaryType().toString();
                var opt_type2 = pk.getSecondaryType(); // nullable
                String type2 = opt_type2.isPresent() ? opt_type2.get().toString() : "";
                String is_legendary = pk.isLegendary() ? "1" : "0";

                String ability_names = "\"[" + Arrays.stream(ability_names_array).map(s -> "'" + s.trim() + "'").collect(Collectors.joining(", ")) + "]\",";
                w.write(ability_names);
                w.write(",".repeat(18)); // 18 unused match-ups
                w.write(attack + ",");
                w.write(base_egg_steps + ",");
                w.write(base_happiness + ",");
                w.write(base_total + ",");
                w.write(",");
                w.write(classification + ",");
                w.write(defense + ",");
                w.write(experience_growth + ",");
                w.write(height_m + ",");
                w.write(hp + ",");
                w.write(","); // japanese_name is unused
                w.write(name + ",");
                w.write(percentage_male + ",");
                w.write(pokedex_number + ",");
                w.write(sp_attack + ",");
                w.write(sp_defense + ",");
                w.write(speed + ",");
                w.write(type1 + ",");
                w.write(type2 + ",");
                w.write(","); // (weight_kg is unused)
                w.write(","); // (generation is unused)
                w.write(is_legendary);
                w.write("\n"); // <- end the line
            }
        }
    }

    /**
     * Converts a line of CSV into an {@link IPokemon} object, if it is in the correct form.
     * @param pokemonCsvFmtStr the CSV line to serialize into a Pokemon
     * @return a Pokemon represented by {@code pokemonCsvFmtStr}.
     * @throws IllegalArgumentException if {@code pokemonCsvFmtStr} is malformed (in a bad format that cannot be parsed)
     * @throws IOException if an I/O error occurs while reading
     */
    @Override
    public IPokemon readCsvLineIntoPokemon(String pokemonCsvFmtStr) throws IllegalArgumentException, IOException {
        try (Reader r = new StringReader(pokemonCsvFmtStr)) {
            List<String> tokens = readOneLineIntoTokens(r);
            if (tokens.size() != HEADER_LINE_COLUMNS) {
                throw new IllegalArgumentException("expected csv line to contain " + HEADER_LINE_COLUMNS
                        + " columns, but it instead has " + tokens.size());
            }

            // region fields
            var str_ability_names = tokens.get(0);
            // <18 empty type match-up fields>
            var str_attack = tokens.get(19);
            var str_base_egg_steps = tokens.get(20);
            var str_base_happiness = tokens.get(21);
            var str_base_total = tokens.get(22);
            // var str_capture_rate = tokens.get(23);
            var str_classification = tokens.get(24);
            var str_defense = tokens.get(25);
            var str_experience_growth = tokens.get(26);
            var str_height_m = tokens.get(27);
            var str_hp = tokens.get(28);
            // var str_japanese_name = tokens.get(29);
            var str_name = tokens.get(30);
            var str_percentage_male = tokens.get(31);
            var str_pokedex_number = tokens.get(32);
            var str_sp_attack = tokens.get(33);
            var str_sp_defense = tokens.get(34);
            var str_speed = tokens.get(35);
            var str_type1 = tokens.get(36).toUpperCase(); // <- make uppercase so we can retrieve type via enum, e.g. PokemonType.GRASS
            var str_type2 = tokens.get(37).toUpperCase(); // <- make uppercase so we can retrieve type via enum, e.g. PokemonType.GRASS
            // var str_weight_kg = tokens.get(38);
            // var str_generation = tokens.get(39);
            var str_is_legendary = tokens.get(40);
            // endregion

            // region transformed fields
            int attack = Integer.parseInt(str_attack);
            int base_egg_steps = Integer.parseInt(str_base_egg_steps);
            int base_happiness = Integer.parseInt(str_base_happiness);
            int base_total = Integer.parseInt(str_base_total);
            int defense = Integer.parseInt(str_defense);
            int experience_growth = Integer.parseInt(str_experience_growth);
            Optional<Float> height_m = str_height_m.length() == 0 ? Optional.empty() : Optional.of(Float.parseFloat(str_height_m));
            int hp = Integer.parseInt(str_hp);
            Optional<Float> percentage_male = str_percentage_male.length() == 0 ? Optional.empty() : Optional.of(Float.parseFloat(str_percentage_male));
            int pokedex_number = Integer.parseInt(str_pokedex_number);
            int sp_attack = Integer.parseInt(str_sp_attack);
            int sp_defense = Integer.parseInt(str_sp_defense);
            int speed = Integer.parseInt(str_speed);
            PokemonType type1 = PokemonType.valueOf(str_type1);
            Optional<PokemonType> type2 = str_type2.length() == 0 ? Optional.empty() : Optional.of(PokemonType.valueOf(str_type2));
            // Optional<Float> weight_kg = str_weight_kg.length() == 0 ? Optional.empty() : Optional.of(Float.parseFloat(str_weight_kg));
            // int generation = Integer.parseInt(str_generation);
            boolean is_legendary = str_is_legendary.equals("1");
            // endregion


            return new PokemonDW(str_name, pokedex_number, str_classification, type1, type2, is_legendary,
                    hp, attack, defense, sp_attack, sp_defense, speed, base_total,
                    str_ability_names, experience_growth, base_egg_steps, base_happiness, height_m, percentage_male);
        }
    }
}
