package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Testdouban01 {
    public static void crawling(String url, Movie a) {
        Document doc1;
        Elements tag1;
        Elements tag2 = null;
        boolean commetGet = true;
        try {
            Thread.sleep(5000);
            doc1 = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)")
                    .timeout(300000)
                    .get();
        } catch (IOException | InterruptedException e) {
            return;
        }
        //程序走到这儿说明doc1已经get到了网站

        try {
            tag1 = doc1.getElementById("info").getElementsByClass("pl");
        } catch (Exception e) {
            return;
        }

        try {
            tag2 = doc1.getElementById("hot-comments").getElementsByClass("comment-item");
        } catch (Exception e) {
            commetGet = false;
        }


        //电影信息
        try {
            a.setMovie(doc1.getElementById("content").getElementsByTag("h1").text());
        } catch (Exception e) {
            //doing nothing!
        }
        try {
            a.setCover(doc1.getElementById("mainpic").getElementsByTag("a").first().getElementsByTag("img").attr("src"));
        } catch (Exception e) {
            //doing nothing!
        }
        try {
            a.setIntro(doc1.getElementById("link-report").getElementsByTag("span").first().text());
        } catch (Exception e) {
            //doing nothing!
        }


        //这里的导演，编剧，主演，片长均为class为pl的标签的文本，先后顺序可能不一致，所以我遍历这个名的class，再抓取有用信息，避免混淆
        for (Element element : tag1) {
            switch (element.text()) {
                case "导演":
                    try {
                        a.setDirector(element.nextElementSibling().text());
                    } catch (Exception e) {
                        //doing nothing!
                    }
                    break;
                case "编剧":
                    try {
                        a.setScriptwriter(element.nextElementSibling().text());
                    } catch (Exception e) {
                        //doing nothing!
                    }
                    break;
                case "主演":
                    try {
                        a.setActor(element.nextElementSibling().text());
                    } catch (Exception e) {
                        //doing nothing!
                    }
                    break;
                case "片长:":
                    try {
                        a.setLength(element.nextElementSibling().text());
                    } catch (Exception e) {
                        //doing nothing!
                    }
                    break;
            }
        }

        //以下信息均可一一对应不会混淆，所以不需要用for遍历
        try {
            a.setRate(doc1.getElementById("interest_sectl").getElementsByClass("ll rating_num").text());
        } catch (Exception e) {
            //doing nothing!
        }
        try {
            a.setRating_people(doc1.getElementsByClass("rating_people").first().getElementsByTag("span").text());
        } catch (Exception e) {
            //doing nothing!
        }
        try {
            a.setStars5(doc1.getElementsByClass("rating_per").get(0).text());
        } catch (Exception e) {
            //doing nothing!
        }
        try {
            a.setStars4(doc1.getElementsByClass("rating_per").get(1).text());
        } catch (Exception e) {
            //doing nothing!
        }
        try {
            a.setStars3(doc1.getElementsByClass("rating_per").get(2).text());
        } catch (Exception e) {
            //doing nothing!
        }
        try {
            a.setStars2(doc1.getElementsByClass("rating_per").get(3).text());
        } catch (Exception e) {
            //doing nothing!
        }
        try {
            a.setStars1(doc1.getElementsByClass("rating_per").get(4).text());
        } catch (Exception e) {
            //doing nothing!
        }

        CRUD.newMovie(a);
        CRUD.updateMovieInfo(a);

        System.out.println("电影名：" + a.getMovie());
        System.out.println("封面：" + a.getCover());
        System.out.println("简介：" + a.getIntro());
        System.out.println("导演：" + a.getDirector());
        System.out.println("编剧：" + a.getScriptwriter());
        System.out.println("主演：" + a.getActor());
        System.out.println("片长：" + a.getLength());
        System.out.println("豆瓣评分：" + a.getRate());
        System.out.println("评分人数：" + a.getRating_people());
        System.out.println("五星比例：" + a.getStars5());
        System.out.println("四星比例：" + a.getStars4());
        System.out.println("三星比例：" + a.getStars3());
        System.out.println("二星比例：" + a.getStars2());
        System.out.println("一星比例：" + a.getStars1());
        if (commetGet) {
            System.out.println("评论：");
            for (Element element : tag2) {
                try {
                    a.setCommet(element.getElementsByClass("short").text());
                    CRUD.commet(a);
                    System.out.println(element.getElementsByTag("p").text());
                } catch (Exception ignored) {

                }
            }
        }
    }
}
