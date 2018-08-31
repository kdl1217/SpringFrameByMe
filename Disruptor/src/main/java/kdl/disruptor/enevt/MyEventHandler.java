package kdl.disruptor.enevt;

import kdl.disruptor.entity.EventBean;
import com.lmax.disruptor.EventHandler;

/**
 * 处理消息
 *
 * @author Kong, created on 2018-08-15T15:06.
 * @since 1.2.0-SNAPSHOT
 */
public class MyEventHandler implements EventHandler<EventBean> {

    @Override
    public void onEvent(EventBean event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.getValue());
    }
}
