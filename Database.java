public class Database {
    private Table users = new Table(User.class, 100);
    private Table products = new Table(Product.class, 100);
    private Table orders = new Table(Order.class, 100);
    private Table categories = new Table(Category.class, 100);

    public Table get(Admin admin, String tableName) throws RuntimeException
    {
        if (admin.isSaved()) {
            if (tableName.toLowerCase()=="users") {
                return users;
            }else if(tableName.toLowerCase()=="products"){
                return products;
            }else if(tableName.toLowerCase()=="orders"){
                return orders;
            }else if(tableName.toLowerCase()=="categories"){
                return categories;
            }
            throw new RuntimeException("this Table does not exist");
        }
        throw new RuntimeException("this Admin does not exist");
    }
}
