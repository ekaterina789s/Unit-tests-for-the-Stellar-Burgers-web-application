import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    Bun bunMock;

    private Burger burger;

    @Mock
    Ingredient ingredientMock;
    @Mock
    Ingredient ingredientFirst;
    @Mock
    Ingredient ingredientSecond;
    @Mock
    Ingredient ingredientThird;

    @Before
    public void setUp() {
        burger = new Burger();
        // Не делаем заглушку для getPrice() здесь: она нужна только в тестах, где реально считаем цену
        burger.setBuns(bunMock);
    }

    @Test
    public void setBunsTest() {
        burger.setBuns(bunMock);
        assertSame(bunMock, burger.bun);
    }
}











