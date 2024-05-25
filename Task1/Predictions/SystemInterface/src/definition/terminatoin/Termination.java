package definition.terminatoin;

import java.time.Instant;

public class Termination implements  TerminationDef{
    private final long ticks, seconds;
    private final boolean secondsDefine, ticksDefine;

    public Termination(long ticks, long seconds, boolean secondsDefine, boolean ticksDefine) {
        this.ticks = ticks;
        this.seconds = seconds;
        this.secondsDefine = secondsDefine;
        this.ticksDefine = ticksDefine;
    }

    @Override
    public boolean checkTermination(long ticks, long startTimeInSeconds) {
        Instant instant = Instant.now();
        long currentTimeInSeconds = instant.getEpochSecond();
        return (this.ticks == ticks || this.seconds <= (currentTimeInSeconds - startTimeInSeconds));
    }

    public long getTicks() {
        return ticks;
    }

    public long getSeconds() {
        return seconds;
    }
}
