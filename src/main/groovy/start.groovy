import br.com.seibzhen.VideoRecorder

def videoName = ""

if (params["name"] != null && params["name"].trim().length() > 0) {
    videoName = params["name"];
}

if (!VideoRecorder.get().isRecording()) {
    println "Starting to record ${videoName}.avi"
    VideoRecorder.get().startRecording(videoName);
} else {
    println "Already recording."
}


