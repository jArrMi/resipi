package com.dartharrmi.resipi.webservice.dto

import com.dartharrmi.resipi.base.BaseUnitTest
import com.dartharrmi.resipi.webservice.deserializer.InstructionsDeserializer
import com.dartharrmi.resipi.webservice.dto.response.GetRecipesResponseDTO
import com.dartharrmi.resipi.webservice.dto.response.toDomain
import com.google.gson.GsonBuilder
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.collection.IsEmptyCollection
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import kotlin.test.assertFailsWith

class RecipeDTOTest : BaseUnitTest() {

    private companion object {
        const val RECIPE_ID: Long = 601768
        const val RECIPE_STEPS: Int = 6
        const val SAMPLE_RESPONSE =
            "{\"results\":[{\"vegetarian\":false,\"vegan\":false,\"glutenFree\":true,\"dairyFree\":true,\"veryHealthy\":false,\"cheap\":false,\"veryPopular\":false,\"sustainable\":false,\"weightWatcherSmartPoints\":4,\"gaps\":\"GAPS_3\",\"lowFodmap\":false,\"preparationMinutes\":10,\"cookingMinutes\":15,\"aggregateLikes\":0,\"spoonacularScore\":25.0,\"healthScore\":3.0,\"creditsText\":\"Oh So Delicioso\",\"sourceName\":\"Oh So Delicioso\",\"pricePerServing\":56.21,\"id\":601768,\"title\":\"Beef\",\"readyInMinutes\":25,\"servings\":15,\"sourceUrl\":\"http://ohsodelicioso.com/beef-n-veggie-sliders-aka-mini-hamburgers/index.php\",\"image\":\"https://spoonacular.com/recipeImages/601768-312x231.jpg\",\"imageType\":\"jpg\",\"nutrition\":{\"nutrients\":[{\"title\":\"Calories\",\"amount\":161.43,\"unit\":\"cal\",\"percentOfDailyNeeds\":8.07},{\"title\":\"Fat\",\"amount\":12.17,\"unit\":\"g\",\"percentOfDailyNeeds\":18.73},{\"title\":\"Saturated Fat\",\"amount\":4.66,\"unit\":\"g\",\"percentOfDailyNeeds\":29.1},{\"title\":\"Carbohydrates\",\"amount\":1.62,\"unit\":\"g\",\"percentOfDailyNeeds\":0.54},{\"title\":\"Net Carbohydrates\",\"amount\":1.14,\"unit\":\"g\",\"percentOfDailyNeeds\":0.42},{\"title\":\"Sugar\",\"amount\":1.01,\"unit\":\"g\",\"percentOfDailyNeeds\":1.12},{\"title\":\"Cholesterol\",\"amount\":42.94,\"unit\":\"mg\",\"percentOfDailyNeeds\":14.31},{\"title\":\"Sodium\",\"amount\":238.63,\"unit\":\"mg\",\"percentOfDailyNeeds\":10.38},{\"title\":\"Protein\",\"amount\":10.7,\"unit\":\"g\",\"percentOfDailyNeeds\":21.4},{\"title\":\"Vitamin B12\",\"amount\":1.29,\"unit\":\"µg\",\"percentOfDailyNeeds\":21.57},{\"title\":\"Vitamin A\",\"amount\":953.98,\"unit\":\"IU\",\"percentOfDailyNeeds\":19.08},{\"title\":\"Zinc\",\"amount\":2.61,\"unit\":\"mg\",\"percentOfDailyNeeds\":17.37},{\"title\":\"Vitamin C\",\"amount\":12.98,\"unit\":\"mg\",\"percentOfDailyNeeds\":15.73},{\"title\":\"Vitamin B3\",\"amount\":2.74,\"unit\":\"mg\",\"percentOfDailyNeeds\":13.69},{\"title\":\"Selenium\",\"amount\":9.13,\"unit\":\"µg\",\"percentOfDailyNeeds\":13.04},{\"title\":\"Vitamin B6\",\"amount\":0.25,\"unit\":\"mg\",\"percentOfDailyNeeds\":12.49},{\"title\":\"Phosphorus\",\"amount\":105.07,\"unit\":\"mg\",\"percentOfDailyNeeds\":10.51},{\"title\":\"Iron\",\"amount\":1.28,\"unit\":\"mg\",\"percentOfDailyNeeds\":7.1},{\"title\":\"Potassium\",\"amount\":232.54,\"unit\":\"mg\",\"percentOfDailyNeeds\":6.64},{\"title\":\"Vitamin B2\",\"amount\":0.11,\"unit\":\"mg\",\"percentOfDailyNeeds\":6.58},{\"title\":\"Vitamin B5\",\"amount\":0.37,\"unit\":\"mg\",\"percentOfDailyNeeds\":3.71},{\"title\":\"Magnesium\",\"amount\":14.45,\"unit\":\"mg\",\"percentOfDailyNeeds\":3.61},{\"title\":\"Folate\",\"amount\":12.49,\"unit\":\"µg\",\"percentOfDailyNeeds\":3.12},{\"title\":\"Vitamin E\",\"amount\":0.42,\"unit\":\"mg\",\"percentOfDailyNeeds\":2.78},{\"title\":\"Vitamin B1\",\"amount\":0.04,\"unit\":\"mg\",\"percentOfDailyNeeds\":2.7},{\"title\":\"Vitamin K\",\"amount\":2.59,\"unit\":\"µg\",\"percentOfDailyNeeds\":2.47},{\"title\":\"Manganese\",\"amount\":0.05,\"unit\":\"mg\",\"percentOfDailyNeeds\":2.46},{\"title\":\"Copper\",\"amount\":0.05,\"unit\":\"mg\",\"percentOfDailyNeeds\":2.43},{\"title\":\"Fiber\",\"amount\":0.47,\"unit\":\"g\",\"percentOfDailyNeeds\":1.89},{\"title\":\"Calcium\",\"amount\":15.84,\"unit\":\"mg\",\"percentOfDailyNeeds\":1.58}],\"ingredients\":[{\"name\":\"carrot\",\"amount\":0.07,\"unit\":\"\",\"nutrients\":[{\"name\":\"Fat\",\"amount\":0.01,\"unit\":\"g\"},{\"name\":\"Trans Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin E\",\"amount\":0.03,\"unit\":\"mg\"},{\"name\":\"Vitamin D\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Mono Unsaturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B5\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Sodium\",\"amount\":2.81,\"unit\":\"mg\"},{\"name\":\"Choline\",\"amount\":0.36,\"unit\":\"mg\"},{\"name\":\"Zinc\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Magnesium\",\"amount\":0.49,\"unit\":\"mg\"},{\"name\":\"Copper\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Net Carbohydrates\",\"amount\":0.28,\"unit\":\"g\"},{\"name\":\"Vitamin K\",\"amount\":0.54,\"unit\":\"µg\"},{\"name\":\"Saturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Calories\",\"amount\":1.67,\"unit\":\"cal\"},{\"name\":\"Carbohydrates\",\"amount\":0.39,\"unit\":\"g\"},{\"name\":\"Vitamin B3\",\"amount\":0.04,\"unit\":\"mg\"},{\"name\":\"Calcium\",\"amount\":1.34,\"unit\":\"mg\"},{\"name\":\"Fluoride\",\"amount\":0.13,\"unit\":\"mg\"},{\"name\":\"Vitamin C\",\"amount\":0.24,\"unit\":\"mg\"},{\"name\":\"Vitamin B2\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Folic Acid\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Fiber\",\"amount\":0.11,\"unit\":\"g\"},{\"name\":\"Phosphorus\",\"amount\":1.42,\"unit\":\"mg\"},{\"name\":\"Protein\",\"amount\":0.04,\"unit\":\"g\"},{\"name\":\"Potassium\",\"amount\":13.01,\"unit\":\"mg\"},{\"name\":\"Iron\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Selenium\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Sugar\",\"amount\":0.19,\"unit\":\"g\"},{\"name\":\"Manganese\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Vitamin B6\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Poly Unsaturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Folate\",\"amount\":0.77,\"unit\":\"µg\"},{\"name\":\"Caffeine\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Cholesterol\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Alcohol\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B12\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Vitamin A\",\"amount\":679.38,\"unit\":\"IU\"},{\"name\":\"Vitamin B1\",\"amount\":0.0,\"unit\":\"mg\"}]},{\"name\":\"ground beef\",\"amount\":0.13,\"unit\":\"lbs\",\"nutrients\":[{\"name\":\"Fat\",\"amount\":12.1,\"unit\":\"g\"},{\"name\":\"Trans Fat\",\"amount\":0.75,\"unit\":\"g\"},{\"name\":\"Vitamin E\",\"amount\":0.25,\"unit\":\"mg\"},{\"name\":\"Vitamin D\",\"amount\":0.06,\"unit\":\"µg\"},{\"name\":\"Mono Unsaturated Fat\",\"amount\":5.3,\"unit\":\"g\"},{\"name\":\"Vitamin B5\",\"amount\":0.3,\"unit\":\"mg\"},{\"name\":\"Sodium\",\"amount\":40.52,\"unit\":\"mg\"},{\"name\":\"Choline\",\"amount\":34.11,\"unit\":\"mg\"},{\"name\":\"Zinc\",\"amount\":2.53,\"unit\":\"mg\"},{\"name\":\"Magnesium\",\"amount\":10.28,\"unit\":\"mg\"},{\"name\":\"Copper\",\"amount\":0.04,\"unit\":\"mg\"},{\"name\":\"Net Carbohydrates\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin K\",\"amount\":1.09,\"unit\":\"µg\"},{\"name\":\"Saturated Fat\",\"amount\":4.64,\"unit\":\"g\"},{\"name\":\"Calories\",\"amount\":153.62,\"unit\":\"cal\"},{\"name\":\"Carbohydrates\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B3\",\"amount\":2.56,\"unit\":\"mg\"},{\"name\":\"Calcium\",\"amount\":10.89,\"unit\":\"mg\"},{\"name\":\"Fluoride\",\"amount\":13.55,\"unit\":\"mg\"},{\"name\":\"Vitamin C\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Vitamin B2\",\"amount\":0.09,\"unit\":\"mg\"},{\"name\":\"Folic Acid\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Fiber\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Phosphorus\",\"amount\":95.56,\"unit\":\"mg\"},{\"name\":\"Protein\",\"amount\":10.38,\"unit\":\"g\"},{\"name\":\"Potassium\",\"amount\":163.29,\"unit\":\"mg\"},{\"name\":\"Iron\",\"amount\":1.17,\"unit\":\"mg\"},{\"name\":\"Selenium\",\"amount\":9.07,\"unit\":\"µg\"},{\"name\":\"Sugar\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Manganese\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Vitamin B6\",\"amount\":0.2,\"unit\":\"mg\"},{\"name\":\"Poly Unsaturated Fat\",\"amount\":0.31,\"unit\":\"g\"},{\"name\":\"Folate\",\"amount\":4.23,\"unit\":\"µg\"},{\"name\":\"Caffeine\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Cholesterol\",\"amount\":42.94,\"unit\":\"mg\"},{\"name\":\"Alcohol\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B12\",\"amount\":1.29,\"unit\":\"µg\"},{\"name\":\"Vitamin A\",\"amount\":0.0,\"unit\":\"IU\"},{\"name\":\"Vitamin B1\",\"amount\":0.03,\"unit\":\"mg\"}]},{\"name\":\"onion\",\"amount\":0.03,\"unit\":\"\",\"nutrients\":[{\"name\":\"Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin E\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Vitamin D\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Mono Unsaturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B5\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Sodium\",\"amount\":0.15,\"unit\":\"mg\"},{\"name\":\"Choline\",\"amount\":0.22,\"unit\":\"mg\"},{\"name\":\"Zinc\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Magnesium\",\"amount\":0.37,\"unit\":\"mg\"},{\"name\":\"Copper\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Net Carbohydrates\",\"amount\":0.28,\"unit\":\"g\"},{\"name\":\"Vitamin K\",\"amount\":0.01,\"unit\":\"µg\"},{\"name\":\"Saturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Calories\",\"amount\":1.47,\"unit\":\"cal\"},{\"name\":\"Carbohydrates\",\"amount\":0.34,\"unit\":\"g\"},{\"name\":\"Vitamin B3\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Calcium\",\"amount\":0.84,\"unit\":\"mg\"},{\"name\":\"Fluoride\",\"amount\":0.04,\"unit\":\"mg\"},{\"name\":\"Vitamin C\",\"amount\":0.27,\"unit\":\"mg\"},{\"name\":\"Vitamin B2\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Folic Acid\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Fiber\",\"amount\":0.06,\"unit\":\"g\"},{\"name\":\"Phosphorus\",\"amount\":1.06,\"unit\":\"mg\"},{\"name\":\"Protein\",\"amount\":0.04,\"unit\":\"g\"},{\"name\":\"Potassium\",\"amount\":5.35,\"unit\":\"mg\"},{\"name\":\"Iron\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Selenium\",\"amount\":0.02,\"unit\":\"µg\"},{\"name\":\"Sugar\",\"amount\":0.16,\"unit\":\"g\"},{\"name\":\"Manganese\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Vitamin B6\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Poly Unsaturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Folate\",\"amount\":0.7,\"unit\":\"µg\"},{\"name\":\"Caffeine\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Cholesterol\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Alcohol\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B12\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Vitamin A\",\"amount\":0.07,\"unit\":\"IU\"},{\"name\":\"Vitamin B1\",\"amount\":0.0,\"unit\":\"mg\"}]},{\"name\":\"red bell pepper\",\"amount\":0.07,\"unit\":\"\",\"nutrients\":[{\"name\":\"Fat\",\"amount\":0.02,\"unit\":\"g\"},{\"name\":\"Vitamin E\",\"amount\":0.13,\"unit\":\"mg\"},{\"name\":\"Vitamin D\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Mono Unsaturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B5\",\"amount\":0.03,\"unit\":\"mg\"},{\"name\":\"Sodium\",\"amount\":0.32,\"unit\":\"mg\"},{\"name\":\"Choline\",\"amount\":0.44,\"unit\":\"mg\"},{\"name\":\"Zinc\",\"amount\":0.02,\"unit\":\"mg\"},{\"name\":\"Magnesium\",\"amount\":0.95,\"unit\":\"mg\"},{\"name\":\"Copper\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Net Carbohydrates\",\"amount\":0.31,\"unit\":\"g\"},{\"name\":\"Vitamin K\",\"amount\":0.39,\"unit\":\"µg\"},{\"name\":\"Saturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Calories\",\"amount\":2.46,\"unit\":\"cal\"},{\"name\":\"Carbohydrates\",\"amount\":0.48,\"unit\":\"g\"},{\"name\":\"Vitamin B3\",\"amount\":0.08,\"unit\":\"mg\"},{\"name\":\"Calcium\",\"amount\":0.56,\"unit\":\"mg\"},{\"name\":\"Vitamin C\",\"amount\":10.13,\"unit\":\"mg\"},{\"name\":\"Vitamin B2\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Folic Acid\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Fiber\",\"amount\":0.17,\"unit\":\"g\"},{\"name\":\"Phosphorus\",\"amount\":2.06,\"unit\":\"mg\"},{\"name\":\"Protein\",\"amount\":0.08,\"unit\":\"g\"},{\"name\":\"Potassium\",\"amount\":16.74,\"unit\":\"mg\"},{\"name\":\"Iron\",\"amount\":0.03,\"unit\":\"mg\"},{\"name\":\"Selenium\",\"amount\":0.01,\"unit\":\"µg\"},{\"name\":\"Sugar\",\"amount\":0.33,\"unit\":\"g\"},{\"name\":\"Manganese\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Vitamin B6\",\"amount\":0.02,\"unit\":\"mg\"},{\"name\":\"Poly Unsaturated Fat\",\"amount\":0.01,\"unit\":\"g\"},{\"name\":\"Folate\",\"amount\":3.65,\"unit\":\"µg\"},{\"name\":\"Caffeine\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Cholesterol\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Alcohol\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B12\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Vitamin A\",\"amount\":248.39,\"unit\":\"IU\"},{\"name\":\"Vitamin B1\",\"amount\":0.0,\"unit\":\"mg\"}]},{\"name\":\"salt and pepper\",\"amount\":1.0,\"unit\":\"servings\",\"nutrients\":[{\"name\":\"Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin E\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Vitamin D\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Mono Unsaturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B5\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Sodium\",\"amount\":193.79,\"unit\":\"mg\"},{\"name\":\"Choline\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Zinc\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Magnesium\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Copper\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Net Carbohydrates\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin K\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Saturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Calories\",\"amount\":0.0,\"unit\":\"cal\"},{\"name\":\"Carbohydrates\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B3\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Calcium\",\"amount\":0.12,\"unit\":\"mg\"},{\"name\":\"Fluoride\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Vitamin C\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Vitamin B2\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Folic Acid\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Fiber\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Phosphorus\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Protein\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Potassium\",\"amount\":0.04,\"unit\":\"mg\"},{\"name\":\"Iron\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Selenium\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Sugar\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Manganese\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Vitamin B6\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Poly Unsaturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Folate\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Caffeine\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Cholesterol\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Alcohol\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B12\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Vitamin A\",\"amount\":0.0,\"unit\":\"IU\"},{\"name\":\"Vitamin B1\",\"amount\":0.0,\"unit\":\"mg\"}]},{\"name\":\"zucchini\",\"amount\":0.07,\"unit\":\"\",\"nutrients\":[{\"name\":\"Fat\",\"amount\":0.04,\"unit\":\"g\"},{\"name\":\"Trans Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin E\",\"amount\":0.02,\"unit\":\"mg\"},{\"name\":\"Vitamin D\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Mono Unsaturated Fat\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B5\",\"amount\":0.03,\"unit\":\"mg\"},{\"name\":\"Sodium\",\"amount\":1.05,\"unit\":\"mg\"},{\"name\":\"Choline\",\"amount\":1.24,\"unit\":\"mg\"},{\"name\":\"Zinc\",\"amount\":0.04,\"unit\":\"mg\"},{\"name\":\"Magnesium\",\"amount\":2.35,\"unit\":\"mg\"},{\"name\":\"Copper\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Net Carbohydrates\",\"amount\":0.28,\"unit\":\"g\"},{\"name\":\"Vitamin K\",\"amount\":0.56,\"unit\":\"µg\"},{\"name\":\"Saturated Fat\",\"amount\":0.01,\"unit\":\"g\"},{\"name\":\"Calories\",\"amount\":2.22,\"unit\":\"cal\"},{\"name\":\"Carbohydrates\",\"amount\":0.41,\"unit\":\"g\"},{\"name\":\"Vitamin B3\",\"amount\":0.06,\"unit\":\"mg\"},{\"name\":\"Calcium\",\"amount\":2.09,\"unit\":\"mg\"},{\"name\":\"Vitamin C\",\"amount\":2.34,\"unit\":\"mg\"},{\"name\":\"Vitamin B2\",\"amount\":0.01,\"unit\":\"mg\"},{\"name\":\"Folic Acid\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Fiber\",\"amount\":0.13,\"unit\":\"g\"},{\"name\":\"Phosphorus\",\"amount\":4.97,\"unit\":\"mg\"},{\"name\":\"Protein\",\"amount\":0.16,\"unit\":\"g\"},{\"name\":\"Potassium\",\"amount\":34.1,\"unit\":\"mg\"},{\"name\":\"Iron\",\"amount\":0.05,\"unit\":\"mg\"},{\"name\":\"Selenium\",\"amount\":0.03,\"unit\":\"µg\"},{\"name\":\"Sugar\",\"amount\":0.33,\"unit\":\"g\"},{\"name\":\"Manganese\",\"amount\":0.02,\"unit\":\"mg\"},{\"name\":\"Vitamin B6\",\"amount\":0.02,\"unit\":\"mg\"},{\"name\":\"Poly Unsaturated Fat\",\"amount\":0.01,\"unit\":\"g\"},{\"name\":\"Folate\",\"amount\":3.14,\"unit\":\"µg\"},{\"name\":\"Caffeine\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Cholesterol\",\"amount\":0.0,\"unit\":\"mg\"},{\"name\":\"Alcohol\",\"amount\":0.0,\"unit\":\"g\"},{\"name\":\"Vitamin B12\",\"amount\":0.0,\"unit\":\"µg\"},{\"name\":\"Vitamin A\",\"amount\":26.13,\"unit\":\"IU\"},{\"name\":\"Vitamin B1\",\"amount\":0.01,\"unit\":\"mg\"}]}],\"caloricBreakdown\":{\"percentProtein\":26.94,\"percentFat\":68.98,\"percentCarbs\":4.08},\"weightPerServing\":{\"amount\":90,\"unit\":\"g\"}},\"summary\":\"Beef might be just the hor d'oeuvre you are searching for. This recipe makes 15 servings with <b>161 calories</b>, <b>11g of protein</b>, and <b>12g of fat</b> each. For <b>56 cents per serving</b>, this recipe <b>covers 8%</b> of your daily requirements of vitamins and minerals. It is a good option if you're following a <b>caveman, gluten free, dairy free, and primal</b> diet. This recipe is liked by 1 foodies and cooks. A mixture of onion, carrot, ground beef, and a handful of other ingredients are all it takes to make this recipe so yummy. From preparation to the plate, this recipe takes about <b>25 minutes</b>. All things considered, we decided this recipe <b>deserves a spoonacular score of 28%</b>. This score is rather bad. Similar recipes include <a href=\\\"https://spoonacular.com/recipes/beef-rouladen-german-beef-rolls-stuffed-with-pickles-bacon-685852\\\">Beef Rouladen (German Beef Rolls Stuffed with Pickles & Bacon)</a>, <a href=\\\"https://spoonacular.com/recipes/garlicky-beef-wrapped-asparagus-the-perfect-appetizer-for-beef-lovers-531238\\\">Garlicky Beef Wrapped Asparagus – The Perfect Appetizer for Beef Lovers</a>, and <a href=\\\"https://spoonacular.com/recipes/sunday-slow-cooker-pepperoncini-beef-aka-drip-beef-510411\\\">Sunday Slow Cooker: Pepperoncini Beef aka Drip Beef</a>.\",\"cuisines\":[],\"dishTypes\":[\"antipasti\",\"starter\",\"snack\",\"appetizer\",\"antipasto\",\"hor d'oeuvre\"],\"diets\":[\"gluten free\",\"dairy free\",\"paleolithic\",\"primal\"],\"occasions\":[],\"winePairing\":{},\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"step\":\"In a food processor, mince the zucchini, bell pepper, carrot and onion.\",\"ingredients\":[{\"id\":10211821,\"name\":\"bell pepper\",\"localizedName\":\"bell pepper\",\"image\":\"bell-pepper-orange.png\"},{\"id\":11477,\"name\":\"zucchini\",\"localizedName\":\"zucchini\",\"image\":\"zucchini.jpg\"},{\"id\":11124,\"name\":\"carrot\",\"localizedName\":\"carrot\",\"image\":\"sliced-carrot.png\"},{\"id\":11282,\"name\":\"onion\",\"localizedName\":\"onion\",\"image\":\"brown-onion.png\"}],\"equipment\":[{\"id\":404771,\"name\":\"food processor\",\"localizedName\":\"food processor\",\"image\":\"food-processor.png\"}]},{\"number\":2,\"step\":\"Add veggies to large skillet with evoo.\",\"ingredients\":[{\"id\":1034053,\"name\":\"extra virgin olive oil\",\"localizedName\":\"extra virgin olive oil\",\"image\":\"olive-oil.jpg\"}],\"equipment\":[{\"id\":404645,\"name\":\"frying pan\",\"localizedName\":\"frying pan\",\"image\":\"pan.png\"}]},{\"number\":3,\"step\":\"Saute over medium high heat until tender, about 5-7 mins. \",\"ingredients\":[],\"equipment\":[],\"length\":{\"number\":7,\"unit\":\"minutes\"}},{\"number\":4,\"step\":\"Combine in large bowl with 1 egg, ground beef and salt and pepper. By hand combine all ingredients thoroughly. Form into small patties and grill as desired.Cook remaining eggs as a \\\"hard' egg, little to no yolk.\",\"ingredients\":[{\"id\":1102047,\"name\":\"salt and pepper\",\"localizedName\":\"salt and pepper\",\"image\":\"salt-and-pepper.jpg\"},{\"id\":10023572,\"name\":\"ground beef\",\"localizedName\":\"ground beef\",\"image\":\"fresh-ground-beef.jpg\"},{\"id\":1123,\"name\":\"egg\",\"localizedName\":\"egg\",\"image\":\"egg.png\"},{\"id\":1125,\"name\":\"egg yolk\",\"localizedName\":\"egg yolk\",\"image\":\"egg-yolk.jpg\"}],\"equipment\":[{\"id\":404706,\"name\":\"grill\",\"localizedName\":\"grill\",\"image\":\"grill.jpg\"},{\"id\":404783,\"name\":\"bowl\",\"localizedName\":\"bowl\",\"image\":\"bowl.jpg\"}]},{\"number\":5,\"step\":\"Serve on Hawaiian Sweet\",\"ingredients\":[],\"equipment\":[]},{\"number\":6,\"step\":\"Roll, garnish with egg, lettuce, red onion, pepper jack cheese, cilantro, shredded ice burg lettuce and mayo sauce (consists of mayonnaise, chili powder, and minced garlic). Pierce with toothpick to hold together.\",\"ingredients\":[{\"id\":1025,\"name\":\"pepperjack cheese\",\"localizedName\":\"pepperjack cheese\",\"image\":\"pepper-jack-cheese.jpg\"},{\"id\":0,\"name\":\"minced garlic\",\"localizedName\":\"minced garlic\",\"image\":\"garlic.png\"},{\"id\":2009,\"name\":\"chili powder\",\"localizedName\":\"chili powder\",\"image\":\"chili-powder.jpg\"},{\"id\":4025,\"name\":\"mayonnaise\",\"localizedName\":\"mayonnaise\",\"image\":\"mayonnaise.png\"},{\"id\":10011282,\"name\":\"red onion\",\"localizedName\":\"red onion\",\"image\":\"red-onion.png\"},{\"id\":11165,\"name\":\"cilantro\",\"localizedName\":\"cilantro\",\"image\":\"cilantro.png\"},{\"id\":11252,\"name\":\"lettuce\",\"localizedName\":\"lettuce\",\"image\":\"iceberg-lettuce.jpg\"},{\"id\":0,\"name\":\"sauce\",\"localizedName\":\"sauce\",\"image\":\"\"},{\"id\":0,\"name\":\"roll\",\"localizedName\":\"roll\",\"image\":\"dinner-yeast-rolls.jpg\"},{\"id\":1123,\"name\":\"egg\",\"localizedName\":\"egg\",\"image\":\"egg.png\"},{\"id\":10014412,\"name\":\"ice\",\"localizedName\":\"ice\",\"image\":\"ice-cubes.png\"}],\"equipment\":[{\"id\":404644,\"name\":\"toothpicks\",\"localizedName\":\"toothpicks\",\"image\":\"toothpicks.jpg\"}]}]}]}],\"offset\":0,\"number\":1,\"totalResults\":6802}"
        const val MALFORMED_RESPONSE = "MALFORMED_RESPONSE"
    }

