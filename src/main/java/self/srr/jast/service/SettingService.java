package self.srr.jast.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.entity.TblTracerSetting;
import self.srr.jast.repository.TblTracerSettingRepository;

/**
 * Setting service
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Service
@Slf4j
public class SettingService {

    @Autowired
    TblTracerSettingRepository tblTracerSettingRepository;

    public boolean saveConfig(String settingGroup, Object settingForm) {
        // serialize
        TblTracerSetting setting = tblTracerSettingRepository.findOneBySettingGroup(settingGroup);
        if (setting == null) {
            setting = new TblTracerSetting();
        }
        setting.setSettingGroup(settingGroup);
        setting.setSettings(new Gson().toJson(settingForm));
        setting = tblTracerSettingRepository.save(setting);
        log.info("Get config: " + settingGroup + ": " + setting.toString());
        return true;
    }

    public <T> T getConfig(String settingGroup, Class<T> target) {
        TblTracerSetting setting = tblTracerSettingRepository.findOneBySettingGroup(settingGroup);
        if (setting != null) {
            log.info("Get config: " + settingGroup + ":" + setting.toString());
            return new Gson().fromJson(setting.getSettings(), target);
        } else {
            log.info("Get config: " + settingGroup + ": null");
            return null;
        }
    }
}
