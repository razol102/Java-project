package DTO.definition;

public class TerminationDTO {
    private final long ticks;
    private final long seconds;

    public TerminationDTO(long ticks, long seconds) {
        this.ticks = ticks;
        this.seconds = seconds;
    }

    public long getTicks() {
        return ticks;
    }

    public long getSeconds() {
        return seconds;
    }
}
