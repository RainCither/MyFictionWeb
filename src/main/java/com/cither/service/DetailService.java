package com.cither.service;

import com.cither.dao.ChapterMapper;
import com.cither.dao.FictionMapper;
import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author raincither
 * @date 2021/3/3 8:53
 */
@Service
public class DetailService {

    @Autowired
    FictionMapper fictionMapper;
    @Autowired
    ChapterMapper chapterMapper;

    public int saveWarpById(int cId, String content){return chapterMapper.saveWarpById(cId, content);}

    public Fiction findFiction(int bId) {
        return fictionMapper.findFictionById(bId);
    }

    public List<Chapter> findChapterListById(int bId) {
        return chapterMapper.findListChapterById(bId);
    }

    public Chapter findChapterById(int cId) {
        return chapterMapper.findChapterById(cId);
    }

    public Integer findChapterByWhich(int bId, int which) {
        Integer chapterByWhich = chapterMapper.findChapterByWhich(bId, which);
        if (chapterByWhich == null) {
            return 0;
        }
        return chapterByWhich;
    }

    public int saveFiction(Fiction fiction) {
        return fictionMapper.saveFiction(fiction);
    }

    public int saveChapter(Chapter chapter) {
        return chapterMapper.saveChapter(chapter);
    }

    public boolean findFictionExist(String link) {
        return fictionMapper.findFictionExist(link);
    }



}
