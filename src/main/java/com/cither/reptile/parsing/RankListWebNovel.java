package com.cither.reptile.parsing;

import com.cither.reptile.util.WebNovelUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author raincither
 * @date 2021/8/26 16:26
 */

public class RankListWebNovel implements PageProcessor {


    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {

        Html html = page.getHtml();

        if(!html.xpath("//div[@class='rank-header']").match()){
            return;
        }

        //获取排行榜信息
        List<Selectable> typeList = html.xpath("//div[@class='type-list']/p/a").nodes();
        Map<String, String> rankMap = new HashMap<>();
        String url = "https://www.qidian.com/rank/chn";

        for (Selectable selectable : typeList) {
            String link = url + selectable.xpath("//a/@data-chanid").get();
            String tag = selectable.xpath("//a/text()").get();
            rankMap.put(tag,link);
        }

        page.putField("rankMap", rankMap);

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
