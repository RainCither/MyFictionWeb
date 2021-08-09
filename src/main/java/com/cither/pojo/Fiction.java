package com.cither.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author raincither
 * @date 2021/3/1 16:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Fiction implements Serializable {

    private final static long serialVersionUID = 7L;

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
