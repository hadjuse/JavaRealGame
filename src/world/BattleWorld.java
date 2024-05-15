package world;

import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class BattleWorld {
    private final String[] rooms = new String[]{
            String.format("%s/src/world/Level/level1.csv", System.getProperty("user.dir")),
            String.format("%s/src/world/Level/level2.csv", System.getProperty("user.dir")),
            String.format("%s/src/world/Level/level3.csv", System.getProperty("user.dir")),
    };
    private TileMap room1;
    private TileMap room2;
    private String roomName;
    public BattleWorld(Stage stage, String roomName) throws FileNotFoundException {
        room1 = new TileMap(rooms[0], stage);
        room2 = new TileMap(rooms[1], stage);
    }
    public TileMap getRoom1() {
        return room1;
    }
    public TileMap getRoom2() {
        return room2;
    }
    public void setRoom1(TileMap room1) {
        this.room1 = room1;
    }
    public void setRoom2(TileMap room2) {
        this.room2 = room2;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    /*
    public void genWorld(String roomName){
        if(roomName.equals("room1")){
            room1.placeItemEntity();
        }else if(roomName.equals("room2")){
            setRoom2(new TileMap(rooms[1], stage));
        }
    }*/
}
