package cn.mxbean;

import java.beans.ConstructorProperties;
import java.util.Date;

/**
 * author czh 453775810@qq.com
 * date: 2017/6/22
 * comments:
 */
public class QueueSample {
    private final Date date;
    private final int size;
    private final String head;

    @ConstructorProperties({"date", "size", "head"})
    public QueueSample(Date date, int size,
                       String head) {
        this.date = date;
        this.size = size;
        this.head = head;
    }

    public Date getDate() {
        return date;
    }

    public int getSize() {
        return size;
    }

    public String getHead() {
        return head;
    }
}
