package com.example.applemusicdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.applemusicdemo.R;

/**
 * 1.输入框前面的图标 input_iocn;
 * 2.输入框的提示内容 input_hint;
 * 3.输入内容是否以密文形式显示 is_password；
 */
public class InputView extends FrameLayout {
    private int input_Icon;
    private String input_Hint;
    private boolean is_Password;
    private View mview;
    private ImageView inputIcon;
    private EditText  etInput;
    public InputView(Context context) {
        super(context);
        init(context,null);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    private void init(Context context,AttributeSet attrs){
        if(attrs==null)return;
        //获取自定义属性
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.inputView);
        input_Icon=typedArray.getResourceId(R.styleable.inputView_input_icon,R.mipmap.logo);
        input_Hint=typedArray.getString(R.styleable.inputView_input_hint);
        is_Password=typedArray.getBoolean(R.styleable.inputView_is_password,false);
        //释放资源
        typedArray.recycle();
        //绑定布局
        mview= LayoutInflater.from(context).inflate(R.layout.input_view,this,false);
        inputIcon=mview.findViewById(R.id.input_icon);
        etInput=mview.findViewById(R.id.et_input_hint);
        //布局关联属性
        inputIcon.setImageResource(input_Icon);
        etInput.setHint(input_Hint);
        etInput.setInputType(is_Password? InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD:InputType.TYPE_CLASS_PHONE);
        addView(mview);

    }
    public String getInputContent(){
        return etInput.getText().toString().trim();
    }
}
