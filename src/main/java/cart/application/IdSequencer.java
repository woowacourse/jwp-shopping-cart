package cart.application;

public class IdSequencer {

    private static int sequence = 0;

    public static int get() {
        return ++sequence;
    }
}
