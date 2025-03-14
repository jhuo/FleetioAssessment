This project is built using Kotlin and Jetpack Compose with MVVM architecture. 
It integrates with the Fleetio API to display and manage vehicles, implementing features like pagination, filtering, updates, and location tracking.

1. Retrofit & OkHttp – Handles API communication with Fleetio (GET & PATCH).
2. Hilt – Provides Dependency Injection for better modularity.
3. Coil – Efficient image loading and caching.
4. Paging 3 – Enables smooth infinite scrolling for large datasets.
5. Room – Stores offline data for a seamless user experience.
6. Google Maps API – Displays vehicle locations on a map.
7. Gson – Converts JSON responses into Kotlin data classes.

Implemented Core Features:

1. Vehicle List Screen – Displays all vehicles with pagination.
2. Vehicle Details Screen – Shows detailed information and allows updates.
3. Comment List Screen – Displays user comments (read-only).

Additional Features Completed:

1. Filtering – Users can filter vehicles by name.
2. Editable VIN & License Plate – Users can update vehicle details.
3. Map View – Displays vehicle’s last known location.
4. Comments Section – Shows a list of vehicle-related comments (read-only).
