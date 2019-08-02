package com.example.applemusicdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applemusicdemo.R;
import com.example.applemusicdemo.utils.UserUtils;
import com.example.applemusicdemo.views.InputView;

public class ChangePasswordActivity extends BaseActivity {
    private InputView ip_old_pass,ip_new_pass,ip_new_ensure_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView() {

    initNavBar(true,"修改密码",false);
    ip_new_ensure_pass=findViewById(R.id.input_ensure_new_password);
    ip_new_pass=findViewById(R.id.input_new_password);
    ip_old_pass=findViewById(R.id.input_old_pass);
    }
    public void OnEnsurePassClick(View v){
        Toast.makeText(ChangePasswordActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
        finish();
    }
    public  void OnChangePassClick(View view){
        String oldpass=ip_old_pass.getInputContent();
        String newpass=ip_new_pass.getInputContent();
        String ensurepass=ip_new_ensure_pass.getInputContent();
        //判断修改是否成功；
        boolean result=UserUtils.changeUserPassword(this,oldpass,newpass,ensurepass);
        if(!result)return;
        //密码修改成功，跳转到登录页面；
        UserUtils.LogOut(this);

    }
}
