<p align="center">
  <img src="app/src/main/res/drawable/ic_logo.png" width="120" alt="WorCowT Logo"/>
</p>

<h1 align="center">WorCowT</h1>
<p align="center"><strong>Build Better Habits, One Moo at a Time!</strong></p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green?logo=android" alt="Platform"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-purple?logo=kotlin" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Backend-Supabase-darkgreen?logo=supabase" alt="Supabase"/>
  <img src="https://img.shields.io/badge/Version-0.1--debug-blue" alt="Version"/>
  <img src="https://img.shields.io/badge/Architecture-MVVM-orange" alt="MVVM"/>
</p>

---

## Abstract

**WorCowT** is a gamified daily-routine and habit-tracking Android application designed to help students, professionals, and health-conscious individuals build and sustain productive habits. The app generates personalised daily schedules based on user preferences (wake/sleep times, water goals, workout habits, medication schedules) and motivates consistency through an XP and streak system. A friendly cow mascot guides users through every step, turning mundane self-improvement into a playful, rewarding experience.

---

## Problem Statement

In today's fast-paced digital world, maintaining a consistent daily routine is one of the most common struggles faced by students and young professionals. Existing productivity apps are either too complex, lack personalisation, or fail to sustain long-term engagement. Users abandon most habit trackers within the first week because the apps feel like chores rather than companions. There is a clear need for a simple, visually engaging, and gamified solution that adapts to individual lifestyles and keeps users motivated through positive reinforcement rather than guilt.

---

## Objectives

- Provide a **personalised routine generator** that adapts to each user's schedule, role, and lifestyle preferences.
- Implement a **gamification engine** (XP, levels, streaks) to sustain user engagement and reward consistency.
- Deliver **timely smart notifications** for water intake, workouts, medication, productivity blocks, and sleep.
- Offer a **clean, minimal, and playful UI** with a mascot-driven experience that feels approachable rather than clinical.
- Use a **cloud-first architecture** (Supabase) so user data is synced, backed up, and accessible across device resets.
- Track and display **progress statistics** so users can visualise their improvement over time.

---

## Features

| Feature | Description |
|---------|-------------|
| Email/Password Auth | Secure sign-up and sign-in via Supabase Authentication |
| Personalised Onboarding | 10-question questionnaire to tailor routines to the user |
| Routine Generator | Algorithm-driven daily schedule based on wake time, sleep time, and preferences |
| Timeline View | Vertical scrollable timeline with task cards showing name, time, and status |
| Task Actions | Complete, snooze, or skip any task -- each action is logged |
| XP and Levels | +10 XP per task, +50 daily bonus, +100 weekly streak; 5 level tiers |
| Streak Tracking | Consecutive-day tracking with streak break/recovery logic |
| Smart Notifications | AlarmManager-based exact reminders for water, workout, meds, productivity, sleep |
| Boot Persistence | Alarms automatically reschedule after device reboot |
| Statistics Dashboard | Numerical stats: XP total, tasks completed/skipped, current/longest streak |
| Mascot Companion | Friendly cow mascot with motivational messages throughout the app |
| Profile Management | View/edit profile, sign out |
| Developer Page | About the developer section |

---

## Screenshots

<p align="center">
  <img src="screenshots/landing.png" width="250" alt="Landing Screen"/>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="screenshots/login.png" width="250" alt="Login Screen"/>
</p>

<p align="center">
  <em>Left: Landing screen with mascot &nbsp;|&nbsp; Right: Email/Password sign-in</em>
</p>

---

## Software Requirements Specification (SRS)

### 1. Functional Requirements

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-01 | The system shall allow users to register and authenticate using email and password | High |
| FR-02 | The system shall present a 10-step onboarding questionnaire on first login | High |
| FR-03 | The system shall generate a personalised daily routine based on onboarding answers | High |
| FR-04 | The system shall display routines as a vertical timeline with task cards | High |
| FR-05 | The system shall allow users to mark tasks as completed, snoozed, or skipped | High |
| FR-06 | The system shall award XP for completed tasks and apply penalties for snooze/skip | Medium |
| FR-07 | The system shall track daily streaks and compute longest-streak records | Medium |
| FR-08 | The system shall calculate user level based on cumulative XP thresholds | Medium |
| FR-09 | The system shall send scheduled notifications for upcoming tasks | High |
| FR-10 | The system shall reschedule notifications after device reboot | Medium |
| FR-11 | The system shall display a statistics dashboard with XP, streaks, and task counts | Medium |
| FR-12 | The system shall allow users to add, edit, and delete custom routines | Medium |
| FR-13 | The system shall persist all data to a cloud database (Supabase) | High |
| FR-14 | The system shall enforce row-level security so users can only access their own data | High |

### 2. Non-Functional Requirements

