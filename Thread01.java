package crawler;

public class Thread01 implements Runnable {
    @Override
    public void run() {
        for (int i = 27119724; i >= 27000000; i--) {
            var movie = new Movie();
            movie.setNum(i);
            Testdouban01.crawling("https://movie.douban.com/subject/"+i+"/", movie);
        }
    }
}
