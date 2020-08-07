package org.linlinjava.litemall.db.service;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.dao.GoodsProductMapper;
import org.linlinjava.litemall.db.dao.LitemallGoodsProductMapper;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import org.linlinjava.litemall.db.domain.LitemallGoodsProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LitemallGoodsProductService {
    @Resource
    private LitemallGoodsProductMapper litemallGoodsProductMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;

    public List<LitemallGoodsProduct> queryByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return litemallGoodsProductMapper.selectByExample(example);
    }

    public List<LitemallGoodsProduct> queryByGidAndSid(Integer gid, Integer shopId) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        if(shopId != null){
            example.or().andGoodsIdEqualTo(gid).andShopIdEqualTo(shopId).andDeletedEqualTo(false);
        }else{
            example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        }
        return litemallGoodsProductMapper.selectByExample(example);
    }

    public LitemallGoodsProduct findById(Integer id) {
        return litemallGoodsProductMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        litemallGoodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        litemallGoodsProductMapper.insertSelective(goodsProduct);
    }

    public void updateById(LitemallGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        litemallGoodsProductMapper.updateByPrimaryKeySelective(goodsProduct);
    }

    public int updateByGoodsId(LitemallGoodsProduct goodsProduct) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andDeletedEqualTo(false).andGoodsIdEqualTo(goodsProduct.getGoodsId());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        return litemallGoodsProductMapper.updateByExampleSelective(goodsProduct, example);
    }

    public int count() {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) litemallGoodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        litemallGoodsProductMapper.logicalDeleteByExample(example);
    }

    public int addStock(Integer id, Short num){
        return goodsProductMapper.addStock(id, num);
    }

    public int reduceStock(Integer id, Short num){
        return goodsProductMapper.reduceStock(id, num);
    }

    public List<LitemallGoodsProduct> queryWarning() {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        List<LitemallGoodsProduct> litemallGoodsProducts = litemallGoodsProductMapper.selectByExample(example);
        List<LitemallGoodsProduct> collect = litemallGoodsProducts.stream().filter(litemallGoodsProduct -> {
            return litemallGoodsProduct.getEarlyWarningValue() >= litemallGoodsProduct.getNumber();
        }).map(litemallGoodsProduct -> {
            return litemallGoodsProduct;
        }).collect(Collectors.toList());
        return collect;
    }
}