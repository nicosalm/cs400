import java.io.IOException;
import java.util.Scanner;

public class FrontendAE implements IFrontend {

    Scanner input;
    IBackend backend;

    public FrontendAE(Scanner input, IBackend backend) {
        this.input = input;
        this.backend = backend;
    }

    @Override
    public void runCommandLoop() {
        mainMenuPrompt();
    }

    @Override
    public char mainMenuPrompt() {
        System.out.println("Menu:");
        return 'a';
    }

    @Override
    public void loadDataCommand() {
        try {
            backend.loadDataFromFile("test");
        } catch (IllegalArgumentException | SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchCommand() {
        backend.searchFor("test");
    }

    @Override
    public void addCommand() {
        try {
            backend.addPokemon("test");
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCommand() {
        backend.removePokemon("test");
    }

    @Override
    public void saveDataCommand() {
        try {
            backend.saveDataToFile("test");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayCatalogStatsCommand() {
        System.out.println(backend.displayCatalogStats());
    }

}
