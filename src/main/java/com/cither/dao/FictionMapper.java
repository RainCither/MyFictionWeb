package com.cither.dao;

import com.cither.pojo.Fiction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author raincither
 * @date 2021/3/2 15:16
 */
@Mapper
@Repository
public interface FictionMapper {

    /**
     * 保存 fiction 书
     * @param fiction 书
     * @return 成功 ： 1 失败 ： 0
     */
    int saveFiction(Fiction fiction);

    /**
     * 根据id查找
     * @param bId 书 id
     * @return fiction
     */
    @Select("select * from detail where b_id = #{bId}")
    Fiction findFictionById(int bId);

    /**
     * 根据书名查找
     * @param bName 书名
     * @return fiction
     */
    @Select("select * from detail where b_name = #{bName}")
    Fiction findFictionByName(String bName);

    /**
     * 查询此书是否已入库
     * @param bName 书名
     * @param author 作者
     * @return true is save
     */
    @Select("select count(*) from detail where author = #{author} and b_name = #{bName}")
    boolean findFictionExist (String bName, String author);

    /**
     * 查询此书是否已入库
     * @param link 书链接
     * @return true is save
     */
    @Select("select count(*) from detail where link = #{link}")
    boolean findFictionExistByLink (String link);
}
