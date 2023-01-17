public class Main {

    private static final Topics topics = new Topics();
    private static final Consumer consumer = new Consumer();

    public static void main(String[] args) throws InterruptedException {
      System.out.println("dupa");
      topics.createTopic();
      consumer.initConsumer();
//        Topics.createTopic();
//        Consumer consumer = new Consumer();
//        consumer.initConsumer();
//        consumer.consume(Consumer.getKafkaConsumer());
    }

}
