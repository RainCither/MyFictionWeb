package com.cither.controller;

import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.reptile.util.WebNovelUtil;
import com.cither.service.InfoServer;
import com.cither.service.ReadService;
import com.cither.util.RedisUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author raincither
 * @date 2021/3/3 8:58
 */
@Controller
public class FictionController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private InfoServer infoServer;
    @Autowired
    private ReadService readService;
    @Autowired
    private WebNovelUtil webNovelUtil;

    @GetMapping("/info/{bId}")
    public String getDetail(@PathVariable int bId, Model model){

        Fiction fiction = infoServer.findFictionById(bId);
        if(fiction == null){
            return "error/404";
        }
        List<Chapter> chapterList = readService.findListChapterInfoById(bId);
        if(chapterList == null){
            chapterList = new ArrayList<>();
        }

        model.addAttribute(fiction);
        model.addAttribute(chapterList);
        return "detail";

    }

    @GetMapping("/read/{bId}/{cId}")
    public String getDetail(@PathVariable int bId, @PathVariable int cId, Model model) {

        Fiction fiction = infoServer.findFictionById(bId);
        Chapter chapter = readService.findChapterById(cId);
        //取出并清空
        if(chapter.getChapter() == null){
            String content = webNovelUtil.readWebNovel(chapter.getChapterLink(), chapter.getCId());
            chapter.setChapter(content);
        }
        String[] contentList = chapter.getChapter().split("\n");
        chapter.setChapter("");

        //获取上下文章id
        int proChapterId = readService.findChapterByWhich(bId,chapter.getChapterWhich() - 1);
        int nextChapterId = readService.findChapterByWhich(bId,chapter.getChapterWhich() + 1);

        model.addAttribute("fiction", fiction);
        model.addAttribute("chapter", chapter);
        model.addAttribute("contentList", contentList);
        model.addAttribute("proChapterId", proChapterId);
        model.addAttribute("nextChapterId", nextChapterId);

        return "read";

    }

}
