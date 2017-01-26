package com.spider.common.amqp.constant;

/**
 * Created by jian.Michael on 2017/1/26.
 */
public enum QueueNameEnum {

    TEST_QUEUE("spider.queue.test", "/logistics/queue/spider-queue-test");

    private String defaultQueueName;
    private String queueZkPath;

    private QueueNameEnum(String defaultQueueName, String queueZkPath) {
        this.defaultQueueName = defaultQueueName;
        this.queueZkPath = queueZkPath;
    }

    public String getDefaultQueueName() {
        return defaultQueueName;
    }

    public void setDefaultQueueName(String defaultQueueName) {
        this.defaultQueueName = defaultQueueName;
    }

    public String getQueueZkPath() {
        return queueZkPath;
    }

    public void setQueueZkPath(String queueZkPath) {
        this.queueZkPath = queueZkPath;
    }
}
