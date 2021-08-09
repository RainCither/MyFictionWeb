package com.cither.reptile.parsing;

import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.reptile.util.HttpClientUtil;
import com.cither.service.InfoServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.cookie.Cookie;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author raincither
 * @date 2021/8/7 15:02
 */
@Component
public class WebNovelInfo implements PageProcessor {

    /**
     * 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     */
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private Cookie cookie = null;

    @Autowired
    private InfoServer infoServer;

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {

        Html html = page.getHtml();

        if(html.xpath("//div[@class='rank-header']").match()){
            //获取所有排行榜链接信息
            List<String> all = html.xpath("//a[@data-bid]/@href").all();
            List<String> remove = new ArrayList<>();
            for (String link : all) {
                if(infoServer.findFictionExistByLink("https:" + link)){
                    remove.add(link);
                }
            }
            all.removeAll(remove);
            page.addTargetRequests(all);
        }
        //详情页
        else if(html.xpath("//a[@id='readBtn']").match()){

            // 书 详情页获取
            //判断是否获取成功
            Selectable information = html.xpath("//div[@class='book-information'");
            if(!information.match()){
                page.putField("error", "详情失效？:" + page.getUrl().get());
                return;
            }

            String bookImg = "http:" + information.xpath("//a[@id='bookImg']/@href").get();
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

            //目录获取
            List<Selectable> allChapter = html.xpath("//li[@data-rid]/a[starts-with(@href,'//read')]").nodes();
            List<Chapter> chapterList;
            //解析失败转Ajax请求
            if(allChapter.isEmpty()){
                chapterList = ajaxCookie(page.getUrl().get().split("/")[4]);
            }else {
                chapterList = new ArrayList<>();
                int which = 0;
                for (Selectable chapterInfo : allChapter) {
                    Chapter chapter = new Chapter();
                    String href = "https:" + chapterInfo.xpath("//a/@href").get();
                    String title = chapterInfo.xpath("//a/text()").get();
                    String time = chapterInfo.xpath("//a/@title").get().substring(5,24);
                    chapter.setTitle(title)
                            .setChapterLink(href)
                            .setUpdateTime(time)
                            .setChapterWhich(which++);
                    chapterList.add(chapter);
                }
            }
            page.putField("chapterList", chapterList);

        }else if (html.xpath("/div[@class='error-img']").match()) {
            page.putField("error", "404");
        }else {
            page.putField("error ", page.getUrl().get());


        }
    }

    public List<Chapter> ajaxCookie(String bId){

        if(cookie == null){
            updateCookie(bId);
        }
        //包装参数
        Map<String, Object> param = new HashMap<>(4);
        param.put(cookie.getName(), cookie.getValue());
        param.put("bookId", bId);
        //请求json
        String chapterJson = HttpClientUtil.get("https://book.qidian.com/ajax/book/category", param);

        //解析 json
        ObjectMapper objectMapper = new ObjectMapper();
        List<Chapter> chapterList = new ArrayList<>();
        JsonNode catalogInfo = null;
        try {
            catalogInfo = objectMapper.readTree(chapterJson).path("data").path("vs");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            updateCookie(bId);
        }
        if(catalogInfo == null){
            return null;
        }
        int which = 0;
        for (JsonNode volumes : catalogInfo) {
            if (volumes.get("vS").asInt() != 0){
                continue;
            }
            JsonNode catalogs = volumes.path("cs");
            for (JsonNode catalog : catalogs) {
                String cName = catalog.get("cN").asText();
                String cTime = catalog.get("uT").asText();
                String clink = "https://read.qidian.com/chapter/" + catalog.get("cU").asText();

                Chapter chapter = new Chapter();
                chapter.setTitle(cName)
                        .setChapterWhich(which++)
                        .setUpdateTime(cTime)
                        .setChapterLink(clink);

                chapterList.add(chapter);
            }

        }
        return chapterList;
    }

    /**
     * 获取cookie
     */
    private void updateCookie(String bid){
        HttpClientUtil.get("https://book.qidian.com/info/" +bid);
        List<Cookie> cookies = HttpClientUtil.httpCookieStore.getCookies();
        for (Cookie c : cookies) {
            //寻找cookie
            if ("_csrfToken".equals(c.getName())) {
                cookie = c;
                break;
            }
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
