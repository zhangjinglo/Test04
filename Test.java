package crawler;

//123880    兄弟
//123845-->123840       其中123843和123841不存在

public class Test {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Thread01());
        Thread t2 = new Thread(new Thread02());
        t1.start();
        //t2.start();
    }
}
