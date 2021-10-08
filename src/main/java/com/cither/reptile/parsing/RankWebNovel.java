package com.cither.reptile.parsing;

import com.cither.reptile.util.WebNovelUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author raincither
 * @date 2021/8/30 18:38
 */
public class RankWebNovel implements PageProcessor {
    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        if(!html.xpath("//div[@class='rank-header']").match()){
            page.putField("error",  "页面解析错误：" + page.getUrl().get());
            return;
        }

        //获取所有排行榜链接信息
        List<String> linkList = html.xpath("//a[@data-bid]/@href").all();

        //去重
        Set<String> set = new HashSet<>(linkList);
        linkList.clear();
        linkList.addAll(set);

        for (int i = 0; i < linkList.size(); i++) {
            linkList.set(i,"https:" + linkList.get(i));
        }
        page.putField("linkList", linkList);
    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return WebNovelUtil.SITE;
    }
}
