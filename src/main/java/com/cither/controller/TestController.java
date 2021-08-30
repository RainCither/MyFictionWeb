package com.cither.controller;

import com.cither.pojo.Chapter;
import com.cither.reptile.util.WebNovelUtil;
import com.cither.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author raincither
 * @date 2021/8/6 18:34
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WebNovelUtil webNovelUtil;

    @RequestMapping("/redis/{str}")
    public Boolean redisTest(@PathVariable String str){
        Chapter chapter = new Chapter(7, 4, 5, "cong", "cai", "cao", "cao");
        return redisUtil.set("test",chapter);
    }

    @RequestMapping("/magic")
    public Boolean magic(){
        webNovelUtil.getRankWebNovel();
        return true;
    }

    @RequestMapping("/pull/{uid}")
    public String pull(@PathVariable int uid){
        webNovelUtil.infoWebNovel("https://book.qidian.com/info/" + uid);
        return "success";
    }
}
