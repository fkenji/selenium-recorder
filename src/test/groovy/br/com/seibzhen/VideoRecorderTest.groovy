package br.com.seibzhen

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore
class VideoRecorderTest {

    def recorder;

    @Before
    void setUp() {
        recorder = br.com.seibzhen.VideoRecorder.get();
    }

    @Test
    void itShouldRecordWhenStarted() {
        recorder.startRecording();

        Thread.sleep(10000L);

        recorder.stopRecording();

    }
}
