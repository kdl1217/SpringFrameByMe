package kdl.disruptor.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * kafka消费者
 *
 * @author Kong, created on 2018-08-14T14:31.
 * @since 1.2.0-SNAPSHOT
 */
@Component
public class KafkaConsumer {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class) ;

    private long time ;

    /**
     * 数据条数
     */
    @Value("${kafkaMQ.data_number}")
    Integer dataNumber ;

//    @KafkaListener(topics = Constant.TOPIC)
//    public void consumer(String content){
//        // 记录消费数据条数
//        if (PerformanceCalc.consumeMsgNumber.addAndGet(1) == 1){
//            time = System.currentTimeMillis() ;
//        }
//
//        if (PerformanceCalc.consumeMsgNumber.get() == dataNumber){
//            logger.info("consumer num {} , times {}" , dataNumber ,  (System.currentTimeMillis()- time)) ;
//        }
//    }
}
