package com.malex.service;

import com.malex.model.ImageModel;

public interface ImageService {

    boolean resize(String inputImagePath, ImageModel imageModel);
}
