package self.srr.jast.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.model.entity.TblAstSetting;
import self.srr.jast.repository.TblAstSettingRepository;

/**
 * Setting service
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Service
@Slf4j
public class SettingService {

    @Autowired
    TblAstSettingRepository tblAstSettingRepository;

    public TblAstSetting saveSetting(String settingGroup, Object settingForm) {
        // serialize
        TblAstSetting setting = tblAstSettingRepository.findOneBySettingGroup(settingGroup);
        if (setting == null) {
            setting = new TblAstSetting();
        }
        setting.setSettingGroup(settingGroup);
        setting.setSettings(new Gson().toJson(settingForm));
        setting = tblAstSettingRepository.save(setting);
        log.info("Set config: " + settingGroup + ": " + setting.toString());
        return setting;
    }

    public <T> T getSetting(String settingGroup, Class<T> target) {
        TblAstSetting setting = tblAstSettingRepository.findOneBySettingGroup(settingGroup);
        if (setting != null) {
            log.info("Get config: " + settingGroup + ":" + setting.toString());
            return new Gson().fromJson(setting.getSettings(), target);
        } else {
            log.info("Get config: " + settingGroup + ": null");
            return null;
        }
    }
}
