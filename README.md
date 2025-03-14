This project is built using Kotlin and Jetpack Compose with MVVM architecture. 
It integrates with the Fleetio API to display and manage vehicles, implementing features like pagination, filtering, updates, and location tracking.

Retrofit & OkHttp – Handles API communication with Fleetio (GET & PATCH).
Hilt – Provides Dependency Injection for better modularity.
Coil – Efficient image loading and caching.
Paging 3 – Enables smooth infinite scrolling for large datasets.
Room – Stores offline data for a seamless user experience.
Google Maps API – Displays vehicle locations on a map.
Gson – Converts JSON responses into Kotlin data classes.

Implemented Core Features:

Vehicle List Screen – Displays all vehicles with pagination.
Vehicle Details Screen – Shows detailed information and allows updates.
Comment List Screen – Displays user comments (read-only).

Also I completed all of the additional features:

Additional Features Completed:

1. Filtering – Users can filter vehicles by name.
2. Editable VIN & License Plate – Users can update vehicle details.
3. Map View – Displays vehicle’s last known location.
4. Comments Section – Shows a list of vehicle-related comments (read-only).
