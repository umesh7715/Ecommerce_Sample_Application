package com.andromesh.ecommercesassesment.bindingUtils;


import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;

public class ECommerceBindingUtils {

    public static String getColor(Product product) {

        return product.getVariants().get(0).getColor();
    }

    public static String getSize(Product product) {

        return product.getVariants().get(0).getSize() + "";
    }

    public static String getVariantPrice(Product product) {

        return "INR " + product.getVariants().get(0).getPrice();
    }

    public static String getSortAttribute(Product product) {

        String attribute = " - ";
        if (product.getViewCount() != null && product.getViewCount() != 0) {
            attribute = "Views - " + product.getViewCount();
        }

        if (product.getShares() != null && product.getShares() != 0) {
            attribute = "Shares - " + product.getShares();
        }

        if (product.getOrderCount() != null && product.getOrderCount() != 0) {
            attribute = "Ordered - " + product.getOrderCount();
        }

        return attribute;
    }
}
