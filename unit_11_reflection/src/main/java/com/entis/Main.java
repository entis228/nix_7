package com.entis;

import com.entis.entity.AppProperties;
import com.entis.service.PropertyMapper;
import com.entis.service.impl.PropertiesMapperImpl;
import com.entis.service.impl.FilePropertiesLoader;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        if(args[0]!=null) {
            String fileName = args[0];
            Properties props = new FilePropertiesLoader().getProperty(fileName);
            PropertyMapper mapper=new PropertiesMapperImpl();
            AppProperties appProps = mapper.map(AppProperties.class, props);
            System.out.println("ID of properties version: " + appProps.getId());
            System.out.println("Class name: " + appProps.getName());
            System.out.println("Date of connection: " + appProps.getCreatedTime());
        }
    }
}
