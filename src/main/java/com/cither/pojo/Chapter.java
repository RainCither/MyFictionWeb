package com.cither.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author raincither
 * @date 2021/3/1 22:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {

    int cId;

    int chapterWhich;

    int bId;

    /**
     * 标题
     */
    String title;

    /**
     * 内容
     */
    String chapter;

    /**
     * 时间
     */
    String updateTime;

    /**
     * 来源链接
     */
    String chapterLink;

}
