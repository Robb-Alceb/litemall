package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallShop;

@Data
public class ShopDto {
    private LitemallShop litemallShop;
    private Integer shopManagerId;
    private Integer shopkeeperId;

}
