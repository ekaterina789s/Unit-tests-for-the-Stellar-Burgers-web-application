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
import static org.mockito.Mockito.when;

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

    @Test
    public void getPrice_emptyIngredients() {
        // Arrange
        when(bunMock.getPrice()).thenReturn(50.0f);
        // у бургера уже есть булка из setUp, ингредиентов нет

        // Act
        float price = burger.getPrice();

        // Assert: 2 булки по 50 = 100, ингредиентов нет, значит цена 100
        assertEquals(100.0f, price, 0.01f);
    }

    @Test
    public void getPrice_withIngredients() {
        // Arrange
        when(bunMock.getPrice()).thenReturn(50.0f);
        when(ingredientFirst.getPrice()).thenReturn(10.0f);
        when(ingredientSecond.getPrice()).thenReturn(20.0f);

        burger.addIngredient(ingredientFirst);
        burger.addIngredient(ingredientSecond);

        // Act
        float price = burger.getPrice();

        // Assert: (50 * 2) + 10 + 20 = 130
        assertEquals(130.0f, price, 0.01f);
    }
}











