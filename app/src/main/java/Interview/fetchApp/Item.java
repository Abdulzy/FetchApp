package Interview.fetchApp;
/**
 * Represents an item with a listId and name.
 */
public class Item {
    private int listId;
    private int id;
    private String name;

    /**
     * Initializes an Item object.
     *
     * @param listId The listId of the item.
     * @param id   The id of the item.
     * @param name   The name of the item.
     */
    public Item(int listId,int id, String name) {
        this.listId = listId;
        this.id = id;
        this.name = name;
    }

    /**
     * Get the listId of the item.
     *
     * @return The listId of the item.
     */
    public int getListId() {
        return listId;
    }


    /**
     * Get the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the id of the item.
     *
     * @return The id of the item.
     */
    public int getId() {
        return id;
    }
}
