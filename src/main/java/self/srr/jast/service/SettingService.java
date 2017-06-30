package self.srr.jast.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.entity.TblTracerSetting;
import self.srr.jast.form.BasicForm;
import self.srr.jast.repository.TblTracerSettingRepository;

/**
 * Setting service
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Service
public class SettingService {

    @Autowired
    TblTracerSettingRepository tblTracerSettingRepository;

    public boolean saveConfig(String settingGroup, BasicForm settingForm) {
        // serialize
        TblTracerSetting setting = tblTracerSettingRepository.findOneBySettingGroup(settingGroup);
        if (setting == null) {
            setting = new TblTracerSetting();
        }
        setting.setSettingGroup(settingGroup);
        setting.setSettings(new Gson().toJson(settingForm));
        tblTracerSettingRepository.save(setting);
        return true;
    }

    public <T> T getConfig(String settingGroup, Class<T> target) {
        TblTracerSetting setting = tblTracerSettingRepository.findOneBySettingGroup(settingGroup);
        if (setting != null) {
            return new Gson().fromJson(setting.getSettings(), target);
        } else {
            return null;
        }

    }
}
