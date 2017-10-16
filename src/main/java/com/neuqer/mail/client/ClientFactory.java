package com.neuqer.mail.client;

import com.neuqer.mail.client.excel.ExcelClient;
import com.neuqer.mail.client.sms.SMSClient;

/**
 * Created by Hotown on 17/6/5.
 */
public class ClientFactory {
    public static Client getClient(String clientType) {
        if (clientType == null) {
            return null;
        }

        if ("SMS".equals(clientType)) {
            return new SMSClient();
        } else if ("EXCEL".equals(clientType)) {
            return new ExcelClient();
        }

        return null;
    }
}
