package org.linlinjava.litemall.core.notify;

import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.config.AwsProperties;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/21 16:03
 * @description：TODO
 */
public class AwsNotifyService {
    private static final Log log = LogFactory.getLog(AwsNotifyService.class);
    private static final String charset = "utf-8";
    private static final String DEFAULT_MESSAGE_TYPE = "PROMOTIONAL";
    private AwsProperties properties;
    private AmazonPinpoint client;

    public AwsProperties getProperties() {
        return properties;
    }

    public void setProperties(AwsProperties properties) {
        this.properties = properties;
    }

    public AmazonPinpoint getClient() {
        return client;
    }

    public void setClient(AmazonPinpoint client) {
        this.client = client;
    }

    /**
     * 发送单个
     * @param subject
     * @param textContent
     * @param htmlContent
     * @param emailAddress
     */
    public void noticeMail(String subject, String textContent, String htmlContent, String emailAddress){
        if(StringUtils.isEmpty(emailAddress)){
           return;
        }
        List<String> list = new ArrayList<>();
        list.add(emailAddress);
        noticeMail(subject, textContent, htmlContent, list);
    }

    /**
     * 发送多个
     * @param subject
     * @param textContent
     * @param htmlContent
     * @param emailAddressList
     */
    public void noticeMail(String subject, String textContent, String htmlContent, List<String> emailAddressList){
        if(!properties.getEmail().isEnable()){
            return;
        }
        try {
            if(emailAddressList == null || emailAddressList.size() == 0){
                return;
            }
            Map<String, AddressConfiguration> addressMap =
                    new HashMap<>();
            for (String toAddress : emailAddressList) {
                addressMap.put(toAddress, new AddressConfiguration()
                        .withChannelType(ChannelType.EMAIL));
            }

            SendMessagesRequest request = (new SendMessagesRequest()
                    .withApplicationId(properties.getAppId())
                    .withMessageRequest(new MessageRequest()
                            .withAddresses(addressMap)
                            .withMessageConfiguration(new DirectMessageConfiguration()
                                    .withEmailMessage(new EmailMessage()
                                            .withSimpleEmail(new SimpleEmail()
                                                    .withHtmlPart(new SimpleEmailPart()
                                                            .withCharset(charset)
                                                            .withData(textContent)
                                                    )
                                                    .withTextPart(new SimpleEmailPart()
                                                            .withCharset(charset)
                                                            .withData(htmlContent)
                                                    )
                                                    .withSubject(new SimpleEmailPart()
                                                            .withCharset(charset)
                                                            .withData(subject)
                                                    )
                                            )
                                    )
                            )
                    )
            );
            log.info("Sending message...");
            client.sendMessages(request);
            log.info("Message sent!");
        } catch (Exception ex) {
            System.out.println("The email wasn't sent. Error message: "
                    + ex.getMessage());
        }
    }

    public void sendSms(String number, String message, String messageType){
        List<String> numbers = new ArrayList<>();
        numbers.add(number);
        sendSms(numbers, message, messageType);
    }
    public void sendSms(List<String> numbers, String message, String messageType){
        if(!properties.getSms().isEnable()){
            return;
        }
        try {
            if(numbers == null || numbers.size() == 0){
                return;
            }
            if(StringUtils.isEmpty(message)){
                message = DEFAULT_MESSAGE_TYPE;
            }
            Map<String, AddressConfiguration> addressMap =
                    new HashMap<>();
            for (String toAddress : numbers) {
                addressMap.put(toAddress, new AddressConfiguration()
                        .withChannelType(ChannelType.SMS));
            }

            SendMessagesRequest request = new SendMessagesRequest()
                    .withApplicationId(properties.getAppId())
                    .withMessageRequest(new MessageRequest()
                            .withAddresses(addressMap)
                            .withMessageConfiguration(new DirectMessageConfiguration()
                                    .withSMSMessage(new SMSMessage()
                                            .withBody(message)
                                            .withMessageType(messageType)
                                            .withOriginationNumber(properties.getOriginationNumber())
                                    )
                            )
                    );
            System.out.println("Sending message...");
            client.sendMessages(request);
            System.out.println("Message sent!");
        } catch (Exception ex) {
            System.out.println("The message wasn't sent. Error message: "
                    + ex.getMessage());
        }
    }
    public void sendSmsTemplate(String number, String templateType, String[] params, String messageType){

        Map<String, String> template = properties.getSms().getTemplate();
        if(template == null){
            return;
        }
        String content = template.get(templateType);
        if(!StringUtils.isEmpty(content)){
            String str = String.format(content, params);
            sendSms(number, str, messageType);
        }
    }

    public void noticeMailTemplate(String subject, String templateName, String sendTo, String[] params) {

        Map<String, String> template = properties.getEmail().getTemplate();
        if(template == null){
            return;
        }
        String content = template.get(templateName);
        if(!StringUtils.isEmpty(content)){
            String str = String.format(content, params);
            noticeMail(subject, str, str, sendTo);
        }
    }

/*    public void sendNotification(String title, String body){
        SendMessagesRequest request = new SendMessagesRequest()
                .withApplicationId(properties.getAppId())
                    .withMessageRequest(new DirectMessageConfiguration().withDefaultPushNotificationMessage(new DefaultPushNotificationMessage()
                                    .withTitle(title)
                                    .withBody(message)
                                    .withAction(action)
                                    .withUrl(url)
                                    .withSilentPush(silent)
                                    .withData(data)));
    }*/
}
