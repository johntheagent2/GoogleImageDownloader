package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScrapper {
    public static void main(String[] args) {
        int count = 1;
        final String url = "https://www.google.com.vn/search?q=pikachu&source=lnms&tbm=isch&sa=X&ved=2ahUKEwj5t8vRyJL9AhVer1YBHTaxA2EQ_AUoAXoECAEQAw&biw=1920&bih=947";

        try {
            final Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();

            Elements e = document.getElementsByTag("img");
            for (Element a : e) {
                String imgLink = String.valueOf(a.select(".yWs4tf"));

                Pattern pattern = Pattern.compile("<img class=\"yWs4tf\" alt=\"\" src=\"([^\"]*([^\"]*(?:[^\\\\\"]|\\\\\\\\|\\\\\")*)+)\">", Pattern.CASE_INSENSITIVE);

                Matcher matcher = pattern.matcher(imgLink);
                if (matcher.matches()) {
                    Path output = Paths.get("C:\\Users\\caoph\\Desktop\\Download\\"+Math.random()+".png");
                    try (InputStream inputStream = new URL(matcher.group(1)).openStream()){
                        Files.copy(inputStream, output, StandardCopyOption.REPLACE_EXISTING);
                    } finally{
                        System.out.println("DONE!!!");
                    }
                }
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
