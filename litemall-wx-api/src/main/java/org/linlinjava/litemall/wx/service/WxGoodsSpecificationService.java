package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;
import org.linlinjava.litemall.db.service.LitemallGoodsSpecificationService;
import org.linlinjava.litemall.wx.vo.GoodsGroupSpecificationVo;
import org.linlinjava.litemall.wx.vo.GoodsSpecificationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/9 17:52
 * @description：TODO
 */
@Service
public class WxGoodsSpecificationService {

    @Autowired
    private LitemallGoodsSpecificationService litemallGoodsSpecificationService;

    /**
     * [
     * {
     * name: '',
     * valueList: [ {}, {}]
     * },
     * {
     * name: '',
     * valueList: [ {}, {}]
     * }
     * ]
     *
     * @param id
     * @return
     */
    public List<GoodsGroupSpecificationVo> getSpecificationVoList(Integer id) {
        List<LitemallGoodsSpecification> goodsSpecificationList = litemallGoodsSpecificationService.queryByGid(id);

        Map<String, GoodsGroupSpecificationVo> map = new HashMap<>();
        List<GoodsGroupSpecificationVo> specificationVoList = new ArrayList<>();

        for (LitemallGoodsSpecification goodsSpecification : goodsSpecificationList) {
            String specification = goodsSpecification.getSpecification();
            GoodsGroupSpecificationVo goodsSpecificationVo = map.get(specification);
            if (goodsSpecificationVo == null) {
                goodsSpecificationVo = new GoodsGroupSpecificationVo();
                goodsSpecificationVo.setName(specification);
                List<GoodsSpecificationVo> valueList = new ArrayList<>();
                GoodsSpecificationVo goodsGroupSpecificationVo = new GoodsSpecificationVo();
                BeanUtils.copyProperties(goodsSpecification, goodsGroupSpecificationVo);
                valueList.add(goodsGroupSpecificationVo);
                goodsSpecificationVo.setValueList(valueList);
                map.put(specification, goodsSpecificationVo);
                specificationVoList.add(goodsSpecificationVo);
            } else {
                List<GoodsSpecificationVo> valueList = goodsSpecificationVo.getValueList();
                GoodsSpecificationVo goodsGroupSpecificationVo = new GoodsSpecificationVo();
                BeanUtils.copyProperties(goodsSpecification, goodsGroupSpecificationVo);
                valueList.add(goodsGroupSpecificationVo);
            }
        }

        return specificationVoList;
    }
}
