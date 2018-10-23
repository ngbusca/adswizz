
class Product {

    private String name;
    private double price;
    private String description;
    
    public Product(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void main(String []args) {
        if (args.length == 0) {
            System.out.println("usage: <product name> <product price> <description>");
            return;
        }
        
        for (int i = 0; i < args.length; i += 3) {
            Product p = new Product(args[i], Double.parseDouble(args[i+1]), args[i+2]);
            System.out.println("created new product "+p.name+" "+p.price+" "+p.description);
        }
    }
}
