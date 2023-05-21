package org.example;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        String dir = System.getProperty("user.dir") + "/content/cornell_movie_quotes_corpus";
        Utils.generateMd(dir);
//        Thread.sleep(5000);
        Utils.generateHtml(dir);
    }
}
