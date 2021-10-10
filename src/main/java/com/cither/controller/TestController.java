package com.cither.controller;

import com.cither.reptile.util.WebNovelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author raincither
 * @date 2021/8/6 18:34
 */
@RestController
@RequestMapping("/conf")
@RequiredArgsConstructor
public class TestController {

    private final WebNovelUtil webNovelUtil;

    @RequestMapping("/get")
    public String magic(){
        if(WebNovelUtil.rankList.isEmpty()){
            WebNovelUtil.getRankList();
        }
        for(String key : WebNovelUtil.rankList.keySet()){
            List<String> rank = webNovelUtil.getRank(key);
        }
        return "success";
    }
    @RequestMapping("/update")
    public String magic1(){
        if(WebNovelUtil.rankList.isEmpty()){
            WebNovelUtil.getRankList();
        }
        for(String key : WebNovelUtil.rankList.keySet()){
            List<String> rank = webNovelUtil.updateRank(key);
        }
        return "success";
    }
    @GetMapping("/tag/{tag}")
    public String tag(@PathVariable String tag){
        webNovelUtil.getRank(tag);
        return "success";
    }

    @GetMapping("/pull/{uid}")
    public String pull(@PathVariable int uid){
        webNovelUtil.getInfo("https://book.qidian.com/info/" + uid);
        return "success";
    }
}
