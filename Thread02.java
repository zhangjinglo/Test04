package crawler;

public class Thread02 implements Runnable {
    @Override
    public void run() {
        for (int i = 123880; i >= 123000; i--) {
            var novel = new Novel();
            novel.setNum(i);
            String url = "https://www.52bqg.com/book_" + i + "/";
            GetNovel.crawling(url, novel);
        }
    }
}
