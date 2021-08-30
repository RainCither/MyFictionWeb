package com.cither.reptile.util;

import com.cither.pojo.Fiction;
import com.cither.reptile.Pipeline.InfoPipeline;
import com.cither.reptile.parsing.InfoWebNovel;
import com.cither.reptile.parsing.RankListWebNovel;
import com.cither.reptile.parsing.RankWebNovel;
import com.cither.reptile.parsing.ReadWebNovel;
import com.cither.service.ReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.Map;

/**
 * @author raincither
 * @date 2021/8/8 14:32
 */
@Component
@Slf4j
public class WebNovelUtil {

    /**
     * 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     */
    public static final Site SITE = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Autowired
    private ReadService readService;
    @Autowired
    private ReadWebNovel readWebNovel;
    @Autowired
    private InfoWebNovel infoWebNovel;
    @Autowired
    private InfoPipeline infoPipeline;
    @Autowired
    private RankListWebNovel rankWebNovel;

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
    public void getInfo(String url){
        if(url == null || url.isEmpty()) {
            return;
        }
        Spider.create(infoWebNovel)
                .addUrl(url)
                .addPipeline(infoPipeline)
                .start();
    }

    /**
     * 获取详情页
     * @param url 页面url read.qidian.com
     */
    public void getInfo(List<String> url){
        if(url == null || url.isEmpty()) {
            return;
        }
        Spider.create(infoWebNovel)
                .startUrls(url)
                .addPipeline(infoPipeline)
                .start();
    }


    /**
     * 获取所有排行榜链接
     */
    public void getRankList(){
        ResultItems rankResultItems = Spider.create(new RankListWebNovel()).get("https://www.qidian.com/rank/");
        if(rankResultItems == null){
            return;
        }

        Map<String, String> rankMap = rankResultItems.get("rankMap");
        for (String key : rankMap.keySet()) {
            getRank(key, rankMap.get(key));
        }

    }

    public void getRank(String tag, String link){
        ResultItems rankResultItems = Spider.create(new RankWebNovel()).get(link);
        if(rankResultItems == null){
            return;
        }
        List<String> linkList = rankResultItems.get("linkList");
        getInfo(linkList);
    }



    /**
     * 获取详情页
     * @param url 页面url read.qidian.com
     */
    public void RankWebNovel(String url){

    }

}
