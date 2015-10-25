package ru.xsrv.view.ilprogress.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import ru.xsrv.view.ilprogress.R;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by calc on 25.10.2015.
 */
public class ImageViewProgress extends FrameLayout {
    private ImageView image;
    private ProgressBar progress;
    private URI uri;
    private DisplayImageOptions options;
    private int defaultImage = android.R.color.transparent;

    private AttributeSet attrs;

    public ImageViewProgress(Context context) {
        this(context, null);
    }

    public ImageViewProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.attrs = attrs;

        // строим
        FrameLayout frame = (FrameLayout)LayoutInflater.from(context).inflate(R.layout.image_view_layout, this);
        image = (ImageView)frame.findViewById(R.id.imageView);
        progress = (ProgressBar)frame.findViewById(R.id.progressBar);

        if(!isInEditMode()) progress.setVisibility(GONE);
        setAttrs(context, defStyle);

        setDefaultImage();
        options = new DisplayImageOptions.Builder().build();

        //this.addView(frame, defStyle);
    }

    private void setAttrs(Context context, int defStyle){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageViewProgress, defStyle, 0);

        String uri = array.getString(R.styleable.ImageViewProgress_uri);
        try {
            if(uri == null) uri = "";
            this.uri = new URI(uri);
            setImage(this.uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        array.recycle();
    }

    public void setImage(int res){
        image.setImageResource(res);
    }

    public void setImage(String uri){
        try {
            URI u = new URI(uri);
            setImage(u);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            setDefaultImage();
        }
    }

    public void setImage(URI uri){
        if(isInEditMode()) return;
        ImageLoader.getInstance().displayImage(uri.toString(), image, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progress.setVisibility(VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progress.setVisibility(GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progress.setVisibility(GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progress.setVisibility(GONE);
            }
        });
    }

    public void setOptions(DisplayImageOptions options) {
        this.options = options;
    }

    public void setScaleType(ImageView.ScaleType type){
        image.setScaleType(type);
    }

    private void setDefaultImage(){
        image.setImageResource(defaultImage);
    }
}
