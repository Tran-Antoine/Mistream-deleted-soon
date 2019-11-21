package net.akami.rlbot.gamedata;

import net.akami.rlbot.vector.Vector3f;
import rlbot.flat.GameTickPacket;
import rlbot.flat.Physics;
import rlbot.flat.Rotator;
import rlbot.flat.Vector3;

import java.util.function.BiFunction;
import java.util.function.Function;

public class LocationsProvider implements DataProvider {

    private Vector3f botLocation;
    private Vector3f botDirection;
    private Vector3f playerLocation;

    @Override
    public void update(GameTickPacket packet) {
        BiFunction<Integer, Function<Physics, Vector3>, Vector3f> gen =
                (i, func) -> new Vector3f(func.apply(packet.players(i).physics()));
        this.botLocation = gen.apply(0, Physics::location);
        this.playerLocation = gen.apply(1, Physics::location);
        Rotator rotation = packet.players(0).physics().rotation();
        float yaw = -rotation.yaw();
        float pitch = rotation.pitch();

        double cp = Math.cos(pitch);
        double sp = Math.sin(pitch);
        double cy = Math.cos(yaw);
        double sy = Math.sin(yaw);

        this.botDirection = new Vector3f(-cp * cy, -cp * sy, -sp);
    }

    public Vector3f getBotLocation() {
        return botLocation;
    }

    public Vector3f getPlayerLocation() {
        return playerLocation;
    }

    public Vector3f getBotDirection() {
        return botDirection;
    }
}
