package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.dto.MerchandiseAllinone;
import org.linlinjava.litemall.admin.beans.enums.PromptEnum;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallMerchandise;
import org.linlinjava.litemall.db.domain.LitemallShopMerchandise;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


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
        if(shopId!=null){
            //查询门店库存
            return ResponseUtil.okList(shopMerchandiseService.querySelective(name, merchandiseSn, shopId,
                    page, limit, sort, order));
        }else{
            //查询所有货品
            return ResponseUtil.okList(merchandiseService.querySelective(name, merchandiseSn, page, limit, sort, order));
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
        return ResponseUtil.ok(merchandiseService.queryById(id));
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

    private LitemallAdmin getLitemallAdmin() {
        return (LitemallAdmin) SecurityUtils.getSubject().getPrincipal();
    }
}
