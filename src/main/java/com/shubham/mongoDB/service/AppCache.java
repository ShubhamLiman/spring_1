package com.shubham.mongoDB.service;

import com.shubham.mongoDB.Entities.ConfigEntity;
import com.shubham.mongoDB.repository.ConfigurationRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    private ConfigurationRepo configrepo;

    private Map<String,String> appcache = new HashMap();

    @PostConstruct
    public void init(){
        List<ConfigEntity> all = configrepo.findAll();
        if(all.isEmpty()){
            System.out.println("⚠️ No configs found in MongoDB");
        } else {
            for(ConfigEntity configentity : all){
                System.out.println("✅ Loaded from DB: " + configentity.getKey() + " -> " + configentity.getValue());
                appcache.put(configentity.getKey(), configentity.getValue());
            }
        }

    }
    public void checkDb(){
        List<ConfigEntity> all = configrepo.findAll();
        if(all.isEmpty()){
            System.out.println("⚠️ No configs found in MongoDB");
        } else {
            for(ConfigEntity configentity : all){
                System.out.println("✅ Loaded from DB: " + configentity.getKey() + " -> " + configentity.getValue());
                appcache.put(configentity.getKey(), configentity.getValue());
            }
        }
    }


    public Map<String, String> getAppcache() {
        return appcache;
    }
}
