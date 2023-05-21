package org.example;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        String dir = System.getProperty("user.dir") + "/content/cornell_movie_quotes_corpus";
        System.out.println(dir);
        Utils.process(dir);
    }
}
