package com.example.mytestapp.ui.weibo.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytestapp.Base.Activity.BaseMvpActivity;
import com.example.mytestapp.Base.clickablespan.CustomUrlSpan;
import com.example.mytestapp.R;
import com.example.mytestapp.ui.weibo.homepage.domin.WeiboHomepageResp;
import com.example.mytestapp.ui.weibo.util.NineImageViewAdapter;
import com.example.mytestapp.ui.weibo.util.WeiboImageUrlUtil;
import com.example.mytestapp.utils.GLideUtil;
import com.jaeger.ninegridimageview.NineGridImageView;

import java.util.List;

/**
 * @Name: WeiboDetailsActivity
 * @Author: wangbufan
 * @Date: 2019/8/31 16:43
 * @Description:
 */
public class WeiboDetailsActivity extends BaseMvpActivity {

    private ImageView IvAvatar;
    private TextView TvName;
    private TextView TvTime;
    private NineGridImageView nineGridImageView;
    private List<String> imageUrls;
    private String avatarUrl,Name,Time,Text;

    public static void launch(Context context, WeiboHomepageResp.StatusesBean data){
        Intent intent=new Intent(context,WeiboDetailsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("detailsData",data);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected void parseArguments() {
        WeiboHomepageResp.StatusesBean data= (WeiboHomepageResp.StatusesBean) getIntent().getSerializableExtra("detailsData");
        imageUrls =WeiboImageUrlUtil.getMiddleImageURL(data.getPic_urls());
        avatarUrl =data.getUser().getAvatar_large();
        Name = data.getUser().getName();
        Time = data.getCreated_at();
        Text = data.getText();

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        IvAvatar = findViewById(R.id.iv_avatar);
        TvName = findViewById(R.id.tv_name);
        TvTime = findViewById(R.id.tv_time);
        TextView tvText = findViewById(R.id.tv_text);

        nineGridImageView=findViewById(R.id.nine_image_view);
        nineGridImageView.setAdapter(new NineImageViewAdapter());
        nineGridImageView.setImagesData(imageUrls);
        GLideUtil.loadImageViewRound(this, avatarUrl, IvAvatar);
        TvName.setText(Name);
        TvTime.setText(Time);
        tvText.setText(Text);
        interceptHyperLink(tvText);
    }




    @Override
    protected void loadData(int page) {

    }

    /**
     * 拦截超链接
     * @param tv
     */
    private void interceptHyperLink(TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable spannable = (Spannable) tv.getText();
            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0) {
                return;
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            // 循环遍历并拦截 所有http://开头的链接
            for (URLSpan uri : urlSpans) {
                String url = uri.getURL();
                if (url.indexOf("http://") == 0) {
                    CustomUrlSpan customUrlSpan = new CustomUrlSpan(this,url);
                    spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri),
                            spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
            tv.setText(spannableStringBuilder);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_weibo_details_layout;
    }

}
