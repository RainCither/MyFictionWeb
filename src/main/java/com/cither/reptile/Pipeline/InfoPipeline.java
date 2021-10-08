package com.cither.reptile.Pipeline;

import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.service.InfoService;
import com.cither.service.ReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author raincither
 * @date 2021/8/27 15:41
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InfoPipeline implements Pipeline {

    private final InfoService infoService;
    private final ReadService readService;

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

        if(!infoService.findFictionExist(fiction.getBName(), fiction.getAuthor())){
            infoService.saveFiction(fiction);
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
