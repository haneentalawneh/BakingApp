package Recipes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haneenalawneh on 9/18/17.
 */

public class Step implements Parcelable {
    int step_id;
    String step_short_description;
    String step_description;
    String step_video_URL;
    String step_thumbnail_URL;

    public Step(int id, String short_description, String description, String video_URL, String thumbnail_URL) {
        step_id = id;
        step_short_description = short_description;
        step_description = description;
        step_video_URL = video_URL;
        step_thumbnail_URL = thumbnail_URL;
    }

    private Step(Parcel in) {
        step_id = in.readInt();
        step_short_description = in.readString();
        step_description = in.readString();
        step_video_URL = in.readString();
        step_thumbnail_URL = in.readString();
    }

    public int getStepId() {
        return step_id;
    }

    public String getStepDescription() {
        return step_description;
    }

    public String getStepShortDescription() {
        return step_short_description;
    }

    public String getStepThumbnailURL() {
        return step_thumbnail_URL;
    }

    public String getStepVideoURL() {
        return step_video_URL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(step_id);
        parcel.writeString(step_short_description);
        parcel.writeString(step_description);
        parcel.writeString(step_video_URL);
        parcel.writeString(step_thumbnail_URL);


    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
