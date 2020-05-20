package org.linlinjava.litemall.core.notify;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商城通知服务类
 */
public class NotifyService {
    private static final Log log = LogFactory.getLog(NotifyService.class);
    
    private MailSender mailSender;
    private String sendFrom;
    private String sendTo;

    private SmsSender smsSender;
    private List<Map<String, String>> smsTemplate = new ArrayList<>();

    private WxTemplateSender wxTemplateSender;
    private List<Map<String, String>> wxTemplate = new ArrayList<>();

    private JpushSender jpushSender;

    public boolean isMailEnable() {
        return mailSender != null;
    }

    public boolean isSmsEnable() {
        return smsSender != null;
    }

    public boolean isWxEnable() {
        return wxTemplateSender != null;
    }

    public boolean isJpushEnable() {
        return jpushSender != null;
    }

    /**
     * 短信消息通知
     *
     * @param phoneNumber 接收通知的电话号码
     * @param message     短消息内容，这里短消息内容必须已经在短信平台审核通过
     */
    @Async
    public void notifySms(String phoneNumber, String message) {
        if (smsSender == null)
            return;

        smsSender.send(phoneNumber, message);
    }

    /**
     * 短信模版消息通知
     *
     * @param phoneNumber 接收通知的电话号码
     * @param notifyType  通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param params      通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     */
    @Async
    public void notifySmsTemplate(String phoneNumber, NotifyType notifyType, String[] params) {
        if (smsSender == null) {
            return;
        }

        String templateIdStr = getTemplateId(notifyType, smsTemplate);
        if (templateIdStr == null) {
            return;
        }

        int templateId = Integer.parseInt(templateIdStr);
        smsSender.sendWithTemplate(phoneNumber, templateId, params);
    }

    /**
     * 以同步的方式发送短信模版消息通知
     *
     * @param phoneNumber 接收通知的电话号码
     * @param notifyType  通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param params      通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     * @return
     */
    public SmsResult notifySmsTemplateSync(String phoneNumber, NotifyType notifyType, String[] params) {
        if (smsSender == null)
            return null;

        int templateId = Integer.parseInt(getTemplateId(notifyType, smsTemplate));

        return smsSender.sendWithTemplate(phoneNumber, templateId, params);
    }

    /**
     * 微信模版消息通知,不跳转
     * <p>
     * 该方法会尝试从数据库获取缓存的FormId去发送消息
     *
     * @param touser     接收者openId
     * @param notifyType 通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param params     通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     */
    @Async
    public void notifyWxTemplate(String touser, NotifyType notifyType, String[] params) {
        if (wxTemplateSender == null)
            return;

        String templateId = getTemplateId(notifyType, wxTemplate);
        wxTemplateSender.sendWechatMsg(touser, templateId, params);
    }

    /**
     * 微信模版消息通知，带跳转
     * <p>
     * 该方法会尝试从数据库获取缓存的FormId去发送消息
     *
     * @param touser     接收者openId
     * @param notifyType 通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param params     通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     * @param page       点击消息跳转的页面
     */
    @Async
    public void notifyWxTemplate(String touser, NotifyType notifyType, String[] params, String page) {
        if (wxTemplateSender == null)
            return;

        String templateId = getTemplateId(notifyType, wxTemplate);
        wxTemplateSender.sendWechatMsg(touser, templateId, params, page);
    }

    /**
     * 邮件消息通知,
     * 接收者在spring.mail.sendto中指定
     *
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    @Async
    public void notifyMail(String subject, String content) {
        notifyMail(subject, content, sendTo);
    }

    /**
     * 邮件消息通知,
     * 接收者在spring.mail.sendto中指定
     *
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    @Async
    public void notifyMail(String subject, String content, String sendTo) {
        if (mailSender == null)
            return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFrom);
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 推送给设备标识参数的用户
     * @param registrationId 设备标识
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToRegistrationId( String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
        if (jpushSender == null)
            return 0;
        int result = 0;
        try {
            PushPayload pushPayload= buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
            log.info(pushPayload);
            PushResult pushResult = jpushSender.getjPushClient().sendPush(pushPayload);
            log.info(pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();

        } catch (APIRequestException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送给所有安卓用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAllAndroid( String notification_title, String msg_title, String msg_content, String extrasparam) {
        if (jpushSender == null)
            return 0;
        int result = 0;
        try {
            PushPayload pushPayload= buildPushObject_android_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
            log.info(pushPayload);
            PushResult pushResult= jpushSender.getjPushClient().sendPush(pushPayload);
            log.info(pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送给所有IOS用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAllIos(String notification_title, String msg_title, String msg_content, String extrasparam) {
        if (jpushSender == null)
            return 0;
        int result = 0;
        try {
            PushPayload pushPayload= buildPushObject_ios_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
            log.info(pushPayload);
            PushResult pushResult= jpushSender.getjPushClient().sendPush(pushPayload);
            log.info(pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送给所有用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAll( String notification_title, String msg_title, String msg_content, String extrasparam) {
        if (jpushSender == null)
            return 0;
        int result = 0;
        try {
            PushPayload pushPayload= buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
            log.info(pushPayload);
            PushResult pushResult= jpushSender.getjPushClient().sendPush(pushPayload);
            log.info(pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }



    public PushPayload buildPushObject_android_and_ios(String notification_title, String msg_title, String msg_content, String extrasparam) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
//                        .setAlert(notification_title)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(IosAlert.newBuilder().setTitleAndBody(notification_title, "", msg_content).build())
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                 .setContentAvailable(true)

                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(jpushSender.getApnsProduction())
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build()
                )
                .build();
    }

    private PushPayload buildPushObject_all_registrationId_alertWithTitle(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {

        log.info("----------buildPushObject_all_all_alert");
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();

        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.registrationId(registrationId))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()

                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)

                                .build())
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(IosAlert.newBuilder().setTitleAndBody(notification_title, "", msg_content).build())
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)

                                .build())


                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()

                        .setMsgContent(msg_content)

                        .setTitle(msg_title)

                        .addExtra("message extras key",extrasparam)

                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(jpushSender.getApnsProduction())
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)

                        .build())

                .build();

    }

    private PushPayload buildPushObject_android_all_alertWithTitle(String notification_title, String msg_title, String msg_content, String extrasparam) {
        log.info("----------buildPushObject_android_registrationId_alertWithTitle");
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.android())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(jpushSender.getApnsProduction())
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

    private PushPayload buildPushObject_ios_all_alertWithTitle( String notification_title, String msg_title, String msg_content, String extrasparam) {
        log.info("----------buildPushObject_ios_registrationId_alertWithTitle");
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.ios())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(IosAlert.newBuilder().setTitleAndBody(notification_title, "", msg_content).build())
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)

                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(jpushSender.getApnsProduction())
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }


    private String getTemplateId(NotifyType notifyType, List<Map<String, String>> values) {
        for (Map<String, String> item : values) {
            String notifyTypeStr = notifyType.getType();

            if (item.get("name").equals(notifyTypeStr))
                return item.get("templateId");
        }
        return null;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void setSmsTemplate(List<Map<String, String>> smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public void setWxTemplateSender(WxTemplateSender wxTemplateSender) {
        this.wxTemplateSender = wxTemplateSender;
    }

    public void setWxTemplate(List<Map<String, String>> wxTemplate) {
        this.wxTemplate = wxTemplate;
    }

    public JpushSender getJpushSender() {
        return jpushSender;
    }

    public void setJpushSender(JpushSender jpushSender) {
        this.jpushSender = jpushSender;
    }
}
