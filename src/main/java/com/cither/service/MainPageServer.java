package com.cither.service;

import com.cither.pojo.Fiction;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raincither
 * @date 2021/8/26 16:24
 */
@Service
public class MainPageServer {


    @Cacheable(cacheNames = "Recommend")
    public List<Fiction> getRecommend(){
        List<Fiction> fictions = new ArrayList<>();

        fictions.add(new Fiction());

        return fictions;
    }


}
