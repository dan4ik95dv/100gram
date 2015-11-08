package me.stogram.android.camera.effect;

import me.stogram.android.camera.util.GPUImageFilterTools;

/**
 * @author tongqian.ni
 */
public class FilterEffect {

    private GPUImageFilterTools.FilterType type;
    private int degree;

    /**
     * @param uri
     */
    public FilterEffect(GPUImageFilterTools.FilterType type, int degree) {
        this.type = type;
        this.degree = degree;
    }


    public GPUImageFilterTools.FilterType getType() {
        return type;
    }
    public int getDegree() {
        return degree;
    }

}
