package com.cither.util;

public enum BookGenreEnum {
    ALL("全部分类"),
    XH("玄幻"),
    QH("奇幻"),
    WX("武侠"),
    XX("仙侠"),
    DS("都市"),
    JS("军事"),
    LS("历史"),
    YX("游戏"),
    TY("体育"),
    KH("科幻"),
    XY("悬疑"),
    QXS("轻小说"),
    XS("现实");

    String genre;

    BookGenreEnum(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}
