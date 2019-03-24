package ua.kiev.prog;

/**
 * Created by Diesel on 23.03.2019.
 */
public class Photo {

    private long id;
    private String fileName;
    private byte[] bytes;

    public Photo(long id, String fileName, byte[] bytes) {
        this.id = id;
        this.fileName = fileName;
        this.bytes = bytes;
    }

    public long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
