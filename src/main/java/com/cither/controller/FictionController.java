package com.cither.controller;

import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.service.InfoServer;
import com.cither.service.ReadService;
import com.cither.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raincither
 * @date 2021/3/3 8:58
 */
@Controller
public class FictionController {

    @Autowired
    private InfoServer infoServer;
    @Autowired
    private ReadService readService;

    @GetMapping("/")
    public String fictionMain(Model model){

        List<Fiction> recommend = new ArrayList<>();
        List<Fiction> recommendTwo = new ArrayList<>();



        model.addAttribute("recommend",recommend);
        model.addAttribute("recommendTwo",recommendTwo);
        return "index";
    }


    @GetMapping("/info/{bId}")
    public String getDetail(@PathVariable int bId, Model model){

        Fiction fiction = infoServer.findFictionById(bId);
        if(fiction == null){
            return "error/404";
        }
        List<Chapter> chapterList = readService.findListChapterInfoById(bId);
        if(chapterList == null || chapterList.isEmpty()){
            return "error/404";
        }

        model.addAttribute(fiction);
        model.addAttribute(chapterList);
        return "detail";

    }

    @GetMapping("/read/{bId}/{cId}")
    public String getDetail(@PathVariable int bId, @PathVariable int cId, Model model) {

        //获取章节信息
        Chapter chapter = readService.findChapterById(cId);
        if(chapter == null || bId != chapter.getBId()){
            return "error/404";
        }
        String[] contentList = chapter.getChapter().split("\n");

        //获取上下文章id
        Integer proChapterId =  readService.findChapterByWhich(bId,chapter.getChapterWhich() - 1);
        Integer nextChapterId = readService.findChapterByWhich(bId,chapter.getChapterWhich() + 1);

        model.addAttribute("bName", infoServer.findFictionNameById(bId));
        model.addAttribute("bId", bId);
        model.addAttribute("chapter", chapter);
        model.addAttribute("contentList", contentList);
        model.addAttribute("proChapterId", proChapterId);
        model.addAttribute("nextChapterId", nextChapterId);

        return "read";

    }

}
