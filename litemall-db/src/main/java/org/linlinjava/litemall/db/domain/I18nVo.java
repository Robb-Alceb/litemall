package org.linlinjava.litemall.db.domain;


import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/21 10:26
 * @description：TODO
 */
public class I18nVo {
    private String key;
    private List<LitemallI18n> i18ns;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<LitemallI18n> getI18ns() {
        return i18ns;
    }

    public void setI18ns(List<LitemallI18n> i18ns) {
        this.i18ns = i18ns;
    }

    @Override
    public String toString() {
        return "I18nVo{" +
                "key='" + key + '\'' +
                ", i18ns=" + i18ns +
                '}';
    }
}
