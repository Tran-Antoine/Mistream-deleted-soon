package net.akami.rlbot.gamedata;

import rlbot.flat.GameTickPacket;

public interface DataProvider {

    void update(GameTickPacket packet);
}
