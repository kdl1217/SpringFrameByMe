package kdl.disruptor.enevt;

import kdl.disruptor.entity.EventBean;
import com.lmax.disruptor.EventFactory;

/**
 * Factory
 *
 * @author Kong, created on 2018-08-15T14:53.
 * @since 1.2.0-SNAPSHOT
 */
public class InitEventFactory implements EventFactory<EventBean> {

    @Override
    public EventBean newInstance() {
        return new EventBean();
    }

}
