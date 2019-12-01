package com.example.geolocation.presentetion.utils;

import androidx.annotation.StringRes;

public interface IResourceWrapper {

    String getString(@StringRes int resId);

    String getString(@StringRes int resId, Object... formatArgs);
}
