package cat.tbq.hospital.dao;

import cat.mybatis.PageResult;
import cat.tbq.hospital.entity.QueryUserDTO;
import cat.tbq.hospital.entity.UserDO;

import java.util.List;

/**
 * Created by czhtbq on 2017/4/6.
 */
public interface UserDao {
    public List<UserDO> selectUserByJobNumber(UserDO user);
    public PageResult<UserDO> queryUser(QueryUserDTO dto);
}
