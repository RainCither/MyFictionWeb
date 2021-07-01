package com.cither;

import com.cither.reptile.obtain.QdObtain;
import com.cither.reptile.util.HttpClientUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FictionApplicationTests {

    @Autowired
    QdObtain qd;

    @Test
    void contextLoads() {

        String url = "https://book.qidian.com/info/1026666395";
        Document parse = HttpClientUtil.getParse(url);
        String qd_g10 = parse.getElementsByAttributeValue("data-eid", "qd_G10").first().text();
        System.out.println(qd_g10);
    }

    @Test
    public void test() {

        int[] pageId = {1, 2, 4, 5, 6, 7, 8, 9, 10, 12, 15, 21, 22};

        for (int i : pageId) {
            qd.getIndexPage(i);
        }

    }
    @Test
    public void testRank() {

        qd.getRank();

    }

}
