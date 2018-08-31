package kdl.disruptor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Kafka配置
 *
 * @author Kong, created on 2018-08-14T14:14.
 * @since 1.2.0-SNAPSHOT
 */
@Configuration
public class KafkaConfiguration {

    private Logger logger = LoggerFactory.getLogger(KafkaConfiguration.class) ;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public KafkaTemplate<String,String> kafkaTemplate(){
        return kafkaTemplate ;
    }

}
