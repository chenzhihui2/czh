package cat.tbq.controller;

import cat.tbq.hospital.entity.QueryUserDTO;
import cat.tbq.hospital.entity.UserDO;
import cat.tbq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by czhtbq on 2017/4/6.
 */
@Controller
@Slf4j
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "queryUserByJobNumber",method = RequestMethod.GET)
    @ResponseBody
    public Object queryUserByJobNumber(){
        List<UserDO> userDOList =this.userService.queryUserByJobNumber(1);
        return userDOList;
    }

    @RequestMapping(value = "queryUser",method = RequestMethod.GET)
    @ResponseBody
    public Object queryUser(){
        return this.userService.queryUser(new QueryUserDTO());
    }

}
