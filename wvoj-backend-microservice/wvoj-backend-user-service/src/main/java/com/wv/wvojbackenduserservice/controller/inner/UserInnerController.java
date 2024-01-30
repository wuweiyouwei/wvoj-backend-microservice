package com.wv.wvojbackenduserservice.controller.inner;

import com.wv.wvojbackendmodel.entity.User;
import com.wv.wvojbackendserviceclient.service.UserFeignClient;
import com.wv.wvojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Period;
import java.util.Collection;
import java.util.List;

/**
 * 该服务仅内部调用
 * @author wv
 * @version V1.0
 * @date 2024/1/28 10:02
 */
@RestController()
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {


    @Resource
    private UserService userService;
    @Override
    @GetMapping("/get/id")
    public User getById(long userId) {
        return userService.getById(userId);
    }

    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
