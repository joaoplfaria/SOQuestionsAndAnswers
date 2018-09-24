package com.desenvolvimento.at.soquestionsandanswers.domain;

public class StackOverflowRepo {

    private boolean is_answered;
    private String title;
    private String link;
    private String tags;

    public boolean getIs_answered() {
        return is_answered;
    }

    public void setIs_answered(boolean is_answered) {
        this.is_answered = is_answered;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

/*    @Override
    public String toString() {
        return "Titulo: " + getTitle()
                + "\nEstá respondida: " + getIs_answered()
                + "\nLink: " + getLink()
                + "\nTags: " + getTags();
    }*/
}
