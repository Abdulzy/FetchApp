package Interview.fetchApp;

public class Item {
    private int listId;
    private String name;

    public Item(int listId, String name) {
        this.listId = listId;
        this.name = name;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }
}
