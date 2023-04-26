class Regex {
    public static void main(String[] args) {
        // demo of regex in java
        String str = "Hello World!";
        System.out.println(str.matches("Hello World!")); // true

        // . matches any character
        System.out.println(str.matches("............")); // true

        // any character 0 or more times
        System.out.println(str.matches("[a-zA-Z0-9\s]+")); // false, includes ! at the end

        // remove all non-alphanumeric characters
        System.out.println(str.replaceAll("[^a-zA-Z0-9\s]", "")); // Hello World

        // append a space after every character
        System.out.println(str.replaceAll(".", "$0 ")); // H e l l o W o r l d !

        // delete the word "World"
        System.out.println(str.replaceAll("World", "")); // Hello !

        // user@domain.com, pick out user
        String email = "nico@gmail.com";
        System.out.println(email.replaceAll("(.*)@.*", "$1")); // user

    }
}