Use Case 3: Centralized Room Inventory Management
Goal

Introduce centralized inventory management by replacing scattered availability variables with a single, consistent data structure, demonstrating how HashMap solves real-world state management problems.

Actor

RoomInventory – responsible for managing and exposing room availability across the system.

Flow
The system initializes the inventory component.
Room types are registered with their available counts.
Availability is stored and retrieved from a centralized HashMap.
Updates to availability are performed through controlled methods.
The current inventory state is displayed when requested.
Key Concepts Used

Problem of Scattered State
In the previous use case, availability was stored in separate variables. This leads to inconsistent updates, duplication, and poor scalability as the system grows.

HashMap
HashMap<String, Integer> is used to map room types to available room counts. This allows fast access, updates, and lookups based on a logical key.

O(1) Lookup
HashMap provides average constant-time complexity for get and put operations. This makes it suitable for systems that require frequent availability checks.

Single Source of Truth
All availability data is maintained in one centralized structure. This eliminates discrepancies caused by multiple variables representing the same state.

Encapsulation of Inventory Logic
Inventory-related operations are encapsulated within a dedicated class. Other parts of the system interact with inventory only through exposed methods, reducing coupling.

Separation of Concerns
Inventory manages how many rooms are available, not what a room is. Room characteristics such as price and size remain part of the Room domain model.

Scalability
Adding a new room type requires only inserting a new entry into the map. No changes are required in application logic, demonstrating scalable design.

Key Requirements
Initialize room availability using a constructor.
Store room availability using a HashMap.
Provide methods to retrieve current availability.
Support controlled updates to room availability.
Display the centralized inventory state.
Key Benefits
Eliminates scattered availability variables
Creates a single source of truth for inventory
Improves scalability and maintainability
Demonstrates practical use of HashMap in real-world systems
Drawbacks of Previous Use Case
Availability was stored in separate variables for each room type.
Updating room counts required manual handling in multiple places.
The design was not scalable for adding new room types.