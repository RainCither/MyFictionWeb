package com.cither.reptile.parsing;

import com.cither.pojo.Fiction;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author raincither
 * @date 2021/8/7 15:02
 */
public class WebNovelInfo implements PageProcessor {

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

        Html html = page.getHtml();

        if(html.xpath("//a[@id='readBtn']").get() == null){
            List<String> all = html.xpath("//a[@data-bid]/@href").all();
            page.addTargetRequests(all);
        }else{
            Selectable information = html.xpath("//div[@class='book-information'");
            if(information.get() == null){ return;}
            String bookImg = information.xpath("//a[@id='bookImg']/@href").toString();
            String bookName = information.xpath("//h1/em/text()").get();
            String bookAuthor = information.xpath("//h1/span/a/text()").get();
            String bookStatus = information.xpath("//p[@class='tag']/span[1]/text()").get();
            String bookTag = information.xpath("//p[@class='tag']/a[1]/text()").get();
            String bookDoc = html.xpath("//div[@class='book-intro']/p").regex(">(.*)<").get();
            Fiction fiction = new Fiction();
            fiction.setCover(bookImg)
                    .setBName(bookName)
                    .setAuthor(bookAuthor)
                    .setState(bookStatus)
                    .setCat(bookTag)
                    .setDesc(bookDoc)
                    .setLink(page.getUrl().get());

            page.putField("fiction", fiction);
        }
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
