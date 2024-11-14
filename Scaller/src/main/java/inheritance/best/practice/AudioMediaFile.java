package inheritance.best.practice;

public class AudioMediaFile extends MediaFile {

    private final String artist;

    public AudioMediaFile(String title, String fileName, String artist) {
        super(title, fileName);
        this.artist = artist;
    }

    @Override
    void play() {
        System.out.println("Playing audio file: " + fileName);
    }

    @Override
    public String getDetails() {
        return super.getDetails() + ", Artist: " + artist ;
    }
}
