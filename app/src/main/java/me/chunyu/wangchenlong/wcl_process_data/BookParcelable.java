package me.chunyu.wangchenlong.wcl_process_data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用于测试User的Parcelable序列化
 * <p/>
 * Created by wangchenlong on 16/5/5.
 */
public class BookParcelable implements Parcelable {
    public String bookName;

    public BookParcelable(String bookName) {
        this.bookName = bookName;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
    }

    public static final Parcelable.Creator<BookParcelable> CREATOR = new Parcelable.Creator<BookParcelable>() {
        @Override public BookParcelable createFromParcel(Parcel source) {
            return new BookParcelable(source);
        }

        @Override public BookParcelable[] newArray(int size) {
            return new BookParcelable[size];
        }
    };

    private BookParcelable(Parcel source) {
        bookName = source.readString();
    }
}