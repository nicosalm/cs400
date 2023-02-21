import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CHSearchBackendFD implements CHSearchBackendInterface {

    public CHSearchBackendFD() {
    }

    @Override
    public void loadData(String filename) throws FileNotFoundException {
        if (!filename.equals("posts.txt")) {
            throw new FileNotFoundException();
        }

    }

    @Override
    public List<String> findPostsByTitleWords(String words) {
        List<String> posts = new ArrayList<String>();
        if (words.equals("apple")) {
            posts.add("Apples [title]");
            posts.add("Bananas and Apples [title]");
        } else if (words.equals("banana")) {
            posts.add("Bananas [title]");
            posts.add("Bananas and Apples [title]");
        } else if (words.equals("orange")) {
            posts.add("Oranges [title]");
        }

        return posts;
    }

    @Override
    public List<String> findPostsByBodyWords(String words) {
        List<String> posts = new ArrayList<String>();
        if (words.equals("apple")) {
            posts.add("Apples are good for you [body]");
            posts.add("Bananas and apples are good for you [body]");
        } else if (words.equals("banana")) {
            posts.add("Bananas are good for you [body]");
            posts.add("Bananas and apples are good for you [body]");
        } else if (words.equals("orange")) {
            posts.add("Oranges are good for you [body]");
        }

        return posts;
    }

    @Override
    public List<String> findPostsByTitleOrBodyWords(String words) {
        List<String> posts = new ArrayList<String>();
        if (words.equals("apple")) {
            posts.add("Apples [title]");
            posts.add("Apples are good for you [body]");
            posts.add("Bananas and Apples [title]");
            posts.add("Bananas and apples are good for you [body]");
        } else if (words.equals("banana")) {
            posts.add("Bananas [title]");
            posts.add("Bananas are good for you [body]");
            posts.add("Bananas and Apples [title]");
            posts.add("Bananas and apples are good for you [body]");
        } else if (words.equals("orange")) {
            posts.add("Oranges [title]");
            posts.add("Oranges are good for you [body]");
        }

        return posts;
    }

    @Override
    public String getStatisticsString() {
        return "Statistics: 4 posts, 4 unique words";
    }

}