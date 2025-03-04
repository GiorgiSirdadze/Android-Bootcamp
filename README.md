# ExploreIt

### Why This App Was Created
I built this app to help people explore different travel tours easily. The idea came from wanting to create something useful that allows users to discover tours, search through them based on filters (like price, duration, etc.), and even book tours directly. It's all about making it easier for travelers to find the right tour for them in just a few taps.

### How to Use the App
- **Browse Tours:** The app shows a list of available tours. You can scroll through them and see details like the location, price, and rating.
- **Search Tours:** Use the search bar to type keywords (like the tour title or location) and find tours that match.
- **Filters:** There are buttons to filter the tours by specific criteria like popular tours, low-price tours, or tours with longer durations.
- **User Profile:** The app grabs the username of the logged-in user and shows a personalized greeting (so you feel like it’s your app).
- **Booking:** You can click on any tour to see more details and book it.

### Attributes Used to Create the App
Here’s a list of things I used to build this app:

1. **Firebase Authentication** – For handling user login and registration.
2. **Firebase Firestore** – To store user data and any tour-related information (like ratings, price, etc.).
3. **RecyclerView** – For displaying the list of tours.
4. **ViewModel + StateFlow** – To manage the UI-related data and handle the business logic efficiently.
5. **Dagger Hilt** – For dependency injection, making it easier to manage objects and their dependencies.
6. **Glide** – For loading images efficiently (like tour images).
7. **Coroutines** – For performing background tasks like fetching tour data from the API without blocking the UI.
8. **Kotlin** – The language I used for everything (because it’s modern, clean, and works great with Android).
9. **API Integration** – I used an API to fetch the list of tours, and wrapped the calls with a `Resource` class to handle loading states and errors gracefully.

### What’s Still to Be Done?
This app is still a work in progress. I’ve added a lot of the core features, but there are always ways to improve:
- More UI/UX tweaks to make it even more user-friendly.
- Adding more tours or extending the search functionality.
- Optimizing some parts of the code and handling edge cases better.

Let me know what you think, and feel free to suggest improvements!
