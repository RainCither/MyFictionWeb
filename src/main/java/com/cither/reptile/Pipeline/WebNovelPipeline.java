package com.cither.reptile.Pipeline;

import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.service.InfoServer;
import com.cither.service.ReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author raincither
 * @date 2021/8/7 22:43
 */
@Component
@Slf4j
public class WebNovelPipeline implements Pipeline {

    @Autowired
    private InfoServer infoServer;

    @Autowired
    private ReadService readService;
    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {

        //检查错误
        if(resultItems.get("error") != null){
            log.error(resultItems.get("error"));
            return;
        }

        Fiction fiction = resultItems.get("fiction");
        List<Chapter> chapterList = resultItems.get("chapterList");

        if(!infoServer.findFictionExist(fiction.getBName(), fiction.getAuthor())){
            infoServer.saveFiction(fiction);
            for (Chapter chapter : chapterList) {
                chapter.setBId(fiction.getBId());
            }
            readService.saveListChapter(chapterList);
            log.info("save success:{}",fiction.getBName());
        }else{
            log.info("saved:{}",fiction.getBName());
        }
    }
}
