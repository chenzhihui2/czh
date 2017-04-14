package cat.tbq.service.impl;

import cat.mybatis.PageResult;
import cat.tbq.hospital.dao.UserDao;
import cat.tbq.hospital.entity.QueryUserDTO;
import cat.tbq.hospital.entity.UserDO;
import cat.tbq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by czhtbq on 2017/4/6.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserDO> queryUserByJobNumber(Integer jobNumber) {
        UserDO params=new UserDO();
        params.setJobNumber("1");
        params.setWorkTerm(2);
        return this.userDao.selectUserByJobNumber(params);
    }

    @Override
    public PageResult<UserDO> queryUser(QueryUserDTO dto) {
        return this.userDao.queryUser(dto);
    }
}
