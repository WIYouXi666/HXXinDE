package HuanXiangLIB.活动;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.EntityGroup;
import mindustry.gen.Entityc;
import mindustry.gen.Player;
import mindustry.gen.Syncc;

import java.nio.FloatBuffer;

public class 活动实体 implements Syncc {
    public 活动基类 type = 活动基类.NULL;
    public transient int id = EntityGroup.nextId();

    /**
     * @param player
     * @return
     */
    @Override
    public boolean isSyncHidden(Player player) {
        return false;
    }

    /**
     * @return
     */
    @Override
    public long lastUpdated() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public long updateSpacing() {
        return 0;
    }

    /**
     *
     */
    @Override
    public void afterSync() {

    }

    /**
     *
     */
    @Override
    public void handleSyncHidden() {

    }

    /**
     *
     */
    @Override
    public void interpolate() {

    }

    /**
     * @param l
     */
    @Override
    public void lastUpdated(long l) {

    }

    /**
     * @param reads
     */
    @Override
    public void readSync(Reads reads) {

    }

    /**
     * @param floatBuffer
     */
    @Override
    public void readSyncManual(FloatBuffer floatBuffer) {

    }

    /**
     * @param <T>
     * @return
     */
    @Override
    public <T extends Entityc> T self() {
        return null;
    }

    /**
     * @param <T>
     * @return
     */
    @Override
    public <T> T as() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isAdded() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isLocal() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isNull() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isRemote() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean serialize() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public int classId() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public int id() {
        return id;
    }

    /**
     *
     */
    @Override
    public void add() {

    }

    /**
     *
     */
    @Override
    public void afterRead() {

    }
    @Override public void id(int id){ this.id = id; }


    /**
     * @param reads
     */
    @Override
    public void read(Reads reads) {

    }

    /**
     *
     */
    @Override
    public void remove() {

    }

    /**
     *
     */
    @Override
    public void snapInterpolation() {

    }

    /**
     *
     */
    @Override
    public void snapSync() {

    }

    /**
     *
     */
    @Override
    public void update() {

    }

    /**
     * @param writes
     */
    @Override
    public void write(Writes writes) {

    }

    /**
     * @param l
     */
    @Override
    public void updateSpacing(long l) {

    }

    /**
     * @param writes
     */
    @Override
    public void writeSync(Writes writes) {

    }

    /**
     * @param floatBuffer
     */
    @Override
    public void writeSyncManual(FloatBuffer floatBuffer) {

    }
}
