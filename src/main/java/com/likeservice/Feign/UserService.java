package com.likeservice.Feign;

import com.likeservice.Model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserService {
    @GetMapping("/users/{userId}")
    public User findByID(@PathVariable("userId") String userId);
}
