import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The Frontend. The general objective is to create an interactive menu
 * displayed via a command loop. The user will select an option, and the
 * corresponding command will be run, drawing information through the backend
 * and printing it for the user to read.
 */
class CHSearchFrontendFD implements CHSearchFrontendInterface {

    private Scanner userInput;
    private CHSearchBackendInterface backend;

    public CHSearchFrontendFD(Scanner userInput, CHSearchBackendInterface backend) {
        this.userInput = userInput;
        this.backend = backend;
    }

    /**
     * This method is in-charge of the command loop which runs until the user quits.
     * Consider the cases from mainMenuPrompt, retrieve the user input with a call
     * to mainMenuPrompt, then run the corresponding method.
     */
    @Override
    public void runCommandLoop() {
        char command = mainMenuPrompt(); // get the first command
        while (command != 'q') { // while the command is not quit
            switch (command) { // run the corresponding method
                case 'l':
                    loadDataCommand();
                    break;
                case 't':
                    searchTitleCommand(chooseSearchWordsPrompt());
                    break;
                case 'b':
                    searchBodyCommand(chooseSearchWordsPrompt());
                    break;
                case 'p':
                    searchPostCommand(chooseSearchWordsPrompt());
                    break;
                case 's':
                    displayStatsCommand();
                    break;
                default:
                    System.out.println("Invalid command");
            }
            command = mainMenuPrompt(); // get the next command
        }
    }

    /**
     * This simple method prints the menu menu prompt as a series of print
     * statements with the different possible key-presses. This method returns the
     * first character of the user’s input string. This will be sent over to
     * runCommandLoop.
     * 
     * @return the first character of the user’s input string
     */
    @Override
    public char mainMenuPrompt() {
        System.out.println("\nMain Menu:");
        System.out.println("l) Load data from file");
        System.out.println("t) Search for posts by title");
        System.out.println("b) Search for posts by body");
        System.out.println("p) Search for posts by title or body");
        System.out.println("s) Display statistics");
        System.out.println("q) Quit");
        System.out.print("Enter a command: ");
        String input = userInput.nextLine().trim(); // get the user input and trim it of whitespace
        if (input.length() > 0) { // if the input is not empty
            return input.charAt(0); // return the first character
        } else { // if the input is empty
            return '\0'; // return null
        }
    }

    /**
     * This method loads File data from user input. The user will type the file
     * name, and if the file exists it will send a call to backend’s loadData method
     * with the imported file as the parameter.
     */
    @Override
    public void loadDataCommand() {
        System.out.print("Enter filename: ");
        String filename = userInput.nextLine().trim(); // get the user input and trim it of whitespace
        try {
            backend.loadData(filename); // try to load the file
            System.out.println("File loaded!");
        } catch (FileNotFoundException e) { // if the file is not found, catch the exception
            System.out.println("File not found.");
        }
    }

    /**
     * This method prompts the user for input, splits the words up at each “ “
     * (space) character, and returns it as a List. This List of Strings will be
     * used when searching for words in the next three methods.
     * 
     * @return a List of Strings that the user inputs separated by spaces
     */
    @Override
    public List<String> chooseSearchWordsPrompt() {
        System.out.print("Enter search words: ");
        String words = userInput.nextLine().trim(); // get the user input and trim it of whitespace
        return Arrays.asList(words.split(" ")); // split the words at each space and return them as a List
    }

    /**
     * Enumerate through your user input. For each word in the list, generate a list
     * of posts by calling backend’s findPostsByTitleWords, feeding the current word
     * in as the parameter. Then, print each post in that list.
     */
    @Override
    public void searchTitleCommand(List<String> words) {
        System.out.println("Retrieving posts with title words: \"" + words + "\"");
        for (String word : words) {
            List<String> posts = backend.findPostsByTitleWords(word); // get the posts with the current word
            for (String post : posts) { // print each post
                System.out.println("\t" + post);
            }
        }
    }

    /**
     * Enumerate through your user input. For each word in the list, generate a list
     * of posts by calling backend’s findPostsByBodyWords, feeding the current word
     * in as the parameter. Then, print each post in that list.
     */
    @Override
    public void searchBodyCommand(List<String> words) {
        System.out.println("Retrieving posts with body words: \"" + words + "\"");
        for (String word : words) {
            List<String> posts = backend.findPostsByBodyWords(word); // get the posts with the current word
            for (String post : posts) { // print each post
                System.out.println(post);
            }
        }
    }

    /**
     * Enumerate through your user input. For each word in the list, generate a list
     * of posts by calling backend’s findPostsbyTitleOrBodyWords, feeding the
     * current word in as the parameter. Then, print each post in that list.
     */
    @Override
    public void searchPostCommand(List<String> words) {
        System.out.println("Retrieving posts with title or body words: \"" + words + "\"");
        for (String word : words) { // get the posts with the current word
            List<String> posts = backend.findPostsByTitleOrBodyWords(word); // print each post
            for (String post : posts) {
                System.out.println(post);
            }
        }
    }

    /**
     * Print out a call to backend’s getStatisticsString method.
     */
    @Override
    public void displayStatsCommand() {
        System.out.println(backend.getStatisticsString()); // print the statistics
    }
}