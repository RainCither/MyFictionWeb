package com.cither.service;

import com.cither.dao.ChapterMapper;
import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.util.RedisUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RedisUtil redisUtil;

    /**
     * 根据书籍id查找
     * @param bId 书 id
     * @return Chapter
     */
    public List<Chapter> findListChapterById(int bId) {
        return chapterMapper.findListChapterById(bId);
    }

    /**
     * 根据书籍id查找章节信息
     * @param bId 书 id
     * @return Chapter
     */
    public List<Chapter> findListChapterInfoById(int bId) {
        return chapterMapper.findListChapterInfoById(bId);
    }

    /**
     * 根据章节id查找
     * @param cId 章节 id
     * @return Chapter
     */
    public Chapter findChapterById(int cId) {
        Chapter chapter = (Chapter) redisUtil.get("chapterContent:" + cId);
        if(chapter == null){
            chapter = chapterMapper.findChapterById(cId);
            if(chapter == null){
                return null;
            }
            redisUtil.set("chapterContent:" + cId, chapter);
        }
        return chapter;
    }

    /**
     * 根据章节目录id查询
     * @param bId 书ID
     * @param chapterWhich chapterWhich 章节索引
     * @return ChapterId
     */
    public Integer findChapterByWhich(int bId, int chapterWhich) {
        Integer cId = (Integer) redisUtil.get(bId + ":which:" + chapterWhich);
        if(cId == null){
            cId = chapterMapper.findChapterByWhich(bId, chapterWhich);
            if (cId == null) {
                return -1;
            }
            redisUtil.set(bId + ":which:" + chapterWhich, cId);
        }
        return cId;
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
     * 根据章节目录id查询
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
    public int saveContentById(int cId, String content){return chapterMapper.saveWarpById(cId, content);}
}
