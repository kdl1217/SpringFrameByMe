package kdl.disruptor.enevt;

import kdl.disruptor.simulation.PerformanceCalc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka生成者
 *
 * @author Kong, created on 2018-08-14T14:25.
 * @since 1.2.0-SNAPSHOT
 */
@Component
public class KafkaProducer {

    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class) ;

    @Autowired
    private KafkaTemplate<String,String> template ;

    /**
     * 数据条数
     */
    @Value("${kafkaMQ.data_number}")
    Integer dataNumber ;

    /**
     * 发送数据
     * @param topic
     * @param msg
     */
    public void sendMsg(String topic,String msg,long time){
        template.send(topic,msg).addCallback(result -> {
                    if (PerformanceCalc.produceMsgNumber.addAndGet(1) == dataNumber) {
                        logger.info("producer num {} , times {}" , dataNumber ,  (System.currentTimeMillis()- time)) ;
                    }
                },
                ex -> logger.info("ERROR!!!"));

    }

}
