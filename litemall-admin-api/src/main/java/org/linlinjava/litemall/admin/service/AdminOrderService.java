package org.linlinjava.litemall.admin.service;

import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.enums.OrderStatusEnum;
import org.linlinjava.litemall.admin.beans.enums.PromptEnum;
import org.linlinjava.litemall.admin.beans.pojo.convert.BeanConvert;
import org.linlinjava.litemall.admin.beans.vo.OrderDetailVo;
import org.linlinjava.litemall.admin.beans.vo.OrderGoodsVo;
import org.linlinjava.litemall.admin.beans.vo.OrderVo;
import org.linlinjava.litemall.core.notify.NoticeHelper;
import org.linlinjava.litemall.core.payment.paypal.service.impl.GoodsPaypalServiceImpl;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.*;

@Service
public class AdminOrderService {
    private final Log logger = LogFactory.getLog(AdminOrderService.class);

    @Autowired
    private LitemallOrderGoodsService orderGoodsService;
    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallCommentService commentService;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private GoodsPaypalServiceImpl paypalService;
    @Autowired
    private LitemallCategoryService categoryService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallBrowseRecordService browseRecordService;
    @Autowired
    private LitemallOrderRecordService orderRecordService;
    @Autowired
    private LitemallSystemConfigService systemConfigService;
    @Autowired
    private NoticeHelper noticeHelper;
    @Autowired
    private LitemallGiftCardUserService litemallGiftCardUserService;
    @Autowired
    private LitemallGiftCardUserLogService litemallGiftCardUserLogService;
    @Autowired
    private LitemallRechargeConsumptionService litemallRechargeConsumptionService;

    /**
     * 订单列表
     * @return
     */
    public Object list(String userName, Integer id,Integer userId, String orderSn, List<Short> orderStatusArray, Integer shopId,
                       Integer page, Integer limit, String sort, String order) {
        List<LitemallUser> users = userService.findByName(userName);
        List<LitemallOrder> litemallOrders = null;
        if(users.size() > 0){
            List<Integer> userIds = users.stream().map(LitemallUser::getId).collect(Collectors.toList());
            litemallOrders = orderService.querySelective(id, userIds, orderSn, orderStatusArray, shopId, page, limit,
                    sort, order);
        }else{
            litemallOrders = orderService.querySelective(id, userId, orderSn, orderStatusArray, shopId, page, limit,
                    sort, order);
        }

        List<OrderVo> collect = litemallOrders.stream().map(o -> {
            return BeanConvert.toOrderVo(o, userService.findById(o.getUserId()));
        }).collect(Collectors.toList());
        return ResponseUtil.okList(collect, litemallOrders);
    }


    /**
     * 详情
     * @param id
     * @return
     */
    public Object detail(Integer id) {
        if(null == id){
            return ResponseUtil.badArgument();
        }
        LitemallOrder order = orderService.findById(id);
        Integer userId = order.getUserId();
        UserVo user = userService.findUserVoById(userId);
        List<LitemallOrderGoods> orderGoods = orderGoodsService.queryByOid(order.getId());
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setOrderGoods(orderGoods);
        vo.setUser(user);
        return ResponseUtil.ok(vo);
    }

    /**
     * 订单退款
     * <p>
     * 1. 检测当前订单是否能够退款;
     * 2. 微信退款操作;
     * 3. 设置订单退款确认状态；
     * 4. 订单商品库存回库。
     * <p>
     * TODO
     * 虽然接入了微信退款API，但是从安全角度考虑，建议开发者删除这里微信退款代码，采用以下两步走步骤：
     * 1. 管理员登录微信官方支付平台点击退款操作进行退款
     * 2. 管理员登录litemall管理后台点击退款操作进行订单状态修改和商品库存回库
     *
     * @param orderId 订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    @Transactional
    public Object refund(Integer orderId, Integer shopId) {
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }
/*        if (StringUtils.isEmpty(refundMoney)) {
            return ResponseUtil.badArgument();
        }*/

        LitemallOrder order = orderService.findById(orderId);
        if(null != shopId && order.getShopId() != shopId){
            return ResponseUtil.badArgument();
        }
        if (order == null) {
            return ResponseUtil.badArgument();
        }

/*        if (order.getActualPrice().compareTo(new BigDecimal(refundMoney)) != 0) {
            return ResponseUtil.badArgumentValue();
        }*/

