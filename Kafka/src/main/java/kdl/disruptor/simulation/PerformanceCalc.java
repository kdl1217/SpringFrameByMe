package kdl.disruptor.simulation;

import kdl.disruptor.consumer.KafkaConsumer;
import kdl.disruptor.enevt.KafkaProducer;
import kdl.disruptor.shared.Constant;
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
 * @author Kong, created on 2018-08-14T14:37.
 * @since 1.2.0-SNAPSHOT
 */
@Component
public class PerformanceCalc {

    private Logger logger = LoggerFactory.getLogger(PerformanceCalc.class) ;

    @Autowired
    private KafkaConsumer consumer ;

    @Autowired
    private KafkaProducer producer ;


    /**
     * 数据条数
     */
    @Value("${kafkaMQ.data_number}")
    Integer dataNumber ;

    /**
     * 生产者线程数量
     */
    @Value("${kafkaMQ.thread_number}")
    Integer threadNumber ;


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
     * 启动项
     */
    public void start(){
        long start = System.currentTimeMillis() ;
        // 初始化生产者
        continuousProducer(start) ;
//        monitoring() ;
    }


    /**
     * 持续发送数据
     */
    private void continuousProducer(long start){

        String msg = "123456789876543212345678987654321123456789876543212345678987654321123456789876543212345678987654321123456789876543212345678987654321123456789876543212345678987654321" ;
        ExecutorService exe = Executors.newFixedThreadPool(threadNumber) ;

        for (int i = 0; i < dataNumber; i++) {
            exe.submit(()->producer.sendMsg(Constant.TOPIC,msg,start)) ;
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
//        },200,200, TimeUnit.MILLISECONDS) ;
//    }

}
