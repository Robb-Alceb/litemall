package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/8 15:11
 * @description：TODO
 */
@Data
public class AdminDto {
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private Integer[] roleIds;
    private String nickName;
    private String location;
    private String mobile;
    private Integer shopId;
    private String email;
    private Integer gender;
    private String code;
    private String socialSecurityNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;
}
