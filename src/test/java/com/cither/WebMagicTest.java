package com.cither;

import com.cither.reptile.Pipeline.WebNovelPipeline;
import com.cither.reptile.parsing.ReadWebNovel;
import com.cither.reptile.parsing.WebNovelInfo;
import org.junit.jupiter.api.Test;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;

/**
 * @author raincither
 * @date 2021/8/7 15:16
 */
public class WebMagicTest {

    @Test
    public void test1(){
        Spider.create(new WebNovelInfo())
                .addUrl("https://book.qidian.com/info/1016311897/")
                .run();
    }

    @Test
    public void test2(){
        Html html = new Html("<a href=\"//vipreader.qidian.com/chapter/1019664125/534766533\" target=\"_blank\" data-eid=\"qd_G55\" data-cid=\"//vipreader.qidian.com/chapter/1019664125/534766533\" title=\"首发时间：2020-05-01 00:05:24 章节字数：2535\">第九十八章 不为人知的隐秘</a>");

        String s = html.xpath("//a/@title").get().substring(5,24);
        System.out.println("s = " + s);
    }
}
