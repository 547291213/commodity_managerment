/**
 * Copyright(C) H3C
 * Author : kefneg
 * Filename : GoodsServiceImpl
 * Description :
 **/
package com.kefeng.service.serviceimpl;


import com.kefeng.dao.GoodsMapper;
import com.kefeng.pojo.Goods;
import com.kefeng.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("goodsService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 5, readOnly = false)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper ;

    @Override
    public List<Goods> queryByCategory(int goodsCategory)  {
        return goodsMapper.queryByCategory(goodsCategory) ;
    }

    @Override
    public Goods getGoodsById(int goodsId) throws Exception {
        return goodsMapper.getGoodsById(goodsId);
    }

    @Override
    public void addGoods(Goods goods) throws Exception {

        goodsMapper.addGoods(goods);
    }

    @Override
    public void delGoods(int goodsId) throws Exception{

        goodsMapper.delGoods(goodsId);
    }

    @Override
    public void updateGoods(Goods goods) throws Exception {

        goodsMapper.updateGoods(goods);
    }


}
