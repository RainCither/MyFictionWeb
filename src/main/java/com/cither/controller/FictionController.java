package com.cither.controller;

import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FictionController {

    @Autowired
    private DetailService detailService;

    @GetMapping("/info/{bId}")
    public String getDetail(@PathVariable int bId, Model model){

        Fiction fiction = detailService.findFiction(bId);
        List<Chapter> chapterList = detailService.findChapterListById(bId);

        model.addAttribute(fiction);
        model.addAttribute(chapterList);
        return "detail";

    }

    @GetMapping("/read/{bId}/{cId}")
    public String getDetail(@PathVariable int bId, @PathVariable int cId, Model model) {

        Fiction fiction = detailService.findFiction(bId);
        Chapter chapter = detailService.findChapterById(cId);
        //取出并清空
        String[] contentList = chapter.getChapter().split("\n");
        chapter.setChapter("");

        //获取上下文章id
        int proChapterId = detailService.findChapterByWhich(bId,chapter.getChapterWhich() - 1);
        int nextChapterId = detailService.findChapterByWhich(bId,chapter.getChapterWhich() + 1);

        model.addAttribute("fiction", fiction);
        model.addAttribute("chapter", chapter);
        model.addAttribute("contentList", contentList);
        model.addAttribute("proChapterId", proChapterId);
        model.addAttribute("nextChapterId", nextChapterId);

        return "read";

    }

}
