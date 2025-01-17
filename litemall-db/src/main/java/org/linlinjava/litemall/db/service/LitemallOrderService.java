package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallOrderMapper;
import org.linlinjava.litemall.db.dao.OrderMapper;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallOrderExample;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class LitemallOrderService {
    @Resource
    private LitemallOrderMapper litemallOrderMapper;
    @Resource
    private OrderMapper orderMapper;

    public int add(LitemallOrder order) {
        order.setAddTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return litemallOrderMapper.insertSelective(order);
    }

    public int count(Integer userId, List<Short> status) {
        return count(userId, status, false);
    }

    public int count(Integer userId, List<Short> status, Boolean today) {
        LitemallOrderExample example = new LitemallOrderExample();
        if(today != null && today){
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            if(userId != null){
                example.or().andUserIdEqualTo(userId).andOrderStatusIn(status).andUpdateTimeBetween(startTime, endTime).andDeletedEqualTo(false);
            }else{
                example.or().andOrderStatusIn(status).andUpdateTimeBetween(startTime, endTime).andDeletedEqualTo(false);
            }
        }else{
            if(userId != null) {
                example.or().andUserIdEqualTo(userId).andOrderStatusIn(status).andDeletedEqualTo(false);
            }else{
                example.or().andOrderStatusIn(status).andDeletedEqualTo(false);
            }
        }
        return (int) litemallOrderMapper.countByExample(example);
    }

    public int countByShop(Integer shopId, List<Short> status, Boolean today) {
        LitemallOrderExample example = new LitemallOrderExample();
        if(today != null && today){
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            example.or().andShopIdEqualTo(shopId).andOrderStatusIn(status).andUpdateTimeBetween(startTime, endTime).andDeletedEqualTo(false);

        }else{
            example.or().andShopIdEqualTo(shopId).andOrderStatusIn(status).andDeletedEqualTo(false);

        }
        return (int) litemallOrderMapper.countByExample(example);
    }

    public LitemallOrder findById(Integer orderId) {
        return litemallOrderMapper.selectByPrimaryKey(orderId);
    }

    public LitemallOrder findByUserAndId(Integer userId, Integer orderId) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andUserIdEqualTo(userId).andIdEqualTo(orderId).andDeletedEqualTo(false);
        return litemallOrderMapper.selectByPrimaryKey(orderId);
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public int countByOrderSn(Integer userId, String orderSn) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return (int) litemallOrderMapper.countByExample(example);
    }

    // TODO 这里应该产生一个唯一的订单，但是实际上这里仍然存在两个订单相同的可能性
    public String generateOrderSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        while (countByOrderSn(userId, orderSn) != 0) {
            orderSn = now + getRandomNum(6);
        }
        return orderSn;
    }


    public List<LitemallOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus, Boolean today) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.setOrderByClause(LitemallOrder.Column.addTime.desc());
        LitemallOrderExample.Criteria criteria = example.or();
        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        if(today != null && today){
            criteria.andUpdateTimeBetween(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        }
        criteria.andDeletedEqualTo(false);
        return litemallOrderMapper.selectByExample(example);
    }

    public List<LitemallOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer limit, String sort, String order) {
        return queryTodayByOrderStatus(userId, false, orderStatus, page, limit, sort, order);
    }

    public List<LitemallOrder> queryTodayByOrderStatus(Integer userId, Boolean today, List<Short> orderStatus, Integer page, Integer limit, String sort, String order) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.setOrderByClause(LitemallOrder.Column.addTime.desc());
        LitemallOrderExample.Criteria criteria = example.or();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        if(today != null && today){
            criteria.andUpdateTimeBetween(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return litemallOrderMapper.selectByExample(example);
    }

    public List<LitemallOrder> queryShopOrderByOrderStatus(Integer shopId, Boolean today, List<Short> orderStatus, Integer page, Integer limit, String sort, String order) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.setOrderByClause(LitemallOrder.Column.addTime.desc());
        LitemallOrderExample.Criteria criteria = example.or();

        if(shopId != null){
            criteria.andShopIdEqualTo(shopId);
        }
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        if(today != null && today){
            criteria.andUpdateTimeBetween(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return litemallOrderMapper.selectByExample(example);
    }

    public List<LitemallOrder> querySelective(Integer id, Integer userId, String orderSn, List<Short> orderStatusArray, Integer shopId, Integer page, Integer limit, String sort, String order) {
        List<Integer> userIds = new ArrayList<>();
        if(userId != null){
            userIds.add(userId);
        }
        return querySelective(id, userIds, orderSn, orderStatusArray, shopId, page, limit, sort, order);
    }

    public List<LitemallOrder> querySelective(Integer id, List<Integer> userIds, String orderSn, List<Short> orderStatusArray, Integer shopId, Integer page, Integer limit, String sort, String order) {
        LitemallOrderExample example = new LitemallOrderExample();
        LitemallOrderExample.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (userIds != null && userIds.size() > 0) {
            criteria.andUserIdIn(userIds);
        }
        if (!StringUtils.isEmpty(orderSn)) {
            criteria.andOrderSnLike("%"+orderSn+"%");
        }
        if (orderStatusArray != null && orderStatusArray.size() != 0) {
            criteria.andOrderStatusIn(orderStatusArray);
        }
        if(null != shopId){
            criteria.andShopIdEqualTo(shopId);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return litemallOrderMapper.selectByExample(example);
    }

    public int updateWithOptimisticLocker(LitemallOrder order) {
        LocalDateTime preUpdateTime = order.getUpdateTime();
        order.setUpdateTime(LocalDateTime.now());
        return orderMapper.updateWithOptimisticLocker(preUpdateTime, order);
    }

    public void deleteById(Integer id) {
        litemallOrderMapper.logicalDeleteByPrimaryKey(id);
    }

    public int count() {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andDeletedEqualTo(false);
        return (int) litemallOrderMapper.countByExample(example);
    }

    public int countShop(Integer shopId) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andDeletedEqualTo(false).andShopIdEqualTo(shopId);
        return (int) litemallOrderMapper.countByExample(example);
    }

    public List<LitemallOrder> queryUnpaid(int minutes) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusMinutes(minutes);
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_CREATE).andAddTimeLessThan(expired).andDeletedEqualTo(false);
        return litemallOrderMapper.selectByExample(example);
    }

    public List<LitemallOrder> queryUnconfirm(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusDays(days);
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_SHIP).andShipTimeLessThan(expired).andDeletedEqualTo(false);
        return litemallOrderMapper.selectByExample(example);
    }

    public LitemallOrder findBySn(String orderSn) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return litemallOrderMapper.selectOneByExample(example);
    }

    public LitemallOrder findByPayId(String payId) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andPayIdEqualTo(payId).andDeletedEqualTo(false);
        return litemallOrderMapper.selectOneByExample(example);
    }

    public LitemallOrder findByOutTradeNo(String outTradeNo) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andOutTradeNoEqualTo(outTradeNo).andDeletedEqualTo(false);
        return litemallOrderMapper.selectOneByExample(example);
    }


    public Map<Object, Object> orderInfo(Integer userId) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<LitemallOrder> orders = litemallOrderMapper.selectByExampleSelective(example, LitemallOrder.Column.orderStatus, LitemallOrder.Column.comments);

        int unpaid = 0;
        int unship = 0;
        int unrecv = 0;
        int uncomment = 0;
        for (LitemallOrder order : orders) {
            if (OrderUtil.isCreateStatus(order)) {
                unpaid++;
            } else if (OrderUtil.isPayStatus(order)) {
                unship++;
            } else if (OrderUtil.isShipStatus(order)) {
                unrecv++;
            } else if (OrderUtil.isConfirmStatus(order) || OrderUtil.isAutoConfirmStatus(order)) {
                uncomment += order.getComments();
            } else {
                // do nothing
            }
        }

        Map<Object, Object> orderInfo = new HashMap<Object, Object>();
        orderInfo.put("unpaid", unpaid);
        orderInfo.put("unship", unship);
        orderInfo.put("unrecv", unrecv);
        orderInfo.put("uncomment", uncomment);
        return orderInfo;

    }

    public List<LitemallOrder> queryComment(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusDays(days);
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andCommentsGreaterThan((short) 0).andConfirmTimeLessThan(expired).andDeletedEqualTo(false);
        return litemallOrderMapper.selectByExample(example);
    }

    public LitemallOrder queryGoodsStatistics(LocalDateTime startTime, LocalDateTime endTime, Integer shopId){
        LitemallOrderExample example = new LitemallOrderExample();
        LitemallOrderExample.Criteria criteria = example.or();
        if(!ObjectUtils.isEmpty(shopId)){
            criteria.andShopIdEqualTo(shopId);
        }
        criteria.andAddTimeBetween(startTime, endTime);
        criteria.andDeletedEqualTo(false);
        return litemallOrderMapper.selectOneByExample(example);
    }

    public List<LitemallOrder> queryGoodsStatistics(LocalDateTime startTime, LocalDateTime endTime){
        LitemallOrderExample example = new LitemallOrderExample();
        LitemallOrderExample.Criteria criteria = example.or();
        criteria.andAddTimeBetween(startTime, endTime);
        criteria.andDeletedEqualTo(false);
        return litemallOrderMapper.selectByExample(example);
    }

    public List<Map<String, Object>>queryGoodsSales(Integer shopId, LocalDateTime startTime, LocalDateTime endTime, Integer page, Integer limit){
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        if(null != shopId){
            map.put("shopId", shopId);
        }
        PageHelper.startPage(page, limit);
        return orderMapper.queryGoodsSales(map);
    }

    public List<Map<String, Object>>queryGoodsCategorySales(LocalDateTime startTime, LocalDateTime endTime, Integer page, Integer limit){
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        PageHelper.startPage(page, limit);
        return orderMapper.queryGoodsCategorySales(map);
    }

    public Long queryOrderGoodsNum(String type){
        return orderMapper.queryOrderGoodsNum(type);
    }

    public List<LitemallOrder> querShopGoodsSalesInfo(Integer shopId, LocalDateTime startTime, LocalDateTime endTime){
        LitemallOrderExample example = new LitemallOrderExample();
        LitemallOrderExample.Criteria criteria = example.or();
        if(!ObjectUtils.isEmpty(shopId)){
            criteria.andShopIdEqualTo(shopId);
        }
        criteria.andDeletedEqualTo(false);
        if(!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)){
            criteria.andAddTimeBetween(startTime, endTime);
        }

        return litemallOrderMapper.selectByExample(example);
    }
    public List<LitemallOrder> querNotCompletedOrder(Integer shopId, LocalDateTime startTime, LocalDateTime endTime){
        LitemallOrderExample example = new LitemallOrderExample();
        LitemallOrderExample.Criteria criteria = example.or();
        if(!ObjectUtils.isEmpty(shopId)){
            criteria.andShopIdEqualTo(shopId);
        }
        if(!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)){
            criteria.andAddTimeBetween(startTime, endTime);
        }
        Short i = 401;
        Short ii = 402;
        criteria.andOrderStatusNotEqualTo(i);
        criteria.andOrderStatusNotEqualTo(ii);
        criteria.andDeletedEqualTo(false);
        return litemallOrderMapper.selectByExample(example);
    }

    public List<LitemallOrder> findByUserId(Integer userId) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return litemallOrderMapper.selectByExample(example);
    }

    public List<LitemallOrder> queryTime(LocalDateTime start , LocalDateTime end) {
        LitemallOrderExample example = new LitemallOrderExample();
        example.setOrderByClause(LitemallOrder.Column.addTime.desc());
        LitemallOrderExample.Criteria criteria = example.or();
        criteria.andUpdateTimeBetween(start, end);
        criteria.andDeletedEqualTo(false);
        return litemallOrderMapper.selectByExample(example);
    }

    public int updateById(LitemallOrder order) {
        order.setUpdateTime(LocalDateTime.now());
        return litemallOrderMapper.updateByPrimaryKeySelective(order);
    }

    public int countTodayByExtendId(Integer externalId) {
        LitemallOrderExample example = new LitemallOrderExample();
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        example.or().andExternalIdEqualTo(externalId).andAddTimeBetween(start, end).andDeletedEqualTo(false);
        return (int)litemallOrderMapper.countByExample(example);
    }
}
