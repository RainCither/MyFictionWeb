package com.cither.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author raincither
 * @date 2021/3/1 16:28
 */
@Data
public class Fiction {

    /**
     * id
     */
    int bId;

    /**
     * 书名
     */
    String bName;

    /**
     * 作者
     */
    String author;

    /**
     * 描述
     */
    String desc;

    /**
     * 分类
     */
    String cat;

    /**
     * 封面
     */
    String cover;

    /**
     * 状态
     */
    String state;

    /**
     * 来源
     */
    String link;



}
