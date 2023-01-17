public class Main {

    private static final Topics topics = new Topics();
    private static final Consumer consumer = new Consumer();

    public static void main(String[] args) {
        System.out.println("Waiting for topics...");
        topics.createTopic();
        consumer.initConsumer();
    }

}
