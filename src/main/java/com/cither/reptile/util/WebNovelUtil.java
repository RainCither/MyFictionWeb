package com.cither.reptile.util;

import com.cither.reptile.Pipeline.WebNovelPipeline;
import com.cither.reptile.parsing.ReadWebNovel;
import com.cither.reptile.parsing.WebNovelInfo;
import com.cither.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author raincither
 * @date 2021/8/8 14:32
 */
@Component
public class WebNovelUtil {

    @Autowired
    private  ReadService readService;
    @Autowired
    private  ReadWebNovel readWebNovel;
    @Autowired
    private  WebNovelInfo webNovelInfo;
    @Autowired
    private  WebNovelPipeline webNovelPipeline;

    private final static String RANK_URL = "https://www.qidian.com/rank/";

    /**
     * 获取阅读页 主体
     * @param url 页面url read.qidian.com
     * @param chapterId 当前页面Id
     * @return 主体文本 content
     */
    public String readWebNovel(String url, int chapterId){
        if(url == null || url.isEmpty()) {
            return null;
        }
        ResultItems str = Spider.create(readWebNovel).get(url);
        if(str == null){
            return null;
        }
        String content = str.get("content");
        readService.saveContentById(chapterId, content);
        return content;
    }
    /**
     * 获取详情页
     * @param url 页面url read.qidian.com
     */
    public void infoWebNovel(String url){
        if(url == null || url.isEmpty()) {
            return;
        }
        Spider.create(webNovelInfo)
                .addUrl(url)
                .addPipeline(webNovelPipeline)
                .start();
    }


    /**
     * 获取并保存排行榜
     */
    public void getWebNovelRank(){
        Spider.create(webNovelInfo)
                .addUrl(RANK_URL)
                .addPipeline(webNovelPipeline)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .start();
    }

}
