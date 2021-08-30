package com.cither.reptile.parsing;

import com.cither.reptile.util.WebNovelUtil;
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
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String readContent = html.xpath("//div[@class='read-content']/tidyText()").get();

        if(readContent == null){
            return;
        }

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
        return WebNovelUtil.SITE;
    }
}
