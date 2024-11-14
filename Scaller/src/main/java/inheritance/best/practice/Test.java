package inheritance.best.practice;

public class Test {

    public static void main(String[] args) {
        MediaFile video = new VedioMediaFile("Video File", "1.mp4", "3:00");
        MediaFile audio = new AudioMediaFile("Audio File", "2.mp2", "rajubhai");
        video.play();
        audio.play();
        StringBuilder build = new StringBuilder();
        build.append("Test");
        build.toString();
    }

}
