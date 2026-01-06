package com.klu;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class App {

    public static void main(String[] args) {

        /* =========================================
           STEP 1: INSERT DATA (RUN ONLY ONCE)
           ========================================= */
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

        /* =========================================
           STEP 2: SORT BY PRICE (ASC & DESC)
           ========================================= */
        session = HibernateUtil.getSessionFactory().openSession();

        List<Product> ascPrice =
                session.createQuery("FROM Product ORDER BY price ASC", Product.class).list();

        System.out.println("\nPrice Ascending:");
        ascPrice.forEach(p ->
                System.out.println(p.getName() + " - " + p.getPrice())
        );

        List<Product> descPrice =
                session.createQuery("FROM Product ORDER BY price DESC", Product.class).list();

        System.out.println("\nPrice Descending:");
        descPrice.forEach(p ->
                System.out.println(p.getName() + " - " + p.getPrice())
        );

        /* =========================================
           STEP 3: SORT BY QUANTITY
           ========================================= */
        List<Product> qtyDesc =
                session.createQuery("FROM Product ORDER BY quantity DESC", Product.class).list();

        System.out.println("\nQuantity High to Low:");
        qtyDesc.forEach(p ->
                System.out.println(p.getName() + " - " + p.getQuantity())
        );

        /* =========================================
           STEP 4: PAGINATION
           ========================================= */
        Query<Product> page1 = session.createQuery("FROM Product", Product.class);
        page1.setFirstResult(0);
        page1.setMaxResults(3);

        System.out.println("\nFirst 3 Products:");
        page1.list().forEach(p -> System.out.println(p.getName()));

        Query<Product> page2 = session.createQuery("FROM Product", Product.class);
        page2.setFirstResult(3);
        page2.setMaxResults(3);

        System.out.println("\nNext 3 Products:");
        page2.list().forEach(p -> System.out.println(p.getName()));

        /* =========================================
           STEP 5: AGGREGATE FUNCTIONS
           ========================================= */
        Long totalCount =
                session.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                        .uniqueResult();

        System.out.println("\nTotal Products: " + totalCount);

        Object[] minMax =
                session.createQuery(
                        "SELECT MIN(p.price), MAX(p.price) FROM Product p",
                        Object[].class
                ).uniqueResult();

        System.out.println("Minimum Price: " + minMax[0]);
        System.out.println("Maximum Price: " + minMax[1]);

        /* =========================================
           STEP 6: WHERE CLAUSE
           ========================================= */
        List<Product> range =
                session.createQuery(
                        "FROM Product WHERE price BETWEEN :min AND :max",
                        Product.class)
                        .setParameter("min", 1000.0)
                        .setParameter("max", 10000.0)
                        .list();

        System.out.println("\nProducts between 1000 and 10000:");
        range.forEach(p ->
                System.out.println(p.getName() + " - " + p.getPrice())
        );

        /* =========================================
           STEP 7: LIKE OPERATIONS
           ========================================= */
        System.out.println("\nNames starting with 'L':");
        session.createQuery("FROM Product WHERE name LIKE 'L%'", Product.class)
                .list()
                .forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames ending with 'r':");
        session.createQuery("FROM Product WHERE name LIKE '%r'", Product.class)
                .list()
                .forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames containing 'top':");
        session.createQuery("FROM Product WHERE name LIKE '%top%'", Product.class)
                .list()
                .forEach(p -> System.out.println(p.getName()));

        session.close();
    }
}
