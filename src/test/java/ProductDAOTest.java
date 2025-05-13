
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.dao.ProductDAOImpl;
import org.example.projektjavaee.model.Product;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDAOTest {

    private static EntityManagerFactory emf;
    public EntityManager em;
    private ProductDAO productDAO;

    @BeforeAll
    public static void initFactory() {
        emf = Persistence.createEntityManagerFactory("SklepPU");
    }

    @AfterAll
    public static void closeFactory() {
        if (emf != null) emf.close();
    }

    @BeforeEach
    public void initEntityManager() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        productDAO = new ProductDAOImpl();
        ((ProductDAOImpl) productDAO).em = em; // ręczne wstrzyknięcie
    }

    @AfterEach
    public void closeEntityManager() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    public void testCreateAndFindProduct() {
        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(3999.99);

        productDAO.create(product);
        em.flush();
        em.clear();

        Product found = productDAO.find(product.getId());
        assertNotNull(found);
        assertEquals("Laptop", found.getName());
    }

    @Test
    public void testFindAll() {
        Product p1 = new Product();
        p1.setName("Phone");
        p1.setDescription("Smartphone");
        p1.setPrice(999.99);

        Product p2 = new Product();
        p2.setName("Tablet");
        p2.setDescription("Android tablet");
        p2.setPrice(1299.49);

        productDAO.create(p1);
        productDAO.create(p2);

        em.flush();
        em.clear();

        List<Product> all = productDAO.findAll();
        assertEquals(2, all.size());
    }
}