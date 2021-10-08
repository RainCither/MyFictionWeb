package com.cither.service;

import com.cither.dao.FictionMapper;
import com.cither.pojo.Fiction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author raincither
 * @date 2021/8/8 8:14
 */
@Service
@RequiredArgsConstructor
public class InfoService {

    private final FictionMapper fictionMapper;

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
    public Boolean findFictionExistByLink (String link){
        Boolean exist = fictionMapper.findFictionExistByLink(link);
        if(exist == null){
            exist = false;
        }
        return exist;
    }

    /**
     * 根据链接查询此书是否已入库
     * @param link 书链接
     * @return > 0 is save
     */
    @Cacheable(cacheNames  = "linkByFiction", unless="#result == null")
    public Fiction findFictionByLink (String link){
        return fictionMapper.findFictionByLink(link);
    }

    /**
     * 获取最新的数据
     * @param limit 几个
     * @return 列表
     */
    public List<Fiction> getLatestFiction(int limit){
        return fictionMapper.getLatestFiction(limit);
    }

    /**
     * 获取最新的数据
     * @param limit 几个
     * @return 列表
     */
    public List<Fiction> getLatestTagFiction(String tag, int limit){
        return fictionMapper.getLatestTagFiction(tag , limit);
    }

    public List<Fiction> searchFictionByName(String name, int limit){
        return fictionMapper.searchFictionByName(name, limit);
    }

}
