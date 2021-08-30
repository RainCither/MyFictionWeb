package com.cither.service;

import com.cither.dao.FictionMapper;
import com.cither.pojo.Fiction;
import com.cither.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author raincither
 * @date 2021/8/8 8:14
 */
@Service

public class InfoServer {

    @Autowired
    FictionMapper fictionMapper;

    /**
     * 根据id查找
     * @param bId 书 id
     * @return fiction
     */
    @Cacheable(cacheNames  = "detailId")
    public Fiction findFictionById(int bId) {
        return fictionMapper.findFictionById(bId);
    }

    /**
     * 保存 fiction 书
     * @param fiction 书
     * @return 成功 ： 1 失败 ： 0
     */
    public int saveFiction(Fiction fiction) {
        return fictionMapper.saveFiction(fiction);
    }

    /**
     * 根据书名查找
     * @param bName 书名
     * @return fiction
     */
    @Cacheable(cacheNames  = "detailName")
    public Fiction findFictionByName(String bName){
        return fictionMapper.findFictionByName(bName);
    }

    /**
     * 根据id查找书名
     * @param bId ID
     * @return 书名
     */
    @Cacheable(cacheNames  = "FictionNameId")
    public String findFictionNameById(int bId){
        return fictionMapper.findFictionNameById(bId);
    }

    /**
     * 根据书名作者查询此书是否已入库
     * @param bName 书名
     * @param author 作者
     * @return true is saved
     */
    public boolean findFictionExist(String bName, String author) {
        return fictionMapper.findFictionExist(bName, author);
    }

    /**
     * 根据链接查询此书是否已入库
     * @param link 书链接
     * @return > 0 is save
     */
    public Integer findFictionExistByLink (String link){
        Integer bId = fictionMapper.findFictionExistByLink(link);
        return  bId == null ? -1 : bId;
    }

}
