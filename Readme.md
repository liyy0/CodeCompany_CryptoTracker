# CodeCompany Crypto Tracker

# Motivation
In today's dynamic cryptocurrency market, rapid price fluctuations present both opportunities and risks. However, individuals often find it challenging to monitor market movements continuously amidst daily activities such as travel, meals, and social engagements. Thankfully, modern technology empowers individuals to carry the functionality of a computer in their pockets through mobile phones.

In response to this need, we are developing Crypto Tracker, an app designed to provide real-time market data for every cryptocurrency along with relevant market news. By leveraging this app, users can actively engage with the crypto market at any time, whether they are investors, active traders, or simply curious about crypto trends.

Our initial concept evolved into a comprehensive app that:

- Tracks cryptocurrencies, capturing essential information such as price, volume, market cap, 24-hour price change, and daily high/low.
- Aggregates news articles related to the crypto market, offering users insights into current trends and developments.
- Incorporates a notification system to alert users when a certain price threshold is reached, enabling timely decision-making.
- Features a user-friendly search bar, eliminating the need for manual scrolling through extensive lists of cryptocurrencies.
- Provides a dedicated watchlist where users can monitor their preferred currencies, facilitating personalized market tracking.
- Includes a settings page for user feedback and communication with developers, ensuring continuous improvement and user engagement.

By offering a seamless experience for accessing real-time market data and news, Crypto Tracker empowers users to stay informed and engaged with the crypto market, anytime and anywhere.


# Main Features

## Market View
<div style="display:flex;">
  <img src="screenshots/market.png" alt="Market view" width="300" style="padding-right: 10px;">
  <img src="screenshots/likebutton.png" alt="click the star icon" width="300" style="padding-right: 10px;">
</div>

- List of cryptocurrencies and their basic information (price change, current price).
- Each cryptocurrency item has a star icon to add it to the watchlist.
- Fixed position search bar for easy coin search.
  
## Watchlist
<img src="screenshots/watchlist.png" alt="Watchlist when the coin Ethereum is added. Shows the price, market cap, 24 hour high and low, and price change in the past 24 hours" width="300" style="margin-right: 10px;">

- Displays cryptocurrencies starred from the market view.
- Shake device to randomly add popular cryptocurrencies if watchlist is empty.
- Clicking on a cryptocurrency directs user to its detail page.
- Swipe left or right to remove cryptocurrency from the watchlist.

## News List
<img src="screenshots/news.png" alt="News list, contains title, photo(if it has one), and some texts of the news" width="300" style="padding-right: 10px;">

- A list of news relevant to the cryptocurrency market.
- Clicking on news directs user to a webpage with more details.

## Detail Page
<div style="display:flex;">
  <img src="screenshots/detailpage1.png" alt="Bitcon Detail View" width="300" style="padding-right: 10px;">
  <img src="screenshots/detailpage2.png" alt="Bitcon Detail View" width="300" style="padding-right: 10px;">
  <img src="screenshots/detailpage3.png" alt="Bitcon Detail View" width="300">
</div>

- Contains detailed information and charts for a cryptocurrency.
- Users can modify the chart duration to display data for 7, 30, or 90 days.
- Basic information about the cryptocurrency and a trade button directing users to Binance.
- Price and volume charts for the selected duration.
- Latest news related to the cryptocurrency, clickable to view more details.

# Architecture
### Common Folder
The **Common** folder contains the `navigation` file, which is crucial for handling events triggered by clicking the navigation bar at the bottom of the app. This file ensures seamless navigation, directing users to the appropriate pages within the application.
### Data Folder
#### local
Within the **Data** folder, the **local** subfolder hosts the `developer` file. This file includes a class responsible for managing developer information displayed in the "Settings" page of the app. Users can access details about the developers behind the app's creation through this feature.
#### model
The **model** subfolder in the **Data** directory encompasses multiple classes defining structures of information fetched from API calls. These models play a pivotal role in displaying data on both the main and detail pages of the application.

