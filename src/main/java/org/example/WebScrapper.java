package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScrapper {
    static String url = "https://www.google.com.vn/search?q=nothing&source=lnms&tbm=isch&sa=X&ved=2ahUKEwj5t8vRyJL9AhVer1YBHTaxA2EQ_AUoAXoECAEQAw&biw=1920&bih=947";
    static String name;
    public static void main(String[] args) {
        getDesireImage();
        try {
            final Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
            Elements e = document.getElementsByTag("img");
            int number = 0;
            for (Element a : e) {
                Matcher matcher = analyseLinkForDownloadable(a);
                if (matcher.matches()) {
                    saveImage(matcher,number);
                    number++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getDesireImage(){
        System.out.println("What do you want to download: ");

        Scanner sc = new Scanner(System.in);
        name = sc.nextLine();

        url = url.replace("nothing", name.replace(" ", "+").toLowerCase());
    }

    public static Matcher analyseLinkForDownloadable(Element a){
        String imgLink = String.valueOf(a.select(".yWs4tf"));
        Pattern pattern = Pattern.compile("<img class=\"yWs4tf\" alt=\"\" src=\"([^\"]*([^\"]*(?:[^\\\\\"]|\\\\\\\\|\\\\\")*)+)\">", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(imgLink);
        return matcher;
    }

    public static void saveImage(Matcher matcher, int number) throws IOException {
        Path output = Paths.get("C:\\Users\\caoph\\Desktop\\Download\\"+number+"_"+name+".png");
        try (InputStream inputStream = new URL(matcher.group(1)).openStream()){
            Files.copy(inputStream, output, StandardCopyOption.REPLACE_EXISTING);
        } finally{
            System.out.println("DONE!!!");
        }
    }

}
