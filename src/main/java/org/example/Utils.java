package org.example;

import com.google.common.io.CharStreams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Utils {

    public static String s = " \\+\\+\\+\\$\\+\\+\\+";

    public static void generateMd(String dirPath) throws Exception {
        String path = dirPath + "/moviequotes.scripts.txt";
        File file = new File(path);

        File output = new File(dirPath + "/output/");
        if (output.exists()) {
            for (File listFile : output.listFiles()) {
                listFile.delete();
            }
        } else {
            output.mkdir();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineArr = line.split(s);
                String lineId = lineArr[0];
                String movieTitle = lineArr[1].replace("face/off", "face off").trim();
                String movieLineNr = lineArr[2].trim();
                String character = lineArr[3];
                String replyToLineId = lineArr[4];
                String sentence = lineArr[5];
                Line obj = new Line(lineId, movieTitle, movieLineNr, character, replyToLineId, sentence);
                String txt = output + "/" + obj.movieTitle + ".md";
                File outputFile = new File(txt);

                String str = "**" + obj.character.trim() + "**: " + obj.sentence.trim() + "\n\n";
                if (outputFile.exists()) {
                    Files.write(Paths.get(txt), str.getBytes(), StandardOpenOption.APPEND);
                } else {
                    outputFile.createNewFile();
                    Files.write(Paths.get(txt), ("# " + obj.movieTitle + "\n\n").getBytes(), StandardOpenOption.APPEND);
                    Files.write(Paths.get(txt), str.getBytes(), StandardOpenOption.APPEND);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateHtml(String dirPath) throws Exception {
        File output = new File(dirPath + "/output/");
        File outputHtml = new File(dirPath + "/output-html/");
        if (!outputHtml.exists()) {
            outputHtml.mkdir();
        }

        File[] files = output.listFiles();
        for (File md : files) {
            String mdPath = md.getAbsolutePath();
            String mdToHtmlCmd = String.format("pandoc %s -s -o %s --highlight-style=pygments", mdPath,
                    outputHtml + "/" + md.getName().replace(".md", ".html"));
            System.out.println("mdToHtmlCmd = " + mdToHtmlCmd);
            Runtime.getRuntime().exec(mdToHtmlCmd);

            FileReader reader = new FileReader(System.getProperty("user.dir") + "/index.html");
            String result = CharStreams.toString(reader);
            Document index = Jsoup.parse(result);
            Element body = index.body();
            System.out.println("index.body() = " + body.html());
        }
    }

    public static void parse() {
        
    }
    
    static class Line {
        private String lineId;
        private String movieTitle;
        private String movieLineNr;
        private String character;
        private String replyToLineId;
        private String sentence;

        public Line(String lineId, String movieTitle, String movieLineNr, String character, String replyToLineId, String sentence) {
            this.lineId = lineId;
            this.movieTitle = movieTitle;
            this.movieLineNr = movieLineNr;
            this.character = character;
            this.replyToLineId = replyToLineId;
            this.sentence = sentence;
        }
    }
}

