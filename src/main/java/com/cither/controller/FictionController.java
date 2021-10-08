package com.cither.controller;

import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.service.InfoService;
import com.cither.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author raincither
 * @date 2021/3/3 8:58
 */
@Controller
@RequiredArgsConstructor
public class FictionController {

    private final InfoService infoService;
    private final ReadService readService;

    @GetMapping("/info/{bId}")
    public String getDetail(@PathVariable int bId, Model model){

        Fiction fiction = infoService.findFictionById(bId);
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

        model.addAttribute("bName", infoService.findFictionNameById(bId));
        model.addAttribute("bId", bId);
        model.addAttribute("chapter", chapter);
        model.addAttribute("contentList", contentList);
        model.addAttribute("proChapterId", proChapterId);
        model.addAttribute("nextChapterId", nextChapterId);

        return "read";

    }

}