| ID | Requirement |
|----|-------------|
| NFR-01 | The app shall launch to the landing screen within 2 seconds on mid-range devices |
| NFR-02 | All network requests shall timeout gracefully after 15 seconds with user feedback |
| NFR-03 | The UI shall follow Material Design 3 guidelines for consistency and accessibility |
| NFR-04 | User passwords shall never be stored locally; authentication is handled by Supabase Auth |
| NFR-05 | The app shall function offline for viewing cached routines (cloud sync on reconnect) |
| NFR-06 | The APK size shall remain under 50 MB |

### 3. Hardware and Software Requirements

| Component | Requirement |
|-----------|-------------|
| OS | Android 7.0 (API 24) or higher |
| RAM | 2 GB minimum |
| Storage | 100 MB free space |
| Network | Internet connection required for authentication and data sync |
| Development IDE | Android Studio Ladybug (2024.3+) |
| Language | Kotlin 1.9+ |
| JDK | 17 or higher |
| Backend | Supabase (PostgreSQL, GoTrue Auth) |

---

## System Architecture

The application follows the **MVVM (Model-View-ViewModel)** pattern with a clean separation between presentation, business logic, and data layers.

```mermaid
graph TD
    subgraph presentation [Presentation Layer]
        Activities[Activities]
        Fragments[Fragments]
        ViewModels[ViewModels]
    end

    subgraph logic [Business Logic]
        RoutineEngine[Routine Generator]
        XPCalculator[XP Calculator]
        StreakManager[Streak Manager]
        NotifScheduler[Notification Scheduler]
    end

    subgraph data [Data Layer]
        Repositories[Repositories]
        SupabaseClient[Supabase Client]
        SharedPrefs[SharedPreferences]
    end

    subgraph backend [Supabase Backend]
        Auth[Email Auth]
        DB[PostgreSQL]
        RLS[Row Level Security]
    end

    Activities --> ViewModels
    Fragments --> ViewModels
    ViewModels --> RoutineEngine
    ViewModels --> XPCalculator
    ViewModels --> StreakManager
    ViewModels --> NotifScheduler
    RoutineEngine --> Repositories
    XPCalculator --> Repositories
    StreakManager --> Repositories
    Repositories --> SupabaseClient
    SupabaseClient --> Auth
    SupabaseClient --> DB
    DB --> RLS
```

---

## ER Diagram

```mermaid
erDiagram
    users {
        uuid user_id PK
        varchar name
        varchar email
        int age
        varchar role
        time wake_time
        time sleep_time
        int water_goal
        int meals_per_day
        boolean workout_preference
        varchar medication_timing
        varchar productivity_preference
        int xp
        int level
        int current_streak
        timestamp created_at
    }

    routines {
        uuid routine_id PK
        uuid user_id FK
        varchar task_name
        varchar category
        time scheduled_time
        boolean recurring
        boolean is_active
        timestamp created_at
    }

    task_logs {
        uuid log_id PK
        uuid user_id FK
        uuid routine_id FK
        varchar completion_status
        timestamp completion_time
        int xp_awarded
        date log_date
    }

    statistics {
        uuid user_id PK
        int tasks_completed
        int tasks_skipped
        int tasks_snoozed
        int current_streak
        int longest_streak
        int xp_total
        date last_active_date
    }

    users ||--o{ routines : "has many"
    users ||--o{ task_logs : "logs"
    users ||--|| statistics : "has one"
    routines ||--o{ task_logs : "tracked by"
```

---

## User Flow Diagram

```mermaid
flowchart TD
    A[App Launch] --> B[Splash Screen]
    B --> C{Logged In?}
    C -->|No| D[Landing Screen]
    D --> E[Login / Sign Up]
    E -->|Sign Up| F[Create Account]
    F --> G[Onboarding Questionnaire]
    E -->|Sign In| H{Onboarding Done?}
    H -->|No| G
    G --> I[Generate Routines]
    I --> J[Home Timeline]
    H -->|Yes| J
    C -->|Yes| H

    J --> K[Complete / Snooze / Skip Task]
    K --> L[XP Awarded + Streak Updated]
    L --> J

    J --> M[Bottom Nav]
    M --> N[Routine Management]
    M --> O[Statistics Dashboard]
    M --> P[Developer Info]

    J --> Q[Top Bar]
    Q --> R[Profile]
    Q --> S[Notifications]
    R --> T[Sign Out]
    T --> D
```

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 1.9 |
| UI Framework | Android Views + Material Design 3 |
| Architecture | MVVM with ViewModels and LiveData |
| Navigation | AndroidX Navigation Component |
| Networking | Ktor Client (CIO engine) |
| Serialization | Kotlinx Serialization (JSON) |
| Backend | Supabase (PostgreSQL + GoTrue Auth) |
| Supabase SDK | supabase-kt (GoTrue, Postgrest, Realtime, Storage) |
| Notifications | AlarmManager + BroadcastReceiver |
| Async | Kotlin Coroutines |
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 34 (Android 14) |

