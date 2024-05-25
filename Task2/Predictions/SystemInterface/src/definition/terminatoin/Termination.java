package definition.terminatoin;

import java.time.Instant;

public class Termination implements TerminationDef{
    private final long seconds;
    private final int ticks;
    private final boolean secondsDefine, ticksDefine;

    public Termination(int ticks, long seconds, boolean secondsDefine, boolean ticksDefine) {
        this.ticks = ticks;
        this.seconds = seconds;
        this.secondsDefine = secondsDefine;
        this.ticksDefine = ticksDefine;
    }

    @Override
    public boolean checkTermination(int ticks, long startTimeInSeconds, boolean userTermination) {
        boolean terminatedBySeconds = false;
        boolean terminatedByTicks = false;
        Instant instant = Instant.now();
        long currentTimeInSeconds = instant.getEpochSecond();
        if(secondsDefine)
            terminatedBySeconds = this.seconds <= (currentTimeInSeconds - startTimeInSeconds);
        if(ticksDefine)
            terminatedByTicks = this.ticks == ticks;
        return (terminatedByTicks || terminatedBySeconds || userTermination);
    }

    @Override
    public long getTicks() {
        return ticks;
    }

    @Override
    public long getSeconds() {
        return seconds;
    }

    @Override
    public boolean getSecondsDefine(){
        return secondsDefine;
    }
    @Override
    public boolean getTicksDefine(){
        return secondsDefine;
    }
}
