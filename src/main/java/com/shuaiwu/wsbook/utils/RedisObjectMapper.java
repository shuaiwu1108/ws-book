package com.shuaiwu.wsbook.utils;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.springframework.beans.factory.InitializingBean;

public class RedisObjectMapper extends ObjectMapper implements InitializingBean {

    private static final long serialVersionUID = 1L;


    private String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";

    public RedisObjectMapper() {}

    @Override
    public void afterPropertiesSet() {
        this.setTimeZone(TimeZone.getDefault());
        this.setDateFormat(new SimpleDateFormat(dateFormatPattern));
        this.activateDefaultTyping(this.getPolymorphicTypeValidator(), DefaultTyping.NON_FINAL,
            As.PROPERTY);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
