package car.com.cartique.client.model;

/**
 * Created by napster on 1/16/18.
 */

public class GridMenu {
    private String title;
    private int menuIcon;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    private byte[] bytes;

    public GridMenu(String title, int menuIcon) {
        this.title = title;
        this.menuIcon = menuIcon;
    }
    public GridMenu(String title, byte[] bytes) {
        this.title = title;
        this.bytes = bytes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
    }
}
