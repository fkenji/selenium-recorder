package br.com.seibzhen;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class VideoRecorder {

    private static ScreenRecorder screenRecorder;
    private static VideoRecorder instance;
    private boolean isRecording;

    private VideoRecorder() {
        isRecording = false;
    }

    public static br.com.seibzhen.VideoRecorder get() {
        if (instance == null) {
            instance = new VideoRecorder();
        }

        return instance;
    }

    public void startRecording(final String fileName) throws IOException, AWTException {

        isRecording = true;

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        this.screenRecorder = new ScreenRecorder(gc,
                new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null) {
            @Override
            protected File createMovieFile(Format fileFormat) throws IOException {
                File movieFolder = new File("/TestMovies");
                File f = new File(movieFolder,//
                        fileName + "." + Registry.getInstance().getExtension(fileFormat));
                return f;
            }


        };
        this.screenRecorder.start();

    }

    public void stopRecording() throws Exception {
        isRecording = false;
        this.screenRecorder.stop();
    }

    public boolean isRecording() {
        return isRecording;
    }
}
