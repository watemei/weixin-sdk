package com.riversoft.weixin.message;

import com.riversoft.weixin.base.Settings;
import com.riversoft.weixin.base.WxClient;
import com.riversoft.weixin.message.json.JsonMessage;
import com.riversoft.weixin.util.JsonMapper;
import com.riversoft.weixin.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by exizhai on 9/26/2015.
 */
public class Messages {

    private static Logger logger = LoggerFactory.getLogger(Messages.class);
    private static Messages messages = null;
    private WxClient wxClient;

    public static Messages defaultMessages() {
        if (messages == null) {
            messages = new Messages();
            messages.setWxClient(WxClient.defaultWxClient());
        }

        return messages;
    }

    public void setWxClient(WxClient wxClient) {
        this.wxClient = wxClient;
    }

    public void send(JsonMessage message) {
        if (message.getAgentId() <= 0) {
            message.setAgentId(Settings.buildIn().getDefaultAgent());
        }
        String url = PropertiesLoader.getInstance().getProperty("url.message.send");
        String json = JsonMapper.nonEmptyMapper().toJson(message);
        logger.info("send message: {}", json);
        wxClient.post(url, json);
    }

}