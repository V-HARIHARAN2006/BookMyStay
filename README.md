README Description
Use Case 5: Booking Request Queue
Goal

Handle multiple booking requests fairly by introducing a request intake mechanism that preserves arrival order, reflecting real-world booking behavior during peak demand.

Actor

Reservation – represents a guest’s intent to book a room.
Booking Request Queue – manages and orders incoming booking requests.

Flow
Guest submits a booking request.
The request is added to the booking queue.
Requests are stored in arrival order.
Queued requests wait for processing by the allocation system.
No inventory mutation occurs at this stage.
Key Concepts Used

Problem of Simultaneous Requests
During peak demand, multiple booking requests can arrive at nearly the same time. Without ordering, requests may be processed inconsistently, leading to unfair allocation.

Queue Data Structure
A Queue<Reservation> is used to store booking requests. Queues naturally model waiting lines where elements are processed in sequence.

FIFO Principle
FIFO (First-Come-First-Served) ensures that the earliest request is processed first. This mirrors fairness expectations in real booking systems.

Fairness
Using a queue guarantees that no request can bypass another. All guests are treated equally based on request arrival time.

Request Ordering
The queue preserves insertion order automatically. This eliminates the need for manual sorting or timestamp comparison.

Decoupling Request Intake from Allocation
Requests are collected first and processed later. This separation prepares the system for controlled allocation and concurrency handling.

Key Requirements
Accept booking requests from guests.
Store requests in a queue structure.
Preserve the order in which requests arrive.
Ensure no room allocation or inventory updates occur at this stage.
Prepare requests for subsequent processing.
Key Benefits
Ensures fair request handling
Preserves arrival order automatically
Separates booking intake from room allocation
Prepares the system for future processing logic
Drawbacks of Previous Use Case
Previous use case allowed room search but did not capture guest booking intent.
No structure existed to manage multiple incoming requests.
The system could display availability, but could not organize requests fairly for later processing