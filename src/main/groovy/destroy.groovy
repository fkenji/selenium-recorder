import br.com.seibzhen.VideoRecorder

if (VideoRecorder.get().isRecording()) {
    VideoRecorder.get().stopRecording();
}

println "Destroying files. . ."

def files = new File("${System.getProperty("user.home")}/Movies").listFiles().grep{ f -> f.getName().contains(".avi")}


files.each {
    file ->
        println "Deleting $file; result=> ${file.delete()?'OK':'NOK'}"
}

println "Done."