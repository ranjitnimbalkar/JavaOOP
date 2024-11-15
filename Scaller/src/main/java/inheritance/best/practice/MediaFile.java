package inheritance.best.practice;

public abstract class MediaFile {

    protected String title;
    protected String fileName;

    public MediaFile(String title, String fileName) {
        this.title = title;
        this.fileName = fileName;
    }

    abstract void play();

    public String getDetails() {
        return "Title: " + title + ", File: " + fileName;
    }

}
