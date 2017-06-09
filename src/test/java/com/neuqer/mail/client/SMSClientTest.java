package com.neuqer.mail.client;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.client.excel.Demo;
import com.neuqer.mail.client.sms.SMSClient;
import org.junit.Test;

/**
 * Created by Hotown on 17/6/6.
 */
public class SMSClientTest extends BaseTest {
    @Test
    public void sendToUser() throws Exception {
        SMSClient client = (SMSClient) ClientFactory.getClient("SMS");
        Demo poi = new Demo();
        poi.loadExcel("/Users/Hotown/WorkSpace/java_project/neuqer-mail-system/src/test/java/com/neuqer/mail/demo.xlsx");
        poi.init();

        for (int i = 1; i < poi.result.length; i++) {
            String message = "请{0}同学，在{1}，于{2}准时参加比赛。";
            for (int j = 0; j < poi.result[i].size() - 1; j++) {
//                System.out.print(poi.result[i].get(j) + "\t");
                String regex = Integer.toString(j);
                message = message.replaceAll("\\{" + regex + "\\}", poi.result[i].get(j).toString());
            }
            String mobile = poi.result[i].get(poi.result[i].size() - 1).toString();
            System.out.println(client.accountPswdMobileMsgGet(mobile, message));
        }
    }
}
