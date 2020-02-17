package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetNovel {
    public static void crawling(String url, Novel a) {
        //这个变量k的作用是：当try块中的某个语句出现异常时（一般是连接时的read time out），就让try块重新执行一遍，重新连接。
        boolean k = true;
        Document doc1 = null;
        CRUD.newNovel(a);//先在novel表里insert一个有编号的记录，避免外键录入失败

        while (k) {
            try {
                doc1 = Jsoup.connect(url).userAgent("User-Agent:Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1")
                        .timeout(300000)
                        .get();
                k = false;
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("网络连接异常");
                k = true;
            }
        }
        //程序走到这儿说明doc1已经连上了网站，但还不确定该网站对应编号的小说是否存在
        k = true;

        //小说信息
        try {
            String novel = doc1.getElementById("info").getElementsByTag("h1").text();//如果网站对应编号的小说不存在，这里会报一个空指针异常
            String novelist = doc1.getElementById("info").getElementsByTag("a").first().text();
            String intro = doc1.getElementById("intro").text();
            String cover = doc1.getElementById("fmimg").getElementsByTag("img").attr("src");
            a.setNovel(novel);
            a.setNovelist(novelist);
            a.setIntro(intro);
            a.setCover(cover);
            CRUD.updateInfo(a);
            System.out.println("标题：" + novel + "\n作者：" + novelist + "\n简介：" + intro + "\n封面链接：" + cover + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("编号为" + a.getNum() + "的小说不存在\n");
            CRUD.deleteNovel(a);
            return;
        }
        //程序走到这儿说明doc1连上的网站对应编号的小说存在。


        //章节和内容
        //得到id为list下的元素
        Element ele1 = doc1.getElementById("list");
        //得到ele1元素中标签为a的元素
        Elements tag = ele1.getElementsByTag("a");
        String tagHref;
        for (int i = 0; i < tag.size(); i++, k = true) {
            while (k) {
                try {
                    Thread.sleep(5000);
                    tagHref = tag.get(i).attr("href");
                    Document doc2 = Jsoup.connect(url + tagHref)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                            .timeout(60000)
                            .get();//异常重灾区！！！！！！
                    Element ele2 = doc2.getElementById("content");
                    String text = ele2.text().replace("一秒记住【笔趣阁 www.52bqg.com】，精彩小说无弹窗免费阅读！", "");
                    String title = doc2.getElementsByClass("bookname").select("h1").text();
                    System.out.println(title);
                    System.out.println(text);
                    System.out.println();
                    a.setChapter(title);
                    a.setContent(text);
                    k = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("常见异常");
                    k = true;
                }
            }
            CRUD.updateNovel(a, i + 1);
        }
    }
}
//参考博客：https://blog.csdn.net/suqi356/article/details/78547137