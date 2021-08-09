package com.cither.reptile.parsing;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
/**
 * @author raincither
 * @date 2021/8/8 13:10
 */
@Component
public class ReadWebNovel implements PageProcessor {

    /**
     * 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     */
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        if(!page.getUrl().regex("read.qidian.com").match()){
            page.putField("content", null);
            return;
        }
        Html html = page.getHtml();
        String readContent = html.xpath("//div[@class='read-content']/tidyText()").get();
        String content = readContent.replaceAll("\n\n", "\n");
        page.putField("content", content);
    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return site;
    }
}
