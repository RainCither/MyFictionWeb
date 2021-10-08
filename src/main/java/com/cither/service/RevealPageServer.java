package com.cither.service;

import com.cither.pojo.Fiction;
import com.cither.reptile.util.WebNovelUtil;
import com.cither.util.BookGenreEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author raincither
 * @date 2021/8/26 16:24
 */
@Service
@RequiredArgsConstructor
public class RevealPageServer {

    private final WebNovelUtil webNovelUtil;
    private final InfoService infoService;

    public List<Fiction> getRecommend(BookGenreEnum tag){
        List<String> rank = webNovelUtil.getRank(tag.getGenre());
        return getRecommendFiction(rank);
    }


    public Map<BookGenreEnum,List<Fiction>> getRecommendFU(){

        BookGenreEnum[] values = BookGenreEnum.values();
        Map<BookGenreEnum,List<Fiction>> fictionMap = new HashMap<>();
        for (int i=1; i<values.length; i++) {
            List<String> rank = webNovelUtil.getRank(values[i].getGenre());
            fictionMap.put(values[i], getRecommendFiction(rank));
        }
        return fictionMap;
    }
    
    public List<Fiction> getRecommendFiction(List<String> rank){
        List<Fiction> fictions = new ArrayList<>();
        for (String link : rank) {
            Fiction fictionByLink = infoService.findFictionByLink(link);
            if (fictionByLink == null){
                continue;
            }
            fictions.add(infoService.findFictionByLink(link));
        }
        return fictions;
    }


    public void getRank(){
        if(WebNovelUtil.rankList.isEmpty()){
            WebNovelUtil.getRankList();
        }
        for(String key : WebNovelUtil.rankList.keySet()){
            webNovelUtil.updateRank(key);
        }
    }

    public void updateRank(){
        if(WebNovelUtil.rankList.isEmpty()){
            WebNovelUtil.getRankList();
        }
        for(String key : WebNovelUtil.rankList.keySet()){
            webNovelUtil.updateRank(key);
        }
    }

}
