package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.EncryptUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/4 10:20
 * @description：TODO
 */
@Service
public class ErpHttpService {
    private static final Log log = LogFactory.getLog(ErpHttpService.class);
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
        String endpoint = "/test";
        HttpHeaders headers = new HttpHeaders();
        String at = EncryptUtil.getInstance().DESencode(secret, key);
        headers.add("at", at);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", "844072586@qq.com");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( erpUri + endpoint + "/post", request , String.class);
        log.info("post res is :" + response.getBody());

        ResponseEntity<String> get = restTemplate.getForEntity(erpUri + endpoint+ "/get?param=dkd", String.class);
        log.info("get res is :" + get.getBody());

        restTemplate.put(erpUri + endpoint + "/put", request);

        restTemplate.delete(erpUri + endpoint + "/delete?param=del", request);
        return true;
    }
}
