package com.cither.scheduling;

import com.cither.reptile.util.WebNovelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author raincither
 * @date 2021/10/8 8:43
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateTask {

    private final WebNovelUtil webNovelUtil;

    @Scheduled(cron = "0 0 3 * * ?")
    public void updateRank(){
        log.info("rank update start");

        if(WebNovelUtil.rankList.isEmpty()){
            WebNovelUtil.getRankList();
        }
        for(String key : WebNovelUtil.rankList.keySet()){
            webNovelUtil.updateRank(key);
        }

        log.info("rank update start end");

    }

}
