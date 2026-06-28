import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class BurgerParamTest {

    private final String bunName;
    private final float bunPrice;
    private final List<Ingredient> ingredients;

    public BurgerParamTest(String bunName, float bunPrice, List<Ingredient> ingredients) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.ingredients = ingredients;
    }

    @Parameterized.Parameters(name = "receipt_test_{index}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //бургер только с булочкой
                {
                        "Black Bun",
                        100f,
                        new ArrayList<>()
                },

                //бургер с одним ингредиентом
                {
                        "White Bun",
                        200f,
                        Arrays.asList(
                                new Ingredient(IngredientType.SAUCE, "sour cream", 200)
                        )
                },

                //бургер с несколькими ингредиентами
                {
                        "Red Bun",
                        300f,
                        Arrays.asList(
                                new Ingredient(IngredientType.FILLING, "cutlet", 100),
                                new Ingredient(IngredientType.SAUCE, "hot sauce", 100)
                        )
                }
        });
    }

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
        Bun bun = new Bun(bunName, bunPrice);
        burger.setBuns(bun);

        for (Ingredient ingredient : ingredients) {
            burger.addIngredient(ingredient);
        }
    }

    @Test
    public void testGetReceipt() {
        String actual = burger.getReceipt();
        String expected = buildExpectedReceipt(bunName, bunPrice, ingredients);

        //нормализуем переносы: превращаем \r\n и \r в \n (так сравнение будет одинаковым на всех ОС)
        String normalizedActual = actual.replace("\r\n", "\n").replace("\r", "\n");
        String normalizedExpected = expected.replace("\r\n", "\n").replace("\r", "\n");

        Assert.assertEquals(normalizedExpected, normalizedActual);
    }

    //собираем ожидаемый чек по тем же правилам, что и в getReceipt()
    private String buildExpectedReceipt(String bunName, float bunPrice, List<Ingredient> ingredients) {
        String ls = "\n";

        StringBuilder sb = new StringBuilder();

        //первая булочка
        sb.append("(==== ").append(bunName).append(" ====").append(ls);

        //ингредиенты: формат "= {type} {name} ="
        for (Ingredient ing : ingredients) {
            String typeStr = ing.getType().toString().toLowerCase();
            sb.append("= ").append(typeStr).append(" ").append(ing.getName()).append(" =").append(ls);
        }

        //вторая булочка
        sb.append("(==== ").append(bunName).append(" ====").append(ls);

        //пустая строка
        sb.append(ls);

        //здесь считаем цену
        float totalPrice = bunPrice * 2;
        for (Ingredient ing : ingredients) {
            totalPrice += ing.getPrice();
        }

        //цена с 6 знаками после запятой — как в String.format("%f")
        sb.append("Price: ").append(String.format("%.6f", totalPrice)).append(ls);

        return sb.toString();
    }

}


