package org.linlinjava.litemall.admin.erp.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.erp.rq.MaterialRQ;
import org.linlinjava.litemall.admin.erp.rs.MaterialRS;
import org.linlinjava.litemall.admin.erp.rs.PageModelRS;
import org.linlinjava.litemall.admin.erp.util.ParamUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 11:52
 * @description：TODO
 */
@Service
public class MaterialClient extends BaseClient {

    private final Log log = LogFactory.getLog(MaterialClient.class);



    public PageModelRS<MaterialRS> getMaterialList(MaterialRQ rq, String url){

        try {
            HttpEntity httpEntity = new HttpEntity<>(this.headers);
            url =  ParamUtils.rqToParam(rq, url);
            ParameterizedTypeReference<PageModelRS<MaterialRS>> reference = new ParameterizedTypeReference<PageModelRS<MaterialRS>>() {};
            ResponseEntity<PageModelRS<MaterialRS>> materialRSResponseEntity = this.getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, reference);
            log.info(materialRSResponseEntity.toString());
            PageModelRS body = null;
            if(materialRSResponseEntity.getStatusCode().is2xxSuccessful()){
                body = materialRSResponseEntity.getBody();
            }
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取库存失败");
        }

    }
}
