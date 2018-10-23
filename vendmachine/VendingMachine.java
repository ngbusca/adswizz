import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

class VendingMachine {
    private HashMap<String, Product> products = new HashMap<>();
    private BufferedWriter log = null;
    private String user = null;

    private void close() {
        try {
            BufferedWriter state = new BufferedWriter(new FileWriter("state.csv"));
            Iterator it = products.values().iterator();
            while (it.hasNext()) {
                Product p = (Product) it.next();
                state.write(p.getName()+", "+p.getPrice()+", "+p.getDescription()+"\n");
            }
            state.close();
            log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void log(String message) {
        if (log == null) 
            try {
                log = new BufferedWriter(new FileWriter("transactions.log", true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            log.append(message+" "+(new Date())+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String name, double price, String description) {
        if (products.containsKey(name))
            return;
        Product p = new Product(name, price, description);
        products.put(name, p);
        log(user + " added product "+name+" "+price+" "+description);
    }

    public void changePrice(String name, double newprice) {
        Product p = products.get(name);
        if (p != null) {
            log(user+" price of "+name+" changed from "+p.getPrice()+" to "+newprice);
            p.setPrice(newprice);
        } else {
            System.out.println("ERROR: attempting to change the price of the non-existing product "+name);
            log(user+" attemtped to change the price of the inexistent product "+name+" to "+newprice);
        }
    }

    public void changeName(String oldname, String newname) {
        Product value = products.remove(oldname);
        products.put(newname, value);
        log(user + "change the name of "+oldname+" to "+newname);
    }

    public Product buy(String name) {
        Product p = products.get(name);
        if (p == null)
            System.out.println("ERROR: attemtping to buy the non-existing product "+name);
        return p;
    }

    public VendingMachine(String user) {
        this.user = user;
        try {
            BufferedReader br = new BufferedReader(new FileReader("state.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                addProduct(fields[0], Double.parseDouble(fields[1]), fields[2]);
            }
            br.close();
        } catch(IOException e) {
            System.out.println("INFO: the vending machine is empty");
        }
    }

    public static void main(String[] args) {
        // usage: args[0] = username
        VendingMachine vm = new VendingMachine(args[0]);
        for (int i=0; i < 10; ++i) 
            vm.addProduct("name_"+i,i,"comment "+i);
        
        vm.close();
    }
}
