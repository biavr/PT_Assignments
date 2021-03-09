package bll;

import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
/**
 * This class invokes the methods from the corresponding dao classes
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class ProductBLL {
    /**
     * This method calls findProductById from ProductDAO
     * @param id
     * @return
     */
    public static Product findProuctById(int id){
        Product p = ProductDAO.findById(id);
        return p;
    }

    /**
     * This method calls findProductByName from ProductDAO
     * @param name
     * @return
     */
    public static Product findProductByName(String name){
        Product p = ProductDAO.findByName(name);
        return p;
    }

    /**
     * This method calls insertProduct from ProductDAO
     * @param product
     * @return 1 if succes, -1 otherwise
     */
    public static int insertProduct(Product product){
        return ProductDAO.insert(product);
    }

    /**
     * This method calls deleteProduct from ProductDAO
     * @param name
     * @return 1 if succes, -1 otherwise
     */
    public static int deleteProduct(String name){
        return ProductDAO.delete(name);
    }

    /**
     * This method calls updateProductPrice from ProductDAO
     * @param id
     * @param newprice
     * @return
     */
    public static int updateProductPrice(int id, float newprice){
        return ProductDAO.updatePrice(id,newprice);
    }
    /**
     * This method calls updateProductQuantity from ProductDAO
     * @param id
     * @param newquantity
     * @return
     */
    public static int updateProductQuantity(int id, int newquantity){
        return ProductDAO.updateQuantity(id,newquantity);
    }

    /**
     * This method calls findAllProducts from ProductDAO
     * @return
     */
    public static ArrayList<Product> findAllClients() {
        return ProductDAO.findAllProducts();
    }

}
