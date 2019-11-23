package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
     * 库存列表
     * @return
     */
    public Object list(String name, String merchandiseSn, Integer shopId,
                       Integer page, Integer limit, String sort, String order) {
        if(shopId!=null){
            //查询门店库存
            return ResponseUtil.okList(shopMerchandiseService.querySelective(name, merchandiseSn, shopId,
                    page, limit, sort, order));
        }else{
            //查询所有商品
            return ResponseUtil.okList(merchandiseService.querySelective(name, merchandiseSn, page, limit, sort, order));
        }
    }

    /**
     * 出库入库列表
     * @return
     */
    public Object goodsProductRecordList(Integer merchandiseId, String merchandiseName, String orderSn, Integer shopId, Integer page,
                                         Integer limit, String sort, String order) {
        return ResponseUtil.okList(merchandiseLogService.querySelective(merchandiseId, merchandiseName, orderSn, shopId, page, limit, sort, order));
    }

}
