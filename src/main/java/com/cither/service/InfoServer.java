package com.cither.service;

import com.cither.dao.FictionMapper;
import com.cither.pojo.Chapter;
import com.cither.pojo.Fiction;
import com.cither.util.RedisUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author raincither
 * @date 2021/8/8 8:14
 */
@Service
public class InfoServer {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    FictionMapper fictionMapper;

    /**
     * 根据id查找
     * @param bId 书 id
     * @return fiction
     */
    public Fiction findFictionById(int bId) {

        Fiction fictionById = (Fiction) redisUtil.get("bidInfo:" + bId);;

        if (fictionById == null) {
            fictionById = fictionMapper.findFictionById(bId);
            if (fictionById != null) {
                redisUtil.set("bidInfo:" + bId, fictionById);
            }
        }

        return fictionById;
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
    public Fiction findFictionByName(String bName){
        return fictionMapper.findFictionByName(bName);
    }

    /**
     * 查询此书是否已入库
     * @param bName 书名
     * @param author 作者
     * @return true is saved
     */
    public boolean findFictionExist(String bName, String author) {
        return fictionMapper.findFictionExist(bName, author);
    }

    /**
     * 查询此书是否已入库
     * @param link 书链接
     * @return true is save
     */
    public boolean findFictionExistByLink (String link){
        return fictionMapper.findFictionExistByLink(link);
    }

}
