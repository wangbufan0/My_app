package com.example.mytestapp.ui.news.detail.view;

import com.example.mytestapp.Base.View.BaseView;
import com.example.mytestapp.ui.news.detail.domain.NewDetailresq;

public interface NewsDetailViewI extends BaseView {

    void getDataSuccessed(NewDetailresq data);
}
