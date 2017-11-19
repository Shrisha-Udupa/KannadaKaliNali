package com.example.android.kannadakalinali;

/**
 * Created by Shrisha on 18-Mar-17.
 */

public class Words {

    private static final int NO_IMAGE_PROVIDED = -1;

    private String mEnglishTranslation;
    private String mKannadaTranslation;
    private int mImageResourceId=NO_IMAGE_PROVIDED;

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }

    private int mAudioResourceId;


    public int getmImageResourceId() {
        return mImageResourceId;
    }



    public  Words(String english, String kannada, int imageResourceId, int audioResourceId){      //Constructor for creating objects with two Strings and an image used in Numbers, Colors and Family activities
        mEnglishTranslation=english;
        mKannadaTranslation=kannada;
        mImageResourceId=imageResourceId;
        mAudioResourceId=audioResourceId;
    }

    public  Words(String english, String kannada,int audioResourceId){      //Constructor for creating objects with two Strings only used in Phrases activity
        mEnglishTranslation=english;
        mKannadaTranslation=kannada;
        mAudioResourceId=audioResourceId;
    }

    public String getmEnglishTranslation() {
        return mEnglishTranslation;
    }


    public String getmKannadaTranslation() {
        return mKannadaTranslation;
    }


    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {      //Overriding the toString() method to print the current state of any object to the logs
        return "Words{" +
                "mEnglishTranslation='" + mEnglishTranslation + '\'' +
                ", mKannadaTranslation='" + mKannadaTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
