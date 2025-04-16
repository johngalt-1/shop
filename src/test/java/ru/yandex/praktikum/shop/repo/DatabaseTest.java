package ru.yandex.praktikum.shop.repo;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        statements = {
                """
                        DELETE FROM shop.product;
                        """,
                """
                        INSERT INTO shop.product VALUES
                            (1, 'Кроссовки', 'Кроссы Adidas', 10999.00, 'adidas.jpg', '2025-04-01T00:00:00', '2025-04-01T00:00:00', false),
                            (2, 'Толстовка Adidas', 'Толстовка', 8999.00, 'adidas1.jpg', '2025-04-01T00:00:00', '2025-04-01T00:00:00', false),
                            (3, 'Толстовка Nike', 'Толстовка', 7999.00, 'nike.jpg', '2025-04-01T00:00:00', '2025-04-01T00:00:00', false),
                            (4, 'Толстовка Nike', 'Толстовка', 7999.00, 'nike1.jpg', '2025-04-01T00:00:00', '2025-04-01T00:00:00', true);
                        """
        },
        executionPhase = BEFORE_TEST_CLASS
)
public class DatabaseTest {
}
