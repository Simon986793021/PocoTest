package com.poco.webviewscanpic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class ViewPhotoActivity extends Activity {
    private String curImgUrl;
    private String imgUrls[];
    private ViewPager viewPager;
    private TextView textView;
    private int curPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        initView();
        getLastIntent();
        loadPhoto();
    }

    private void loadPhoto() {
        viewPager.setAdapter(new MyPageAdapter());
        curPosition = returnClickedPosition() == -1 ? 0 : returnClickedPosition();
        viewPager.setCurrentItem(curPosition);//设置当前显示的图片
        textView.setText((curPosition + 1) + "/" + imgUrls.length);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                textView.setText((curPosition + 1) + "/" + imgUrls.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        viewPager = findViewById(R.id.vp_photo);
        textView = findViewById(R.id.tv_photo_order);
    }

    public void getLastIntent() {
        curImgUrl = getIntent().getStringExtra("curImg");
        imgUrls = getIntent().getStringArrayExtra("imageUrls");
    }

    private int returnClickedPosition() {
        if (imgUrls == null || curImgUrl == null) {
            return -1;
        }
        for (int i = 0; i < imgUrls.length; i++) {
            if (curImgUrl.equals(imgUrls[i])) {
                return i;
            }
        }
        return -1;
    }

    private class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgUrls.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (imgUrls[position] != null) {
                ImageView imageView = new ImageView(ViewPhotoActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Glide.with(ViewPhotoActivity.this)
                        .load(imgUrls[position])
                        .into(imageView);
                container.addView(imageView);
                return imageView;
            }
            return null;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
