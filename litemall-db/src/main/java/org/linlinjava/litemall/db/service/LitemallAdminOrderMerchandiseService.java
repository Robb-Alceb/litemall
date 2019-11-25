package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallAdminOrderMerchandiseMapper;
import org.linlinjava.litemall.db.domain.LitemallAdminOrderMerchandise;
import org.linlinjava.litemall.db.domain.LitemallAdminOrderMerchandiseExample;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallAdminOrderMerchandiseService {
    @Resource
    private LitemallAdminOrderMerchandiseMapper adminOrderMerchandiseMapper;


    public void insert(LitemallAdminOrderMerchandise adminOrderMerchandise) {
        adminOrderMerchandise.setAddTime(LocalDateTime.now());
        adminOrderMerchandise.setUpdateTime(LocalDateTime.now());
        adminOrderMerchandiseMapper.insertSelective(adminOrderMerchandise);
    }

    public List<LitemallAdminOrderMerchandise> querybyAdminOrderId(Integer adminOrderId) {
        LitemallAdminOrderMerchandiseExample example = new LitemallAdminOrderMerchandiseExample();
        example.or().andAdminOrderIdEqualTo(adminOrderId);
        return adminOrderMerchandiseMapper.selectByExample(example);
    }
}
