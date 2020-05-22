package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.admin.beans.dto.I18nDto;
import org.linlinjava.litemall.admin.util.AdminResponseEnum;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallI18n;
import org.linlinjava.litemall.db.service.LitemallI18nService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.RectangularShape;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/20 17:39
 * @description：TODO
 */
@Service
public class AdminI18nService {
    @Autowired
    private LitemallI18nService i18nService;

    public Object list(String key, String type,
                       Integer page, Integer limit, String sort, String order){
        return ResponseUtil.okList(i18nService.querySelective(key, type, page, limit, sort, order));
    }

    public Object update(I18nDto dto) {
        LitemallI18n record = new LitemallI18n();
        record.setValue(dto.getValue().trim());
        record.setId(dto.getId());
        i18nService.update(record);
        return ResponseUtil.ok(record);
    }

    public Object create(I18nDto dto) {
        if(i18nService.countByKeyAndType(dto.getKey(),dto.getType()) > 0){
            return ResponseUtil.fail(AdminResponseEnum.I18N_EXIST);
        };
        LitemallI18n record = new LitemallI18n();
        BeanUtils.copyProperties(dto, record);
        i18nService.add(record);
        return ResponseUtil.ok(record);
    }

    public Object delete(String key) {
        i18nService.deleteByKey(key);
        return ResponseUtil.ok();
    }

    public Object get(String key) {
        List<LitemallI18n> i18ns = i18nService.queryByKey(key);
        Map<String, String> collect = i18ns.stream().collect(Collectors.toMap(LitemallI18n::getType, LitemallI18n::getValue));
        return ResponseUtil.ok(collect);
    }
}
