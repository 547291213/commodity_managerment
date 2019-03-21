/**
 * Copyright(C) H3C
 * Author : kefeng
 * Filename : GoodsController
 * Description :
 **/
package com.kefeng.controller;

import com.kefeng.pojo.Code;
import com.kefeng.pojo.Goods;
import com.kefeng.pojo.GoodsGrid;
import com.kefeng.service.GoodsService;
import com.kefeng.util.ImageUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {


    @Autowired
    GoodsService goodsService;

    @RequestMapping(value = "/queryByCategory", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Goods> queryByCategory(@RequestParam("goodsCategory") int goodsCategory) {

        try {
            return goodsService.queryByCategory(goodsCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //测试网址：http://localhost:8083/goods/page/getGoodsById?goodsCategory=2&current=1&rowCount=10
    @RequestMapping(value = "/page/getGoodsById", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private GoodsGrid getStuGrid(@RequestParam("goodsCategory") int goodsCategory, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount) {
        int total = goodsService.getGoodsNum(goodsCategory);
        List<Goods> list = goodsService.getPageGoods(goodsCategory, current, rowCount);
        GoodsGrid goodsGrid = new GoodsGrid();
        goodsGrid.setCurrent(current);
        goodsGrid.setRowCount(rowCount);
        goodsGrid.setRows(list);
        goodsGrid.setTotal(total);
        return goodsGrid;
    }

    @RequestMapping(value = "/getGoodsById", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Goods getGoodsById(@RequestParam("goodsId") int goodsId) {
        try {
            return goodsService.getGoodsById(goodsId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加商品
     *
     * @param goodsName
     * @param goodsCount
     * @param goodsDescribe
     * @param goodsCategory
     * @param goodsImg
     * @param lastModifyTime
     * @param lastModifyUser
     * @param originalPrice
     * @param presentPrice
     * @return 0表示成功 1表示失败
     */
    //测试网址http://localhost:8083/goods/addGoodsData?&goodsName=%E7%BA%AF%E7%89%9B%E5%A5%B6&goodsCount=24&goodsDescribe=%E4%BC%8A%E5%88%A9%E7%BA%AF%E7%89%9B%E5%A5%B6&goodsCategory=0&goodsImg=heheda&lastModifyTime=2019-03-18%2020:26&lastModifyUser=kefeng&originalPrice=68&presentPrice=60
    @RequestMapping(value = "/goodsDataAdd", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Code addGoods(@RequestParam("goodsName") String goodsName,
                         @RequestParam("goodsCount") int goodsCount,
                         @RequestParam("goodsDescribe") String goodsDescribe,
                         @RequestParam("goodsCategory") int goodsCategory,
                         @RequestParam("goodsImg") Object goodsImg,
                         @RequestParam("lastModifyTime") String lastModifyTime,
                         @RequestParam("lastModifyUser") String lastModifyUser,
                         @RequestParam("originalPrice") int originalPrice,
                         @RequestParam("presentPrice") int presentPrice,
                         HttpServletRequest request,
                         HttpSession session,
                         HttpServletResponse response
    ) {

        //String path = "E:\\IDEA_workspace\\commodity_managerment\\src\\main\\webapp\\upload\\";
        Code code = new Code();
        System.out.println("goodsName" + goodsName);
        try {
            Goods goods = new Goods();
            goods.setGoodsName(goodsName);
            goods.setGoodsCount(goodsCount);
            goods.setGoodsDescribe(goodsDescribe);
            goods.setGoodsCategory(goodsCategory);
            goods.setGoodsImg(goodsImg);
            goods.setLastModifyTime(lastModifyTime);
            goods.setLastModifyUser(lastModifyUser);
            goods.setOriginalPrice(originalPrice);
            goods.setPresentPrice(presentPrice);
            goodsService.addGoods(goods);
            code.setCode("0");
            return code;

        } catch (Exception e) {
            e.printStackTrace();
            code.setCode("1");
            return code;
        }
    }

    /**
     * 文件输出流方法
     */
    public void fileOutputStream() {
        File file = new File(".");
        FileOutputStream out = null;
        try {
            if (!file.exists()) {
                // 先得到文件的上级目录，并创建上级目录，在创建文件
                file.getParentFile().mkdir();
                file.createNewFile();
            }

            //创建文件输出流
            out = new FileOutputStream(file);
            //将字符串转化为字节
            byte[] byteArr = "FileInputStream Test".getBytes();
            out.write(byteArr);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除指定id的商品
     *
     * @param goodsId 商品id
     * @return 0表示删除成功 1 表示删除失败
     */
    @RequestMapping(value = "/delGoods", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Code delGoods(@RequestParam("goodsId") int goodsId) {

        Code code = new Code();
        try {
            goodsService.delGoods(goodsId);
            code.setCode("0");
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        code.setCode("1");
        return code;
    }


    /**
     * 更新商品信息
     *
     * @param goodsId
     * @param goodsName
     * @param goodsCount
     * @param goodsDescribe
     * @param goodsCategory
     * @param goodsImg
     * @param lastModifyTime
     * @param lastModifyUser
     * @param originalPrice
     * @param presentPrice
     * @return 0更新成功 1更新失败
     */
    //测试网址http://localhost:8083/goods/updateGoods?goodsId=1&goodsName=%E7%BA%AF%E7%89%9B%E5%A5%B6&goodsCount=24&goodsDescribe=%E4%BC%8A%E5%88%A9%E7%BA%AF%E7%89%9B%E5%A5%B6&goodsCategory=0&goodsImg=heheda&lastModifyTime=2019-03-18%2020:26&lastModifyUser=kefeng&originalPrice=68&presentPrice=60
    @RequestMapping(value = "/updateGoods", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Code updateGoods(
            @RequestParam("goodsId") int goodsId,
            @RequestParam("goodsName") String goodsName,
            @RequestParam("goodsCount") int goodsCount,
            @RequestParam("goodsDescribe") String goodsDescribe,
            @RequestParam("goodsCategory") int goodsCategory,
            @RequestParam("goodsImg") Object goodsImg,
            @RequestParam("lastModifyTime") String lastModifyTime,
            @RequestParam("lastModifyUser") String lastModifyUser,
            @RequestParam("originalPrice") int originalPrice,
            @RequestParam("presentPrice") int presentPrice) {

        Goods goods1 = new Goods();
        goods1.setGoodsId(goodsId);
        goods1.setGoodsName(goodsName);
        goods1.setGoodsCount(goodsCount);
        goods1.setGoodsDescribe(goodsDescribe);
        goods1.setGoodsCategory(goodsCategory);
        if(goodsImg!=null && !goodsImg.toString().isEmpty())
        {
            goods1.setGoodsImg(goodsImg);
            System.out.println("传入的商品图片数据不为空");

        }else{
            System.out.println("传入的商品图片数据为空");
        }
        goods1.setLastModifyTime(lastModifyTime);
        goods1.setLastModifyUser(lastModifyUser);
        goods1.setOriginalPrice(originalPrice);
        goods1.setPresentPrice(presentPrice);

        Code code = new Code();

        try {
            goodsService.updateGoods(goods1);
            code.setCode("0");
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        code.setCode("1");
        return code;
    }

}
