package com.cither.scheduling;

import com.cither.reptile.util.WebNovelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author raincither
 * @date 2021/10/8 9:16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitializeProgram implements ApplicationRunner {

    private final WebNovelUtil webNovelUtil;
    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(WebNovelUtil.rankList.isEmpty()){
            WebNovelUtil.getRankList();
        }
        for(String key : WebNovelUtil.rankList.keySet()){
            webNovelUtil.getRank(key);
        }

        log.info("Program initialization is complete ");
    }
}
