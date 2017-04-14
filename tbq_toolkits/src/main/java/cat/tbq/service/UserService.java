package cat.tbq.service;

import cat.mybatis.PageResult;
import cat.tbq.hospital.entity.QueryUserDTO;
import cat.tbq.hospital.entity.UserDO;

import java.util.List;

/**
 * Created by czhtbq on 2017/4/6.
 */
public interface UserService {
    public List<UserDO> queryUserByJobNumber(Integer jobNumber);
    public PageResult<UserDO> queryUser(QueryUserDTO dto);
}
