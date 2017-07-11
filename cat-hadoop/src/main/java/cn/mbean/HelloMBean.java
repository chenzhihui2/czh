package cn.mbean;

/**
 * author czh 453775810@qq.com
 * date: 2017/6/22
 * comments:
 */
public interface HelloMBean {
    public void sayHello();
    public int add(int x, int y);

    public String getName();

    public int getCacheSize();
    public void setCacheSize(int size);
}
