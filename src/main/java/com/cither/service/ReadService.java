package com.cither.service;

import com.cither.dao.ChapterMapper;
import com.cither.pojo.Chapter;
import com.cither.reptile.util.WebNovelUtil;
import com.cither.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author raincither
 * @date 2021/8/7 14:28
 */
@Service
public class ReadService {

    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    private WebNovelUtil webNovelUtil;

    /**
     * 根据书籍id查找
     * @param bId 书 id
     * @return Chapter
     */
    @Cacheable(cacheNames = "chapterAllList",unless="#result == null")
    public List<Chapter> findListChapterById(int bId) {
        return chapterMapper.findListChapterById(bId);
    }

    /**
     * 根据书籍id查找章节信息
     * @param bId 书 id
     * @return Chapter
     */
    @Cacheable(cacheNames = "chapterList",unless="#result == null")
    public List<Chapter> findListChapterInfoById(int bId) {
        return chapterMapper.findListChapterInfoById(bId);
    }

    /**
     * 根据章节id查找
     * @param cId 章节 id
     * @return Chapter
     */
    @Cacheable(cacheNames = "chapter",unless="#result == null")
    public Chapter findChapterById(int cId) {
        Chapter chapter = chapterMapper.findChapterById(cId);
        if(chapter == null){
            return null;
        }
        //检查正文是否存在
        if(chapter.getChapter() == null){
            //获取正文
            String content = webNovelUtil.readWebNovel(chapter.getChapterLink(), chapter.getCId());
            chapter.setChapter(content);
        }

        return chapter;
    }

    /**
     * 根据章节目录id查询
     * @param bId 书ID
     * @param chapterWhich chapterWhich 章节索引
     * @return ChapterId
     */
    @Cacheable(cacheNames = "which",unless="#result == null")
    public Integer findChapterByWhich(int bId, int chapterWhich) {
        if(chapterWhich < 0){
            return null;
        }
        return chapterMapper.findChapterByWhich(bId, chapterWhich);
    }

    /**
     * 保存 chapter 章节
     * @param chapter 章节
     * @return 成功 ： 1 失败 ： 0
     */
    public int saveChapter(Chapter chapter) {
        return chapterMapper.saveChapter(chapter);
    }

    /**
     * 批量保存章节
     * @param chapterList 书记列表
     * @return 成功 ： 1 失败 ： 0
     */
    public int saveListChapter(List<Chapter> chapterList){
        return chapterMapper.saveListChapter(chapterList);
    }

    /**
     * 根据 cId 更新章节内容
     * @param cId cId
     * @param content 章节内容
     * @return 成功 ： 1 失败 ： 0
     */
    @CachePut(cacheNames = "chapter",key = "#cId",unless="#result == null")
    public int saveContentById(int cId, String content){return chapterMapper.saveWarpById(cId, content);}
}
