import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    public void addIngredientTest() {
        int beforeSize = burger.ingredients.size();
        burger.addIngredient(ingredientMock);
        assertEquals(beforeSize + 1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientTest() {
        // Никаких заглушек для цены: тест только про удаление из списка
        burger.addIngredient(ingredientMock);

        int beforeSize = burger.ingredients.size();
        burger.removeIngredient(0);

        assertEquals(beforeSize - 1, burger.ingredients.size());
        assertFalse(burger.ingredients.contains(ingredientMock));
    }

    @Test
    public void moveIngredientTest() {
        // Arrange: готовим список [ingredientFirst, ingredientSecond, ingredientThird]
        burger.addIngredient(ingredientFirst);
        burger.addIngredient(ingredientSecond);
        burger.addIngredient(ingredientThird);

        // Act: перемещаем ingredientSecond (индекс 1) в начало (индекс 0)
        burger.moveIngredient(1, 0);

        // Assert: проверяем, что порядок реально изменился
        List<Ingredient> list = burger.ingredients;
        assertEquals(3, list.size());

        assertSame(ingredientSecond, list.get(0)); // на первом месте теперь второй ингредиент
        assertSame(ingredientFirst, list.get(1));  // первый сдвинулся на второе место
        assertSame(ingredientThird, list.get(2)); // третий остался на третьем месте
    }
}











