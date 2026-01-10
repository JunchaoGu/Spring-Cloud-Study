package com.xidian.nacosserviceb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "demo-name",url = "http://aliv18.data.moji.com")
public interface DemoRemoteFeign {

    @PostMapping("/whapi/json/alicityweather")
    String getWeather(
            @RequestParam("Authorization") String auth,
            @RequestParam("token") String token,
            @RequestParam("cityId") String id);

}
