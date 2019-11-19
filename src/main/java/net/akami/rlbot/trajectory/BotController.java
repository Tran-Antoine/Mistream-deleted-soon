package net.akami.rlbot.trajectory;

import net.akami.rlbot.trajectory.list.DiagonalKickoff;
import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;

import java.util.*;
import java.util.function.Supplier;

public class BotController {

    private static final int MAX_PREDICTIONS = 3;
    private final Queue<OutputSequence> queue;
    private final List<Supplier<OutputSequence>> generator;

    private OutputSequence current;

    public BotController() {
        this.queue = new LinkedList<>();
        this.generator = loadSuppliers();
    }

    private List<Supplier<OutputSequence>> loadSuppliers() {
        return Arrays.asList(
                DiagonalKickoff::new
        );
    }

    public void update(GameTickPacket packet) {
        // If several trajectories are already planned, we avoid loading any more
        if(queue.size() >= MAX_PREDICTIONS) {
            return;
        }

        for(Supplier<OutputSequence> supplier : generator) {
            OutputSequence sequence = supplier.get();
            if(sequence.isSuitable(packet, waitTime(), queue)) {
                sequence.queue(queue);
            }
        }
    }

    private int waitTime() {
        return queue
                .stream()
                .mapToInt(OutputSequence::frameExecutionsNumber)
                .reduce((a, b) -> a+b)
                .orElse(0);
    }

    public Optional<ControllerState> findController() {
        if(current == null || current.isStopped()) {
            this.current = queue.poll();

            if(current == null) {
                return Optional.empty();
            }
        }
        return Optional.of(current.apply());
    }

    public OutputSequence getCurrent() {
        return current;
    }
}
