package com.rjAbhi.journalApp.cache;

import com.rjAbhi.journalApp.entity.ConfigJournalAppEntity;
import com.rjAbhi.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public enum keys{
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCacheMap ;

    @PostConstruct
    public void init() {
        appCacheMap=new HashMap<>();
        List<ConfigJournalAppEntity> allPairs = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : allPairs) {
            appCacheMap.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
