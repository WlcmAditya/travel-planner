# Travel Planner

## Overview

Travel Planner is an AI-powered travel planning platform built as an enterprise-grade Spring Boot backend. It combines durable workflow orchestration, AI-driven itinerary generation, and process-modeled business logic to plan, adjust, and manage end-to-end travel experiences.

## Technology Stack

- Java 21
- Spring Boot 3.x
- Maven
- MySQL
- Spring Data JPA
- Temporal (durable workflow orchestration)
- LangGraph (AI agent orchestration)
- OpenRouter (LLM gateway)
- JWT (authentication)
- Swagger / OpenAPI (API documentation)
- Docker

## Repository Structure

- `.ai/` — Machine-readable and human-readable specifications, rules, and prompts used to drive automated code generation (OpenSWE).
- `bpmn/` — Business process model definitions for the travel planning workflow.
- `docs/` — Architecture, API, database, and deployment documentation.
- `src/` — Application source root (populated during code generation).
- `.github/workflows/` — CI/CD pipeline definitions.
- `docker/` — Containerization assets.
- `scripts/` — Operational and build scripts.

## Status

This repository currently contains only the initial project skeleton. Business logic, domain models, and application code are generated in a subsequent phase by OpenSWE, driven by the specifications under `.ai/` and `bpmn/`.

## License

See [LICENSE](./LICENSE).
