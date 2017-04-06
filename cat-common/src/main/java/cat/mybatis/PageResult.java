package cat.mybatis;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
@Data
public class PageResult<T> implements Serializable{
    private int pageSize;
    private int pageNum;
    private int totalPage;//总页数
    private int totalSize;//总记录数
    private List<T> result;
}
