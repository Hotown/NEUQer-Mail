package com.neuqer.mail.client;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.client.excel.Excel;
import com.neuqer.mail.client.excel.ExcelClient;
import com.neuqer.mail.client.sms.SMSClient;
import com.neuqer.mail.exception.Client.ApiException;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by Hotown on 17/6/6.
 */
public class SMSClientTest extends BaseTest {
    @Test
    public void sendToUser() throws Exception {
        SMSClient client = (SMSClient) ClientFactory.getClient("SMS");
        ExcelClient excelClient = (ExcelClient) ClientFactory.getClient("EXCEL");
        LinkedList[] poi = excelClient.readExcel("/Users/Hotown/WorkSpace/java_project/neuqer-mail-system/src/main/resources/upload/tid34-1497339078226.xlsx");

        for (int i = 1; i < poi.length; i++) {
            String message = "【图灵杯】{0}同学，感谢您的队伍参与本次竞赛。请安排至少两名队员于今晚18:50至综合楼1227参加颁奖典礼，谢谢！";
            for (int j = 0; j < poi[i].size() - 1; j++) {
                String regex = Integer.toString(j);
                message = message.replaceAll("\\{" + regex + "\\}", poi[i].get(j).toString());
            }
            String mobile = poi[i].get(poi[i].size() - 1).toString();
            String result = client.accountPswdMobileMsgGet(mobile, message);
            int begin = result.indexOf(",");
            int end = result.indexOf(" ");
            if (!"0".equals(result.substring(begin + 1 , end))) {
                System.out.println(result);
                throw new ApiException();
            }
        }
    }
}
