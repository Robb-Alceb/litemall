package org.linlinjava.litemall.admin.service;

import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.dto.GoodsReviewDto;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallGoodsLog;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


/**
 * @author ：stephen
 * @date ：Created in 11/15/2019 10:28 AM
 * @description：商品审批服务
 */
@Service
public class GoodsReviewService {
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallGoodsLogService goodsLogService;

    /**
     * 审核通过
     * @param goodsReviewDto
     * @return
     */
    public Object approve(GoodsReviewDto goodsReviewDto){
        LitemallGoods goods = new LitemallGoods();
        goods.setId(goodsReviewDto.getId());
        goods.setReviewType(Constants.GOODS_REVIEW_APPROVE.byteValue());
        saveLog(goodsReviewDto,Constants.GOODS_REVIEW_APPROVE);
        return goodsService.updateById(goods) == 1 ? ResponseUtil.ok() : ResponseUtil.updatedDataFailed();
    }

    /**
     * 审核不通过
     * @param goodsReviewDto
     * @return
     */
    public Object reject(GoodsReviewDto goodsReviewDto){
        LitemallGoods goods = new LitemallGoods();
        goods.setId(goodsReviewDto.getId());
        goods.setReviewType(Constants.GOODS_REVIEW_REJECT.byteValue());
        saveLog(goodsReviewDto,Constants.GOODS_REVIEW_REJECT);
        return goodsService.updateById(goods) == 1 ? ResponseUtil.ok() : ResponseUtil.updatedDataFailed();
    }

    /**
     * 保存审核日志
     * @param goodsReviewDto
     * @param type
     */
    public void saveLog(GoodsReviewDto goodsReviewDto, Integer type){
        LitemallGoodsLog log = new LitemallGoodsLog();
        log.setGoodsId(goodsReviewDto.getId());
        log.setGoodsSn(goodsReviewDto.getGoodsSn());
        log.setLogType(Constants.GOODS_LOG_REVIEW.byteValue());
        LitemallAdmin admin = (LitemallAdmin)SecurityUtils.getSubject().getPrincipal();
        log.setAddUserId(admin.getId());
        log.setUserName(admin.getUsername());
        log.setContent(goodsReviewDto.getContent());
        log.setGoodsName(goodsReviewDto.getGoodsName());
        String content = StringUtils.isEmpty(goodsReviewDto.getContent())?"":goodsReviewDto.getContent();
        if(!ObjectUtils.isEmpty(type)){
            if(type == Constants.GOODS_REVIEW_APPROVE){
                log.setContent(Constants.LogMessage.GOODS_REVIEW_APPROVE_LOG.getValue() + content);
            }else if(type == Constants.GOODS_REVIEW_REJECT){
                log.setContent(Constants.LogMessage.GOODS_REVIEW_REJECT_LOG.getValue() + content);
            }
        }
        goodsLogService.add(log);
    }

}
