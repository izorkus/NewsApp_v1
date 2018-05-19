package com.example.android.newsapp_v1;

public class News {
    private String mSectionName;
    private String mWebTitle;
    private String mWebUrl;
    private String mWebPublicationDate;
    private String mAuthor;

    public News(String SectionName, String WebTitle, String WebUrl, String WebPublicationDate, String Author) {
        mSectionName = SectionName;
        mWebTitle = WebTitle;
        mWebUrl = WebUrl;
        mWebPublicationDate = WebPublicationDate;
        mAuthor = Author;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getRawDataStr() {
        return mWebPublicationDate;
    }

    public String getAuthor() {
        if (mAuthor == "") {
            return null;
        } else {
            return mAuthor;
        }
    }

    @Override
    public String toString() {
        return "News{" +
                "mSectionName='" + mSectionName + '\'' +
                ", mWebTitle='" + mWebTitle + '\'' +
                ", mWebUrl='" + mWebUrl + '\'' +
                ", mWebPublicationDate='" + mWebPublicationDate + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                '}';
    }
}
