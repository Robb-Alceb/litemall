package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/18 18:48
 * @description：TODO
 */
@Data
public class UserDto {
    @NotNull
    private Integer id;
    private Byte gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String nickname;
    private String email;
    private String mobile;
    private Byte status;
    private BigDecimal points;
}
