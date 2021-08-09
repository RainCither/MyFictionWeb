package com.cither.dao;

import com.cither.pojo.Chapter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author raincither
 * @date 2021/3/2 16:15
 */
@Mapper
@Repository
public interface ChapterMapper {

    /**
     * 保存 chapter 章节
     * @param chapter 章节
     * @return 成功 ： 1 失败 ： 0
     */
    int saveChapter(Chapter chapter);

    /**
     * 根据章节目录id查询
     * @param chapterList 书记列表
     * @return 成功 ： 1 失败 ： 0
     */
    int saveListChapter(List<Chapter> chapterList);

    /**
     * 根据 cId 更新章节内容
     * @param cId cId
     * @param content 章节内容
     * @return 成功 ： 1 失败 ： 0
     */
    @Update("update chapter set chapter = #{content} where c_id = #{cId}")
    int saveWarpById(int cId, String content);

    /**
     * 根据书籍id查找
     * @param bId 书 id
     * @return Chapter
     */
    @Select("select c_id, b_id, title, update_time, chapter，chapter_which, chapter_link from chapter where b_id = #{bId}")
    List<Chapter> findListChapterById(int bId);

    /**
     * 根据书籍id查找章节信息
     * @param bId 书 id
     * @return Chapter
     */
    @Select("select c_id, b_id, title, update_time, chapter_which, chapter_link from chapter where b_id = #{bId}")
    List<Chapter> findListChapterInfoById(int bId);

    /**
     * 根据章节id查找
     * @param cId 章节 id
     * @return Chapter
     */
    @Select("select * from chapter where c_id = #{cId}")
    Chapter findChapterById(int cId);

    /**
     * 根据章节目录id查询
     * @param bId 书ID
     * @param chapterWhich chapterWhich 章节索引
     * @return Chapter
     */
    @Select("select c_id from chapter where chapter_which = #{chapterWhich} && b_id = #{bId}")
    Integer findChapterByWhich(int bId, int chapterWhich);

}