        // 如果订单不是退款状态，则不能退款
/*        if (!order.getOrderStatus().equals(OrderUtil.STATUS_REFUND)) {
            return ResponseUtil.fail(ORDER_CONFIRM_NOT_ALLOWED, "订单不能退款");
        }*/

/*        // 微信退款
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutTradeNo(order.getOrderSn());
        wxPayRefundRequest.setOutRefundNo("refund_" + order.getOrderSn());
        // 元转成分
        Integer totalFee = order.getActualPrice().multiply(new BigDecimal(100)).intValue();
        wxPayRefundRequest.setTotalFee(totalFee);
        wxPayRefundRequest.setRefundFee(totalFee);*/

        if(order.getPayType() == Constants.PAY_TYPE_PAYPAL.byteValue()){
            if(!paypalService.refund(order.getId())){
                return ResponseUtil.fail(ORDER_REFUND_FAILED, "订单退款失败");
            };
        }else if(order.getPayType() == Constants.PAY_TYPE_BALANCE.byteValue()){
            if(!balanceRefund(order.getId())){
                return ResponseUtil.fail(ORDER_REFUND_FAILED, "订单退款失败");
            }
        }else if(order.getPayType() == Constants.PAY_TYPE_GIFT_CARD.byteValue()){
            if(!giftCardRefund(order.getId())){
                return ResponseUtil.fail(ORDER_REFUND_FAILED, "订单退款失败");
            }
        }

        // 设置订单取消状态
        order.setOrderStatus(OrderUtil.STATUS_REFUND_CONFIRM);
        if (orderService.updateWithOptimisticLocker(order) == 0) {
            throw new RuntimeException("更新数据已失效");
        }

        // 商品货品数量增加
        List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
        for (LitemallOrderGoods orderGoods : orderGoodsList) {
            Integer productId = orderGoods.getProductId();
            Short number = orderGoods.getNumber();
            if (productService.addStock(productId, number) == 0) {
                throw new RuntimeException("商品货品库存增加失败");
            }
        }

        //TODO 发送邮件和短信通知，这里采用异步发送
        // 退款成功通知用户, 例如“您申请的订单退款 [ 单号:{1} ] 已成功，请耐心等待到账。”
        // 注意订单号只发后6位
//        notifyService.notifySmsTemplate(order.getMobile(), NotifyType.REFUND,
//                new String[]{order.getOrderSn().substring(8, 14)});

//        awsNotifyService.sendSms(order.getMobile(), "您申请的订单退款 [" +order.getOrderSn()+ "] 已成功，请耐心等待到账。", org.linlinjava.litemall.db.beans.Constants.AWS_MESSAGE_TYPE_PROMOTIONAL);

