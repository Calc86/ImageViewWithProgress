package ru.xsrv.view.ilprogress;

import android.app.Application;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by calc on 25.10.2015.
 */
public class MyApp extends Application {
    private static MyApp instance;

    public MyApp() {
        //super();
        instance = this;
    }

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // DON'T COPY THIS CODE TO YOUR PROJECT! This is just example of ALL options using.
        // See the sample project how to use ImageLoader correctly.
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
    }
}
