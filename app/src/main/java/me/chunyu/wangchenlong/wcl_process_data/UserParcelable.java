package me.chunyu.wangchenlong.wcl_process_data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Android的序列化对象
 * <p/>
 * Created by wangchenlong on 16/5/5.
 */
public class UserParcelable implements Parcelable {
    public int userId;
    public String userName;
    public boolean isMale;
    public BookParcelable book;

    public UserParcelable(int userId, String userName, boolean isMale, String bookName) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
        this.book = new BookParcelable(bookName);
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale ? 1 : 0);
        dest.writeParcelable(book, 0);
    }

    public static final Parcelable.Creator<UserParcelable> CREATOR = new Parcelable.Creator<UserParcelable>() {
        @Override public UserParcelable createFromParcel(Parcel source) {
            return new UserParcelable(source);
        }

        @Override public UserParcelable[] newArray(int size) {
            return new UserParcelable[size];
        }
    };

    private UserParcelable(Parcel source) {
        userId = source.readInt();
        userName = source.readString();
        isMale = source.readInt() == 1;
        book = source.readParcelable(Thread.currentThread().getContextClassLoader());
    }
}
