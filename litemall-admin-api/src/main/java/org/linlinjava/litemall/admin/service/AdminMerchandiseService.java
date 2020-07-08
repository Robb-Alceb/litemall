package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.dto.MerchandiseAllinone;
import org.linlinjava.litemall.admin.beans.enums.PromptEnum;
import org.linlinjava.litemall.admin.beans.vo.MerchandiseVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallMerchandise;
import org.linlinjava.litemall.db.domain.LitemallShopMerchandise;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminMerchandiseService {
    private final Log logger = LogFactory.getLog(AdminMerchandiseService.class);

    @Autowired
    private LitemallMerchandiseService merchandiseService;
    @Autowired
    private LitemallShopMerchandiseService shopMerchandiseService;
    @Autowired
    private LitemallMerchandiseLogService merchandiseLogService;

    /**
     * 货品列表
     * @return
     */
    public Object list(String name, String merchandiseSn, Integer shopId,
                       Integer page, Integer limit, String sort, String order) {
        List<LitemallMerchandise> litemallMerchandises = merchandiseService.querySelective(name, merchandiseSn, page, limit, sort, order);
        if(shopId!=null){
            List<LitemallShopMerchandise> litemallShopMerchandises = shopMerchandiseService.queryByMerIds(shopId, litemallMerchandises.stream().map(LitemallMerchandise::getId).collect(Collectors.toList()));
            //查询门店库存,货品信息来自总部，价格和数量来自自己
            List<LitemallMerchandise> collect = litemallMerchandises.stream().map(merchandise -> {
                LitemallShopMerchandise shopMerchandise = shopMerchandiseService.queryByMerId(merchandise.getId(), shopId);
                if(shopMerchandise != null){
                    merchandise.setNumber(shopMerchandise.getNumber());
                    merchandise.setSellingPrice(shopMerchandise.getSellPrice());
                }else{
                    merchandise.setNumber(0);
                }
                return merchandise;
            }).collect(Collectors.toList());
            return ResponseUtil.okList(collect);
        }else{
            return ResponseUtil.okList(litemallMerchandises);
        }
    }

    /**
     * 添加货品
     */
    public Object create(LitemallMerchandise litemallMerchandise){

        if(StringUtils.isEmpty(litemallMerchandise.getMerchandiseSn()) && StringUtils.isEmpty(litemallMerchandise.getName())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }
        LitemallAdmin admin = getLitemallAdmin();
        litemallMerchandise.setAddUserId(admin.getId());
        litemallMerchandise.setUpdateUserId(admin.getId());
        merchandiseService.create(litemallMerchandise);
        return ResponseUtil.ok();
    }

    /**
     * 修改货品
     */
    public Object update(MerchandiseAllinone merchandiseAllinone, Integer shopId){

        Integer userId = getLitemallAdmin().getId();
        //修改门店货品
        if(!ObjectUtils.isEmpty(shopId)){
            LitemallShopMerchandise litemallShopMerchandise = merchandiseAllinone.getLitemallShopMerchandise();
            litemallShopMerchandise.setUpdateUserId(userId);
            shopMerchandiseService.updateById(litemallShopMerchandise);
            return ResponseUtil.ok();
        }

        LitemallMerchandise litemallMerchandise = merchandiseAllinone.getLitemallMerchandise();
        if(ObjectUtils.isEmpty(litemallMerchandise.getId())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }
        litemallMerchandise.setUpdateUserId(userId);
        merchandiseService.updateById(litemallMerchandise);
        return ResponseUtil.ok();
    }


    /**
     * 获取货品详情
     */
    public Object read(Integer id, Integer shopId){
        if(null != shopId){
            return ResponseUtil.ok(shopMerchandiseService.queryById(id, shopId));
        }
        return ResponseUtil.ok(merchandiseService.findById(id));
    }

    /**
     * 删除货品
     */
    public Object delete(Integer id, Integer shopId){

        if(ObjectUtils.isEmpty(id)){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }
        //删除门店货品
        if(!ObjectUtils.isEmpty(shopId)){
            shopMerchandiseService.deleteById(id);
            return ResponseUtil.ok();
        }
        LitemallMerchandise litemallMerchandise = new LitemallMerchandise();
        litemallMerchandise.setId(id);
        litemallMerchandise.setUpdateUserId(getLitemallAdmin().getId());
        merchandiseService.deleteById(litemallMerchandise);
        return ResponseUtil.ok();
    }

    /**
     * 出库入库列表
     * @return
     */
    public Object goodsProductRecordList(Integer merchandiseId, String merchandiseName, String orderSn, Integer shopId, Integer page,
                                         Integer limit, String sort, String order) {
        return ResponseUtil.okList(merchandiseLogService.querySelective(merchandiseId, merchandiseName, orderSn, shopId, page, limit, sort, order));
    }

    /**
     * 货品列表
     * @return
     */
    public Object all() {

        //查询所有货品
        return ResponseUtil.ok(merchandiseService.all());

    }


    /**
     * TODO 此处没有使用乐观锁判断数据更新，可能存在库存问题
     *
     * @param vo
     * @return
     */
    @Transactional
    public Object addNumber(MerchandiseVo vo) {
        LitemallMerchandise litemallMerchandise = merchandiseService.findById(vo.getId());

        LitemallMerchandise updateData = new LitemallMerchandise();
        updateData.setId(vo.getId());
        updateData.setNumber(vo.getNumber() + litemallMerchandise.getNumber());
        litemallMerchandise.setUpdateUserId(getLitemallAdmin().getId());
        merchandiseService.updateById(updateData);
        return ResponseUtil.ok();
    }

    /**
     * 查询门店库存数量
     *
     * @param shopId
     * @param merchandiseSn
     * @return
     */
    public Object count(Integer shopId, String merchandiseSn) {
        LitemallMerchandise mer = merchandiseService.queryBySn(merchandiseSn);
        if(mer != null){
            LitemallShopMerchandise litemallShopMerchandise = shopMerchandiseService.queryByMerId(mer.getId(), shopId);
            if(null != litemallShopMerchandise && null != litemallShopMerchandise.getNumber()){
                return ResponseUtil.ok(litemallShopMerchandise.getNumber());
            }
        }
        return ResponseUtil.ok(0);
    }

    /**
     * 查询门店库存数量
     *
     * @param shopId
     * @param merchandiseId
     * @return
     */
    public Object count(Integer shopId, Integer merchandiseId) {
        LitemallShopMerchandise shopMerchandise = shopMerchandiseService.queryByMerId(merchandiseId, shopId);
        if(shopMerchandise != null){
            return ResponseUtil.ok(shopMerchandise.getNumber());
        }else{
            return ResponseUtil.ok(0);
        }
    }

    private LitemallAdmin getLitemallAdmin() {
        return (LitemallAdmin) SecurityUtils.getSubject().getPrincipal();
    }


    public Object updatePrice(MerchandiseVo vo, Integer shopId) {
        LitemallShopMerchandise shopMerchandise = shopMerchandiseService.queryByMerId(vo.getId(), shopId);
        if(shopMerchandise != null){
            LitemallShopMerchandise update = new LitemallShopMerchandise();
            update.setId(shopMerchandise.getId());
            update.setSellPrice(vo.getSellingPrice());
        }
        return null;
    }
}
