package org.linlinjava.litemall.admin.erp.client;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.erp.rq.PurchaseRQ;
import org.linlinjava.litemall.admin.erp.rs.PurchaseRS;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 16:20
 * @description：TODO
 */
@Service
public class DepotClient extends BaseClient {

    private static final Log log  = LogFactory.getLog(DepotClient.class);
    /**
     * 向erp发起采购申请，erp会生成销售单
     * @param rq
     * @param url
     * @return
     * @throws Exception
     */
    public String addDepotHeadAndDetail(PurchaseRQ rq, String url){
//        MultiValueMap<String, String> map = ParamUtils.rqToMultiMap(rq);
        try {
            Object param = JSONObject.toJSON(rq);
            HttpEntity httpEntity = new HttpEntity<>(param, this.headers);
            ResponseEntity<PurchaseRS> objectResponseEntity = this.getRestTemplate().postForEntity(url, httpEntity, PurchaseRS.class);
            log.info(objectResponseEntity.toString());
            if(objectResponseEntity.getStatusCode().is2xxSuccessful()){
                /**
                 * TODO 成功后记录单据编号
                 */
                PurchaseRS body = objectResponseEntity.getBody();
                return body == null ? null : body.getNumber();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发起采购申请失败");
        }
        return null;
    }

    public Object getBuildNumber(String url){
        HttpEntity httpEntity = new HttpEntity<>(this.headers);
        ResponseEntity<String> resEntity = this.getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, String.class);
        if(resEntity.getStatusCode().is2xxSuccessful()){
            return resEntity.getBody();
        }else{
            return null;
        }
    }
}
