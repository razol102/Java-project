package definition.terminatoin;

public interface TerminationDef {
    boolean checkTermination(int ticks, long startTimeInSeconds, boolean userTermination);
    long getTicks();
    long getSeconds();
    public boolean getSecondsDefine();
    public boolean getTicksDefine();
}
