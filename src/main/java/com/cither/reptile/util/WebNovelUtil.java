package com.cither.reptile.util;

import com.cither.reptile.pipeline.InfoPipeline;
import com.cither.reptile.parsing.InfoWebNovel;
import com.cither.reptile.parsing.RankListWebNovel;
import com.cither.reptile.parsing.RankWebNovel;
import com.cither.reptile.parsing.ReadWebNovel;
import com.cither.service.InfoService;
import com.cither.service.ReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import java.util.*;

/**
 * @author raincither
 * @date 2021/8/8 14:32
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WebNovelUtil {

    /**
     * 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     */
    public static final Site SITE = Site.me().setRetryTimes(3).setSleepTime(1000);
    public static Map<String,String> rankList = new HashMap<>();

    private final InfoService infoService;
    private final InfoPipeline infoPipeline;

    private static final ReadWebNovel READ_WEB_NOVEL = new ReadWebNovel();
    private static final InfoWebNovel INFO_WEB_NOVEL = new InfoWebNovel();
    private static final RankWebNovel RANK_WEB_NOVEL = new RankWebNovel();
    private static final RankListWebNovel RANK_LIST_WEB_NOVEL = new RankListWebNovel();

    /**
     * 获取阅读页 主体
     * @param url 页面url read.qidian.com
     * @param chapterId 当前页面Id
     * @return 主体文本 content
     */
    public static String getReadPage(String url, int chapterId, ReadService readService){
        if(url == null || url.isEmpty()) {
            return null;
        }
        ResultItems str = Spider.create(READ_WEB_NOVEL).get(url);
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
    public Boolean getInfo(String url){
        if(url == null || url.isEmpty()) {
            return false;
        }
        Spider.create(INFO_WEB_NOVEL)
                .addUrl(url)
                .addPipeline(infoPipeline)
                .start();

        return true;
    }

    /**
     * 获取详情页
     * @param url 页面url read.qidian.com
     */
    public Boolean getInfo(List<String> url){
        if(url == null || url.isEmpty()) {
            return false;
        }
        ArrayList<String> links = new ArrayList<>(url);
        links.removeIf(infoService::findFictionExistByLink);
        if(links.isEmpty()){
            return false;
        }
        Spider.create(INFO_WEB_NOVEL)
                .addPipeline(infoPipeline)
                .startUrls(links)
                .start();
        return true;
    }


    /**
     * 获取所有排行榜链接
     */
    public static void getRankList(){
        ResultItems rankResultItems = Spider.create(RANK_LIST_WEB_NOVEL).get("https://www.qidian.com/rank/");
        if(rankResultItems == null){
            return;
        }
        rankList = rankResultItems.get("rankMap");
    }

    /**
     * 获取对应分类书籍链接
     * @param tag 要获取的分类
     * @return 获取到的书籍链接列表
     */
    @Cacheable(cacheNames = "recommend")
    public List<String> getRank(String tag){
        if(rankList.isEmpty()){
            getRankList();
        }
        ResultItems rankResultItems = Spider.create(RANK_WEB_NOVEL).get(rankList.get(tag));
        if(rankResultItems == null){
            return null;
        }
        List<String> linkList = rankResultItems.get("linkList");
        //保存书籍信息
        if(!getInfo(linkList)){
            log.info("{} saved", tag);
        }
        return linkList;
    }

    @CachePut(cacheNames = "recommend")
    public List<String> updateRank(String tag){
        List<String> rank = getRank(tag);
        return rank;
    }

}
