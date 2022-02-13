package com.safi;

public class RoomsSet {

    private String path;
    protected int nRoom;
    private String[] roomName;
    private int[] dirtValue;

    RoomsSet(){}
    RoomsSet(String dir, int n){
        this.path = dir;
        this.nRoom = n;
        roomName = new String[nRoom];
        dirtValue = new int[nRoom];
    }

    //Getters
    public String getPath (){
        return this.path;
    }
    public int getnRoom (){
        return this.nRoom;
    }
    public String[] getRoomName(){
        return this.roomName;
    }
    public int[] getDirtValue(){
        return this.dirtValue;
    }

    //Setters
    public void setAll (String path, int nRoom, String[] roomNames, int[] dirtValue){
        this.path = path;
        this.nRoom = nRoom;
        this.roomName = roomNames;
        this.dirtValue = dirtValue;
    }
    public void setPath (String path){
        this.path = path;
    }
    public void setnRoom (int n) {
        this.nRoom = n;
    }
    public void setRoomNameByIndex (String value, int i){
        this.roomName[i] = value;
    }
    public void setDirtValueByIndex (int value, int i){
        this.dirtValue[i] = value;
    }


}
