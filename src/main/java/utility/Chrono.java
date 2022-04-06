package utility;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Chrono {
    private long startTime=0;

    public void start(){
        startTime = System.nanoTime();
    }

    public void stop(String label){
        long durationMs = (System.nanoTime() - startTime)/1000000;
        long hours, minutes, seconds, milis;

        hours = durationMs / (1000 * 60 * 60);
        durationMs -= hours * (1000 * 60 * 60);
        minutes = durationMs / (1000 * 60);
        durationMs -= minutes * (1000 * 60);
        seconds = durationMs / 1000;
        durationMs -= seconds * 1000;
        milis = durationMs;

        log.info("Total duration for {} : {}h {}m {}s {}ms", label, hours, minutes, seconds, milis);
    }
}
