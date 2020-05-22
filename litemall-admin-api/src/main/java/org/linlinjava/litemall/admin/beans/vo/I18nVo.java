package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallI18n;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/21 10:26
 * @description：TODO
 */
@Data
public class I18nVo {
    private String key;
    private List<LitemallI18n> i18ns;
}
