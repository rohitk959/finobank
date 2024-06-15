package com.finobank.accounts.adapter.feignclients;

import com.finobank.accounts.core.model.ApiUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "users")
public interface UsersFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/users", produces = {"application/json"})
    ResponseEntity<List<ApiUser>> getUsers(@RequestParam(value = "userIds", required = false, defaultValue = "") List<UUID> userIds);
}
