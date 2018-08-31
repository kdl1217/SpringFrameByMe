package kdl.mq.config;

/**
 * note
 *
 * @author Kong, created on 2018-08-09T15:50.
 * @since 1.2.0-SNAPSHOT
 */
public class ProducerConfig {

    private String producerid ;

    private String topic ;

    private String tags ;

    public String getProducerid() {
        return producerid;
    }

    public void setProducerid(String producerid) {
        this.producerid = producerid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
