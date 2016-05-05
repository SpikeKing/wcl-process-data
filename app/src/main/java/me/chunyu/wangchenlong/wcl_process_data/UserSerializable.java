package me.chunyu.wangchenlong.wcl_process_data;

import java.io.Serializable;

/**
 * 序列化对象, 需要标注serialVersionUID, 表示版本一致
 * <p/>
 * Created by wangchenlong on 16/5/4.
 */
public class UserSerializable implements Serializable {
    // 标准序列ID, 用于判断版本
    private static final long serialVersionUID = 1L;

    public int userId;
    public String userName;
    public boolean isMale;

    public UserSerializable(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }
}
