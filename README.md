# first-job-ms

A microservices rewrite of [find-job](https://github.com/anshul2ind/find-job), splitting the job-board domain (companies, jobs, reviews) into independently deployable Spring Boot services with service discovery, centralized config, distributed tracing, and event-driven data consistency.

## Services

**Core infrastructure** (`core/`)
- `service-reg` — Eureka service registry
- `config-server` — Spring Cloud Config server
- `gateway` — Spring Cloud Gateway, routes `/companies/**`, `/jobs/**`, `/reviews/**` to their respective services

**Domain services** (`ms/`)
- `company-ms` — owns company data; consumes rating-update events off RabbitMQ to keep each company's aggregate rating in sync
- `job-ms` — owns job postings; calls `company-ms` and `review-ms` via OpenFeign to enrich job details with company info and reviews
- `review-ms` — owns reviews; recomputes a company's average rating on write and publishes the change via the transactional outbox pattern over RabbitMQ

## Cross-cutting concerns

- **Service discovery & routing** — Netflix Eureka + Spring Cloud Gateway
- **Inter-service calls** — OpenFeign clients, resilience via Resilience4j (circuit breaker/retry)
- **DTO mapping** — MapStruct
- **Async consistency** — transactional outbox + RabbitMQ, so rating aggregation in `review-ms` reaches `company-ms` without dual-write issues
- **Distributed tracing** — Micrometer Tracing (Brave) exporting to Zipkin
- **Data** — one PostgreSQL instance, one database per service

## Running locally

```bash
docker-compose up --build
```

Brings up Postgres, RabbitMQ, Zipkin, Eureka, Config Server, the gateway, and all three domain services. Gateway is exposed on `:8080`, Eureka dashboard on `:8761`, RabbitMQ management UI on `:15673`, Zipkin UI on `:9412`.

A Kubernetes manifest set for the same topology is under [`k8s/`](k8s/).
