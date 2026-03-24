
Use Case 10: Booking Cancellation & Inventory Rollback
Goal

Enable safe cancellation of confirmed bookings by correctly reversing system state changes, ensuring inventory consistency and predictable recovery behavior.

Actor

Guest – initiates a cancellation request for an existing booking.
Cancellation Service – validates cancellations and performs controlled rollback operations.

Flow
Guest initiates a cancellation request.
The system validates the reservation to ensure it exists and is cancellable.
The allocated room ID is recorded in a rollback structure.
Inventory count for the corresponding room type is incremented.
Booking history is updated to reflect the cancellation.
System state is restored consistently.
Key Concepts Used

State Reversal
Cancellation requires undoing previously completed operations. The system must revert inventory and booking state without introducing inconsistencies.

Stack Data Structure
A Stack<String> is used to track recently released room IDs. Stacks follow a Last-In-First-Out (LIFO) order, which naturally models rollback behavior.

LIFO Rollback Logic
The most recent allocation is the first to be reversed. This aligns with real-world undo operations and simplifies recovery logic.

Controlled Mutation
State changes during cancellation are performed in a strict, predefined order. This prevents partial rollbacks and protects system integrity.

Inventory Restoration
Inventory counts are incremented immediately after cancellation. This ensures availability accurately reflects the current system state.

Validation of Cancellation Requests
The system verifies that a reservation exists before allowing cancellation. Invalid or duplicate cancellation attempts are rejected safely.

Key Requirements
Allow cancellation of confirmed bookings only.
Validate reservation existence before performing rollback.
Release allocated room IDs back to the availability pool.
Restore inventory counts accurately and immediately.
Prevent cancellation of non-existent or already cancelled bookings.
Key Benefits
Safely reverses confirmed bookings
Restores inventory consistently
Prevents duplicate or invalid cancellations
Demonstrates rollback logic using stack behavior
Drawbacks of Previous Use Case
Previous use case validated booking input but did not support undoing confirmed reservations.
Once inventory was decremented, no recovery path existed.
The system lacked controlled rollback behavior for cancellations
=======
Use Case 9: Error Handling & Validation
Goal

Strengthen system reliability by introducing structured validation and error handling, ensuring that invalid inputs and inconsistent states are detected and handled early.

Actor

Guest – provides booking input that must be validated.
Invalid Booking Validator – validates input and system state before processing requests.

Flow
Guest provides booking input.
System validates input values and system constraints.
If validation fails, an error is raised immediately.
A meaningful failure message is displayed.
The system prevents invalid state changes and continues running safely.
Key Concepts Used

Input Validation
Validation ensures that incoming data conforms to expected rules before processing. This prevents invalid or inconsistent data from entering the system.

Custom Exceptions
Domain-specific exceptions are used to represent invalid booking scenarios. Custom exceptions make error causes explicit and improve code readability.

Fail-Fast Design
The system detects errors as early as possible and stops further processing. This avoids cascading failures and simplifies debugging.

Guarding System State
Checks are performed before inventory updates or allocations. This ensures that critical state, such as availability counts, remains valid.

Graceful Failure Handling
Errors are communicated clearly without crashing the application. This improves system usability and maintainability.

Correctness over Happy Path
The system is designed to handle incorrect usage, not just ideal scenarios. This reflects real-world conditions where invalid input is common.

Key Requirements
Validate room types before processing bookings.
Prevent inventory from reaching invalid or negative values.
Throw and handle custom exceptions for invalid scenarios.
Display clear and informative failure messages.
Ensure the system remains stable after errors.
Key Benefits
Improves booking reliability
Prevents invalid system state changes
Makes failures easier to understand
Keeps the application stable even when errors occur
Drawbacks of Previous Use Case
Previous use case stored booking history but did not validate bad booking input.
Invalid room types or inconsistent inventory states could still enter the flow.
Error conditions were not modeled explicitly through custom exceptions.
