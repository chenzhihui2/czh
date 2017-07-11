package cn.mxbean;

/**
 * author czh 453775810@qq.com
 * date: 2017/6/22
 * comments:
 */
public interface QueueSamplerMXBean {
    public QueueSample getQueueSample();
    public void clearQueue();
}
