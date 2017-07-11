package cat.tbq.hospital.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

/**
 * author czh 453775810@qq.com
 * date: 2017/7/7
 * comments:
 */
public class DataMonitor implements Watcher, AsyncCallback.StatCallback {
    ZooKeeper zk;
    String znode;
    Watcher chainedWatcher;
    boolean dead;
    DataMonitorListener listener;
    byte preData[];

    public DataMonitor(ZooKeeper zk, String znode, Watcher watcher,
                       DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.chainedWatcher = watcher;
        this.listener = listener;
        zk.exists(znode, true, this, null);
    }

    public interface DataMonitorListener {
        void exists(byte data[]);

        void closing(int rc);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc){
            case KeeperException.CodeDeprecated.Ok:
                exists=true;
                break;
            case KeeperException.CodeDeprecated.NoNode:
                exists=false;
                break;
            case KeeperException.CodeDeprecated.SessionExpired:
            case KeeperException.CodeDeprecated.NoAuth:
                dead=true;
                listener.closing(rc);
                return;
            default:
                zk.exists(znode,true,this,null);
                return;
        }
        byte b[]=null;
        if(exists){
            try {
                b=zk.getData(znode,false,null);
            }catch (KeeperException e){
                e.printStackTrace();
            }catch (InterruptedException e){
                return;
            }
        }

        if((b==null&&b!=preData)
                ||(b!=null && !Arrays.equals(preData,b))){
            listener.exists(b);
            preData=b;
        }


    }

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()){
                case SyncConnected:
                    System.out.println("sdfs");
                    break;
                case Expired:
                    dead=true;
                    listener.closing(KeeperException.Code.SessionExpired);
                    break;
            }
        } else {
            if (path != null && path.equals(znode)) {
                zk.exists(znode, true, this, null);
            }
        }
        if(chainedWatcher!=null){
            chainedWatcher.process(event);
        }
    }
}
