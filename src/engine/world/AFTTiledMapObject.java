package engine.world;

/**
 *
 * @author KiQDominaN
 */
public class AFTTiledMapObject {

    private final int[][] map;
    private final int columns, rows, tile_width, tile_height;
    public final String tileset;

    public AFTTiledMapObject(String tileset, int columns, int rows, int tile_width, int tile_height) {
        this.tileset = tileset;
        this.columns = columns;
        this.rows = rows;
        this.tile_width = tile_width;
        this.tile_height = tile_height;

        map = new int[rows][columns];
    }

    public void setMap(int[] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            int column = i % columns;
            int row = (i - column) / rows;
            setTile(row, column, tiles[i]);
        }
    }

    public int getTile(int x, int y) {
        return map[x][y];
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getTileWidth() {
        return tile_width;
    }

    public int getTileHeight() {
        return tile_height;
    }

    private void setTile(int x, int y, int tile) {
        map[x][y] = tile;
    }
}
