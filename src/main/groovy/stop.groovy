import br.com.seibzhen.VideoRecorder

println "Stopping to record. . ."

if (VideoRecorder.get().isRecording()) {
    VideoRecorder.get().stopRecording();
}
println "Done."