package com.neuqer.mail.client.sms;

import com.neuqer.mail.client.Client;
import com.neuqer.mail.exception.Client.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Hotown on 17/6/5.
 */
public class SMSClient implements Client {

    private static Logger logger = LoggerFactory.getLogger(SMSClient.class);

    private SMSClientProfile profile = new SMSClientProfile();
    private Properties prop;

    private String account;
    private String pswd;

    public SMSClient() {
        logger.info("=======sms client initializing...=======");
        prop = new Properties();
        InputStream in = SMSClient.class.getClassLoader().getResourceAsStream("sms.properties");

        try {
            prop.load(in);
        } catch (IOException error) {
            logger.info(error.toString());
        }

        account = prop.getProperty("sms.account");
        pswd = prop.getProperty("sms.password");

    }

    public String accountPswdMobileMsgGet(String mobile, String message) {
        if (mobile == null) {
            throw new ApiException("Missing thr required parameter 'mobile'...");
        } else if (message == null) {
            throw new ApiException("Missing thr required parameter 'message'...");
        }

//        String url = "http://zapi.253.com/msg/HttpBatchSendSM?account={account}&pswd={pswd}&mobile={mobile}&msg={msg}"
//                .replaceAll("\\{account\\}", account).replaceAll("\\{pswd\\}", pswd)
//                .replaceAll("\\{mobile}", mobile).replaceAll("\\{msg\\}", message);

        String url = "http://sms.253.com/msg/send?un={un}&pw={pw}&phone={phone}&msg={msg}"
                .replaceAll("\\{un\\}", account).replaceAll("\\{pw\\}", pswd)
                .replaceAll("\\{phone}", mobile).replaceAll("\\{msg\\}", message);

        System.out.println(url);

//        String result = "";
//        BufferedReader in = null;
//
//        try {
//            URL realUrl = new URL(url);
//            //打开连接
//            URLConnection connection = realUrl.openConnection();
//            //设置通用请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            //建立实际连接
//            connection.connect();
//            //获取所有相应头字段
//            Map<String, List<String>> headerString = connection.getHeaderFields();
//
//            for (String key : headerString.keySet()) {
//                logger.info(key + "--->" + headerString.get(key));
//                System.out.println(key + "--->" + headerString.get(key));
//            }
//            //定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            System.out.println("请求异常" + e);
//            e.printStackTrace();
//        } finally {
//            //关闭输入流
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return result;
        return null;
    }
}
