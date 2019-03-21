package com.kefeng.dao;

import com.kefeng.pojo.Goods;

import java.util.List;

public interface GoodsMapper {

    /**
     * 根据商品所在分区来查询商品
     * @param goodsCategory 商品分区
     * @return 商品列表
     */
    List<Goods> queryByCategory(int goodsCategory);

    /**
     * 根据搜索进行模糊查询
     * @param data 搜索内容
     * @return 商品列表
     */
    List<Goods> getSearchGoods(String data);



    /**
     * 根据商品id来获取指定商品的i洗脑洗
     * @param goodsId
     * @return 指定商品的信息
     */
    Goods getGoodsById(int goodsId) ;

    /**
     * 添加商品
     * @param goods 商品对象实例
     */
    void addGoods(Goods goods) ;

    /**
     * 根据商品id删除指定商品
     * @param goodsId 商品id
     */
    void delGoods(int goodsId) ;

    /**
     * 更新商品信息
     * @param goods 商品对象实例
     */
    void updateGoods(Goods goods) ;


}