        logHelper.logOrderSucceed("退款", "订单编号 " + orderId);
        //消息推送和保存
        noticeHelper.noticeUser(Constants.MSG_TYPE_ORDER,"退款", "订单编号 "+order.getOrderSn()+"退款", order.getUserId());
        return ResponseUtil.ok();
    }

    /**
     * 发货
     * 1. 检测当前订单是否能够发货
     * 2. 设置订单发货状态
     *
     * @param body 订单信息，{ orderId：xxx, shipSn: xxx, shipChannel: xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    public Object ship(String body) {
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        String shipSn = JacksonUtil.parseString(body, "shipSn");
        String shipChannel = JacksonUtil.parseString(body, "shipChannel");
        if (orderId == null || shipSn == null || shipChannel == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }

        // 如果订单不是已付款状态，则不能发货
        if (!order.getOrderStatus().equals(OrderUtil.STATUS_PAY)) {
            return ResponseUtil.fail(ORDER_CONFIRM_NOT_ALLOWED, "订单不能确认收货");
        }

        order.setOrderStatus(OrderUtil.STATUS_SHIP);
        order.setShipSn(shipSn);
        order.setShipChannel(shipChannel);
        order.setShipTime(LocalDateTime.now());
        if (orderService.updateWithOptimisticLocker(order) == 0) {
            return ResponseUtil.updatedDateExpired();
        }

        //TODO 发送邮件和短信通知，这里采用异步发送
        // 发货会发送通知短信给用户:          *
        // "您的订单已经发货，快递公司 {1}，快递单 {2} ，请注意查收"
//        notifyService.notifySmsTemplate(order.getMobile(), NotifyType.SHIP, new String[]{shipChannel, shipSn});
//        notifyService.notifyMailTemplate("发货", NotifyType.SHIP, order.get);

        logHelper.logOrderSucceed("发货", "订单编号 " + orderId);
        //消息推送和保存
        noticeHelper.noticeUser(Constants.MSG_TYPE_ORDER, "订单编号 "+order.getOrderSn()+"发货", order.getUserId());
        return ResponseUtil.ok();
    }


    /**
     * 回复订单商品
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    public Object reply(String body) {
        Integer commentId = JacksonUtil.parseInteger(body, "commentId");
        if (commentId == null || commentId == 0) {
            return ResponseUtil.badArgument();
        }
        // 目前只支持回复一次
/*        if (commentService.findById(commentId) != null) {
            return ResponseUtil.fail(ORDER_REPLY_EXIST, "订单商品已回复！");
        }*/
        String content = JacksonUtil.parseString(body, "content");
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        // 创建评价回复
        LitemallComment comment = new LitemallComment();
        comment.setType((byte) 2);
        comment.setValueId(commentId);
        comment.setContent(content);
        comment.setUserId(0);                 // 评价回复没有用
        comment.setStar((short) 0);           // 评价回复没有用
        comment.setHasPicture(false);        // 评价回复没有用
        comment.setPicUrls(new String[]{});  // 评价回复没有用
        commentService.save(comment);

        return ResponseUtil.ok();
    }

    /**
     * 备注订单商品
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    public Object remark(String body, Integer shopId) {
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null || orderId == 0) {
            return ResponseUtil.badArgument();
        }
        // 目前只支持回复一次
        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.fail(ORDER_NOT_EXIST, "订单不存在！");
        }
/*        if(shopId != null){
            if(!shopId.equals(order.getShopId())){
                return ResponseUtil.fail(ORDER_NOT_PERMISSION, "无权处理该订单！");
            }
        }*/
        String remark = JacksonUtil.parseString(body, "remark");
        if (StringUtils.isEmpty(remark)) {
            return ResponseUtil.badArgument();
        }
        // 创建备注
        LitemallAdmin admin = (LitemallAdmin)SecurityUtils.getSubject().getPrincipal();
        LitemallOrderRecord record = new LitemallOrderRecord();
        record.setOrderId(order.getId());
        record.setAddUserId(admin.getId());
        record.setOrderStatus(order.getOrderStatus());
        record.setPayStatus(order.getPayType());
        record.setRemark(remark);
        record.setShipStatus(order.getShipStatus());
        record.setUserId(admin.getId());
        record.setUserName(admin.getUsername());
        orderRecordService.add(record);


        return ResponseUtil.ok();
    }
    /**
     * 商品统计
     * @return
     */
    public Object goodsStatistics(String startTime, String endTime, Integer shopId){
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTimes = LocalDateTime.parse(startTime, timeDtf);
        LocalDateTime endTimes = LocalDateTime.parse(endTime, timeDtf);
        //订单数据
        LitemallOrder litemallOrder = orderService.queryGoodsStatistics(startTimes, endTimes, shopId);
        if(ObjectUtils.isEmpty(litemallOrder)){
            return ResponseUtil.ok(null);
        }
        Map<String, Object> map = Maps.newHashMap();
        //商品订单统计
        List<LitemallOrderGoods> orderGoods = getOrderGoods(orderGoodsService.queryGoodsStatistics(startTimes, endTimes, shopId, null));
        //商品统计
        map.put("orderGoods", orderGoods);
        //类目统计
        map.put("categorys", getCategory(orderGoods));
        return ResponseUtil.ok(map);
    }

    /**
     * 商品销售统计
     * @return
     */
    public Object goodsSalesStatistics(Integer shopId, String type, String startTime,  String endTime, Integer page,
                                       Integer limit, String sort, String order){
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTimes = LocalDateTime.parse(startTime, timeDtf);
        LocalDateTime endTimes = LocalDateTime.parse(endTime, timeDtf);

        List<Map<String, Object>> maps;
        if(type.equals(Constants.GOODS_TYPE)){
            maps = getGoodsInfo(shopId, page, limit, sort, order, startTimes, endTimes);
        }else{
            maps = getCategoryInfo(shopId, page, limit, sort, order, startTimes, endTimes);
        }

        return ResponseUtil.okList(maps);
    }

    /**
     * 销售统计
     * @param startTime
     * @param endTime
     * @return
     */
    public Object salesStatistics(String startTime,  String endTime){

        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTimes = LocalDateTime.parse(startTime, timeDtf);
        LocalDateTime endTimes = LocalDateTime.parse(endTime, timeDtf);

        Map<String, Object> map = Maps.newHashMap();
        //订单查询
        List<LitemallOrder> litemallOrder = orderService.queryGoodsStatistics(startTimes, endTimes);
        if(CollectionUtils.isEmpty(litemallOrder)){
            return ResponseUtil.fail(PromptEnum.P_102.getCode(), PromptEnum.P_102.getDesc());
        }
        //浏览人数
        List<LitemallBrowseRecord> browseRecords = browseRecordService.queryBrowseUserCount(startTimes, endTimes);
        map.put("browseUserNum", !CollectionUtils.isEmpty(browseRecords)?browseRecords.size():0);
        //过滤 取消和自动取消 退款和自动退款状态
        List<LitemallOrder> ordreList = litemallOrder.stream().filter(order -> !(Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_102.getCode() || Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_103.getCode() || Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_202.getCode() || Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_203.getCode())).collect(Collectors.toList());

        //下单人数
        Map<Integer, List<LitemallOrder>>collect = litemallOrder.stream().collect(Collectors.groupingBy(LitemallOrder::getUserId));
        map.put("userOrderNum", !CollectionUtils.isEmpty(collect)?collect.size():0);
        //订单数
        map.put("orderNum", !CollectionUtils.isEmpty(litemallOrder)?litemallOrder.size():0);
        //下单件数
        map.put("orderGoodsNum", orderService.queryOrderGoodsNum(Constants.ORDER_GOODS_NUM));
        //有效订单数
        map.put("validOrderNum", !CollectionUtils.isEmpty(ordreList)?ordreList.size():0);
        //下单金额
        BigDecimal b = new BigDecimal(litemallOrder.stream().mapToDouble(order -> Double.valueOf(order.getOrderPrice().toString())).sum());
        map.put("orderPrice", b.setScale(2, BigDecimal.ROUND_HALF_UP));
        //退款金额
        List<LitemallOrder> refundList = litemallOrder.stream().filter(order -> Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_202.getCode() || Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_203.getCode()).collect(Collectors.toList());
        map.put("refundPrice", !CollectionUtils.isEmpty(refundList)?refundList.stream().mapToDouble(refund-> Double.valueOf(refund.getActualPrice().toString())).sum():0);
        //付款人数 过滤下单和取消订单状态 再人员分组
        List<LitemallOrder> collect1 = litemallOrder.stream().filter(order -> !(Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_101.getCode() || Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_102.getCode() || Integer.valueOf(order.getOrderStatus()) == OrderStatusEnum.P_103.getCode())).collect(Collectors.toList());
        Map<Integer, List<LitemallOrder>> collect2 = collect1.stream().collect(Collectors.groupingBy(LitemallOrder::getUserId));
        map.put("payUserNum", !CollectionUtils.isEmpty(collect2)?collect2.size():0);
        //付款订单数
        map.put("payOrderNum", !CollectionUtils.isEmpty(collect1)?collect1.size():0);
        //付款件数
        map.put("PayOrderGoodsNum", orderService.queryOrderGoodsNum(Constants.PAY_ORDER_GOODS_NUM));
        //付款金额
        BigDecimal bd = new BigDecimal(collect1.stream().mapToDouble(cc->Double.valueOf(cc.getActualPrice().toString())).sum());
        map.put("payPrice", bd.setScale(2, BigDecimal.ROUND_HALF_UP));

        return ResponseUtil.ok(map);
    }

    /**
     * 交易数据
     * @param startTime
     * @param endTime
     * @return
     */
    public Object transactionData(String startTime,  String endTime){
        if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTimes = LocalDateTime.parse(startTime, timeDtf);
        LocalDateTime endTimes = LocalDateTime.parse(endTime, timeDtf);

        //订单查询
        List<LitemallOrder> litemallOrder = orderService.queryGoodsStatistics(startTimes, endTimes);
        if(CollectionUtils.isEmpty(litemallOrder)){
            return ResponseUtil.fail(PromptEnum.P_102.getCode(), PromptEnum.P_102.getDesc());
        }
        //获取配置的金额范围
        Map<String, String> data = systemConfigService.listAmount();
        List<String> amounts = new ArrayList<>();
        if(null != data){
            String str = data.get(SystemConfig.LITEMALL_STATISTICS_AMOUNT);
            if(!StringUtils.isEmpty(str)){
                amounts = new ArrayList<>(Arrays.asList(str.split(";")));
            }else{
                //默认配置
                amounts = new ArrayList<>(Arrays.asList("0-50;50-100;100-200;200-500;500-1000;1000-5000;5000-10000".split(";")));
            }
        }
        LinkedHashMap<String, Object> map = Maps.newLinkedHashMap();
        for(String amountRange : amounts){
            String[] amountArr = amountRange.split("-");
            if(amountArr.length != 2){
                continue;
            }
            try{
                map.put(amountRange, getActualPrice(litemallOrder,Integer.valueOf(amountArr[0]), Integer.valueOf(amountArr[1])));
            }catch (Exception e){
                return ResponseUtil.fail();
            }
        }

        //0~50
/*
        map.put("fifty", litemallOrder.stream().filter(order -> order.getActualPrice().compareTo(new BigDecimal(50)) <= 0).collect(Collectors.toList()).size());
        //51~100
        map.put("hundred", getActualPrice(litemallOrder,50, 100));
        //101~200
        map.put("twoHundred", getActualPrice(litemallOrder,100, 200));
        //201~500
        map.put("fiveHundred", getActualPrice(litemallOrder,200, 500));
        //501~1000
        map.put("thousand", getActualPrice(litemallOrder,500, 1000));
        //1001~5000
        map.put("fiveThousand", getActualPrice(litemallOrder,1000, 5000));
        //5001~10000
        map.put("tenThousand", getActualPrice(litemallOrder,5000, 10000));
        //10001
        map.put("greaterThanTenThousand", litemallOrder.stream().filter(order -> order.getActualPrice().compareTo(new BigDecimal(10000)) > 0).collect(Collectors.toList()).size());
*/

        return ResponseUtil.ok(map);
    }


    /**
     * 根据订单ID 查询订单是否完成 或者已评价 1：已完成 2：已评价 3：未完成
     * @param orderGoodsId
     * @return
     */
    public Object queryOrderIsCompletionById(Integer orderGoodsId){

        LitemallOrderGoods orderGoods = orderGoodsService.findById(orderGoodsId);
        if(commentService.findById(orderGoods.getComment()) != null){
            return ResponseUtil.ok(2);
        }
        LitemallOrder order = orderService.findById(orderGoods.getOrderId());
        if(order.getOrderStatus().toString().equals(OrderStatusEnum.P_401) || order.getOrderStatus().toString().equals(OrderStatusEnum.P_402)){
            return ResponseUtil.ok(1);
        }
        return ResponseUtil.ok(3);
    }
    private int getActualPrice(List<LitemallOrder> litemallOrder, int start, int end) {
        return litemallOrder.stream().filter(order -> (order.getActualPrice().compareTo(new BigDecimal(end)) <= 0 && order.getActualPrice().compareTo(new BigDecimal(start)) > 0)).collect(Collectors.toList()).size();
    }

    private List<Map<String, Object>> getCategoryInfo(Integer shopId, Integer page, Integer limit, String sort, String order, LocalDateTime startTimes, LocalDateTime endTimes) {
        //时间段内商品类目 销售总额 goodsId, goodsName, salesNum(销售件数), actualPrice(金额)
        List<Map<String, Object>> maps;
        maps = orderService.queryGoodsCategorySales(startTimes, endTimes, page, limit);
        if(!CollectionUtils.isEmpty(maps)){
            maps.stream().forEach(map -> {
                Integer cId = (Integer)map.get("categoryId");
                //查询商品浏览量
                List<LitemallBrowseRecord> litemallBrowseRecords = browseRecordService.querySelective(null, cId, startTimes, endTimes, page, limit, sort, order);
                if(!CollectionUtils.isEmpty(litemallBrowseRecords)){
                    //浏览人数
                    map.put("browseUserNum", litemallBrowseRecords.size());
                    //商品类目浏览量
                    Integer browseNum = litemallBrowseRecords.stream().collect(Collectors.summingInt(LitemallBrowseRecord::getBrowseNumber));
                    map.put("browseNum", browseNum);
                    //付款人数
                    //根据商品类目ID查询订单
                    List<LitemallOrderGoods> litemallOrderGoods = orderGoodsService.queryGoodsSalesStatistics(null, cId, startTimes, endTimes, page, limit, sort, order);
                    map.put("payUserNum", !CollectionUtils.isEmpty(litemallOrderGoods) ? litemallOrderGoods.size() : 0);
                    //单品转化率
                    map.put("goodsConversionRate", browseNum/Integer.valueOf(map.get("salesNum").toString()));
                }else{
                    //浏览人数
                    map.put("browseUserNum", 0);
                    //商品浏览量
                    map.put("browseNum", 0);
                    //付款人数
                    map.put("payUserNum", 0);
                    //单品转化率
                    map.put("goodsConversionRate", 0);
                }
            });
        }
        return maps;
    }

    private List<Map<String, Object>> getGoodsInfo(Integer shopId, Integer page, Integer limit, String sort, String order, LocalDateTime startTimes, LocalDateTime endTimes) {
        //时间段内商品销售总额 goodsId, goodsName, salesNum(销售件数), actualPrice(金额)
        List<Map<String, Object>> maps = orderService.queryGoodsSales(shopId, startTimes, endTimes, page, limit);
        if(!CollectionUtils.isEmpty(maps)){
            maps.stream().forEach(map -> {
                Integer gId = (Integer)map.get("goodsId");
                //查询商品浏览量
                List<LitemallBrowseRecord> litemallBrowseRecords = browseRecordService.querySelective(gId, null, startTimes, endTimes, page, limit, sort, order);
                if(!CollectionUtils.isEmpty(litemallBrowseRecords)){
                    //浏览人数
                    map.put("browseUserNum", litemallBrowseRecords.size());
                    //商品浏览量
                    Integer browseNum = litemallBrowseRecords.stream().collect(Collectors.summingInt(LitemallBrowseRecord::getBrowseNumber));
                    map.put("browseNum", browseNum);
                    //付款人数
                    //根据商品ID查询订单
                    List<LitemallOrderGoods> litemallOrderGoods = orderGoodsService.queryGoodsStatistics(startTimes, endTimes, null, gId);
                    map.put("payUserNum", !CollectionUtils.isEmpty(litemallOrderGoods) ? litemallOrderGoods.size() : 0);
                    //单品转化率
                    map.put("goodsConversionRate", browseNum/Integer.valueOf(map.get("salesNum").toString()));
                }else{
                    //浏览人数
                    map.put("browseUserNum", 0);
                    //商品浏览量
                    map.put("browseNum", 0);
                    //付款人数
                    map.put("payUserNum", 0);
                    //单品转化率
                    map.put("goodsConversionRate", 0);
                }
            });
        }
        return maps;
    }


    private List<OrderGoodsVo> getCategory(List<LitemallOrderGoods> orderGoods) {
        List<OrderGoodsVo> orderGoodsVos = new ArrayList<>();
        orderGoods.stream().forEach(og->{
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            LitemallCategory category = categoryService.findById(goodsService.findById(og.getGoodsId()).getCategoryId());
            BeanUtils.copyProperties(og, orderGoodsVo);
            orderGoodsVo.setCategoryId(category.getId());
            orderGoodsVo.setCategoryName(category.getName());
            orderGoodsVo.setNumber(og.getNumber());
            orderGoodsVos.add(orderGoodsVo);
        });

        List<OrderGoodsVo> ordergoodsVolist = new ArrayList<>();
        Map<Integer, List<OrderGoodsVo>> collect = orderGoodsVos.stream().collect(Collectors.groupingBy(OrderGoodsVo::getCategoryId));
        collect.keySet().forEach(key->{
            List<OrderGoodsVo> lrgs = collect.get(key);
            if(lrgs.size()>1){
                OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
                BeanUtils.copyProperties(lrgs.get(0), orderGoodsVo);
                orderGoodsVo.setNumber((short)(lrgs.stream().mapToInt(OrderGoodsVo::getNumber).sum()));
                ordergoodsVolist.add(orderGoodsVo);
            }else{
                ordergoodsVolist.add(lrgs.get(0));
            }
        });
        return ordergoodsVolist;
    }

    /**
     * //商品订单 分组求和
     * @param orderGoodsList
     * @return
     */
    private List<LitemallOrderGoods> getOrderGoods(List<LitemallOrderGoods> orderGoodsList) {
        List<LitemallOrderGoods> orderGoodss = new ArrayList<>();
        Map<Integer, List<LitemallOrderGoods>> collect = orderGoodsList.stream().collect(Collectors.groupingBy(LitemallOrderGoods::getGoodsId));
        collect.keySet().forEach(key->{
            List<LitemallOrderGoods> lrgs = collect.get(key);
            if(lrgs.size()>1){
                LitemallOrderGoods orderGoods = new LitemallOrderGoods();
                BeanUtils.copyProperties(lrgs.get(0), orderGoods);
                int intStream = lrgs.stream().mapToInt(LitemallOrderGoods::getNumber).sum();
                orderGoods.setNumber((short)intStream);
                orderGoodss.add(orderGoods);
            }else{
                orderGoodss.add(lrgs.get(0));
            }
        });
        return orderGoodss;
    }

    /**
     * 退款到余额
     * @param orderId
     * @return
     */
    public boolean balanceRefund(Integer orderId){
        try {
            LitemallOrder order = orderService.findById(orderId);
            Integer userId = order.getUserId();
            LitemallUser user = userService.findById(userId);
            LitemallUser update = new LitemallUser();
            update.setId(userId);
            update.setAvailableAmount(update.getAvailableAmount().add(order.getActualPrice()));
            userService.updateWithOptimisticLocker(update, user.getUpdateTime());

            LitemallRechargeConsumption log = new LitemallRechargeConsumption();
            log.setOrderId(order.getId());
            log.setAmount(order.getActualPrice());
            log.setAddUserId(user.getId());
            log.setUserId(user.getId());
            log.setUsername(user.getUsername());
            log.setMobile(user.getMobile());
            log.setPoints(user.getPoints());
            log.setType(org.linlinjava.litemall.db.beans.Constants.LOG_GIFTCARD_REFUND);
            log.setUserLevel(user.getUserLevel());
            log.setAvailableAmount(user.getAvailableAmount().add(order.getActualPrice()));
            litemallRechargeConsumptionService.add(log);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 退款到礼物卡
     * @param orderId
     * @return
     */
    public boolean giftCardRefund(Integer orderId){
        try {
            LitemallOrder order = orderService.findById(orderId);
            Integer cardId = Integer.valueOf(order.getTransationId());
            LitemallGiftCardUser card = litemallGiftCardUserService.findById(cardId);
            LitemallGiftCardUser update = new LitemallGiftCardUser();
            update.setId(cardId);
            update.setAmount(card.getAmount().add(order.getActualPrice()));
            litemallGiftCardUserService.updateWithOptimisticLocker(update, card.getUpdateTime());

            LitemallGiftCardUserLog log = new LitemallGiftCardUserLog();
            log.setAmount(order.getActualPrice());
            log.setAddUserId(order.getUserId());
            log.setType(org.linlinjava.litemall.db.beans.Constants.LOG_GIFTCARD_REFUND);
            log.setCardNumber(card.getCardNumber());
            log.setContent("退款");
            litemallGiftCardUserLogService.add(log);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改收货人
     * @param body
     * @param shopId
     * @return
     */
    public Object consignee(String body, Integer shopId) {
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        String consignee = JacksonUtil.parseString(body, "consignee");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String address = JacksonUtil.parseString(body, "address");
        if (orderId == null || orderId == 0) {
            return ResponseUtil.badArgument();
        }
        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.fail(ORDER_NOT_EXIST, "订单不存在！");
        }
        LitemallOrder update = new LitemallOrder();
        update.setId(orderId);
        if(!StringUtils.isEmpty(consignee)){
            update.setConsignee(consignee);
        }
        if(!StringUtils.isEmpty(mobile)) {
            update.setMobile(mobile);
        }
        if(!StringUtils.isEmpty(address)) {
            update.setAddress(address);
        }
        orderService.updateById(update);
        return ResponseUtil.ok();
    }
}
