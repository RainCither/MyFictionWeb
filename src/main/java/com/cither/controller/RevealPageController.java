package com.cither.controller;

import com.cither.pojo.Fiction;
import com.cither.service.InfoService;
import com.cither.service.RevealPageServer;
import com.cither.util.BookGenreEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author raincither
 * @date 2021/9/27 14:51
 */
@Controller
@RequiredArgsConstructor
public class RevealPageController {

    private final RevealPageServer revealPageServer;
    private final InfoService infoService;

    @GetMapping("/")
    public String fictionMain(Model model) {

        List<Fiction> recommend = revealPageServer.getRecommend(BookGenreEnum.ALL).subList(0,9);

        List<Fiction> latestFiction = infoService.getLatestFiction(50);

        model.addAttribute("recommend", recommend);
        model.addAttribute("latestFiction", latestFiction);

        return "index";
    }

    @GetMapping("/type/{tag}")
    public String fictionType(@PathVariable int tag, Model model) {
        BookGenreEnum[] values = BookGenreEnum.values();
        if(tag < 0 || tag > values.length ){
            return "error/404";
        }
        BookGenreEnum value = values[tag];

        List<Fiction> recommend = revealPageServer.getRecommend(value).subList(0,9);

        List<Fiction> latestFiction = infoService.getLatestTagFiction(value.getGenre(),50);

        model.addAttribute("recommend", recommend);
        model.addAttribute("latestFiction", latestFiction);

        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam String name, Model model){
        if(name.length() > 25){
            name = name.substring(0, 25);
        }
        name = name.replaceAll("_", "");
        name = name.replaceAll("%", "");

        List<Fiction> fictions = infoService.searchFictionByName(name, 50);

        for (Fiction fiction : fictions) {
            int length = fiction.getDesc().length();
            fiction.setDesc(fiction.getDesc()
                            .substring(3, Math.min(length, 40))
                            .replaceAll("<br>","")
            );
        }

        model.addAttribute("name", name);
        model.addAttribute("fictions", fictions);
        return  "search";
    }


}
