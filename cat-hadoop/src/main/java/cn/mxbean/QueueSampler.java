package cn.mxbean;

import java.util.Date;
import java.util.Queue;

/**
 * author czh 453775810@qq.com
 * date: 2017/6/22
 * comments:
 */
public class QueueSampler  implements QueueSamplerMXBean{
    private Queue<String> queue;

    public QueueSampler (Queue<String> queue) {
        this.queue = queue;
    }

    public QueueSample getQueueSample() {
        synchronized (queue) {
            return new QueueSample(new Date(),
                    queue.size(), queue.peek());
        }
    }

    public void clearQueue() {
        synchronized (queue) {
            queue.clear();
        }
    }
}