---

## Project Structure

```
WorCowt/
├── app/
│   └── src/main/
│       ├── java/com/worcowt/app/
│       │   ├── WorCowTApp.kt                  # Application class
│       │   ├── data/
│       │   │   ├── models/                     # Data classes (User, Routine, TaskLog, Statistics)
│       │   │   ├── repository/                 # CRUD repositories for each table
│       │   │   └── supabase/SupabaseManager.kt # Supabase client singleton
│       │   ├── engine/
│       │   │   ├── RoutineGenerator.kt         # Daily schedule algorithm
│       │   │   ├── XPCalculator.kt             # XP award/penalty logic
│       │   │   └── StreakManager.kt            # Streak continuation/break logic
│       │   ├── notifications/
│       │   │   ├── NotificationHelper.kt       # Alarm scheduling
│       │   │   ├── ReminderReceiver.kt         # BroadcastReceiver for alarms
│       │   │   └── BootReceiver.kt             # Reschedule after reboot
│       │   ├── ui/
│       │   │   ├── splash/                     # Splash screen
│       │   │   ├── landing/                    # Landing page with mascot
│       │   │   ├── auth/                       # Login / Sign Up
│       │   │   ├── onboarding/                 # 10-step questionnaire
│       │   │   ├── main/                       # MainActivity + bottom nav
│       │   │   ├── home/                       # Timeline view
│       │   │   ├── routine/                    # Routine management
│       │   │   ├── statistics/                 # Stats dashboard
│       │   │   ├── profile/                    # User profile
│       │   │   ├── developer/                  # About developer
│       │   │   └── workout/                    # Workout motivation
│       │   └── utils/
│       │       ├── Constants.kt                # App-wide constants
│       │       └── Extensions.kt               # Kotlin extensions
│       ├── res/
│       │   ├── drawable/                       # Mascot assets, icons
│       │   ├── layout/                         # XML layouts
│       │   ├── navigation/                     # Navigation graph
│       │   ├── menu/                           # Bottom nav menu
│       │   └── values/                         # Colors, strings, themes
│       └── AndroidManifest.xml
├── screenshots/                                # App screenshots
├── WorCowt-v0.1-debug.apk                     # Pre-built debug APK
├── build.gradle.kts                            # Project-level Gradle
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

---

## Installation and Setup

### Prerequisites

- Android Studio Ladybug (2024.3+) or later
- JDK 17 or higher
- Android SDK with API 34 platform and build tools

### Steps

1. **Clone the repository**

```bash
git clone https://github.com/to-sayana/WorCowt.git
cd WorCowt
```

2. **Open in Android Studio**

   File > Open > select the `WorCowt/` directory. Gradle will sync automatically.

3. **Configure local.properties**

   Ensure `local.properties` contains the correct SDK path:
   ```
   sdk.dir=/path/to/your/Android/Sdk
   ```

4. **Build the APK**

```bash
./gradlew assembleDebug
```

   Output: `app/build/outputs/apk/debug/app-debug.apk`

5. **Run on Emulator or Device**

   - Create an AVD (Pixel 7, API 34) via Device Manager, or
   - Connect a physical Android device with USB debugging enabled
   - Click **Run** in Android Studio

### APK Download

A pre-built debug APK is available in the repository root:

[**WorCowt-v0.1-debug.apk**](WorCowt-v0.1-debug.apk)

---

## XP and Level System

| Level | XP Required | Title |
|-------|-------------|-------|
| 1 | 0 | Newborn Calf |
| 2 | 200 | Growing Calf |
| 3 | 500 | Strong Bull |
| 4 | 900 | Mighty Ox |
| 5 | 1,500 | Legendary Moo |

| Action | XP |
|--------|-----|
| Complete a task | +10 |
| Complete all daily tasks | +50 |
| 7-day streak bonus | +100 |
| Snooze a task | -5 |
| Skip a task | -3 |

---

## Database

- **Provider**: [Supabase](https://supabase.com) (managed PostgreSQL)
- **Authentication**: Email/Password via Supabase GoTrue
- **Security**: Row-Level Security (RLS) policies ensure each user can only read/write their own rows
- **Tables**: `users`, `routines`, `task_logs`, `statistics` (see ER Diagram above)

---

## Version History

| Version | Date | Notes |
|---------|------|-------|
| v0.1-debug | March 2026 | Initial release -- core features: auth, onboarding, routine generation, timeline, XP/streaks, notifications, statistics |

---

## Authors

Developed as a personal productivity project.

---

## License

This project is for educational and personal use.
