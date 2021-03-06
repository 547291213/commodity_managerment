package com.kefeng.service;

import com.kefeng.pojo.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> queryByCategory(int goodsCategory) throws Exception;

    Goods getGoodsById(int goodsId) throws Exception;

    void addGoods(Goods goods) throws Exception;

    void delGoods(int goodsId) throws Exception;

    void updateGoods(Goods goods) throws Exception;

    List<Goods> getPageGoods(int goodsCategory, int pageNum, int pageSize);

    int getGoodsNum(int goodsCategory);

    List<Goods> getSearchGoods(String data, int pageNum, int pageSize);

    int getSearchCount(String data);


}
