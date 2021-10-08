package com.cither.controller;

import com.cither.reptile.util.WebNovelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/magic")
    public String magic(){
        if(WebNovelUtil.rankList.isEmpty()){
            WebNovelUtil.getRankList();
        }
        for(String key : WebNovelUtil.rankList.keySet()){
            List<String> rank = webNovelUtil.getRank(key);
        }
        return "success";
    }
    @RequestMapping("/magic1")
    public String magic1(){
        //webNovelUtil.updateRank();
        return "success";
    }
    @RequestMapping("/tag/{tag}")
    public String tag(@PathVariable String tag){
        webNovelUtil.getRank(tag);
        return "success";
    }

    @RequestMapping("/pull/{uid}")
    public String pull(@PathVariable int uid){
        webNovelUtil.getInfo("https://book.qidian.com/info/" + uid);
        return "success";
    }
}
