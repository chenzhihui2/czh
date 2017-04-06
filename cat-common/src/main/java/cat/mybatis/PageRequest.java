package cat.mybatis;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/14.
 */
@Data
public class PageRequest  implements Serializable{
    private int pageSize;
    private int pageNum;
}
