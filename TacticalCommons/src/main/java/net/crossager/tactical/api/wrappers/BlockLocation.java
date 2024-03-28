package net.crossager.tactical.api.wrappers;

import org.bukkit.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a block location in the world, defined by its x, y, and z coordinates.
 */
public class BlockLocation {

    private int x;
    private int y;
    private int z;

    /**
     * Creates a new BlockLocation object with the given coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public BlockLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the x coordinate of this BlockLocation.
     *
     * @return the x coordinate
     */
    public int x() {
        return x;
    }

    /**
     * Returns the y coordinate of this BlockLocation.
     *
     * @return the y coordinate
     */
    public int y() {
        return y;
    }

    /**
     * Returns the z coordinate of this BlockLocation.
     *
     * @return the z coordinate
     */
    public int z() {
        return z;
    }

    /**
     * Sets the x coordinate of this BlockLocation to the specified value.
     *
     * @param x the new x coordinate
     * @return this BlockLocation object
     */
    @NotNull
    public BlockLocation x(int x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the y coordinate of this BlockLocation to the specified value.
     *
     * @param y the new y coordinate
     * @return this BlockLocation object
     */
    @NotNull
    public BlockLocation y(int y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the z coordinate of this BlockLocation to the specified value.
     *
     * @param z the new z coordinate
     * @return this BlockLocation object
     */
    @NotNull
    public BlockLocation z(int z) {
        this.z = z;
        return this;
    }

    /**
     * Adds the given x, y, and z values to this BlockLocation's coordinates.
     *
     * @param x the amount to add to the x coordinate
     * @param y the amount to add to the y coordinate
     * @param z the amount to add to the z coordinate
     * @return this BlockLocation object
     */
    @NotNull
    public BlockLocation add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Returns a Location object representing this BlockLocation.
     *
     * @return a new Location object with the same coordinates as this BlockLocation
     */
    @NotNull
    public Location toLocation() {
        return new Location(null, x, y, z);
    }

    /**
     * Compresses the coordinates of this BlockLocation into a single long value.
     *
     * @return a long value representing this BlockLocation's coordinates
     */
    @Contract(pure = true)
    public long compress() {
        return compress(x, y, z);
    }

    /**
     * Creates a new BlockLocation object from the given Location.
     *
     * @param location the Location to create a BlockLocation from
     * @return a new BlockLocation object with the same coordinates as the given Location
     */
    @NotNull
    public static BlockLocation fromLocation(@NotNull Location location) {
        return new BlockLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Compresses the given block coordinates into a single long integer for efficient storage.
     * This method uses a bit-shifting algorithm, as taken from the NMS BlockPosition class.
     *
     * @param x the x-coordinate of the block
     * @param y the y-coordinate of the block
     * @param z the z-coordinate of the block
     * @return a long integer representing the compressed block location
     */
    public static long compress(int x, int y, int z) {
        long compressed = 0L;
        compressed |= ((long)x & 67108863) << 38;
        compressed |= (long)y & 4095;
        compressed |= ((long)z & 67108863) << 12;
        return compressed;
    }

    /**
     * Decompresses the given long integer into a BlockLocation object.
     *
     * @param compressed the compressed block location as a long integer
     * @return the decompressed BlockLocation object
     */
    public static BlockLocation decompress(long compressed) {
        return new BlockLocation(
                (int)(compressed >> 38),
                (int)(compressed << 52 >> 52),
                (int)(compressed << 26 >> 38)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockLocation that = (BlockLocation) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "BlockLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