### ViewModels
ViewModels, including `CoinTickerViewModel`, `CoinDataViewModel`, and `CoinNewsViewModel`, serve as intermediaries between the Views (UI components) and the Model (data layer). Leveraging Kotlin's coroutines and Flow, these ViewModels efficiently manage asynchronous data streams, ensuring a responsive and dynamic user interface.

### Network Folder
The **Network** folder houses Retrofit interfaces, such as `CoinRepos`, which facilitate seamless communication with cryptocurrency APIs to fetch data. By abstracting the complexity of network communication and data retrieval, this layer provides a clean API for ViewModels to interact with, enhancing overall code maintainability and readability.

### Presentation Folder
The **Presentation** folder comprises files responsible for user interaction, initializing pages/fragments, and designing the user interface using Jetpack Compose. From handling user actions like touching, swiping, and shaking the device to initializing various pages such as the crypto currency list, news list, watchlist, settings, and crypto currency detail, this folder plays a pivotal role in shaping the user experience of the application.

### ui.theme Folder
The **ui.theme** folder is dedicated to setting the color scheme for the app, ensuring a visually cohesive and aesthetically pleasing user interface across different screens and components.


## Technologies / Dependencies

- Java
- Android SDK
- AndroidX
- Preview: Android WebView
- Data Plotting: Ychart
- Build system: Gradle

## External Resources

- Cryptocurrency API: [https://api.coingecko.com/api/v3/](https://api.coingecko.com/api/v3/)
- News API: [https://newsapi.org/](https://newsapi.org/)

## Privacy

Crypto Tracker will not use your local data or request your personal information. No personal data is shared with the author or any third parties. The app does not involve any trading recommendation mechanism nor any trade involving real currency. Files are stored locally in a user-selectable folder, defaulting to the internal storage "Documents" directory.

## Android Permissions

- INTERNET
- ACCELEROMETER SENSOR
- WRITE & READ FROM LOCAL FILE

# Important Functionality

### Plotting Data
In **AssetDetail.kt**, the `Chart()` function serves as a cornerstone for visualizing historical market data based on the selected date range (7 days, 30 days, or 90 days). By leveraging the `DottedLinechart()` function, this feature seamlessly renders charts, connecting coordinate points and offering users valuable insights into market trends.

### Adding Crypto Currency to Favorite
The **AssetScreen.kt** file introduces functionality enabling users to add crypto currencies to their favorites list. By simply clicking the star icon associated with each crypto currency on the main page, users can effortlessly curate their watchlist, enhancing their ability to track preferred assets and make informed decisions.

### Language Support
The app boasts robust language support, with current implementation catering to Mandarin-speaking users. Through seamless language detection, the app dynamically adapts its content based on the user's device language, ensuring a personalized and inclusive user experience. The **AssetDetail.kt** function further enhances language support, enabling seamless display of content in the user's preferred language.

### Shake to Add Coin, Swipe to Remove from Watchlist
Incorporating intuitive gestures, the app allows users to shake their device to add a popular coin to their watchlist seamlessly. Utilizing the accelerometer sensor, the **ShakeDetector.kt** file detects device movement, triggering the addition of a coin to the watchlist upon a shake gesture. Furthermore, the **SwipeCard** function in **AssetScreen.kt** empowers users to effortlessly manage their watchlist by enabling swipe gestures for removing coins, enhancing overall usability and user engagement.

# Acknowledgment

This report underwent thorough review for typographical and grammatical errors, ensuring clarity and consistency in the presentation of the talk's content and analysis. Tools like Grammarly and ChatGPT were utilized in this process.

## Software Development Disclaimer

In the development of our software, we employ ChatGPT exclusively for debugging purposes. Any modifications made to the code by ChatGPT are minor and are implemented solely to ensure the quality of the software.

The application developed by our team serves solely as a data conduit and does not offer any form of advice or recommendations. The developers disclaim all responsibility for any risks or losses that may arise from the use of this application. Users should be aware that investing carries inherent risks and must approach market entry with caution.

