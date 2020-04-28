package org.linlinjava.litemall.wx;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.notify.netty.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/8 14:01
 * @description：TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class NettyTest {
    @Autowired
    private PushService pushService;

    @Test
    public void testPushOne(){
        String userId = "123456";
        String msg = "测试netty推送单个用户消息";
        pushService.pushMsgToOne(userId, msg);
//        new PushServiceImpl().pushMsgToOne(userId, msg);
    }

    @Test
    public void testPushAll(){
        String msg = "测试netty推送所有用户消息";
        pushService.pushMsgToAll(msg);
    }

    @Test
    public static void main(String[] args) throws  Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("param", "{\"fileSize\":2652,\"fileName\":\"3.jpge\",\"parentId\":\"\",\"fileCategory\":\"personal\",\"uoType\":2,\"portalModelId\":5}")
                                .addFormDataPart("id", "WU_FILE_1")
                                .addFormDataPart("name", "2.jpge")
                                .addFormDataPart("size", "26527")
                                .addFormDataPart("type", "image/jpge")
                                .addFormDataPart("file","/D:/temp/1556181469374.jpeg",
                                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                                new File("/D:/temp/1556181469374.jpeg")))
                                .addFormDataPart("newsId", "1")
                                .build();
        Request request = new Request.Builder()
                .url("http://192.168.0.80/upload/stream")
                .method("POST", body)
                .addHeader("ct", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjk3LCJ0aW1lIjoxNTg2NzQ0OTQ1LCJrZXkiOiJieWQ0MjI1aTNyc3EiLCJpYXQiOjE1ODY3NDQ5NDV9.KBzILiTXW6oyVvMBCptMCz4ZIN5Aro7JeljNmVUTUm4")
                .addHeader("ct", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjk3LCJ0aW1lIjoxNTg2NzQ0OTQ1LCJrZXkiOiJieWQ0MjI1aTNyc3EiLCJpYXQiOjE1ODY3NDQ5NDV9.KBzILiTXW6oyVvMBCptMCz4ZIN5Aro7JeljNmVUTUm4")
                .build();
        Response response = client.newCall(request).execute();

        System.out.println(JSON.toJSONString(response).toString());
    }
}
