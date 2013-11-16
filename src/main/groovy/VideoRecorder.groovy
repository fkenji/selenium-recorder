/**
 * Created with IntelliJ IDEA.
 * User: hien
 * Date: 11/16/13
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
package br.com.seibzhen

import org.monte.media.Format
import org.monte.media.FormatKeys
import org.monte.media.math.Rational
import org.monte.screenrecorder.ScreenRecorder

import java.awt.*

import static org.monte.media.FormatKeys.*
import static org.monte.media.VideoFormatKeys.*

class VideoRecorder {

    private static ScreenRecorder screenRecorder;
    private static VideoRecorder instance;


    private VideoRecorder() {
    }

    static def get() {
        if (instance == null) {
            instance = new VideoRecorder();
        }
        return instance;
    }

    def startRecording() {

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        this.screenRecorder = new ScreenRecorder(gc,
                new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f,
                        KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(30)),
                null);
        this.screenRecorder.start();

    }

    public void stopRecording() throws Exception {
        this.screenRecorder.stop();
    }


}
