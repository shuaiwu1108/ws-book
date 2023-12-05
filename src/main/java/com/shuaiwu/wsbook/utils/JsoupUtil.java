package com.shuaiwu.wsbook.utils;

import java.util.ListIterator;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.crypto.Mac;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * html解析工具
 * <p>
 * 2023/12/2 23:53
 **/
@Slf4j
public class JsoupUtil {
    // 获取笔趣阁-首页下的所有书籍类型
    public static List<String> bqg_index(String html){
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("div.nav ul li a[href]");
        links.remove(0);
        links.remove(links.size() - 1);
        links.remove(links.size() - 1);
        List<String> tmp = new ArrayList<>();
        for (Element link : links) {
            tmp.add(link.attr("href"));
        }
        return tmp;
    }

    // 获取笔趣阁-某个类别的所有书籍
    public static List<String> bqg_type(String html){
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("div.r ul li a[href]");
        List<String> tmp = new ArrayList<>();
        for (Element link : links) {
            tmp.add(link.attr("href"));
        }
        return tmp;
    }

    // 获取某个书籍的目录
    public static Map<String, String> bqg_catalogue(String html){
        Map<String, String> map = new HashMap<>();
        map.put("author", "未知");
        Document doc = Jsoup.parse(html);
        Element description = doc.getElementById("intro");
        String descriptionText = description.text();
        Elements bookNames = doc.select("div#info h1");
        if(!bookNames.isEmpty()){
            Element bookName = bookNames.get(0);
            map.put("name", bookName.text());
        }
        Elements authors = doc.select("div#info p a");
        if(!authors.isEmpty()){
            Element element = authors.get(0);
            map.put("author", element.text());
        }
        map.put("description", descriptionText);
//        Elements links = doc.select("dl dd a[href]");
//        for (Element link : links) {log.info(link.attr("href") + ", " + link.text());}
//        map.put("catalogueSize", links.size() + "");

        Element element = doc.select("div#fmimg img[src]").get(0);
        String icon = element.attr("src");

        map.put("icon", icon);
        return map;
    }

    public static List<String> bqg_catalogue_single(String html){
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("dl dd a[href]");
        List<String> tmps = new ArrayList<>();
        for (Element link : links) {
            tmps.add(link.text().concat("======").concat(link.attr("href")));
        }
        return tmps;
    }

    public static String readHtml(String name){
        String path = System.getProperty("user.dir").concat(File.separator)
                .concat("src").concat(File.separator)
                .concat("main").concat(File.separator)
                .concat("resources").concat(File.separator)
                .concat(name);
        return path;
    }
}
