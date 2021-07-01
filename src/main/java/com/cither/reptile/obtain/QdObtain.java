package com.cither.reptile.obtain;

import com.cither.dao.ChapterMapper;
import com.cither.dao.FictionMapper;
import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.reptile.util.HttpClientUtil;
import com.cither.service.DetailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.cookie.Cookie;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author raincither
 * @date 2021/3/1 16:49
 */
@Slf4j
@Component
public class QdObtain {

    @Autowired
    private DetailService detailService;

    /**
     * 获取 详情页
     * @param bId qidian 书id
     * @return fictionId if not find return 0。 parsError return 2
     */
    public int saveDetail(int bId){

        String url = "https://book.qidian.com/info/" + bId;

        //判断是否已获取
        if (detailService.findFictionExist(url)) {
            log.info("The saved {}", bId);
            return -2;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Fiction fiction = new Fiction();
        fiction.setLink(url);

        //测试页 1039430
        Document parse = HttpClientUtil.getParse(url);

        if(isError(parse)){
            log.info("{} is 404", bId);
            return -1;
        }

        //获取主页面
        Element info = parse.getElementsByClass("book-detail-wrap").first();

        //获取 书名 作者
        String[] titles = info.getElementsByTag("h1").first().text().split(" ");
        fiction.setBName(titles[0]);
        fiction.setAuthor(titles[1]);

        //获取 状态 标签分类
        String State = info.getElementsByClass("blue").first().text();
        fiction.setState(State);
        String tag = info.getElementsByAttributeValue("data-eid", "qd_G10").text();
        fiction.setCat(tag);

        //获取 描述
        String docs = info.getElementsByClass("book-intro").first().text();
        fiction.setDesc(docs);

        //获取 封面地址
        String cover = info.getElementsByTag("img").first().attr("src");
        fiction.setCover("http:" + cover);

        detailService.saveFiction(fiction);

        //获取目录
        Elements dataCid = info.getElementById("j-catalogWrap").getElementsByAttribute("data-cid");
        ArrayList<Chapter> chapterArrayList = new ArrayList<>();

        int count = 0;
        for (Element cid : dataCid) {
            Chapter chapter = new Chapter();
            //获取章节信息
            chapter.setBId(fiction.getBId());
            chapter.setTitle(cid.text());
            chapter.setChapterLink("https:" + cid.attr("href"));
            chapter.setChapterWhich(count++);
            chapter.setUpdateTime(cid.attr("title").substring(5, 24));
            detailService.saveChapter(chapter);
        }

        return fiction.getBId();

    }


    public String getReadPage(int cId, String link) {

        //测试页 1039430
        Document parse = HttpClientUtil.getParse(link);

        if(isError(parse)){
            log.info("{} is 404", link);
            return "404";
        }
        //获取主页面
        Element read = parse.getElementsByClass("read-content").first();
        Elements wraps = read.getElementsByClass("content-wrap");

        StringBuilder content = new StringBuilder();
        for (Element wrap : wraps) {
            content.append(wrap.text()).append("\n");
        }
        detailService.saveWarpById(cId, content.toString());

        return content.toString();
    }

    /**
     * 解析卷方式二
     * @param volumeList 卷地址列表
     * @param bId 书id
     * @return 0 ：error , 1 ：success
     */
    public int parsFreeVolume(List<String> volumeList, int bId) {
            List<Cookie> cookies = HttpClientUtil.httpCookieStore.getCookies();
            for (Cookie cookie : cookies) {
                //寻找cookie
                if (!"_csrfToken".equals(cookie.getName())) {
                    continue;
                }

                //包装参数
                Map<String, Object> param = new HashMap<>();
                param.put(cookie.getName(), cookie.getValue());
                param.put("bookId", bId);
                //请求json
                String chapterJson = HttpClientUtil.get("https://book.qidian.com/ajax/book/category", param);
                //解析 json
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode node = objectMapper.readTree(chapterJson).path("data").path("vs");

                    for (JsonNode jsonNode : node) {
                        if (jsonNode.get("vS").asInt() == 0) {
                            int vId = jsonNode.get("vId").asInt();
                            String volumeUrl = "https://read.qidian.com/hankread/" + bId + "/" + vId;
                            volumeList.add(volumeUrl);
                        }
                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return 0;
                }

            }
            //判断是否获取到信息
            if (volumeList.size() > 0){
                return 1;
            }else {
                return 0;
            }
    }

    /**
     * 请求卷并保存章节
     * @param urlList fiction.getListVolume 卷链接列表
     */
    public void saveVolume(List<String> urlList, int bId) {

        List<Chapter> chapters = new ArrayList<>();
        int count = 0;
        //获取 卷
        for (String url : urlList) {

            //获取页面
            Document parse = HttpClientUtil.getParse(url);
            //判断
            if(isError(parse)){
                return ;
            }

            //获取 卷数据
            Elements textWrap = parse.getElementsByClass("text-wrap");
            //获取 卷章节
            for (Element wrap : textWrap) {

                //创建章节
                Chapter chapter = new Chapter();

                //获取章节信息
                String title = wrap.getElementsByClass("content-wrap").first().text();
                chapter.setTitle(title);

                String updateTime = wrap.getElementsByClass("j_updateTime").first().text();
                chapter.setUpdateTime(updateTime);

                //获取章节
                Elements texts = wrap.getElementsByClass("read-content").first().getElementsByTag("p");
                StringBuilder text = new StringBuilder();
                //格式化
                for (Element p : texts) {
                    text.append(p.text()).append("\n");
                }
                chapter.setChapter(text.toString());

                chapter.setChapterWhich(count++);

                //添加
                chapters.add(chapter);
            }
        }

        //保存
        for (Chapter chapter : chapters) {
            chapter.setBId(bId);
            detailService.saveChapter(chapter);
        }

    }

    /**
     * 过时代码留存
     * @param info 0
     * @param bId 0
     * @param fiction 0
     */
    public int stale(Element info, int bId, Fiction fiction) {
        //获取 免费目录信息
        Elements free = info.getElementsByClass("free");
        //创建 卷地址列表
        List<String> volumeList = new ArrayList<>();
        //判断 免费卷为空 更换解析方式
        if (free.isEmpty()) {
            int flag = parsFreeVolume(volumeList, bId);
            //解析失败 直接返回
            if (flag < 1){
                return 0;
            }
        }else {
            for (Element element : free) {
                //获取 卷链接
                String volume = "http:" + element.firstElementSibling().attr("href");
                volumeList.add(volume);
            }
        }
        //保存并获取id
        detailService.saveFiction(fiction);
        //保存章节
        saveVolume(volumeList, fiction.getBId());

        return 0;
    }


    /**
     * 判断是否为404页面
     * @param page 请求解析后的文档
     * @return true ：is 404 page
     */
    public boolean isError(Document page) {
        //判断页面是否存在
        Elements error = page.getElementsByClass("error-img");
        return !error.isEmpty();
    }

    /**
     * 获取 排行页面
     * @param pageId 排行榜id
     */
    public void getIndexPage(int pageId) {
        Document parse = HttpClientUtil.getParse("https://www.qidian.com/rank?chn=" + pageId);

        Element rankListRow = parse.getElementsByClass("rank-list-row").first();
        Elements books = rankListRow.getElementsByClass("name");


        for (Element book : books) {
            int bId = Integer.parseInt(book.attr("data-bid"));
            log.info("try down {}", bId);
            saveDetail(bId);
          }


    }

    public void updateInfo() {

    }

    /**
     * 获取 主页面
     */
    public void getRank() {
        Document parse = HttpClientUtil.getParse("https://www.qidian.com/rank");
        Elements names = parse.getElementsByAttribute("data-bid");
        for (Element name : names) {
            int bId = Integer.parseInt(name.attr("data-bid"));
            log.info("try down {}", bId);
            saveDetail(bId);
        }
    }
}
