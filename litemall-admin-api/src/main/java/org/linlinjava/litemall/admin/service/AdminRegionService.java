package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.db.domain.LitemallRegion;
import org.linlinjava.litemall.db.service.LitemallRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/14 10:30
 * @description：TODO
 */
@Service
public class AdminRegionService {
    @Autowired
    private LitemallRegionService litemallRegionService;

    public void getParentIds(Integer id, LinkedList<Integer> ids){
        LitemallRegion region = litemallRegionService.findById(id);
        if(region != null){
            ids.addFirst(region.getId());
            if(region.getPid() != null && region.getPid() != 0){
                getParentIds(region.getPid(), ids);
            }
        }
    }
}
