package inheritance.best.practice;

public class VedioMediaFile extends MediaFile {

    private final String duration;

    public VedioMediaFile(String title, String fileName, String duration) {
        super(title, fileName);
        this.duration = duration;
    }

    @Override
    void play() {
        System.out.println("Playing video file: " + fileName);
    }

    @Override
    public String getDetails() {
        return super.getDetails() + ", Duration: " + duration;
    }
}
