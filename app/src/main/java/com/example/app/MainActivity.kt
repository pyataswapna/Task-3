package com.example.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var selectedFood = ""
    private var selectedPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLayout = LinearLayout(this)
        mainLayout.orientation = LinearLayout.VERTICAL
        mainLayout.setBackgroundColor(Color.WHITE)

        // Header
        val header = LinearLayout(this)
        header.orientation = LinearLayout.VERTICAL
        header.setPadding(25, 30, 25, 25)
        header.setBackgroundColor(Color.rgb(255, 87, 34))

        val restaurantName = TextView(this)
        restaurantName.text = "🍴 Tasty Bites"
        restaurantName.textSize = 30f
        restaurantName.setTextColor(Color.WHITE)
        restaurantName.gravity = Gravity.CENTER

        val subtitle = TextView(this)
        subtitle.text = "Delicious food, delivered with love ❤️"
        subtitle.textSize = 16f
        subtitle.setTextColor(Color.WHITE)
        subtitle.gravity = Gravity.CENTER

        header.addView(restaurantName)
        header.addView(subtitle)

        // Scroll area
        val scrollView = ScrollView(this)
        val content = LinearLayout(this)
        content.orientation = LinearLayout.VERTICAL
        content.setPadding(25, 20, 25, 30)

        // Search
        val search = EditText(this)
        search.hint = "🔍 Search food..."
        search.setPadding(20, 10, 20, 10)

        content.addView(search)

        // Categories
        val categoryTitle = TextView(this)
        categoryTitle.text = "Food Categories"
        categoryTitle.textSize = 22f
        categoryTitle.setPadding(0, 20, 0, 10)

        content.addView(categoryTitle)

        val categories = LinearLayout(this)
        categories.orientation = LinearLayout.HORIZONTAL

        val veg = Button(this)
        veg.text = "🥗 Veg"

        val nonVeg = Button(this)
        nonVeg.text = "🍗 Non-Veg"

        val drinks = Button(this)
        drinks.text = "🥤 Drinks"

        categories.addView(veg)
        categories.addView(nonVeg)
        categories.addView(drinks)

        content.addView(categories)

        // Menu
        val menuTitle = TextView(this)
        menuTitle.text = "Today's Special Menu"
        menuTitle.textSize = 24f
        menuTitle.setPadding(0, 25, 0, 15)

        content.addView(menuTitle)

        // Food items
        addFoodItem(
            content,
            "🍕 Cheese Pizza",
            "Cheesy and delicious pizza",
            199
        )

        addFoodItem(
            content,
            "🍔 Chicken Burger",
            "Crispy chicken with fresh vegetables",
            149
        )

        addFoodItem(
            content,
            "🍛 Chicken Biryani",
            "Special aromatic Hyderabad biryani",
            249
        )

        addFoodItem(
            content,
            "🥞 Masala Dosa",
            "Crispy dosa with chutney and sambar",
            99
        )

        addFoodItem(
            content,
            "🍜 Veg Noodles",
            "Fresh vegetables with tasty noodles",
            129
        )

        addFoodItem(
            content,
            "🥤 Cold Drink",
            "Chilled refreshing soft drink",
            59
        )

        // Selected item
        val selectedText = TextView(this)
        selectedText.text = "No item selected"
        selectedText.textSize = 18f
        selectedText.setPadding(0, 20, 0, 10)

        content.addView(selectedText)

        // Order button
        val orderButton = Button(this)
        orderButton.text = "🛒 PLACE ORDER"
        orderButton.textSize = 18f

        orderButton.setOnClickListener {

            if (selectedFood.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please select a food item first",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                selectedText.text =
                    "✅ Order Confirmed!\n$selectedFood - ₹$selectedPrice"

                Toast.makeText(
                    this,
                    "Order placed successfully!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        content.addView(orderButton)

        // Restaurant information
        val info = TextView(this)
        info.text =
            "\n📍 Location: Hyderabad\n" +
                    "🕐 Opening Hours: 10:00 AM - 10:00 PM\n" +
                    "⭐ Rating: 4.5/5\n" +
                    "📞 Contact: 9876543210"

        info.textSize = 16f
        info.setPadding(0, 25, 0, 20)

        content.addView(info)

        scrollView.addView(content)

        mainLayout.addView(header)
        mainLayout.addView(
            scrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        setContentView(mainLayout)
    }

    private fun addFoodItem(
        parent: LinearLayout,
        name: String,
        description: String,
        price: Int
    ) {

        val card = LinearLayout(this)
        card.orientation = LinearLayout.VERTICAL
        card.setPadding(20, 15, 20, 15)
        card.setBackgroundColor(Color.rgb(245, 245, 245))

        val nameText = TextView(this)
        nameText.text = name
        nameText.textSize = 21f

        val descriptionText = TextView(this)
        descriptionText.text = description
        descriptionText.textSize = 14f

        val priceText = TextView(this)
        priceText.text = "₹$price"
        priceText.textSize = 18f

        val selectButton = Button(this)
        selectButton.text = "Select"

        selectButton.setOnClickListener {

            selectedFood = name
            selectedPrice = price

            Toast.makeText(
                this,
                "$name selected",
                Toast.LENGTH_SHORT
            ).show()
        }

        card.addView(nameText)
        card.addView(descriptionText)
        card.addView(priceText)
        card.addView(selectButton)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(0, 0, 0, 15)

        parent.addView(card, params)
    }
}