public class Database {
    private Table users = new Table(User.class, 100);
    private Table products = new Table(Product.class, 100);
    private Table orders = new Table(Order.class, 100);
    private Table categories = new Table(Category.class, 100);

    public Table get(Admin admin, String tableName) throws RuntimeException
    {
        if (admin.isSaved()) {
            if (tableName.toLowerCase()=="users") {
                if (admin.getRole()==Role.superadmin || admin.getRole()==Role.customer) {
                    return users;
                }
                throw new RuntimeException("You don't have the permission to access this data");
            }else if(tableName.toLowerCase()=="products"){
                if (admin.getRole()==Role.superadmin || admin.getRole()==Role.product) {
                    return products;
                }
                throw new RuntimeException("You don't have the permission to access this data");
            }else if(tableName.toLowerCase()=="orders"){
                if (admin.getRole()==Role.superadmin || admin.getRole()==Role.order) {
                    return orders;
                }
                throw new RuntimeException("You don't have the permission to access this data");
            }else if(tableName.toLowerCase()=="categories"){
                if (admin.getRole()==Role.superadmin || admin.getRole()==Role.product) {
                    return categories;
                }
                throw new RuntimeException("You don't have the permission to access this data");
            }
            throw new RuntimeException("this Table does not exist");
        }
        throw new RuntimeException("this Admin does not exist");
    }
}
