package kdl.disruptor.launch;

import kdl.disruptor.enevt.InitEventFactory;
import kdl.disruptor.enevt.MyEventHandler;
import kdl.disruptor.enevt.MyEventProducer;
import kdl.disruptor.entity.EventBean;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;

/**
 * Disruptor 启动配置
 *
 * @author Kong, created on 2018-08-15T15:13.
 * @since 1.2.0-SNAPSHOT
 */
@Component
public class LaunchDisruptor implements Runnable {

    @Override
    public void run() {

        WaitStrategy waitStrategy = new YieldingWaitStrategy() ;

        EventFactory<EventBean> eventFactory = new InitEventFactory() ;

        int ringBufferSize = 1024 * 1024 ;

        ThreadFactory threadFactory = new ThreadPoolExecutorFactoryBean() ;

        Disruptor<EventBean> disruptor = new Disruptor<>(eventFactory,
                ringBufferSize,threadFactory, ProducerType.SINGLE,waitStrategy) ;

        EventHandler<EventBean> eventHandler = new MyEventHandler() ;

        disruptor.handleEventsWith(eventHandler) ;

        disruptor.start() ;

        RingBuffer<EventBean> ringBuffer = disruptor.getRingBuffer() ;

        MyEventProducer producer = new MyEventProducer(ringBuffer) ;

        producer.run();
    }
}
