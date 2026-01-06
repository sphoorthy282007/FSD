package com.klu;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class App {

    public static void main(String[] args) {

        /* ==============================
           STEP 1: INSERT DATA (RUN ONCE)
           ============================== */
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(new Product("Laptop", "Electronics", 75000, 10));
        session.save(new Product("Mouse", "Electronics", 800, 50));
        session.save(new Product("Keyboard", "Electronics", 2500, 30));
        session.save(new Product("Chair", "Furniture", 4500, 20));
        session.save(new Product("Table", "Furniture", 9000, 5));
        session.save(new Product("Pen", "Stationery", 20, 200));
        session.save(new Product("Notebook", "Stationery", 80, 150));

        tx.commit();
        session.close();
        System.out.println("Products Inserted Successfully");

        /* ==============================
           STEP 2: SORT BY PRICE
           ============================== */
        session = HibernateUtil.getSessionFactory().openSession();

        List<Product> priceAsc =
            session.createQuery("FROM Product ORDER BY price ASC", Product.class).list();

        System.out.println("\nPrice Ascending:");
        priceAsc.forEach(p ->
            System.out.println(p.getName() + " - " + p.getPrice())
        );

        List<Product> priceDesc =
            session.createQuery("FROM Product ORDER BY price DESC", Product.class).list();

        System.out.println("\nPrice Descending:");
        priceDesc.forEach(p ->
            System.out.println(p.getName() + " - " + p.getPrice())
        );

        /* ==============================
           STEP 3: SORT BY QUANTITY
           ============================== */
        List<Product> qtyDesc =
            session.createQuery("FROM Product ORDER BY quantity DESC", Product.class).list();

        System.out.println("\nQuantity High → Low:");
        qtyDesc.forEach(p ->
            System.out.println(p.getName() + " - " + p.getQuantity())
        );

        /* ==============================
           STEP 4: PAGINATION
           ============================== */
        Query<Product> page1 =
            session.createQuery("FROM Product", Product.class);
        page1.setFirstResult(0);
        page1.setMaxResults(3);

        System.out.println("\nFirst 3 Products:");
        page1.list().forEach(p -> System.out.println(p.getName()));

        Query<Product> page2 =
            session.createQuery("FROM Product", Product.class);
        page2.setFirstResult(3);
        page2.setMaxResults(3);

        System.out.println("\nNext 3 Products:");
        page2.list().forEach(p -> System.out.println(p.getName()));

        /* ==============================
           STEP 5: AGGREGATES
           ============================== */
        Long total =
            session.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                   .uniqueResult();
        System.out.println("\nTotal Products: " + total);

        Long available =
            session.createQuery(
                "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0",
                Long.class).uniqueResult();
        System.out.println("Products with Quantity > 0: " + available);

        Object[] minMax =
            session.createQuery(
                "SELECT MIN(p.price), MAX(p.price) FROM Product p",
                Object[].class).uniqueResult();
        System.out.println("Min Price: " + minMax[0]);
        System.out.println("Max Price: " + minMax[1]);

        /* ==============================
           STEP 6: GROUP BY
           ============================== */
        List<Object[]> grouped =
            session.createQuery(
                "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description",
                Object[].class).list();

        System.out.println("\nGrouped by Description:");
        for (Object[] row : grouped) {
            System.out.println(row[0] + " → " + row[1]);
        }

        /* ==============================
           STEP 7: WHERE + LIKE
           ============================== */
        System.out.println("\nPrice Range 1000–10000:");
        session.createQuery(
            "FROM Product WHERE price BETWEEN :min AND :max", Product.class)
            .setParameter("min", 1000.0)
            .setParameter("max", 10000.0)
            .list()
            .forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames starting with L:");
        session.createQuery("FROM Product WHERE name LIKE 'L%'", Product.class)
               .list()
               .forEach(p -> System.out.println(p.getName()));

        session.close();
    }
}