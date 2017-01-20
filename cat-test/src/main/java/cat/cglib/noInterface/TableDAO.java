package cat.cglib.noInterface;

/**
 * Created by Administrator on 2017/1/19.
 */
public class TableDAO {
    public void create(){
        System.out.println("create table");
    }
    public String query(){
        System.out.println("query table");
        return "111";
    }
}
