package me.stogram.android.camera;


import java.util.ArrayList;
import java.util.List;

import me.stogram.android.camera.effect.FilterEffect;
import me.stogram.android.camera.util.GPUImageFilterTools;


public class EffectService {

    private static EffectService mInstance;

    public static EffectService getInst() {
        if (mInstance == null) {
            synchronized (EffectService.class) {
                if (mInstance == null)
                    mInstance = new EffectService();
            }
        }
        return mInstance;
    }

    private EffectService() {
    }

    public List<FilterEffect> getLocalFilters() {
        List<FilterEffect> filters = new ArrayList<FilterEffect>();
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.NORMAL, 0));
        filters.add(new FilterEffect( GPUImageFilterTools.FilterType.ACV_AIMEI, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_DANLAN, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_DANHUANG, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_FUGU, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_GAOLENG, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_HUAIJIU, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_JIAOPIAN, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_KEAI, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_LOMO, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_MORENJIAQIANG, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_NUANXIN, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_QINGXIN, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_RIXI, 0));
        filters.add(new FilterEffect(GPUImageFilterTools.FilterType.ACV_WENNUAN, 0));

        //TODO Здесь можно добавить еще фильтров

        return filters;
    }

}
