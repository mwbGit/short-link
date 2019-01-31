package com.mwb.shortlink.web;

import com.mwb.shortlink.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * -
 * Created by MengWeiBo on 2019-01-30
 */
@Controller
@Slf4j
public class BuilderController {
    private static String ID = "id";
    private static String LINKS = "links:";

    @Autowired
    private RedisService redisService;

    @Value("${domain}")
    private String domain;

    @RequestMapping("l/generate")
    @ResponseBody
    public Map<String, Object> generate(@RequestParam String link) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isEmpty(link)) {
            result.put("code", 400);
            result.put("message", "参数错误");
            return result;
        }

        long id = redisService.incrBy(ID, 10);
        String shortLink = domain + Long.toString(id, 16);
        redisService.setex(LINKS + id, 3600 * 24 * 7, link);
        log.warn("generate link success, {} to {}", link, shortLink);

        result.put("link", shortLink);
        result.put("code", 200);
        result.put("message", "成功");
        return result;
    }

    @RequestMapping("/l/{link}")
    @ResponseBody
    public void getLink(@PathVariable("link") String shortLink, HttpServletResponse response) {
        Long id = Long.parseLong(shortLink, 16);
        String link = redisService.get(LINKS + id);

        if (link == null) {
            log.warn("link is Expired，{}", shortLink);
            return;
        }

        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setStatus(302);
        response.addHeader("location", link);
    }
}
