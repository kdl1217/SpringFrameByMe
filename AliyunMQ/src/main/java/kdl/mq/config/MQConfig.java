package kdl.mq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * note
 *
 * @author Kong, created on 2018-08-09T15:43.
 * @since 1.2.0-SNAPSHOT
 */
@Component
@ConfigurationProperties(prefix="aliyun_mq")
public class MQConfig {

    private String accesskey ;

    private String secretkey ;

    private String onsaddr ;

    private ProducerConfig producer ;

    private ConsumerConfig consumer ;

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getOnsaddr() {
        return onsaddr;
    }

    public void setOnsaddr(String onsaddr) {
        this.onsaddr = onsaddr;
    }

    public ProducerConfig getProducer() {
        return producer;
    }

    public void setProducer(ProducerConfig producer) {
        this.producer = producer;
    }

    public ConsumerConfig getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfig consumer) {
        this.consumer = consumer;
    }
}
