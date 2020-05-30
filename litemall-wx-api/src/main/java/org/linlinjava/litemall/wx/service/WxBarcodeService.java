package org.linlinjava.litemall.wx.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.EncryptUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.service.LitemallGiftCardUserService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.util.WxResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/9 16:19
 * @description：TODO
 */
@Service
public class WxBarcodeService {
    private static final Log log = LogFactory.getLog(WxBarcodeService.class);
    @Value("${zone.offset}")
    private int zoneOffset;
    @Value("${barcode.key}")
    private String barcodeKey;
    @Value("${barcode.delimiter}")
    private String delimiter;

    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private LitemallGiftCardUserService litemallGiftCardUserService;


    /**
     * 生成用户二维码,加密处理，格式为userId+时间戳—+类型
     * @param userId
     * @return
     */
    public Object userBarcodeGenerate(Integer userId){
        if(litemallUserService.findById(userId)== null){
            return ResponseUtil.fail(WxResponseEnum.NOT_FIND_USER);
        };
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.ofHours(zoneOffset)).toEpochMilli();
        Integer type = Constants.BARCODE_PAY_BALANCE;
        StringBuilder builder = new StringBuilder();
        builder.append(userId).append(delimiter).append(milliSecond).append(delimiter).append(type);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("expire",Constants.BARCODE_EXPIRE_SECOND);
        rtn.put("barcode",EncryptUtil.getInstance().DESencode(builder.toString(), barcodeKey));
        return ResponseUtil.ok(rtn);
    }

    /**
     * 生成礼物卡二维码,加密处理，格式为userId+时间戳—+类型+cardId
     * @param userId
     * @return
     */
    public Object cardBarcodeGenerate(Integer userId, Integer cardId){
        if(litemallGiftCardUserService.countByCardId(userId, cardId) == 0){
            return ResponseUtil.fail(WxResponseEnum.IS_NULL_CARD);
        };
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.ofHours(zoneOffset)).toEpochMilli();
        Integer type = Constants.BARCODE_PAY_BALANCE;
        StringBuilder builder = new StringBuilder();
        builder.append(userId).append(delimiter).append(milliSecond).append(delimiter).append(type).append(delimiter).append(cardId);

        Map<String, Object> rtn = new HashMap<>();
        rtn.put("expire",Constants.BARCODE_EXPIRE_SECOND);
        rtn.put("barcode",EncryptUtil.getInstance().DESencode(builder.toString(), barcodeKey));
        return ResponseUtil.ok(rtn);
    }
}
