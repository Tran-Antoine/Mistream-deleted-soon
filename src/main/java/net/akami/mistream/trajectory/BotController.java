package net.akami.mistream.trajectory;

import net.akami.mistream.gamedata.BoostDataProvider;
import net.akami.mistream.gamedata.CarInfoProvider;
import net.akami.mistream.gamedata.DataProvider;
import net.akami.mistream.trajectory.list.DiagonalKickoff;
import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;

import java.util.*;
import java.util.function.Function;

public class BotController implements DataProvider {

    private static final int MAX_PREDICTIONS = 3;
    private final Queue<OutputSequence> queue;
    private final List<Function<BotController, OutputSequence>> generator;
    private final List<DataProvider> dataProviders;

    private OutputSequence currentSequence;

    public BotController() {
        this.queue = new LinkedList<>();
        this.generator = loadSuppliers();
        this.dataProviders = loadProviders();
    }

    private List<Function<BotController, OutputSequence>> loadSuppliers() {
        return Arrays.asList(
                DiagonalKickoff::new
        );
    }

    private List<DataProvider> loadProviders() {
        return Arrays.asList(
                new BoostDataProvider(),
                new CarInfoProvider()
        );
    }

    @Override
    public void update(GameTickPacket packet) {
        dataProviders.forEach((dataProvider -> dataProvider.update(packet)));
        updateQueue(packet);
    }

    private void updateQueue(GameTickPacket packet) {
        // If several trajectories are already planned, we avoid loading any more
        if(queue.size() >= MAX_PREDICTIONS) {
            return;
        }

        for(Function<BotController, OutputSequence> supplier : generator) {
            OutputSequence sequence = supplier.apply(this);
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

    public Optional<ControllerState> provideController() {
        if(currentSequence == null || currentSequence.isStopped()) {
            this.currentSequence = queue.poll();
            if(currentSequence == null) {
                return Optional.empty();
            }
        }
        return Optional.of(currentSequence.apply());
    }

    public <T extends DataProvider> T data(Class<T> clazz) {
        for(DataProvider provider : dataProviders) {
            if(provider.getClass().equals(clazz)) {
                return (T) provider;
            }
        }
        throw new IllegalStateException();
    }

    public boolean hasNothingPlanned() {
        return currentSequence == null && queue.size() == 0;
    }
}
