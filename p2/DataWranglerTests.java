import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class DataWranglerTests {

    private static final String CANONICAL_FILENAME = "pokemon.csv";

    /**
     * Checks that no exceptions are thrown when loading the canonical (valid) CSV file, {@code pokemon.csv}.
     *
     * @throws IOException
     */
    @Test
    public void testReadCanonicalFile() throws IOException {
        var cr = new CatalogReaderDW();
        cr.readFromFile(CANONICAL_FILENAME);
        // ^ if an exception is thrown on a valid file, this test fails.
    }

    /**
     * Checks that no exceptions are thrown when parsing sample CSV lines from the canonical file,
     * particularly when parsing the following fields:
     * <ul>
     *     <li>{@code ability_names}</li>
     *     <li>{@code height_m} (nullable)</li>
     *     <li>{@code percentage_male} (nullable)</li>
     *     <li>{@code type2} (nullable)</li>
     *     <li>{@code weight_kg} (nullable)</li>
     * </ul>
     *
     * @throws IOException
     */
    @Test
    public void testReadCanonicalCSVLines() throws IOException {
        var cr = new CatalogReaderDW();
        cr.readCsvLineIntoPokemon("['Shields Down'],0.5,1,1,2,1,1,0.5,0.5,1,1,0,2,0.5,0.5,1,2,2,2,100,6400,70,500,30 (Meteorite)255 (Core),Meteor Pokémon,60,1059860,0.3,60,Metenoメテノ,Minior,,774,100,60,120,rock,flying,40.0,7,0\n");
        cr.readCsvLineIntoPokemon("\"['Reckless', 'Rock Head', 'Adaptability', 'Mold Breaker']\",1,1,1,2,1,1,0.5,1,1,2,1,0.5,1,1,1,1,0.5,0.5,92,10240,70,460,25,Hostile Pokémon,65,1000000,1.0,70,Bassraoバスラオ,Basculin,50,550,80,55,98,water,,18.0,5,0\n");
        cr.readCsvLineIntoPokemon("['Multitype'],1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,1,120,30720,0,720,3,Alpha Pokémon,120,1250000,3.2,120,Arceusアルセウス,Arceus,,493,120,120,120,normal,,320.0,4,1\n");
    }

    /**
     * Tests that no exceptions are thrown when parsing sample CSV lines in the format written by {@link ICatalogReader#writeToFile(String, List)}.
     * As an example, the original {@code pokemon.csv} file includes type matchup multiplier fields,
     * but the write method omits them. This will check every nullable field.
     *
     * @throws IOException
     */
    @Test
    public void testReadPostprocessCSVLines() throws IOException {
        var cr = new CatalogReaderDW();
        cr.readCsvLineIntoPokemon("\"['Multitype']\",,,,,,,,,,,,,,,,,,,120,30720,0,720,0.0,Alpha Pokémon,120,1250000,3.2,120,,Arceus,,493,120,120,120,Normal,,,,1\n");
        cr.readCsvLineIntoPokemon("\"['Reckless', 'Rock Head', 'Adaptability', 'Mold Breaker']\",,,,,,,,,,,,,,,,,,,92,10240,70,460,0.0,Hostile Pokémon,65,1000000,1.0,70,,Basculin,50.0,550,80,55,98,Water,,,,0\n");
        cr.readCsvLineIntoPokemon("\"['Shields Down']\",,,,,,,,,,,,,,,,,,,100,6400,70,500,,Meteor Pokémon,60,1059860,0.3,60,,Minior,,774,100,60,120,Rock,Flying,,,0\n");
    }

    /**
     * Checks that no exceptions are thrown when reading odd (but valid) CSV lines,
     * including those with empty strings.
     *
     * @throws IOException
     */
    @Test
    public void testReadOddCSVLines() throws IOException {
        var cr = new CatalogReaderDW();
        cr.readCsvLineIntoPokemon("\"[',,-, , ,']\",,,,,,,,,,,,,,,,,,,120,30720,0,720,,,120,1250000,3.2,120,,,,493,120,120,120,Normal,,,,1\n");
    }


    // region Failing tests

    /**
     * Checks that {@link IllegalArgumentException} is thrown by {@link ICatalogReader#readFromFile(String)}
     * when given a invalid file extension.
     */
    @Test
    public void testBadFileExtensionThrowsIAE() {
        var cr = new CatalogReaderDW();
        assertThrows(IllegalArgumentException.class, () -> cr.readFromFile("pokemon.txt"));
    }

    /**
     * Checks that {@link IllegalArgumentException} is thrown by {@link ICatalogReader#readFromFile(String)}
     * when given an invalid file name.
     */
    @Test
    public void testBadFilenameThrowsIAE() {
        var cr = new CatalogReaderDW();
        assertThrows(IllegalArgumentException.class, () -> cr.readFromFile(CANONICAL_FILENAME + ".txt"));
    }

    /**
     * Checks that {@link FileNotFoundException} is thrown by {@link ICatalogReader#readFromFile(String)}
     * when given a nonexistent file name.
     */
    @Test
    public void testMissingFileThrowsFNFE() {
        var cr = new CatalogReaderDW();
        assertThrows(FileNotFoundException.class, () -> cr.readFromFile("dne.csv"));
    }

    /**
     * Checks that {@link IllegalArgumentException} is thrown by {@link ICatalogReader#readCsvLineIntoPokemon(String)}
     * when given a line that doesn't have all columns.
     */
    @Test
    public void testInvalidCsvLineWithMissingColumns() {
        var cr = new CatalogReaderDW();
        assertThrows(IllegalArgumentException.class, () -> cr.readCsvLineIntoPokemon("not,41,columns"));
    }

    /**
     * Checks that {@link IllegalArgumentException} is thrown by {@link ICatalogReader#readCsvLineIntoPokemon(String)}
     * when given a line that has too many columns.
     */
    @Test
    public void testInvalidCsvLineWithExtraColumns() {
        var cr = new CatalogReaderDW();
        assertThrows(IllegalArgumentException.class, () -> {
            cr.readCsvLineIntoPokemon("\"['Overgrow', 'Chlorophyll']\",1,1,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,318,45,Seed Pokémon,49,1059860,0.7,45,Fushigidaneフシギダネ,Bulbasaur,88.1,1,65,65,45,grass,poison,6.9,1,0" + ",,,");
        });
    }

    /**
     * Checks that {@link IllegalArgumentException} is thrown by {@link ICatalogReader#readCsvLineIntoPokemon(String)}
     * when given a line whose data is malformed.
     */
    @Test
    public void testInvalidCsvLine() {
        var cr = new CatalogReaderDW();
        assertThrows(IllegalArgumentException.class, () -> {
            cr.readCsvLineIntoPokemon("\"['Overgrow', 'Chlorophyll']\",1,1,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,[MALFORMED],5120,70,318,45,Seed Pokémon,49,1059860,0.7,45,Fushigidaneフシギダネ,Bulbasaur,88.1,1,65,65,45,grass,poison,6.9,1,0");
        });
    }
    // endregion

    /**
     * Sample dummy entries are used for this test.
     * Checks that no exceptions are thrown when writing to file with {@link ICatalogReader#writeToFile(String, List)}.
     * Also checks for name and type equality between the entries read/written to.
     *
     * @throws IOException
     */
    @Test
    public void testWriteWithSampleEntries() throws IOException {
        var filename = "test_write_sample.csv~";
        var cr = new CatalogReaderDW();

        var p2 = PokemonDW.from("Algoengineer", PokemonType.PSYCHIC);
        var p1 = PokemonDW.from("Datawranglerer", PokemonType.NORMAL, PokemonType.ELECTRIC);

        cr.writeToFile(filename, List.of(p1, p2));
        var pkms = cr.readFromFile(filename); // <- tests that read works after a write


        // do some equality checks to check that fields are written to file
        assertEquals(0, pkms.get(0).compareTo(p1));
        assertEquals(0, pkms.get(1).compareTo(p2));

        assertEquals(pkms.get(0).getSecondaryType(), p1.getSecondaryType());
        assertEquals(pkms.get(1).getPrimaryType(), p2.getPrimaryType());
    }

    /**
     * Canonical entries from the {@code pokemon.csv} file are used for this test.
     * Checks that no exceptions are thrown when writing to file with {@link ICatalogReader#writeToFile(String, List)}.
     * Also checks for name and type equality between the entries read/written to.
     *
     * @throws IOException
     */
    @Test
    public void testWriteWithCanonicalEntries() throws IOException {
        var filename = "test_write_canonical.csv~";
        var cr = new CatalogReaderDW();

        var p1 = cr.readCsvLineIntoPokemon("['Shields Down'\"\"],0.5,1,1,2,1,1,0.5,0.5,1,1,0,2,0.5,0.5,1,2,2,2,100,6400,70,500,30 (Meteorite)255 (Core),Meteor Pokémon,60,1059860,0.3,60,Metenoメテノ,Minior,,774,100,60,120,rock,flying,40.0,7,0\n");
        var p2 = cr.readCsvLineIntoPokemon("\"['Reckless', 'Rock Head', 'Adaptability', 'Mold Breaker']\",1,1,1,2,1,1,0.5,1,1,2,1,0.5,1,1,1,1,0.5,0.5,92,10240,70,460,25,Hostile Pokémon,65,1000000,1.0,70,Bassraoバスラオ,Basculin,50,550,80,55,98,water,,18.0,5,0\n");
        var p3 = cr.readCsvLineIntoPokemon("['Multitype'],1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,1,120,30720,0,720,3,Alpha Pokémon,120,1250000,3.2,120,Arceusアルセウス,Arceus,,493,120,120,120,normal,,320.0,4,1\n");

        cr.writeToFile(filename, List.of(p1, p2, p3));
        var pkms = cr.readFromFile(filename); // <- tests that read works after a write


        // do some equality checks to check that fields are written to file
        assertEquals(0, pkms.get(0).compareTo(p1));
        assertEquals(0, pkms.get(1).compareTo(p2));
        assertEquals(0, pkms.get(2).compareTo(p3));

        assertEquals(pkms.get(0).getSecondaryType(), p1.getSecondaryType());
        assertEquals(pkms.get(1).getPrimaryType(), p2.getPrimaryType());
        assertEquals(pkms.get(2).getPrimaryType(), p3.getPrimaryType());
    }


    /**
     * Checks that {@link BackendBD#loadDataFromFile(String)} loads {@link RbtAE}
     * such that Pokemon from the {@code pokemon.csv} are present in the red-black tree.
     */
    @Test
    public void DWIntegrationTestLoadCanonicalFile() throws IllegalArgumentException, SecurityException, IOException {
        var rbt = new RbtAE();
        var reader = new CatalogReaderDW();
        var backend = new BackendBD(rbt, reader);

        backend.loadDataFromFile(CANONICAL_FILENAME);

        // check that the rbt contains arbitrary pokemon; shouldn't throw NSEE
        rbt.get("Bulbasaur");
        rbt.get("Arbok");
        rbt.get("Arceus");
        rbt.get("Minior");
        rbt.get("Xatu");
        rbt.get("Aerodactyl");
        rbt.get("Nidoran♀");
    }

    /**
     * Checks correctness of output when loading the canonical file, adding a sample Pokemon, searching for it,
     * and exporting the loaded Pokemon to file.
     *
     * @throws IOException
     */
    @Test
    public void DWIntegrationTestAddSearchAndExport() throws IOException {
        var tempfilename = "test_DW_integrations.csv~";
        var csv_str = "['dne'],,,,,,,,,,,,,,,,,,,-1,-99999,0,-6,,,-1,1000000,-3.0,-1,,Missingno,,-999,-1,-1,-1,Ghost,,,,1\n";
        var textuitester = new TextUITester("L\n" + CANONICAL_FILENAME + "\nA\n" + csv_str + "\nS\nMissingno\nE\n" + tempfilename + "\nQ");

        try (Scanner scanner = new Scanner(System.in)) {
            var rbt = new RbtAE();
            var reader = new CatalogReaderDW();
            var backend = new BackendBD(rbt, reader);
            var frontend = new FrontendFD(scanner, backend);
            frontend.runCommandLoop();

            // check that the searched pokemon appears when searched
            assertTrue(textuitester.checkOutput().contains("=> Missingno (id: -999)"));

            // now check that Missingno is contained in the written tempfile
            // check that one AND ONLY ONE pokemon with name "Missingno" appears
            var savedPks = reader.readFromFile(tempfilename);
            assertEquals(1, savedPks.stream().map(IPokemon::getName).filter(s -> s.equals("Missingno")).toList().size());
        }
    }

    /**
     * Tests general RBT insertion properties in {@link RbtAE}.
     */
    @Test
    public void CodeReviewOfAlgorithmEngineerRbtInsertGeneral() {
        var rbt = new RbtAE();
        assertEquals(0, rbt.size()); // check that 0 elements are in rbt initially
        assertTrue(rbt.isEmpty());

        var f = PokemonDW.from("f");
        var e = PokemonDW.from("e");
        var d = PokemonDW.from("d");
        var c = PokemonDW.from("c");
        var b = PokemonDW.from("b");
        var a = PokemonDW.from("a");

        rbt.insert(f);
        assertEquals(1, rbt.size());
        assertFalse(rbt.isEmpty());

        rbt.insert(e);
        rbt.insert(d);
        rbt.insert(c);
        rbt.insert(b);
        rbt.insert(a); // now check if an element exists

        // check size == 6
        assertEquals(6, rbt.size());

        // check that all items are contained in rbt
        assertEquals(a, rbt.get("a"));
        assertEquals(b, rbt.get("b"));
        assertEquals(c, rbt.get("c"));
        assertEquals(d, rbt.get("d"));
        assertEquals(e, rbt.get("e"));
        assertEquals(f, rbt.get("f"));

        // check black heights
        assertEquals(1, rbt.root.blackHeight);
        assertEquals(0, rbt.root.context[1].blackHeight);
        assertEquals(1, rbt.root.context[2].blackHeight);
        assertEquals(1, rbt.root.context[1].context[1].blackHeight);
        assertEquals(1, rbt.root.context[1].context[2].blackHeight);
        assertEquals(0, rbt.root.context[1].context[1].context[1].blackHeight);

        // check that an item we didn't add is NOT in the rbt
        assertFalse(rbt.contains(PokemonDW.from("dne")));
    }

    /**
     * Checks that duplicates aren't allowed in {@link RbtAE}.
     */
    @Test
    public void CodeReviewOfAlgorithmEngineerRbtInsertDuplicateThrowsIAE() {
        var rbt = new RbtAE();
        var norm = PokemonDW.from("a", PokemonType.GHOST);
        var duped = PokemonDW.from("a");
        rbt.insert(norm);
        assertThrows(IllegalArgumentException.class, () -> {
            rbt.insert(duped); // <- should throw IAE
        });
    }

    /**
     * Checks red-black tree properties after removing a Pokemon from {@link RbtAE}.
     */
    @Test
    public void CodeReviewOfAlgorithmEngineerRbtRemove() {
        var rbt = new RbtAE();

        var f = PokemonDW.from("f");
        var e = PokemonDW.from("e");
        var d = PokemonDW.from("d");
        var c = PokemonDW.from("c");
        var b = PokemonDW.from("b");
        var a = PokemonDW.from("a");

        rbt.insert(f);
        rbt.insert(e);
        rbt.insert(d);
        rbt.insert(c);
        rbt.insert(b);
        rbt.insert(a);

        rbt.remove(a); // check black heights after removing `a`
        assertEquals(1, rbt.root.blackHeight);
        assertEquals(0, rbt.root.context[1].blackHeight);
        assertEquals(1, rbt.root.context[2].blackHeight);
        assertEquals(1, rbt.root.context[1].context[1].blackHeight);
        assertEquals(1, rbt.root.context[1].context[2].blackHeight);
    }
}
