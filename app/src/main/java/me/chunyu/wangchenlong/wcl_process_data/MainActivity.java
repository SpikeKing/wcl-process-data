package me.chunyu.wangchenlong.wcl_process_data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String PASS_PARCEL_FILTER = "MainActivity.PASS_PARCEL_FILTER";
    private static final String PARCEL_EXTRA = "MainActivity.PARCEL_EXTRA";

    @Bind(R.id.serial_tv_content) TextView mSerialTvContent; // Serializable序列化内容
    @Bind(R.id.parcel_tv_content) TextView mParcelTvContent; // Parcelable序列化内容

    private LocalBroadcastManager mLBM; // 局部广播

    private BroadcastReceiver mParcelReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            UserParcelable newUser = intent.getParcelableExtra(PARCEL_EXTRA);
            if (newUser != null) {
                String content = "序号: " + newUser.userId
                        + ", 姓名: " + newUser.userName
                        + ", 性别: " + (newUser.isMale ? "男" : "女")
                        + ", 书: " + newUser.book.bookName;
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
                mParcelTvContent.setText(content);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLBM = LocalBroadcastManager.getInstance(getApplicationContext());
        mLBM.registerReceiver(mParcelReceiver, new IntentFilter(PASS_PARCEL_FILTER));
    }

    @Override protected void onDestroy() {
        mLBM.unregisterReceiver(mParcelReceiver);
        super.onDestroy();
    }

    /**
     * 序列化对象, 按钮的点击事件
     *
     * @param view 视图
     */
    public void serialIn(View view) {
        Context context = view.getContext();
        File cache = new File(context.getCacheDir(), "cache.txt");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cache));
            UserSerializable user = new UserSerializable(0, "Spike", false);
            out.writeObject(user);
            out.close();
            Toast.makeText(context, "序列化成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化对象, 按钮的点击事件
     *
     * @param view 视图
     */
    public void serialOut(View view) {
        Context context = view.getContext();
        File cache = new File(context.getCacheDir(), "cache.txt");
        UserSerializable newUser = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(cache));
            newUser = (UserSerializable) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "请先序列化", Toast.LENGTH_SHORT).show();

        }
        if (newUser != null) {
            String content = "序号: " + newUser.userId
                    + ", 姓名: " + newUser.userName
                    + ", 性别: " + (newUser.isMale ? "男" : "女");
            mSerialTvContent.setText(content);
        } else {
            mSerialTvContent.setText("无数据");
        }
    }

    /**
     * 删除缓存的序列化对象
     *
     * @param view 视图
     */
    public void serialDelete(View view) {
        Context context = view.getContext();
        File cache = new File(context.getCacheDir(), "cache.txt");
        if (cache.delete()) {
            Toast.makeText(context, "缓存删除", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送Parcelable的序列化内容
     *
     * @param view 视图
     */
    public void parcelSend(View view) {
        Intent intent = new Intent(PASS_PARCEL_FILTER);
        intent.putExtra(PARCEL_EXTRA, new UserParcelable(0, "Spike", false, "三国演义"));
        mLBM.sendBroadcast(intent);
    }
}
