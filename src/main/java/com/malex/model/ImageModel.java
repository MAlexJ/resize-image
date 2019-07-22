package com.malex.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ImageModel {

    /**
     * Image full name template
     */
    private static final String TEMPLATE_FULL_NAME = "%s.%s";

    private String name;
    private String extension;
    private double percent;

    @Setter
    private int scaledWidth;

    @Setter
    private int scaledHeight;

    public boolean isImageScalable() {
        return scaledWidth > 0 || scaledHeight > 0;
    }

    public String getFullFileName() {
        return String.format(TEMPLATE_FULL_NAME, name, extension);
    }
}
