package com.cither.controller;

import com.cither.reptile.obtain.QdObtain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author raincither
 * @date 2021/3/2 18:18
 */
@RestController
@RequestMapping("/qd")
public class QdController {

    @Autowired
    private QdObtain qd;

    @GetMapping("/down/{bId}")
    public String down(@PathVariable int bId) {
        int bookId = qd.saveDetail(bId);

        if (bookId == -2) {
            return "Success Saved";
        }else if (bookId > 1){
            return "success bookID:" + bookId;
        }else if(bookId == 0){
            return "暂不支持 - - bookID:" + bookId;
        }

        return "error 检查bookID ==> "  + "https://book.qidian.com/info/" + bId;
    }

    @GetMapping("down/rank")
    public String rank(){
        String rankLink = "https://www.qidian.com/rank";


        return rankLink;
    }

}
