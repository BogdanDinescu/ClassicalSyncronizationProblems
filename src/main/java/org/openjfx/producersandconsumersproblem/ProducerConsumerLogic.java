package org.openjfx.producersandconsumersproblem;

public class ProducerConsumerLogic {
    private Integer bufferSize;
    private final ProducerConsumerPane producerConsumerPane;
    private Producer producer;
    private Consumer consumer;
    private Band band;

    private boolean startState;

    public ProducerConsumerLogic(Integer bufferSize, ProducerConsumerPane producerConsumerPane){
        this.bufferSize = bufferSize;
        this.producerConsumerPane = producerConsumerPane;
        this.startState = false;
    }

    public void createAndStartConsumerProducer(){
        createBand();
        createConsumer();
        createProducer();

        producer.start();
        consumer.start();
        startState = true;
    }

    public void createBand(){
        band = new Band(bufferSize, producerConsumerPane);
    }

    public void createConsumer(){
        consumer = new Consumer(band);
    }

    public void createProducer(){
        producer = new Producer(band);
    }

    public void stopConsumerAndProducer() {
        if (startState) {
            stopProducer();
            stopConsumer();
        }
    }

    public boolean allThreadsFinished() {
        if (producer == null | consumer == null)
            return true;
        return !producer.isAlive() && !consumer.isAlive();
    }

    public void stopProducer(){
        producer.setRunning(false);
    }

    public void stopConsumer(){
        consumer.setRunning(false);
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

}
