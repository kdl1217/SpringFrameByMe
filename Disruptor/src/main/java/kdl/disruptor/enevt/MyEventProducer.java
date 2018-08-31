package kdl.disruptor.enevt;

import kdl.disruptor.entity.EventBean;
import com.lmax.disruptor.RingBuffer;

/**
 * 生产消息
 *
 * @author Kong, created on 2018-08-15T15:07.
 * @since 1.2.0-SNAPSHOT
 */
public class MyEventProducer implements Runnable {

    private final RingBuffer<EventBean> ringBuffer ;

    public MyEventProducer(RingBuffer<EventBean> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用来发布事件，每调用一次就发布一次事件
     * 它的参数会用过事件传递给消费者
     * @param str
     */
    public void onData(String str){
        long sequence = ringBuffer.next() ;
        try {
            EventBean event = ringBuffer.get(sequence) ;
             event.setValue(str);
        } finally {
            ringBuffer.publish(sequence);
        }

    }

    @Override
    public void run() {
        for (int i = 0; i < 100 ; i++) {
            this.onData("Kong: this msg num:"+i);
        }
    }

}
