import java.util.Scanner;

public class Pokeylog {
    public static void main(String[] args) {

        // use data wrangler's catalog reader
        ICatalogReader reader = new CatalogReaderAE();

        // use the AE implementation of the RBT
        IRbt<IPokemon> rbt = new RbtAE();

        // use BD implementation of the backend
        IBackend backend = new BackendAE(rbt, reader);

        Scanner input = new Scanner(System.in);

        // use FD implementation of the frontend
        IFrontend frontend = new FrontendAE(input, backend);

        // execute the frontend
        frontend.runCommandLoop();

    }

}
