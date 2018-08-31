package kdl.mq.simulation;

import kdl.mq.consumer.ConsumerMQ;
import kdl.mq.producer.ProducerMQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 性能计算
 *
 * @author Kong, created on 2018-08-09T15:34.
 * @since 1.2.0-SNAPSHOT
 */
@Component
public class PerformanceCalc {

    private Logger logger = LoggerFactory.getLogger(PerformanceCalc.class) ;

    /**
     * 数据条数
     */
    @Value("${messageMQ.data_number}")
    Integer dataNumber ;

    /**
     * 生产者线程数量
     */
    @Value("${messageMQ.thread_number}")
    Integer threadNumber ;

    /**
     * 操作- 生产或消费
     */
    @Value("${messageMQ.operation}")
    String operation ;


    @Autowired
    private ProducerMQ producerMQ ;

    @Autowired
    private ConsumerMQ consumerMQ ;

    /**
     *  持续发送数据条数
     */
    public static AtomicInteger produceMsgNumber = new AtomicInteger(0);

    /**
     * 消费数据条数
     */
    public static AtomicInteger consumeMsgNumber = new AtomicInteger(0);

    /**
     * 监控线程情况
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);


    /**
     * 启动计算
     */
    public void start(){
        // 初始化生产者
        if ("producer".equals(operation)){
            producerMQ.init() ;
            continuousProducer() ;
        }else if("consumer".equals(operation)){
            consumerMQ.init();
            consumerMQ.start();
        }
//        monitoring() ;
    }


    /**
     * 持续发送数据
     */
    private void continuousProducer(){
        long start = System.currentTimeMillis() ;

        byte[] bytes = new byte[500] ;

        ExecutorService exe = Executors.newFixedThreadPool(threadNumber) ;

        for (int i = 0; i < dataNumber; i++) {
            exe.submit(()->producerMQ.sendMsg(bytes,start)) ;
        }

        exe.shutdown();
        while (true){
            if (exe.isTerminated()){
                System.out.println("Thread Pool execute complete!!!");
                break;
            }
        }
    }


//    /**
//     * 数据监控
//     */
//    private void monitoring(){
//        long start = System.currentTimeMillis() ;
//        scheduledExecutorService.scheduleAtFixedRate(()->{
//            logger.info("producer num {} , consumer num {}, times {}" , produceMsgNumber.get() , consumeMsgNumber.get() , (System.currentTimeMillis()- start) );
//        },1,1, TimeUnit.SECONDS) ;
//    }


}
