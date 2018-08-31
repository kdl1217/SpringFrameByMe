package kdl.mq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import kdl.mq.config.MQConfig;
import kdl.mq.shared.Constant;
import kdl.mq.simulation.PerformanceCalc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

/**
 * MQ 消费者
 *
 * @author Kong, created on 2018-08-08T14:42.
 * @since 1.2.0-SNAPSHOT
 */
@Component
public class ConsumerMQ {

    private Logger logger = LoggerFactory.getLogger(ConsumerMQ.class) ;

    @Autowired
    private MQConfig mqConfig ;

    private Consumer consumer ;

    /**
     * 数据条数
     */
    @Value("${messageMQ.data_number}")
    Integer dataNumber ;

    /**
     * 初始时间
     */
    private long time ;

    public void init(){
        if (null == consumer){
            consumer = getConsumer() ;
        }
    }


    private Consumer getConsumer(){
        Properties properties = new Properties();
        // 您在控制台创建的 Consumer ID
        properties.put(PropertyKeyConst.ConsumerId, mqConfig.getConsumer().getConsumerid());
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, mqConfig.getAccesskey());
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, mqConfig.getSecretkey());
        // 设置 TCP 接入域名（此处以公共云生产环境为例）
        properties.put(PropertyKeyConst.ONSAddr, mqConfig.getOnsaddr());
        // 集群订阅方式 (默认)
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        // 广播订阅方式
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        Consumer consumer = ONSFactory.createConsumer(properties);
        return consumer ;
    }

    public void start(){

        List<String> topics = mqConfig.getConsumer().getTopics() ;
        for (int i = 0; i < topics.size(); i++) {
            String topic = topics.get(i) ;
            List<String> tags = mqConfig.getConsumer().getTags() ;

            String subExpression = "";
            for (int j = 0; j < tags.size() ; j++) {
                subExpression += tags.get(i) + "||" ;
            }
            if (null != subExpression)
                subExpression = subExpression.substring(0, subExpression.length()-2) ;
            //订阅多个 Tag
            consumer.subscribe(topic, subExpression, (message, context) -> {

                // 记录消费数据条数
                if (PerformanceCalc.consumeMsgNumber.addAndGet(1) == 1){
                    time = System.currentTimeMillis() ;
                }

                if (PerformanceCalc.consumeMsgNumber.get() == dataNumber){
                    logger.info("consumer num {} , times {}" , dataNumber ,  (System.currentTimeMillis()- time)) ;
                }
//                System.out.println("Receive: " + message);
                return Action.CommitMessage;
            });
        }
        consumer.start();
    }


    public static void main(String[] args) {
        Properties properties = new Properties();
        // 您在控制台创建的 Consumer ID
        properties.put(PropertyKeyConst.ConsumerId, "CID_GMMC_MQ_OUTTER");
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, Constant.ALIYUN.ACCESSKEY);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, Constant.ALIYUN.SECRETKEY);
        // 设置 TCP 接入域名（此处以公共云生产环境为例）
        properties.put(PropertyKeyConst.ONSAddr,
                "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");
        // 集群订阅方式 (默认)
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        // 广播订阅方式
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("GMMC_MQ_OUTTER", "TagA||TagB", new MessageListener() { //订阅多个 Tag
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                return Action.CommitMessage;
            }
        });
        //订阅另外一个 Topic
//        consumer.subscribe("GMMC_MQ_OUTTER", "*", new MessageListener() { //订阅全部 Tag
//            public Action consume(Message message, ConsumeContext context) {
//                System.out.println("Receive: " + message);
//                return Action.CommitMessage;
//            }
//        });
        consumer.start();
        System.out.println("Consumer Started");
    }

}
