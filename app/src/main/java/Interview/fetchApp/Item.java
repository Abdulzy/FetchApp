package Interview.fetchApp;

public class Item {
    private int listId;
    private int id;
    private String name;

    public Item(int listId,int id, String name) {
        this.listId = listId;
        this.id = id;
        this.name = name;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}