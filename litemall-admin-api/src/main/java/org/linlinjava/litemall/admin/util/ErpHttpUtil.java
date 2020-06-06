package org.linlinjava.litemall.admin.util;

import org.linlinjava.litemall.core.util.EncryptUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/4 10:20
 * @description：TODO
 */
public class ErpHttpUtil {
    @Value("${erp.enable}")
    private boolean enable;
    @Value("${erp.uri}")
    private String erpUri;
    @Value("${erp.key}")
    private String key;
    @Value("${erp.secret}")
    private String secret;
    @Autowired
    private RestTemplate restTemplate;

    public Object setErpAdmin(LitemallAdmin litemallAdmin){
        if(!enable){
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        String at = EncryptUtil.getInstance().DESencode(secret, key);
        headers.add("at", at);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", "844072586@qq.com");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( erpUri, request , String.class);
        System.out.println(response.getBody());

        return true;
    }
}
