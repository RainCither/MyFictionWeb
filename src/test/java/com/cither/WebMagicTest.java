package com.cither;

import com.cither.reptile.parsing.WebNovelInfo;
import org.junit.jupiter.api.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

/**
 * @author raincither
 * @date 2021/8/7 15:16
 */
public class WebMagicTest {

    @Test
    public void test1(){
        Spider.create(new WebNovelInfo()).addUrl("https://book.qidian.com/info/1029006481").run();
    }

    @Test
    public void test2(){
        Html html = new Html("<p>\n" + "\n" +
                "　　这是一个以御兽为主流的异世界。<br>　　当时宇携带技能图鉴穿越到这里，并培育出一堆奇葩宠兽后，所有御兽师的三观都破碎了……<br>　　关键词：御兽、宠兽、宠物、召唤。\n" +
                "\n" + "</p>");

        String s = html.xpath("//p").regex(">(.*)<").get();
        String s1 = s.substring(1);
        System.out.println("s = " + s1);
    }
}
