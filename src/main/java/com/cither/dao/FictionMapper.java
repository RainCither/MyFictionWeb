package com.cither.dao;

import com.cither.pojo.Fiction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * 根据id查找
     * @param bId 书 id
     * @return fiction
     */
    @Select("select b_name from detail where b_id = #{bId}")
    String findFictionNameById(int bId);

    /**
     * 查询此书是否已入库
     * @param bName 书名
     * @param author 作者
     * @return true is save
     */
    @Select("select count(*) from detail where author = #{author} and b_name = #{bName}")
    Boolean findFictionExist (String bName, String author);

    /**
     * 查询此书是否已入库
     * @param link 书链接
     * @return 入库ID
     */
    @Select("select 1 from detail where link = #{link}")
    Boolean findFictionExistByLink (String link);

    /**
     * 根据链接查询书
     * @param link 书链接
     * @return 入book
     */
    @Select("select * from detail where link = #{link}")
    Fiction findFictionByLink (String link);

    /**
     * 获取最新的数据
     * @param limit 几个
     * @return 列表
     */
    @Select("SELECT * FROM detail GROUP BY b_id DESC limit #{limit}")
    List<Fiction> getLatestFiction(int limit);

    /**
     * 获取最新的数据
     * @param limit 几个
     * @return 列表
     */
    @Select("SELECT * FROM detail where cat = #{tag} GROUP BY b_id DESC limit #{limit}")
    List<Fiction> getLatestTagFiction(String tag,int limit);

    @Select("SELECT * FROM detail where b_name like concat('%',#{name},'%') limit #{limit}")
    List<Fiction> searchFictionByName(String name, int limit);

}
