package kdl.mq.config;

import java.util.List;

/**
 * note
 *
 * @author Kong, created on 2018-08-09T15:51.
 * @since 1.2.0-SNAPSHOT
 */
public class ConsumerConfig {

    private String consumerid ;

    private List<String> topics ;

    private List<String> tags ;

    public String getConsumerid() {
        return consumerid;
    }

    public void setConsumerid(String consumerid) {
        this.consumerid = consumerid;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