    @Test
    fun testParseRecipeDTO() {
        val getRecipesResponseDTO = GsonBuilder().apply {
            this.registerTypeAdapter(InstructionsDeserializer.typeToken, InstructionsDeserializer())
        }.create()
            .fromJson<GetRecipesResponseDTO>(SAMPLE_RESPONSE, GetRecipesResponseDTO::class.java)

        getRecipesResponseDTO.results?.let {
            assertThat(it.size, `is`(1))

            // Test the custom deserializer for the instructions
            assertThat(
                "It should not be empty",
                it[0].nutrition?.nutritionFacts,
                not(IsEmptyCollection.empty())
            )
            assertThat(
                "It should not be empty",
                it[0].analyzedInstructions,
                not(IsEmptyCollection.empty())
            )
        }
    }

    @Test
    fun testParseDtoToDomain() {
        val getRecipesResponseDTO = GsonBuilder().apply {
            this.registerTypeAdapter(InstructionsDeserializer.typeToken, InstructionsDeserializer())
        }.create()
            .fromJson<GetRecipesResponseDTO>(SAMPLE_RESPONSE, GetRecipesResponseDTO::class.java)

        getRecipesResponseDTO.results?.let {
            val domain = it[0].toDomain()

            assertEquals(
                "It should not be empty",
                domain.id,
                RECIPE_ID
            )
            assertThat(
                "It should not be empty",
                domain.nutrition.nutritionFacts,
                not(IsEmptyCollection.empty())
            )
            assertThat(
                "It should not be empty",
                domain.analyzedInstructions,
                not(IsEmptyCollection.empty())
            )
            assertEquals(
                "Length should be $RECIPE_STEPS",
                domain.analyzedInstructions.size,
                RECIPE_STEPS
            )
        }
    }

    @Test
    fun testToDomainException() {
        assertFailsWith<IllegalStateException> {
            GsonBuilder().apply {
                this.registerTypeAdapter(
                    InstructionsDeserializer.typeToken,
                    InstructionsDeserializer()
                )
            }.create()
                .fromJson<GetRecipesResponseDTO>(SAMPLE_RESPONSE, GetRecipesResponseDTO::class.java)
                .toDomain()
        }
    }
}