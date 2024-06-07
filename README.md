# Qryptium: An Android Cryptocurrency App

### Overview

Qryptium is an Android application that displays a list of cryptocurrency trading pairs in USD,
retrieved from the Bitfinex API. The app updates the crypto pairs every 5 seconds and allows users
to manually refresh the data via a pull-to-refresh feature.

---

## Table of Contents

1. [Introduction](#introduction)
2. [Requirements](#requirements)
3. [Architecture](#architecture)
4. [UI Design](#ui-design)
5. [API Integration](#api-integration)
6. [Features](#features)
7. [Testing](#testing)

---

## Introduction

Qryptium is a comprehensive cryptocurrency marketplace application built with Kotlin, utilizing the
MVVM w/ multi-module architecture. The app integrates multiple modern technologies to ensure a
seamless user experience and robust functionality.

---

## Requirements

- Data on the screen should update automatically every 5 seconds.
- Users should be able to filter the results using a search bar.
- The app should react to connectivity changes.
- Keep the architecture simple and user-centric.
- Include unit tests for business logic.

---

## Architecture

The application follows a modular architecture, leveraging a combination of feature-based and
component-based modularization. It mas use of MVVM, with layering of Data, Domain & Presentation.
*Marketplace domain layer is empty for now, since alla domain specific files have been moved to
core-domain in order to be accessible within the app.

### Core Components

- **Presentation Layer:**
    - **Design System:** Contains all the reusable design components.

- **Domain Layer:**
    - Contains business logic and is written in pure Kotlin for reusability across different
      applications.

- **Data Layer:**
    - Handles data fetching from remote and local sources. In this application, the remote source is
      used.

### Modules

Each feature module comprises three layers: data, domain, and presentation. This separation ensures
clean structure to achieve seperation of concerns and facilitates future scalability and
maintenance.

---

## UI Design

The UI is built using Jetpack Compose, ensuring a modern and responsive design.

### Main Screen

- **Cryptocurrency List:** Displays a list of trading pairs with their current prices.
- **Pull to Refresh:** Allows users to manually refresh the data.
- **Search Bar:** Enables users to filter the trading pairs.
- **Connectity indicator at the top bar, indicatin network connectity changes

---

## API Integration

The app retrieves cryptocurrency data from the Bitfinex API.

### Data Fetching

- **Coroutine-based threading:** Ensures smooth data fetching and UI updates.
- **Ktor:** Used for making network requests.

### DI (Dependency Injection)

- **Koin:** Used for dependency injection.

### UI

- Compose is used for ui items
- Actions are user actions that are sent to the VM
- State is the current screen state and source of truth, which indicates the app's unique screen
  state at any time

---

## Features

### Auto-update

The app updates the cryptocurrency data every 5 seconds automatically.

### Search Functionality

Users can filter the list of trading pairs using the search bar.

### Connectivity Changes

The app monitors network connectivity in real-time and notifies users of any changes.

### Utility Functions

A utility for handling string resources from ViewModel to UI without requiring context.

---

## Testing

The application includes unit tests for the business logic to ensure reliability and correctness.

---
