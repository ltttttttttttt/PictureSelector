package com.luck.picture.lib.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：luck
 * @date：2016-12-31 15:21
 * @describe：MediaFolder Entity
 */

public class LocalMediaFolder implements Parcelable {
    /**
     * 文件夹名字
     */
    private String name;
    /**
     * 该文件夹内第一个媒体的名称
     */
    private String firstImagePath;
    /**
     * 媒体数量
     */
    private int imageNum;
    /**
     * If the selected num
     */
    private int checkedNum;
    /**
     * If the selected
     */
    private boolean isChecked;

    /**
     * type
     * TYPE_ALL = 0;
     * TYPE_IMAGE = 1
     * TYPE_VIDEO = 2
     * TYPE_AUDIO = 3
     */
    private int ofAllType = -1;
    /**
     * Whether or not the camera ,是否相机文件夹?
     */
    private boolean isCameraFolder;

    /**
     * 媒体文件集合
     */
    private List<LocalMedia> images = new ArrayList<LocalMedia>();

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public List<LocalMedia> getImages() {
        return images == null ? new ArrayList<LocalMedia>() : images;
    }

    public void setImages(List<LocalMedia> images) {
        this.images = images;
    }

    public int getCheckedNum() {
        return checkedNum;
    }

    public void setCheckedNum(int checkedNum) {
        this.checkedNum = checkedNum;
    }

    public int getOfAllType() {
        return ofAllType;
    }

    public void setOfAllType(int ofAllType) {
        this.ofAllType = ofAllType;
    }

    public boolean isCameraFolder() {
        return isCameraFolder;
    }

    public void setCameraFolder(boolean cameraFolder) {
        isCameraFolder = cameraFolder;
    }

    public LocalMediaFolder() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.firstImagePath);
        dest.writeInt(this.imageNum);
        dest.writeInt(this.checkedNum);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ofAllType);
        dest.writeByte(this.isCameraFolder ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.images);
    }

    protected LocalMediaFolder(Parcel in) {
        this.name = in.readString();
        this.firstImagePath = in.readString();
        this.imageNum = in.readInt();
        this.checkedNum = in.readInt();
        this.isChecked = in.readByte() != 0;
        this.ofAllType = in.readInt();
        this.isCameraFolder = in.readByte() != 0;
        this.images = in.createTypedArrayList(LocalMedia.CREATOR);
    }

    public static final Creator<LocalMediaFolder> CREATOR = new Creator<LocalMediaFolder>() {
        @Override
        public LocalMediaFolder createFromParcel(Parcel source) {
            return new LocalMediaFolder(source);
        }

        @Override
        public LocalMediaFolder[] newArray(int size) {
            return new LocalMediaFolder[size];
        }
    };
}
